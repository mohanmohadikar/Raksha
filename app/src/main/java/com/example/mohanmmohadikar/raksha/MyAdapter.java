package com.example.mohanmmohadikar.raksha;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohanmmohadikar on 9/9/18.
 */





public class MyAdapter extends ArrayAdapter<Data> {

    private Context mContext;
    private List<Data> clist = new ArrayList<>();

    public MyAdapter(@NonNull Context context,  ArrayList<Data> list) {
        super(context, 0 , list);
        mContext = context;
        clist = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list,parent,false);

        Data currentMovie = clist.get(position);

       // ImageView image = (ImageView)listItem.findViewById(R.id.imageView_poster);
       // image.setImageResource(currentMovie.getmImageDrawable());

        TextView number = (TextView) listItem.findViewById(R.id.numberid);
        number.setText(currentMovie.getNumber());



        return listItem;
    }
}