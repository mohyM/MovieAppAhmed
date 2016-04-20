package com.example.ahmed.movieapp.data_base;

import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Created by ahmed on 11/04/16.
 */
public class Movies_info {


    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }

    public static final class MOVIES implements BaseColumns {

        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_MOVIES_ID = "id";
        public static final String POSTER_PATH = "poster_path";
        public static final String ADULT = "adult";
        public static final String OVERVIEW = "over_view";
        public static final String RELEASE_DATE = "release_date";
        public static final String ORIGINAL_TITLE = "original_title";
        public static final String TITLE = "title";
        public static final String POPULARITY = "popularity";
        public static final String VOTE_COUNT = "vote_count";
        public static final String VIDEO = "video";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String BACKDROP_PATH = "backdrop_path";
    }

    public static final class Favourite {
        public static final String TABLE_NAME = "favourite";

        public static final String COLUMN_MOVIES_ID = "id";
        public static final String POSTER_PATH = "poster_path";
        public static final String ADULT = "adult";
        public static final String OVERVIEW = "over_view";
        public static final String RELEASE_DATE = "release_date";
        public static final String ORIGINAL_TITLE = "original_title";
        public static final String TITLE = "title";
        public static final String POPULARITY = "popularity";
        public static final String VOTE_COUNT = "vote_count";
        public static final String VIDEO = "video";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String BACKDROP_PATH = "backdrop_path";

    }
}
