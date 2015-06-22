package com.example.chitra.moviesapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private static final String IMAGE_URL_BASE = "http://image.tmdb.org/t/p/w185/";
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            Intent intent = getActivity().getIntent();
            if (intent != null) {
                String title = intent.getStringExtra("title");
                String image_poster = intent.getStringExtra("image_poster");
                String votes = intent.getStringExtra("votes");
                String overview = intent.getStringExtra("overview");
                String release_date = intent.getStringExtra("release_date");
                ((TextView) rootView.findViewById(R.id.title_text))
                        .setText(title);
                ((TextView) rootView.findViewById(R.id.votes_text))
                        .setText(votes);
                ((TextView) rootView.findViewById(R.id.overview_text))
                        .setText("Overview\n" + overview);
                ((TextView) rootView.findViewById(R.id.date_text))
                        .setText("Releasing on " + release_date);
                Picasso.with(getActivity()).load(IMAGE_URL_BASE + image_poster).into((ImageView)rootView.findViewById(R.id.image_view));
            }
            return rootView;
        }
    }
}