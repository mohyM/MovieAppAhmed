package com.example.ahmed.movieapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.ahmed.movieapp.data_base.Movies_Dp_Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class BlankFragment extends Fragment {

    ArrayList<Movie> movie1;
    GridView grid;
    lisener_two_ui lisener_two_ui;


    public BlankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v("ahmedmohy", "on start");
        if (isOnline()) {
            upView();
            Toast.makeText(getActivity().getApplicationContext(), "the internet success", Toast.LENGTH_LONG).show();

        } else {

            Movies_Dp_Helper movies_dp_helper = new Movies_Dp_Helper(getContext());
            ArrayList<Movie> movies = new ArrayList<Movie>();
            movies = movies_dp_helper.getAlldatafroMovies();
            GridViewAdapter gridViewAdapter = new GridViewAdapter(getContext(), R.layout.item_grad, movies);
            grid.setAdapter(gridViewAdapter);
            Toast.makeText(getActivity().getApplicationContext(), "the internet faild", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_blank, container, false);

        grid = (GridView) rootView.findViewById(R.id.grad_view_id);
        //retrivemovies retrivemovies=new retrivemovies();
        //retrivemovies.execute();
        if (isOnline()) {
            upView();
            Toast.makeText(getActivity().getApplicationContext(), "the internet success", Toast.LENGTH_LONG).show();
        } else {

            Toast.makeText(getActivity().getApplicationContext(), "the internet faild", Toast.LENGTH_LONG).show();
        }


        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              /*  Intent intent = new Intent(getContext(), DetailsMovie.class);

                intent.putExtra("poster_path", movie1.get(position).getPoster_path());
                intent.putExtra("adult", (movie1.get(position).getAdult()));
                intent.putExtra("overview", (movie1.get(position).getOverview()));
                intent.putExtra("release_date", (movie1.get(position).getRelease_date()));
                intent.putExtra("idmove", (movie1.get(position).getId()));
                intent.putExtra("original_title", (movie1.get(position).getOriginal_title()));
                intent.putExtra("original_language", (movie1.get(position).getOriginal_language()));
                intent.putExtra("title", (movie1.get(position).getTitle()));
                intent.putExtra("popularity", (movie1.get(position).getPopularity()));
                intent.putExtra("vote_count", (movie1.get(position).getVote_count()));
                intent.putExtra("video", (movie1.get(position).getVideo()));
                intent.putExtra("vote_average", (movie1.get(position).getVote_average()));
                intent.putExtra("backdrop_path", (movie1.get(position).getBackdrop_path()));

                // Toast.makeText(getContext(), "id" + id + "posetion" + position + "parent" + parent, Toast.LENGTH_LONG).show();
                startActivity(intent);*/
                lisener_two_ui.getConnectLisener(movie1.get(position).getPoster_path(), movie1.get(position).getAdult(),
                        movie1.get(position).getOverview(), movie1.get(position).getRelease_date(),
                        movie1.get(position).getId(), movie1.get(position).getOriginal_title(),
                        movie1.get(position).getOriginal_language(), movie1.get(position).getTitle(),
                        movie1.get(position).getPopularity(), movie1.get(position).getVote_count(),
                        movie1.get(position).getVideo(), movie1.get(position).getVote_average(),
                        movie1.get(position).getBackdrop_path()
                );
            }
        });


        // Inflate the layout for this fragment
        return rootView;

    }

    public void setnamelisterner(lisener_two_ui lisener_two_ui1) {
        lisener_two_ui = lisener_two_ui1;
    }

    private void upView() {
        retrivemovies retrivemovies = new retrivemovies();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String view = prefs.getString(getString(R.string.sort_list_key), getString(R.string.pref_location_default));
        if (view.toString().equals("1")) {
            Movies_Dp_Helper movies_dp_helper = new Movies_Dp_Helper(getContext());

            movie1 = movies_dp_helper.getAlldatafromFavourite();
            GridViewAdapter gridViewAdapter = new GridViewAdapter(getContext(), R.layout.item_grad, movie1);
            grid.setAdapter(gridViewAdapter);

        } else {
            retrivemovies.execute(view);
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public ArrayList<Movie> getitemsnotindatabase(ArrayList<Movie> movie1) {
        ArrayList<Movie> items = new ArrayList<Movie>();
        ArrayList<Movie> items_database = new ArrayList<Movie>();
        Movies_Dp_Helper movies_dp_helper = new Movies_Dp_Helper(getContext());
        items_database = movies_dp_helper.getAlldatafroMovies();
        for (int i = 0; i <= movie1.size() - 1; i++) {
            boolean exist = true;
            for (int y = 0; y <= items_database.size() - 1; y++) {
                if (movie1.get(i).getId().equals(items_database.get(y).getId())) {
                    exist = false;
                    continue;

                }
            }
            if (exist) {
                //is exist
                items.add(movie1.get(i));

            } else {
                exist = true;

            }
        }


        return items;
    }

    public class retrivemovies extends AsyncTask<String, Void, ArrayList<Movie>> {
        private final String LOG_TAG = retrivemovies.class.getSimpleName();


        @Override
        protected ArrayList<Movie> doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String forecastJsonStr = null;
            String baseUrl = new String();

            try {
                //connection
                if (params.length != 0) {
                    String x = params[0].toString();
                    if (x.equals("0")) {
                        baseUrl = "https://api.themoviedb.org/3/movie/popular?";

                    } else if (x.equals("2") || x.equals("1")) {
                        baseUrl = "https://api.themoviedb.org/3/movie/top_rated?";
                    }
                } else {
                    baseUrl = "https://api.themoviedb.org/3/movie/popular?";
                }

                String apiKey = "api_key=" + BuildConfig.API_Key;
                URL url = new URL(baseUrl.concat(apiKey));
                Log.v(LOG_TAG, url.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                //get data
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
              /*  if (inputStream == null) {
                    return null;
                }*/
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {

                    return null;
                }
                forecastJsonStr = buffer.toString();
                JSONObject jsonObject = new JSONObject(forecastJsonStr);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                movie1 = new ArrayList<Movie>();


                movie1 = convertJsonToArrayList(jsonArray);
                return movie1;
            } catch (Exception e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null)
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);

                    }
            }

        }

        public ArrayList<Movie> convertJsonToArrayList(JSONArray jsonArray) throws JSONException {

            movie1 = new ArrayList<Movie>();

            for (int i = 0; i < jsonArray.length(); i++) {
                Movie Movie = new Movie();
                JSONObject tempobj = jsonArray.getJSONObject(i);
                String s = new String("http://image.tmdb.org/t/p/w185");
                s = s + tempobj.getString("poster_path");
                String adult = new String(tempobj.getString("adult"));
                String overview = new String(tempobj.getString("overview"));
                String id = new String(tempobj.getString("id"));
                String original_title = new String(tempobj.getString("original_title"));
                String original_language = new String(tempobj.getString("original_language"));
                String backdrop_path = new String(tempobj.getString("backdrop_path"));
                String popularity = new String(tempobj.getString("popularity"));
                String vote_count = new String(tempobj.getString("vote_count"));
                String video = new String(tempobj.getString("video"));
                String vote_average = new String(tempobj.getString("vote_average"));
                String release_date = new String(tempobj.getString("release_date"));
                Movie.setRelease_date(release_date);
                Movie.setPoster_path(s);
                Movie.setAdult(adult);
                Movie.setOverview(overview);
                Movie.setId(id);
                Movie.setOriginal_title(original_title);
                Movie.setOriginal_language(original_language);
                Movie.setBackdrop_path(backdrop_path);
                Movie.setPopularity(popularity);
                Movie.setVote_count(vote_count);
                Movie.setVideo(video);
                Movie.setVote_average(vote_average);


                Log.v(LOG_TAG, s + adult + overview + id + original_title + original_language + backdrop_path + popularity + vote_count +
                        video + vote_average);
                movie1.add(Movie);
            }

            ArrayList<Movie> movieArrayList = new ArrayList<Movie>();
            movieArrayList = getitemsnotindatabase(movie1);
            if (movieArrayList.size() != 0) {
                Movies_Dp_Helper movies_dp_helper = new Movies_Dp_Helper(getContext());
                for (int i = 0; i <= movieArrayList.size() - 1; i++) {
                    movies_dp_helper.addcat(movieArrayList.get(i));
                }

            }

            return movie1;


        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            GridViewAdapter gridViewAdapter = new GridViewAdapter(getContext(), R.layout.item_grad, movies);
            grid.setAdapter(gridViewAdapter);

        }


    }


}