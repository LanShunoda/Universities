package com.plorial.universities;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Arrays;

/**
 * Created by plorial on 07.02.16.
 */
public class UniversitiesActivity extends BaseActivity {

    private ListView listView;
    private int universitiesCount;
    public static final String EXTRA_KEY_UNIVERSITY = "University_id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_universities);
        setActionBarHomeButton();
        listView = (ListView) findViewById(R.id.listView);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = (Item) parent.getItemAtPosition(position);
                int i = item.getTag();
                Intent intent = new Intent(view.getContext(), UniversityActivity.class);
                intent.putExtra(EXTRA_KEY_UNIVERSITY,String.valueOf(i));
                startActivity(intent);
            }
        });
        fillTable();
        MobileAds.initialize(this, getString(R.string.ads_app_id));
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void fillTable() {
        Intent intent = getIntent();
        String idOfUniversities = intent.getStringExtra(BaseActivity.ID_OF_UNIVERSITIES);
        String[] ids = idOfUniversities.split(", ");
        ids[0] = ids[0].substring(1,ids[0].length());
        ids[ids.length - 1] = ids[ids.length - 1].substring(0, ids[ids.length - 1].length() - 1);
        universitiesCount = ids.length;
        DataBaseHelper dbHelper = DataBaseHelper.getInstance(this);
        SQLiteDatabase db = dbHelper.openDataBase();
        Cursor cursor = db.query(UniversitiesTable.TABLE_NAME.toString(), new String[]{UniversitiesTable.NAME.toString(), UniversitiesTable.CITY.toString()}, null, null, null, null, null);
        ItemAdapter adapter = new ItemAdapter(this, R.layout.item);
        listView.setAdapter(adapter);

        for(int i = 0; i < universitiesCount; i++) {

            cursor.moveToPosition(Integer.parseInt(ids[i]) - 1);
            Item item = new Item(cursor.getString(cursor.getColumnIndex(UniversitiesTable.NAME.toString())),cursor.getString(cursor.getColumnIndex(UniversitiesTable.CITY.toString())));

            item.setTag(Integer.parseInt(ids[i]));
            adapter.add(item);
        }
        cursor.close();
    }
}
