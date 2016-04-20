package com.example.ahmed.movieapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmed.movieapp.data_base.Movies_Dp_Helper;
import com.squareup.picasso.Picasso;

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


public class Details_movie_Fragment extends Fragment {


    private static final String MOVIE_SHARE_HASHTAG = " #moveAppahmedmohy";
    public String original_title = new String();
    public String Original_language = new String();
    public String release_date = new String();
    public String overview = new String();
    public String vote_average = new String();
    public String popularity = new String();
    public String adult = new String();
    public String title = new String();
    public String id1 = new String();
    public String backdrop_path = new String();
    public String vote_count = new String();
    public String video = new String();
    public String urltrailer = new String();
    public ArrayList all_keys_of_trailers = new ArrayList<>();
    public String urlReviews = new String();
    public String shar;
    ///// move attributes
    String poster_path = new String();
    ArrayList<Reviews> reviewsArrayList = new ArrayList<>();
    ListView listViewReviews;
    android.support.v7.widget.ShareActionProvider share;
    private ArrayAdapter<String> mkeyAdapter;
    private ArrayList<String> mkeysString;
    //Moves object to add favourite


    public Details_movie_Fragment() {

    }
    // String release_date=new String();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Movie movie = new Movie();
        View rootView = inflater.inflate(R.layout.fragment_details_movie, container, false);
        Intent getIntent = getActivity().getIntent();


        if (getIntent.getExtras() != null) {
            ///poster photo
            poster_path = getIntent.getStringExtra("poster_path");

            // all details


            original_title = getIntent.getStringExtra("original_title");
            Original_language = getIntent.getStringExtra("original_language");
            release_date = getIntent.getStringExtra("release_date");
            overview = getIntent.getStringExtra("overview");
            vote_average = getIntent.getStringExtra("vote_average");
            popularity = getIntent.getStringExtra("popularity");
            id1 = getIntent.getStringExtra("idmove");
            adult = getIntent.getStringExtra("adult");
            backdrop_path = getIntent.getStringExtra("backdrop_path");
            vote_count = getIntent.getStringExtra("vote_count");
            video = getIntent.getStringExtra("video");
            title = getIntent.getStringExtra("title");

        } else if (getArguments() != null) {
            poster_path = getArguments().getString("poster_path");

            // all details


            original_title = getArguments().getString("original_title");
            Original_language = getArguments().getString("original_language");
            release_date = getArguments().getString("release_date");
            overview = getArguments().getString("overview");
            vote_average = getArguments().getString("vote_average");
            popularity = getArguments().getString("popularity");
            id1 = getArguments().getString("idmove");
            adult = getArguments().getString("adult");
            backdrop_path = getArguments().getString("backdrop_path");
            vote_count = getArguments().getString("vote_count");
            video = getArguments().getString("video");
            title = getArguments().getString("title");
        }
        ///add alll to one object
          /*  int myNum = 0;

            try {
                myNum = Integer.parseInt(id1);
            } catch(NumberFormatException nfe) {
                // Handle parse error.
            }*/
        //
        ImageView imageView = (ImageView) rootView.findViewById(R.id.poster_photo);
        Picasso.with(getContext()).load(poster_path).into(imageView);
        movie.setId(id1);
        movie.setOriginal_title(original_title);
        movie.setOriginal_language(Original_language);
        movie.setRelease_date(release_date);
        movie.setOverview(overview);
        movie.setVote_average(vote_average);
        movie.setPopularity(popularity);
        movie.setAdult(adult);
        movie.setBackdrop_path(backdrop_path);
        movie.setPoster_path(poster_path);
          /*  int myNum1 = 0;

            try {
                myNum1 = Integer.parseInt(vote_count);
            } catch(NumberFormatException nfe) {
                // Handle parse error.
            }*/
        movie.setVote_count(vote_count);
        movie.setVideo(video);
        movie.setTitle(title);

        TextView oringinal_title1 = (TextView) rootView.findViewById(R.id.title_text);
        oringinal_title1.setText(original_title);

        TextView oringinal_language = (TextView) rootView.findViewById(R.id.language_text);
        oringinal_language.setText(Original_language);
        //  textView.setText(getIntent.getStringExtra("title"));
        TextView release_date1 = (TextView) rootView.findViewById(R.id.release_date);
        release_date1.setText(release_date);

        TextView overview1 = (TextView) rootView.findViewById(R.id.overview_text);
        overview1.setText(overview);

        RatingBar ratingBar = (RatingBar) rootView.findViewById(R.id.rate_bar);
        Float rat = Float.parseFloat(vote_average);
        rat = rat / 2;
        ratingBar.setRating(rat);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            }
        });
        TextView popularity1 = (TextView) rootView.findViewById(R.id.popylair_text);
        popularity1.setText(popularity);
        ImageButton favourite = (ImageButton) rootView.findViewById(R.id.favourite_button);
        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Movies_Dp_Helper movies_dp_helper = new Movies_Dp_Helper(getContext());
                ArrayList<Movie> movieArrayList = new ArrayList<Movie>();
                movieArrayList = movies_dp_helper.getAlldatafromFavourite();
                boolean x = true;
                for (int i = 0; i <= movieArrayList.size() - 1; i++) {
                    if (movieArrayList.get(i).getId().equals(movie.getId())) {
                        Toast.makeText(getContext(), "movie in the database", Toast.LENGTH_LONG).show();
                        x = false;
                    }
                }
                if (movieArrayList.size() == 0 || x == true) {
                    movies_dp_helper.addFavourite(movie);
                }
                ArrayList<Movie> movieArrayList1 = new ArrayList<Movie>();
                movieArrayList = movies_dp_helper.getAlldatafromFavourite();
                Log.v("m", movieArrayList.toString());
            }
        });


        urltrailer = "http://api.themoviedb.org/3/movie/" + movie.getId() + "/videos?api_key=" + BuildConfig.API_Key;
        retrivekeys retrivekeys = new retrivekeys();
        retrivekeys.execute(urltrailer);
        mkeyAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.railer_items, R.id.list_item_trailer_textView, new ArrayList<String>());
        ListView listViewTrailers = (ListView) rootView.findViewById(R.id.listView_trailers);
        listViewTrailers.setAdapter(mkeyAdapter);
        listViewTrailers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String shar = "http://www.youtube.com/watch?v=" + all_keys_of_trailers.get(position);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(shar)));

            }
        });
        urlReviews = "http://api.themoviedb.org/3/movie/" + movie.getId() + "/reviews?api_key=" + BuildConfig.API_Key;
        listViewReviews = (ListView) rootView.findViewById(R.id.listView_reviews);
        Retrive_Reviews retrive_reviews = new Retrive_Reviews();
        retrive_reviews.execute(urlReviews);
        listViewReviews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String x = reviewsArrayList.get(position).getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(x));
                startActivity(i);
            }
        });






        return rootView;
    }


    public ArrayList<String> convertJsonToArraystring(JSONArray jsonArray) throws JSONException {
        ArrayList<String> keys = new ArrayList<String>();
        for (int i = 0; i <= jsonArray.length() - 1; i++) {
            String s = new String();
            JSONObject tempobj = jsonArray.getJSONObject(i);
            s = tempobj.getString("key");
            keys.add(s);
        }
        return keys;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.share_menu, menu);
        MenuItem item = menu.findItem(R.id.action_share);
        share = (android.support.v7.widget.ShareActionProvider) MenuItemCompat.getActionProvider(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_share) {


        }

        return super.onOptionsItemSelected(item);

    }

    public class retrivekeys extends AsyncTask<String, Void, ArrayList<String>> {


        @Override
        protected ArrayList<String> doInBackground(String... params) {
            if (params.length == 0)
                return null;

            HttpURLConnection conn = null;
            BufferedReader br = null;
            String total;
            try {
                URL url = new URL(params[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                InputStream isn = conn.getInputStream();
                if (isn == null) {
                    total = null;
                }

                br = new BufferedReader(new InputStreamReader(isn));
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                if (sb.length() == 0) {
                    total = null;
                }
                total = sb.toString();
                JSONObject obj = new JSONObject(total);
                JSONArray arr = obj.getJSONArray("results");

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject ob = arr.getJSONObject(i);
                    String key = ob.getString("key");
                    all_keys_of_trailers.add(key);

                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                System.out.println("Error");
                e.printStackTrace();
            } finally {
                if (conn != null)
                    conn.disconnect();
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return all_keys_of_trailers;

        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            if (strings != null)
                //  mkeyAdapter.clear();
                for (int i = 0; i <= strings.size() - 1; i++) {
                    String x = "trailesr" + (i + 1);
                    mkeyAdapter.add(x);

                }


        }
    }

    public class Retrive_Reviews extends AsyncTask<String, Void, ArrayList<Reviews>> {


        @Override
        protected ArrayList<Reviews> doInBackground(String... params) {

            if (params.length == 0)
                return null;

            HttpURLConnection conn = null;
            BufferedReader br = null;
            String total;
            try {
                URL url = new URL(params[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                Log.v("url", url.toString());
                conn.connect();
                InputStream isn = conn.getInputStream();
                if (isn == null) {
                    total = null;
                }
                br = new BufferedReader(new InputStreamReader(isn));
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                if (sb.length() == 0) {
                    total = null;
                }
                total = sb.toString();
                JSONObject obj = new JSONObject(total);
                JSONArray arr = obj.getJSONArray("results");

                for (int i = 0; i < arr.length(); i++) {
                    Reviews reviews = new Reviews();
                    JSONObject ob = arr.getJSONObject(i);
                    String auth = ob.getString("author");
                    String content = ob.getString("content");
                    String path = ob.getString("url");
                    reviews.setAuther(auth);
                    reviews.setContent(content);
                    reviews.setUrl(path);
                    reviewsArrayList.add(reviews);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                System.out.println("Error");
                e.printStackTrace();
            } finally {
                if (conn != null)
                    conn.disconnect();
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return reviewsArrayList;

        }

        @Override
        protected void onPostExecute(ArrayList<Reviews> reviewses) {
            super.onPostExecute(reviewses);
            Review_Adapter review_adapter = new Review_Adapter(getContext(), R.layout.review_resource, reviewses);
            listViewReviews.setAdapter(review_adapter);
        }
    }



}


