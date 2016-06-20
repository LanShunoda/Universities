package com.plorial.universities;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by plorial on 6/20/16.
 */
public class ItemAdapter extends ArrayAdapter<Item> {

    private final Context context;
    private final int resource;

    public ItemAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        row = inflater.inflate(resource, parent, false);
        Item item = getItem(position);
        ((TextView)row.findViewById(R.id.tvCityName)).setText(item.city);
        ((TextView)row.findViewById(R.id.tvUnivName)).setText(item.name);

        return row;
    }
}
