package com.plorial.universities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by plorial on 15.02.16.
 */
public class BaseActivity extends AppCompatActivity {

    public static final String ID_OF_UNIVERSITIES = "idOfUniversities";
    public static final String FAVOURITE_UNIVERSITIES = "FAVOURITE_UNIVERSITIES";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {           
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_favourites:
                startUniversitiesActivityWithFavourites();
                return true;
        }       
        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void startUniversitiesActivityWithFavourites() {
        Set<String> idesUniversities = getSharedPreferences(BaseActivity.FAVOURITE_UNIVERSITIES, MODE_PRIVATE).getStringSet(BaseActivity.FAVOURITE_UNIVERSITIES, null);
        if (idesUniversities != null && !idesUniversities.isEmpty()) {
            Intent intent = new Intent(this, UniversitiesActivity.class);
            intent.putExtra(ID_OF_UNIVERSITIES, idesUniversities.toString());
            startActivity(intent);
        }
        else
            Toast.makeText(this,"You dont have favourites",Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    protected void setActionBarHomeButton(){
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_home_white_48dp);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
