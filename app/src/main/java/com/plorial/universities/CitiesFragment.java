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
public class CitiesFragment extends TabFragment {

    public CitiesFragment() {
        super("Cities", "City");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
