package com.example.ahmed.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ahmed on 18/04/16.
 */
public class Review_Adapter extends ArrayAdapter<Reviews> {

    ArrayList<Reviews> reviewsArrayList;
    int resource;
    Context context;


    public Review_Adapter(Context context, int resource, ArrayList<Reviews> objects) {
        super(context, R.layout.review_resource, objects);
        this.reviewsArrayList = objects;
        this.resource = resource;
        this.context = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder Holder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.review_resource, null);
            Holder.textView = (TextView) convertView.findViewById(R.id.author);
            Holder.textView1 = (TextView) convertView.findViewById(R.id.review);
            Holder.url = (TextView) convertView.findViewById(R.id.link);
            convertView.setTag(Holder);
        } else {
            Holder = (ViewHolder) convertView.getTag();
        }
        for (int i = 0; i <= reviewsArrayList.size() - 1; i++) {
            Holder.textView.setText(reviewsArrayList.get(position).getAuther());
            Holder.textView1.setText(reviewsArrayList.get(position).getContent());
            Holder.url.setText(reviewsArrayList.get(position).getUrl());
            //  convertView.setTag(Holder);
        }

        return convertView;
    }

    class ViewHolder {
        TextView textView;
        TextView textView1;
        TextView url;
    }

}
