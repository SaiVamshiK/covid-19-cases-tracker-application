package com.example.covid_19trackerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class EachCountryDetails extends AppCompatActivity {
    private int positioncountry;
    TextView tvCases,tvRecovered,tvCritical,tvActive,tvTodayCases,tvTotalDeaths,tvTodayDeaths,tvCountryName;
    PieChart pieChart;
    ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_country_details);

        Intent intent=getIntent();
        positioncountry=intent.getIntExtra("position",0);

        getSupportActionBar().setTitle("Details of "+AffectedNations.eachcountryList.get(positioncountry).getCountry());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvCases=findViewById(R.id.tv1cases);
        tvRecovered=findViewById(R.id.tv1Recovered);
        tvCritical=findViewById(R.id.tv1Critical);
        tvActive=findViewById(R.id.tv1Active);
        tvTodayCases=findViewById(R.id.tv1TodayCases);
        tvTotalDeaths=findViewById(R.id.tv1TotalDeaths);
        tvTodayDeaths=findViewById(R.id.tv1TodayDeaths);
        tvCountryName=findViewById(R.id.tv1CountryName);

        pieChart=findViewById(R.id.piechart1);
        scrollView=findViewById(R.id.scrollstats1);

        tvCountryName.setText(AffectedNations.eachcountryList.get(positioncountry).getCountry());
        tvCases.setText(AffectedNations.eachcountryList.get(positioncountry).getCases());
        tvRecovered.setText(AffectedNations.eachcountryList.get(positioncountry).getRecovered());
        tvCritical.setText(AffectedNations.eachcountryList.get(positioncountry).getCritical());
        tvActive.setText(AffectedNations.eachcountryList.get(positioncountry).getActive());
        tvTodayCases.setText(AffectedNations.eachcountryList.get(positioncountry).getTodaycases());
        tvTotalDeaths.setText(AffectedNations.eachcountryList.get(positioncountry).getDeaths());
        tvTodayDeaths.setText(AffectedNations.eachcountryList.get(positioncountry).getTodaydeaths());

        pieChart.addPieSlice(new PieModel("Cases",Integer.parseInt(tvCases.getText().toString()), Color.parseColor("#FFA726")));
        pieChart.addPieSlice(new PieModel("Recovered",Integer.parseInt(tvRecovered.getText().toString()), Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(tvTotalDeaths.getText().toString()), Color.parseColor("#EF5350")));
        pieChart.addPieSlice(new PieModel("Active",Integer.parseInt(tvActive.getText().toString()), Color.parseColor("#29B6F6")));
        pieChart.startAnimation();


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void gotrackstates(View view)
    {

    }
}
