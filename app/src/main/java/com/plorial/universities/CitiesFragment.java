package com.plorial.universities;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by plorial on 04.02.16.
 */
public class CitiesFragment extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DataBaseHelper dbHelper = new DataBaseHelper(getContext());
        SQLiteDatabase db = dbHelper.openDataBase();
        Cursor cursor = db.query("Cities", new String[]{"City"}, null, null, null, null, null);
        if(!cursor.isFirst())
        cursor.moveToFirst();
        ArrayList<String> cities = new ArrayList<>();
        Log.d("LOG", "cursors" + cursor.getCount() + " cursor current " + cursor.getPosition() + " columns " + cursor.getColumnCount());
        while (!cursor.isAfterLast()) {
            cities.add(cursor.getString(cursor.getColumnIndex("City")));
            cursor.moveToNext();
        }

        cursor.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, cities);
        setListAdapter(adapter);
    }

}
