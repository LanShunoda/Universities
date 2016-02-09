package com.plorial.universities;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Arrays;

/**
 * Created by plorial on 07.02.16.
 */
public class UniversitiesActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    private int universitiesCount;
    private final int TAG_KEY = R.id.tableLayout;
    public static final String EXTRA_KEY_UNIVERSITY = "University_id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_universities);

        tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        fillTable();
    }

    private void fillTable() {
        Intent intent = getIntent();
        String idOfUniversities = intent.getStringExtra(TabFragment.ID_OF_UNIVERSITIES);
        String[] ids = idOfUniversities.split(", ");
        ids[0] = ids[0].substring(1,ids[0].length());
        ids[ids.length - 1] = ids[ids.length - 1].substring(0, ids[ids.length - 1].length() - 1);
        universitiesCount = ids.length;
        DataBaseHelper dbHelper = DataBaseHelper.getInstance(this);
        SQLiteDatabase db = dbHelper.openDataBase();
        Cursor cursor = db.query(UniversitiesTable.TABLE_NAME.toString(), new String[]{UniversitiesTable.NAME.toString(), UniversitiesTable.CITY.toString()}, null, null, null, null, null);

        Display d = getWindowManager().getDefaultDisplay();
        int screenDivider = d.getWidth()/4;
        for(int i = 0; i < universitiesCount; i++) {

            cursor.moveToPosition(Integer.parseInt(ids[i]) - 1);

            TableRow tableRow = new TableRow(this);
            TextView tvUniversityName = new TextView(this);
            TextView tvUniversityCity = new TextView(this);

            tableRow.setPadding(5, 10, 5, 10);
            tvUniversityName.setPadding(0,0,5,0);

            tvUniversityName.setTextSize(16);
            tvUniversityCity.setTextSize(16);

            tableRow.setTag(TAG_KEY, new Integer(Integer.parseInt(ids[i])));
            tableRow.setOnClickListener(new TableRowClickListener());
            tableRow.setClickable(true);

            tvUniversityName.setWidth(screenDivider*3 - 15);
            tvUniversityCity.setWidth(screenDivider);

            tvUniversityName.setText(cursor.getString(cursor.getColumnIndex(UniversitiesTable.NAME.toString())));
            tvUniversityCity.setText(cursor.getString(cursor.getColumnIndex(UniversitiesTable.CITY.toString())));

            tableRow.addView(tvUniversityName);
            tableRow.addView(tvUniversityCity);

            tableLayout.addView(tableRow);
        }
    }

    private class TableRowClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            TableRow tr = (TableRow) v;
            int i = (int) tr.getTag(TAG_KEY);
            Intent intent = new Intent(v.getContext(), UniversityActivity.class);
            intent.putExtra(EXTRA_KEY_UNIVERSITY,String.valueOf(i));
            startActivity(intent);
        }
    }
}
