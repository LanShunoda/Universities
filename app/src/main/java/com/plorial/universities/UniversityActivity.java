package com.plorial.universities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by plorial on 09.02.16.
 */
public class UniversityActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university);
        SQLiteDatabase db = DataBaseHelper.getInstance(this).openDataBase();
        Cursor cursor = db.query("UNIVERSITIES", new String[]{"NAME", "CITY", "YEAR", "STATUS", "ACCREDITATION",
                "DOCUMENT", "FORM_OF_EDUCATION", "QUALIFICATION", "ADDRESS", "TELEPHONE",
                "TELEPHONE_OF_SELECTION_COMMITTEE", "SITE", "URL", "TRAINING_AREAS", "FACULTIES"}, null, null, null, null, null);
        Intent intent = getIntent();
        String idOfUniversity = intent.getStringExtra(UniversitiesActivity.EXTRA_KEY_UNIVERSITY);

        cursor.moveToPosition(Integer.parseInt(idOfUniversity) - 1);
        TextView name = (TextView) findViewById(R.id.tvName);
        name.setText(cursor.getString(cursor.getColumnIndex("NAME")));
        TextView city = (TextView) findViewById(R.id.tvCity);
        city.setText(cursor.getString(cursor.getColumnIndex("CITY")));
        TextView year = (TextView) findViewById(R.id.tvYear);
        year.setText(cursor.getString(cursor.getColumnIndex("YEAR")));
        TextView status = (TextView) findViewById(R.id.tvStatus);
        status.setText(cursor.getString(cursor.getColumnIndex("STATUS")));
        TextView acreditation = (TextView) findViewById(R.id.tvAcreditation);
        acreditation.setText(cursor.getString(cursor.getColumnIndex("ACCREDITATION")));
        TextView document = (TextView) findViewById(R.id.tvDocument);
        document.setText(cursor.getString(cursor.getColumnIndex("DOCUMENT")));
        TextView form = (TextView) findViewById(R.id.tvFormOfEducation);
        form.setText(cursor.getString(cursor.getColumnIndex("FORM_OF_EDUCATION")));
        TextView qualification = (TextView) findViewById(R.id.tvQualification);
        qualification.setText(cursor.getString(cursor.getColumnIndex("QUALIFICATION")));
        TextView address = (TextView) findViewById(R.id.tvAddress);
        address.setText(cursor.getString(cursor.getColumnIndex("ADDRESS")));
        TextView telephone = (TextView) findViewById(R.id.tvTelephone);
        telephone.setText(cursor.getString(cursor.getColumnIndex("TELEPHONE")));
        TextView telephoneOfSelectionCommittee = (TextView) findViewById(R.id.tvTelephoneOfSelectionCommittee);
        telephoneOfSelectionCommittee.setText(cursor.getString(cursor.getColumnIndex("TELEPHONE_OF_SELECTION_COMMITTEE")));
        TextView site = (TextView) findViewById(R.id.tvSite);
        site.setText(cursor.getString(cursor.getColumnIndex("SITE")));
    }
}
