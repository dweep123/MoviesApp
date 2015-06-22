package com.example.chitra.moviesapp;

import android.content.Intent;
import android.content.SharedPreferences;
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

public  class MovieFragment extends Fragment {

    private GridViewAdapter mGridViewAdapter;
    public MovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.gridview);
        mGridViewAdapter = new GridViewAdapter(getActivity(),getActivity().getLayoutInflater());
        gridView.setAdapter(mGridViewAdapter);
        FetchMovieData movieData = new FetchMovieData();
        movieData.execute();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public  void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                MovieItem movie = mGridViewAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("title" , movie.title);
                intent.putExtra("overview" , movie.overview);
                intent.putExtra("release_date" , movie.release_date);
                intent.putExtra("image_poster" , movie.image_poster);
                intent.putExtra("votes" , String.valueOf(movie.votes));
                startActivity(intent);

            }
        });
        return rootView;
    }
    /*private void updateMovies() {
        FetchMovieData movieData = new FetchMovieData();
        movieData.execute();
    }
    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }*/

//    public class FetchMovieData  extends AsyncTask<Void,Void,JSONArray>{
public class FetchMovieData  extends AsyncTask< Void, Void, ArrayList<MovieItem> >{
        private final String LOG_TAG = FetchMovieData.class.getSimpleName();

        private ArrayList<MovieItem> getMovieDataFromJson(String movieJsonStr)
                throws JSONException {

            JSONObject movieJson = new JSONObject(movieJsonStr);
            JSONArray movieArray = movieJson.getJSONArray("results");
            int len = movieArray.length();
            ArrayList<MovieItem> resultMovies = new ArrayList<MovieItem>();
            for(int i=0;i<len;i++){
                JSONObject movie =  movieArray.getJSONObject(i);
                MovieItem m = new MovieItem(movie.getLong("id"),
                                            movie.getString("original_title"),
                                            movie.getString("overview"),
                                            movie.getString("release_date"),
                                            movie.getString("poster_path"),
                                            movie.getDouble("vote_average"));
                resultMovies.add(m);
            }
            return resultMovies;
        }


        @Override
        //protected JSONArray doInBackground(Void... params){
            protected ArrayList<MovieItem> doInBackground(Void... params){
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String movieJsonStr = null;

            try{
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String sort_key = prefs.getString(getString(R.string.pref_sort_key),
                        getString(R.string.pref_sort_default));
                URL url = new URL("http://api.themoviedb.org/3/discover/movie?sort_by="+sort_key+"&api_key=313efd4427d6c0a1bbd8d8463d912c4b");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieJsonStr = buffer.toString();
            }
            catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            try {
                return getMovieDataFromJson(movieJsonStr);
                /*JSONObject movieJson = new JSONObject(movieJsonStr);
                JSONArray movieArray = movieJson.getJSONArray("results");
                return movieArray;*/
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            // This will only happen if there was an error getting or parsing the forecast.
            return null;

        }
        @Override
      /*  protected void onPostExecute(JSONArray result) {
            if (result != null) {
                mGridViewAdapter.updateData(result);
                // New data is back from the server.  Hooray!
            }
        }*/
    protected void onPostExecute(ArrayList<MovieItem> result) {
        if (result != null) {
            mGridViewAdapter.updateData(result);
            // New data is back from the server.  Hooray!
        }
    }
    }
}