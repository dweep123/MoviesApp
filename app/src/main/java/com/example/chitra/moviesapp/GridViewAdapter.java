package com.example.chitra.moviesapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by chitra on 21/6/15.
 */
public class GridViewAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater mInflater;
    JSONArray mJsonArray;

    ArrayList<MovieItem> movies;

    private static final String IMAGE_URL_BASE = "http://image.tmdb.org/t/p/w185/";
    public GridViewAdapter(Context context, LayoutInflater inflater ){
        Log.v("Adapter","Adapter is initailzed ");
        this.mContext = context;
        this.mInflater = inflater;
        mJsonArray = new JSONArray();
        movies = new ArrayList<MovieItem>();
    }
    @Override
    public int getCount() {
        Log.v("Adapter","Get Count is called");
        Log.v("Adapter","ans " +movies.size());
       // return mJsonArray.length();
        return movies.size();
    }
    @Override
    public MovieItem getItem(int position) {
        // return mJsonArray.optJSONObject(position);
        return movies.get(position);
    }
// TO DO  Change it to movie id
    @Override
    public long getItemId(int position) {
        // your particular dataset uses String IDs
        // but you have to put something in this method
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;
        Log.v("Adapter","Get view is called ");
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.grid_item_movie,null);
            holder = new ViewHolder();
            holder.thumbnailImageView = (ImageView) convertView.findViewById(R.id.img_thumbnail);
            convertView.setTag(holder);

        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }


        MovieItem movie = movies.get(position);
        Picasso.with(mContext).load(IMAGE_URL_BASE + movie.image_poster).into(holder.thumbnailImageView);

        /*JSONObject jsonObject = (JSONObject) getItem(position);
        if(jsonObject.has("poster_path")) {
            Log.v("Adapter","It has poster path");
            String poster_path = jsonObject.optString("poster_path");
            Picasso.with(mContext).load(IMAGE_URL_BASE + poster_path).into(holder.thumbnailImageView);
        }
        else{
            Picasso.with(mContext).load(IMAGE_URL_BASE + "/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg").into(holder.thumbnailImageView);
        }*/
        return convertView;
    }

    /*public void updateData(JSONArray jsonArray) {

        this.mJsonArray = jsonArray;
        notifyDataSetChanged();
    }*/
    public void updateData(ArrayList<MovieItem> m) {

        this.movies = m;
        notifyDataSetChanged();
    }

private static class ViewHolder{
    public ImageView thumbnailImageView;
}




}