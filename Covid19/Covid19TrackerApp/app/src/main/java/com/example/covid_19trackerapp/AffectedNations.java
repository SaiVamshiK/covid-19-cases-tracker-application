package com.example.covid_19trackerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AffectedNations extends AppCompatActivity {
    EditText edtSearch;
    ListView listView;
    SimpleArcLoader simpleArcLoader;

    public static List<eachcountry> eachcountryList=new ArrayList<>();
    eachcountry eachcountryobj;
    adaptercustom adaptercustomobj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected_nations);

        edtSearch=findViewById(R.id.edtsearch);
        listView=findViewById(R.id.listview);
        simpleArcLoader=findViewById(R.id.loader1);

        getSupportActionBar().setTitle("Affected Nations");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        fetchData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(),EachCountryDetails.class).putExtra("position",position));
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adaptercustomobj.getFilter().filter(s);
                    adaptercustomobj.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchData() {
        String url="https://corona.lmao.ninja/v2/countries";
        simpleArcLoader.start();
        StringRequest request=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            for(int i=0;i<jsonArray.length();i++){

                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                String countryname=jsonObject.getString("country");
                                String cases=jsonObject.getString("cases");
                                String todaycases=jsonObject.getString("todayCases");
                                String deaths=jsonObject.getString("deaths");
                                String todaydeaths=jsonObject.getString("todayDeaths");
                                String recovered=jsonObject.getString("recovered");
                                String active=jsonObject.getString("active");
                                String critical=jsonObject.getString("critical");
                                JSONObject object=jsonObject.getJSONObject("countryInfo");
                                String flagurl=object.getString("flag");

                                eachcountryobj=new eachcountry(flagurl,countryname,cases,todaycases,deaths,todaydeaths,recovered,active,critical);
                                eachcountryList.add(eachcountryobj);

                            }

                            adaptercustomobj=new adaptercustom(AffectedNations.this,eachcountryList);
                            listView.setAdapter(adaptercustomobj);
                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                Toast.makeText(AffectedNations.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
}
