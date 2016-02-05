package com.plorial.universities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by plorial on 04.02.16.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    Context context;
    private static final String DB_NAME = "universities";
//    private static String DB_PATH = "/data/data/com.plorial.universities/databases/";
    private SQLiteDatabase dataBase;


    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
        createDataBase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createDataBase() {
        Log.d("LOG","createDataBase()");
        File DB_PATH = context.getExternalCacheDir();
        DB_PATH.mkdirs();
        File db = new File(DB_PATH, DB_NAME);
        if (!db.exists()) {
            try {
                db.createNewFile();
                copyFromZipFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void copyFromZipFile() throws IOException{
        InputStream is = context.getResources().openRawResource(R.raw.universities_db);
        File outFile = new File(context.getExternalCacheDir() ,DB_NAME);
        OutputStream myOutput = new FileOutputStream(outFile.getAbsolutePath());
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is));
        try {
            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int count;

                while ((count = zis.read(buffer)) != -1) {
                    baos.write(buffer, 0, count);
                }
                baos.writeTo(myOutput);
            }
        } finally {
            zis.close();
            myOutput.flush();
            myOutput.close();
            is.close();
        }
    }

    public SQLiteDatabase openDataBase() {
        File DB_PATH = context.getExternalCacheDir();
        File dbFile = new File (DB_PATH,DB_NAME);
        dataBase = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        return dataBase;
    }
}