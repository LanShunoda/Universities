package com.plorial.universities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.style.TextAppearance_Large;
import static android.R.style.TextAppearance_Medium;

/**
 * Created by plorial on 09.02.16.
 */
public class UniversityActivity extends BaseActivity {

    private String idOfUniversity;
    private Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university);
        setActionBarHomeButton();
        button = (Button) findViewById(R.id.bAddToFavourites);

        SQLiteDatabase db = DataBaseHelper.getInstance(this).openDataBase();

        Cursor cursor = db.query(UniversitiesTable.TABLE_NAME.toString(), UniversitiesTable.TABLE_NAME.getTableColumns(), null, null, null, null, null);
        Intent intent = getIntent();
        idOfUniversity = intent.getStringExtra(UniversitiesActivity.EXTRA_KEY_UNIVERSITY);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            Set<String> idesUniversities = getSharedPreferences(BaseActivity.FAVOURITE_UNIVERSITIES, MODE_PRIVATE).getStringSet(BaseActivity.FAVOURITE_UNIVERSITIES, null);
            if(idesUniversities != null && idesUniversities.contains(idOfUniversity)){
                button.setText(R.string.bRemoveFromFavourites);
            }
        }

        cursor.moveToPosition(Integer.parseInt(idOfUniversity) - 1);

        fillTable(cursor);
        addTrainingAreas(cursor);
        addFaculties(cursor);
    }

    private void addFaculties(Cursor cursor) {
        String faculties = cursor.getString(cursor.getColumnIndex(UniversitiesTable.FACULTIES.toString()));
        LinearLayout linearLayoutFaculties = (LinearLayout) findViewById(R.id.lvFaculties);
        if(faculties.equals("{}") || faculties.equals("{Специальности=[]}")) {
            TextView header = new TextView(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                header.setTextAppearance(TextAppearance_Medium);
            }else {
                header.setTextAppearance(this, TextAppearance_Medium);
            }
            header.setText(R.string.noFaculties);
            linearLayoutFaculties.addView(header);
            return;
        }
        Map<String,String[]> mapFaculties = parseFaculties(faculties);

        for (Map.Entry<String,String[]> entry : mapFaculties.entrySet()){
            TextView header = new TextView(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                header.setTextAppearance(TextAppearance_Large);
            }else {
                header.setTextAppearance(this, TextAppearance_Large);
            }
            header.setText(entry.getKey());
            linearLayoutFaculties.addView(header);
            LinearLayout childrenLayout = new LinearLayout(this);
            childrenLayout.setOrientation(LinearLayout.VERTICAL);
            childrenLayout.setPadding(20,0, 0, 0);
            String[] values = entry.getValue();
            for(String s : values){
                TextView child = new TextView(this);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    child.setTextAppearance(TextAppearance_Medium);
                }else {
                    child.setTextAppearance(this, TextAppearance_Medium);
                }
                child.setText("- " + s);
                childrenLayout.addView(child);
            }
            linearLayoutFaculties.addView(childrenLayout);
        }
        cursor.close();
    }

    private Map<String, String[]> parseFaculties(String s) {
        HashMap<String, String[]> result = new HashMap<>();
        String[] splited = s.split("\\]");
        for(int i = 0; i < splited.length - 1; i++){
            String[] strings = splited[i].split("\\[");
            String key = strings[0].substring(1,strings[0].length()-1);
            String[] values;
            if(strings.length > 1) {
                values = strings[1].split("\\;\\,");
            }
            else {
                values = new String[]{};
            }
            result.put(key, values);
        }
        return result;
    }

    private void fillTable(Cursor cursor) {
        TextView name = (TextView) findViewById(R.id.tvName);
        name.setText(cursor.getString(cursor.getColumnIndex(UniversitiesTable.NAME.toString())));
        TextView city = (TextView) findViewById(R.id.tvCity);
        city.setText(cursor.getString(cursor.getColumnIndex(UniversitiesTable.CITY.toString())));
        TextView year = (TextView) findViewById(R.id.tvYear);
        year.setText(cursor.getString(cursor.getColumnIndex(UniversitiesTable.YEAR.toString())));
        TextView status = (TextView) findViewById(R.id.tvStatus);
        status.setText(cursor.getString(cursor.getColumnIndex(UniversitiesTable.STATUS.toString())));
        TextView accreditation = (TextView) findViewById(R.id.tvAccreditation);
        accreditation.setText(cursor.getString(cursor.getColumnIndex(UniversitiesTable.ACCREDITATION.toString())));
        TextView document = (TextView) findViewById(R.id.tvDocument);
        document.setText(cursor.getString(cursor.getColumnIndex(UniversitiesTable.DOCUMENT.toString())));
        TextView form = (TextView) findViewById(R.id.tvFormOfEducation);
        form.setText(cursor.getString(cursor.getColumnIndex(UniversitiesTable.FORM_OF_EDUCATION.toString())));
        TextView qualification = (TextView) findViewById(R.id.tvQualification);
        qualification.setText(cursor.getString(cursor.getColumnIndex(UniversitiesTable.QUALIFICATION.toString())));
        TextView address = (TextView) findViewById(R.id.tvAddress);
        address.setText(cursor.getString(cursor.getColumnIndex(UniversitiesTable.ADDRESS.toString())));
        TextView telephone = (TextView) findViewById(R.id.tvTelephone);
        telephone.setText(cutTelephone(cursor.getString(cursor.getColumnIndex(UniversitiesTable.TELEPHONE.toString()))));
        TextView telephoneOfSelectionCommittee = (TextView) findViewById(R.id.tvTelephoneOfSelectionCommittee);
        telephoneOfSelectionCommittee.setText(cutTelephone(cursor.getString(cursor.getColumnIndex(UniversitiesTable.TELEPHONE_OF_SELECTION_COMMITTEE.toString()))));
        TextView site = (TextView) findViewById(R.id.tvSite);
        site.setText(cursor.getString(cursor.getColumnIndex(UniversitiesTable.SITE.toString())));
    }

    private void addTrainingAreas(Cursor cursor) {
        LinearLayout trainingAreas = (LinearLayout) findViewById(R.id.lvTrainingAreas);
        List<String> listTrainingAreas = parseTrainingAreas(cursor.getString(cursor.getColumnIndex(UniversitiesTable.TRAINING_AREAS.toString())));
        for(String s : listTrainingAreas){
            TextView tv = new TextView(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tv.setTextAppearance(TextAppearance_Medium);
            }else {
                tv.setTextAppearance(this, TextAppearance_Medium);
            }
            tv.setText("- " + s);
            trainingAreas.addView(tv);
        }
    }

    private String cutTelephone(String s){
        if(s.length() > 0)
        return s.substring(0, s.length()-9);
        return s;
    }

    private List parseTrainingAreas(String s){
        Matcher matcher = Pattern.compile("\\((.*?)\\)").matcher(s); //O_o magic
        String res = "";
        ArrayList<String> list = new ArrayList<>();
        while (matcher.find()) {
            res = matcher.group();
            String trainArea = res.substring(1, res.length() - 1);
            list.add(trainArea);
        }
        return list;
    }

    public void addToFavourites(View view){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            SharedPreferences preferences = getSharedPreferences(BaseActivity.FAVOURITE_UNIVERSITIES, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            if (button.getText().toString().equals(getString(R.string.bAddToFavourites))) {
                Set<String> idesUniversities = preferences.getStringSet(BaseActivity.FAVOURITE_UNIVERSITIES, new HashSet<String>());
                idesUniversities.add(idOfUniversity);
                editor.putStringSet(BaseActivity.FAVOURITE_UNIVERSITIES, idesUniversities);
                button.setText(R.string.bRemoveFromFavourites);
                editor.commit();
                return;
            }else if (button.getText().toString().equals(getString(R.string.bRemoveFromFavourites))){
                Set<String> idesUniversities = preferences.getStringSet(BaseActivity.FAVOURITE_UNIVERSITIES, null);
                idesUniversities.remove(idOfUniversity);
                editor.putStringSet(BaseActivity.FAVOURITE_UNIVERSITIES, idesUniversities);
                button.setText(R.string.bAddToFavourites);
                editor.commit();
                return;
            }
        }else{
            Toast.makeText(this,getString(R.string.bAddToFavouritesError),Toast.LENGTH_LONG).show();
        }
    }
}
