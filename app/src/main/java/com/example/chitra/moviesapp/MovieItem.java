package com.example.chitra.moviesapp;

/**
 * Created by chitra on 21/6/15.
 */
public class MovieItem {
    public long id;
    public String title;
    public String overview;
    public String release_date;
    public String image_poster;
    public double votes;

    MovieItem(long d,String t,  String o, String r ,String i, double v){
        this.id = d;
        this.title = t;
        this.overview = o;
        this.release_date = r;
        this.image_poster = i;
        this.votes = v;
    }
}
