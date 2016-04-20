package com.example.ahmed.movieapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements lisener_two_ui {
    public boolean two_panal = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FrameLayout flPanel2 = (FrameLayout) findViewById(R.id.f1panal_two);
        two_panal = flPanel2 != null;

        if (savedInstanceState == null) {
            BlankFragment blankFragment = new BlankFragment();
            blankFragment.setnamelisterner(this);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.f1panal_one, blankFragment)
                    .commit();
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.setting) {
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void getConnectLisener(String poster_path, String adult, String overview, String release_date, String id, String original_title, String original_language, String title, String popularity, String vote_count, String video, String vote_average, String backdrop_path) {

        if (two_panal) {
            Details_movie_Fragment details_movie_fragment = new Details_movie_Fragment();
            Bundle bundle = new Bundle();
            bundle.putString("poster_path", poster_path);
            bundle.putString("adult", (adult));
            bundle.putString("overview", overview);
            bundle.putString("release_date", (release_date));
            bundle.putString("idmove", (id));
            bundle.putString("original_title", (original_title));
            bundle.putString("original_language", (original_language));
            bundle.putString("title", (title));
            bundle.putString("popularity", (popularity));
            bundle.putString("vote_count", (vote_count));
            bundle.putString("video", (video));
            bundle.putString("vote_average", (vote_average));
            bundle.putString("backdrop_path", (backdrop_path));
            details_movie_fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.f1panal_two, details_movie_fragment).commit();


        } else {
            Intent intent = new Intent(this, DetailsMovie.class);

            intent.putExtra("poster_path", poster_path);
            intent.putExtra("adult", (adult));
            intent.putExtra("overview", overview);
            intent.putExtra("release_date", (release_date));
            intent.putExtra("idmove", (id));
            intent.putExtra("original_title", (original_title));
            intent.putExtra("original_language", (original_language));
            intent.putExtra("title", (title));
            intent.putExtra("popularity", (popularity));
            intent.putExtra("vote_count", (vote_count));
            intent.putExtra("video", (video));
            intent.putExtra("vote_average", (vote_average));
            intent.putExtra("backdrop_path", (backdrop_path));

            // Toast.makeText(getContext(), "id" + id + "posetion" + position + "parent" + parent, Toast.LENGTH_LONG).show();
            startActivity(intent);
        }
    }
}
