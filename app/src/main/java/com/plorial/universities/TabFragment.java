package com.plorial.universities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by plorial on 05.02.16.
 */
public class TabFragment extends ListFragment {

    private String table;
    private String column;
    private Cursor cursor;

    @SuppressLint("ValidFragment")
    public TabFragment(String table, String column) {
        super();
        this.table = table;
        this.column = column;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DataBaseHelper dbHelper = DataBaseHelper.getInstance(getContext());
        SQLiteDatabase db = dbHelper.openDataBase();
        cursor = db.query(table, new String[]{column,"idOfUniversity"}, null, null, null, null, null);
        if(!cursor.isFirst())
            cursor.moveToFirst();
        ArrayList<String> listNames = new ArrayList<>();

        while (!cursor.isAfterLast()) {
            listNames.add(cursor.getString(cursor.getColumnIndex(column)));
            cursor.moveToNext();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, listNames);
        setListAdapter(adapter);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        cursor.moveToPosition(position);
        String idOfUniversities = cursor.getString(cursor.getColumnIndex("idOfUniversity"));
        Intent intent = new Intent(getContext(),UniversitiesActivity.class);
        intent.putExtra(BaseActivity.ID_OF_UNIVERSITIES,idOfUniversities);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
    }
}
