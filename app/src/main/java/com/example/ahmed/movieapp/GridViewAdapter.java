package com.example.ahmed.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ahmed on 25/03/16.
 */
public class GridViewAdapter extends ArrayAdapter<Movie> {

    Context context;
    int resource;
    ArrayList<Movie> mgradData = new ArrayList<Movie>();

    public GridViewAdapter(Context context, int resource, ArrayList<Movie> mgradData) {
        super(context, R.layout.item_grad, mgradData);
        this.resource = resource;
        this.context = context;
        this.mgradData = mgradData;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_grad, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.itme_of_grid);
            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(context).load(mgradData.get(position).getPoster_path()).into(holder.imageView);
        return convertView;


    }

    class ViewHolder {
        ImageView imageView;
    }


}
