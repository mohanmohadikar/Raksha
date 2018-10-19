package com.example.mohanmohadikar.raksha;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<ListItem> listItems;

    private Context ctx;

    public MyAdapter(List<ListItem> listItem, Context ctx) {
        this.listItems = listItems;
        this.ctx = ctx;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ListItem listItem = listItems.get(position);

        holder.number.setText(listItem.getNum());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public TextView number;

        public ViewHolder(View itemView) {
            super(itemView);


            number = (TextView)itemView.findViewById(R.id.cardtext);
        }
    }

}
