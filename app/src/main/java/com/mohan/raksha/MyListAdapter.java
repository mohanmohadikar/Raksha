package com.mohan.raksha;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mohanmohadikar.raksha.R;

import java.util.List;







public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
    //private MyListData[] listdata;
    private List<MyListData> listdata;

    private Context mContext;
    private Cursor mCursor;


  //  DatabaseHelper myDb;

    public MyListAdapter(Context context, Cursor cursor){
        mContext = context;
        mCursor = cursor;
    }


    // RecyclerView recyclerView;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        if(!mCursor.moveToPosition(position)){
            return;
        }

        String name = mCursor.getString(mCursor.getColumnIndex(DatabaseContracts.ContactsEntry.COLUMN_NAME));
        String number = mCursor.getString(mCursor.getColumnIndex(DatabaseContracts.ContactsEntry.COLUMN_NUMBER));

        long id = mCursor.getLong(mCursor.getColumnIndex(DatabaseContracts.ContactsEntry._ID));



      //  MyListData list = listdata.get(position);
        holder.textView1.setText(name);
        holder.textView2.setText(number);
        holder.itemView.setTag(id);


    }


    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView1;
        public TextView textView2;
        public LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textView1 = (TextView) itemView.findViewById(R.id.textView1);
            this.textView2 = (TextView) itemView.findViewById(R.id.textView2);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }



    public void swapCursor(Cursor newCursor){

        if(mCursor !=null){
            mCursor.close();
        }

        mCursor = newCursor;

        if(newCursor != null){
            notifyDataSetChanged();
        }

    }

}

