package com.example.ahmed.movieapp.data_base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ahmed.movieapp.Movie;

import java.util.ArrayList;

/**
 * Created by ahmed on 11/04/16.
 */
public class Movies_Dp_Helper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "movies1.db";
    private static final int DATABASE_VERSION = 6;

    public Movies_Dp_Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + Movies_info.MOVIES.TABLE_NAME + " (" +
                Movies_info.MOVIES._ID + " INTEGER PRIMARY KEY," +
                Movies_info.MOVIES.COLUMN_MOVIES_ID + " TEXT NOT NULL," +
                Movies_info.MOVIES.POSTER_PATH + " TEXT NOT NULL, " +
                Movies_info.MOVIES.ADULT + " TEXT NOT NULL, " +
                Movies_info.MOVIES.OVERVIEW + " TEXT NOT NULL, " +
                Movies_info.MOVIES.RELEASE_DATE + " TEXT NOT NULL, " +
                Movies_info.MOVIES.ORIGINAL_TITLE + " TEXT NOT NULL, " +
                Movies_info.MOVIES.TITLE + " TEXT , " +
                Movies_info.MOVIES.POPULARITY + " TEXT NOT NULL, " +
                Movies_info.MOVIES.VOTE_COUNT + " TEXT NOT NULL, " +
                Movies_info.MOVIES.VIDEO + " TEXT NOT NULL, " +
                Movies_info.MOVIES.VOTE_AVERAGE + "  TEXT NOT NULL, " +
                Movies_info.MOVIES.BACKDROP_PATH + " TEXT NOT NULL " +
                " );";
        Log.v("SQL_CREATE_MOVIE_TABLE", SQL_CREATE_MOVIE_TABLE);

        final String SQL_CREATE_FAVOURIT_TABLE = "CREATE TABLE " + Movies_info.Favourite.TABLE_NAME + " (" +
                Movies_info.Favourite.COLUMN_MOVIES_ID + " INTEGER NOT NULL," +
                Movies_info.Favourite.POSTER_PATH + " TEXT NOT NULL, " +
                Movies_info.Favourite.ADULT + " TEXT NOT NULL, " +
                Movies_info.Favourite.OVERVIEW + " TEXT NOT NULL, " +
                Movies_info.Favourite.RELEASE_DATE + " TEXT NOT NULL, " +
                Movies_info.Favourite.ORIGINAL_TITLE + " TEXT NOT NULL, " +
                Movies_info.Favourite.TITLE + " TEXT, " +
                Movies_info.Favourite.POPULARITY + " TEXT NOT NULL, " +
                Movies_info.Favourite.VOTE_COUNT + " TEXT NOT NULL, " +
                Movies_info.Favourite.VIDEO + " TEXT NOT NULL, " +
                Movies_info.Favourite.VOTE_AVERAGE + "  TEXT NOT NULL, " +
                Movies_info.Favourite.BACKDROP_PATH + " TEXT NOT NULL " +
                " );";
        Log.v("SQL_FAVOURIT_TABLE", SQL_CREATE_FAVOURIT_TABLE);

        db.execSQL(SQL_CREATE_MOVIE_TABLE);
        db.execSQL(SQL_CREATE_FAVOURIT_TABLE);
        Log.v("database", "created successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Movies_info.MOVIES.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Movies_info.Favourite.TABLE_NAME);
        onCreate(db);

    }

    public void addcat(Movie m) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Movies_info.MOVIES.COLUMN_MOVIES_ID, m.getId());
        values.put(Movies_info.MOVIES.POSTER_PATH, m.getPoster_path()); //poster
        values.put(Movies_info.MOVIES.ADULT, m.getAdult()); // adult
        values.put(Movies_info.MOVIES.OVERVIEW, m.getOverview());
        values.put(Movies_info.MOVIES.RELEASE_DATE, m.getRelease_date());
        values.put(Movies_info.MOVIES.ORIGINAL_TITLE, m.getOriginal_title());
        values.put(Movies_info.MOVIES.TITLE, m.getTitle());
        values.put(Movies_info.MOVIES.POPULARITY, m.getPopularity());
        values.put(Movies_info.MOVIES.VOTE_COUNT, m.getVote_count());
        values.put(Movies_info.MOVIES.VIDEO, m.getVideo());
        values.put(Movies_info.MOVIES.VOTE_AVERAGE, m.getVote_average());
        values.put(Movies_info.MOVIES.BACKDROP_PATH, m.getBackdrop_path());
        // Inserting Row
        long rowid = db.insert(Movies_info.MOVIES.TABLE_NAME, null, values);
        System.out.println("Insertion status : " + rowid);
        // Toast.makeText(Context,"hello"+rowid,Toast.LENGTH_LONG).show();
        db.close(); // Closing database connection
    }

    public void addFavourite(Movie m) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Movies_info.Favourite.COLUMN_MOVIES_ID, m.getId());
        values.put(Movies_info.Favourite.POSTER_PATH, m.getPoster_path()); //poster
        values.put(Movies_info.Favourite.ADULT, m.getAdult()); // adult
        values.put(Movies_info.Favourite.OVERVIEW, m.getOverview());
        values.put(Movies_info.Favourite.RELEASE_DATE, m.getRelease_date());
        values.put(Movies_info.Favourite.ORIGINAL_TITLE, m.getOriginal_title());
        values.put(Movies_info.Favourite.TITLE, m.getTitle());
        values.put(Movies_info.Favourite.POPULARITY, m.getPopularity());
        values.put(Movies_info.Favourite.VOTE_COUNT, m.getVote_count());
        values.put(Movies_info.Favourite.VIDEO, m.getVideo());
        values.put(Movies_info.Favourite.VOTE_AVERAGE, m.getVote_average());
        values.put(Movies_info.Favourite.BACKDROP_PATH, m.getBackdrop_path());
        // Inserting Row
        long rowid = db.insert(Movies_info.Favourite.TABLE_NAME, null, values);
        System.out.println("Insertion status : " + rowid);

        // Toast.makeText(Context,"hello"+rowid,Toast.LENGTH_LONG).show();
        db.close(); // Closing database connection
    }

    public ArrayList<Movie> getAlldatafroMovies() {
        ArrayList<Movie> catList = new ArrayList<Movie>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Movies_info.MOVIES.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int x = cursor.getColumnCount();

        Log.v("data", "" + x);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Movie contact = new Movie();
                contact.setId((cursor.getString(1)));
                contact.setPoster_path(cursor.getString(2));
                contact.setAdult(cursor.getString(3));
                contact.setOverview(cursor.getString(4));
                contact.setRelease_date(cursor.getString(5));
                contact.setOriginal_title(cursor.getString(6));
                contact.setTitle(cursor.getString(7));
                contact.setPopularity(cursor.getString(8));
                contact.setVote_count(cursor.getString(9));
                contact.setVideo(cursor.getString(10));
                contact.setVote_average(cursor.getString(11));
                contact.setBackdrop_path(cursor.getString(12));
                Log.v("data", contact.toString());
                // Adding contact to list
                catList.add(contact);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return catList;
    }

    public ArrayList<Movie> getAlldatafromFavourite() {
        ArrayList<Movie> catList = new ArrayList<Movie>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Movies_info.Favourite.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int x = cursor.getColumnCount();

        // Log.v("data",""+x);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Movie contact = new Movie();
                contact.setId((cursor.getString(0)));
                contact.setPoster_path(cursor.getString(1));
                contact.setAdult(cursor.getString(2));
                contact.setOverview(cursor.getString(3));
                contact.setRelease_date(cursor.getString(4));
                contact.setOriginal_title(cursor.getString(5));
                contact.setTitle(cursor.getString(6));
                contact.setPopularity(cursor.getString(7));
                contact.setVote_count(cursor.getString(8));
                contact.setVideo(cursor.getString(9));
                contact.setVote_average(cursor.getString(10));
                contact.setBackdrop_path(cursor.getString(11));
                //  Log.v("data",contact.toString());
                // Adding contact to list
                catList.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list
        return catList;
    }
}
