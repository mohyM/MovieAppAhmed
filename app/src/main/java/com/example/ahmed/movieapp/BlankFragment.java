package com.example.ahmed.movieapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class BlankFragment extends Fragment {

    ArrayList<Movie>movie1;
    GridView grid;


    public BlankFragment() {
        // Required empty public constructor
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
        retrivemovies retrivemovies=new retrivemovies();
        retrivemovies.execute();
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getContext(),DetailsMovie.class);
                Toast.makeText(getContext(),"id"+id+"posetion"+position+"parent"+parent,Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });


        // Inflate the layout for this fragment
        return rootView;

    }


    public class retrivemovies extends AsyncTask<Void, Void, ArrayList<Movie>> {
        private final String LOG_TAG = retrivemovies.class.getSimpleName();


        @Override
        protected ArrayList<Movie> doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String forecastJsonStr = null;

            try {
                //connection
                String baseUrl = "https://api.themoviedb.org/3/movie/popular?";
                String apiKey = "api_key=" + BuildConfig.API_Key;
                URL url = new URL(baseUrl.concat(apiKey));
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
                JSONObject jsonObject=new JSONObject(forecastJsonStr);
                JSONArray jsonArray=jsonObject.getJSONArray("results");
                   movie1 =new ArrayList<Movie>();

                for (int i =0 ; i < jsonArray.length();i++)
                {
                    Movie Movie =new Movie();
                   JSONObject tempobj=jsonArray.getJSONObject(i);
                    String s=new String("http://image.tmdb.org/t/p/w185");
                    s=s+tempobj.getString("poster_path");
                    String adult=new String();
                    adult=tempobj.getString("adult");
                    String overview=new String();
                    overview=tempobj.getString("overview");

                    Movie.setPoster_path(s);
                    Log.v(LOG_TAG,s);
                    movie1.add(Movie);
                }
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

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            GridViewAdapter gridViewAdapter=new GridViewAdapter(getContext(),R.layout.item_grad, movies);
            grid.setAdapter(gridViewAdapter);

        }
    }
}