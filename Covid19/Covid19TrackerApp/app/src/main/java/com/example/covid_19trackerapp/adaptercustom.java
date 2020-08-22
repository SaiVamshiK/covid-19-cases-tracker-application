package com.example.covid_19trackerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class adaptercustom extends ArrayAdapter<eachcountry> {

    private Context context;
    private List<eachcountry> eachcountryList;
    private List<eachcountry> eachcountryListfiltered;

    public adaptercustom(Context context, List<eachcountry> eachcountryList) {
        super(context, R.layout.listcustomitem,eachcountryList);
        this.context=context;
        this.eachcountryList=eachcountryList;
        this.eachcountryListfiltered=eachcountryList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.listcustomitem,null,true);
        TextView tvCountryName=view.findViewById(R.id.tvCountryNane);
        ImageView imageView=view.findViewById(R.id.imageflag);
        tvCountryName.setText(eachcountryListfiltered.get(position).getCountry());
        Glide.with(context).load(eachcountryListfiltered.get(position).getFlag()).into(imageView);
        return view;
    }

    @Override
    public int getCount() {

        return eachcountryListfiltered.size();
    }

    @Nullable
    @Override
    public eachcountry getItem(int position) {
        return eachcountryListfiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {


        Filter filter=new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults=new FilterResults();
                if(constraint==null||constraint.length()==0)
                {
                    filterResults.count=eachcountryList.size();
                    filterResults.values=eachcountryList;
                }else{
                    List<eachcountry> resultsmodel =new ArrayList<>();
                    String searchstr=constraint.toString().toLowerCase();
                    for (eachcountry itemmodel:eachcountryList){
                        if(itemmodel.getCountry().toLowerCase().contains(searchstr)){
                            resultsmodel.add(itemmodel);
                        }
                        filterResults.count=resultsmodel.size();
                        filterResults.values=resultsmodel;
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                eachcountryListfiltered=(List<eachcountry>)results.values;
                AffectedNations.eachcountryList=(List<eachcountry>)results.values;
                notifyDataSetChanged();
            }
        };

        return filter;
    }
}
