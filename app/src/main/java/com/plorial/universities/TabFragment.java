package com.plorial.universities;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by plorial on 05.02.16.
 */
public class TabFragment extends ListFragment {

    private String table;
    private String column;

    @SuppressLint("ValidFragment")
    public TabFragment(String table, String column) {
        super();
        this.table = table;
        this.column = column;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DataBaseHelper dbHelper = new DataBaseHelper(getContext());
        SQLiteDatabase db = dbHelper.openDataBase();
        Cursor cursor = db.query(table, new String[]{column}, null, null, null, null, null);
        if(!cursor.isFirst())
            cursor.moveToFirst();
        ArrayList<String> list = new ArrayList<>();

        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(cursor.getColumnIndex(column)));
            cursor.moveToNext();
        }

        cursor.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, list);
        setListAdapter(adapter);
    }
}
