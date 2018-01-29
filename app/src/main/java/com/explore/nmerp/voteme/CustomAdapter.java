package com.explore.nmerp.voteme;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by NMERP on 28-Oct-17.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyviewHolder>  {
    private Context context;
    private MemberlistModel memberlistModel;
    private ArrayList<MemberlistModel> list;

    public CustomAdapter(Context context,ArrayList<MemberlistModel> list) {
        this.list=list;
        this.context = context;
    }

    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context.getApplicationContext());
        View row = layoutInflater.inflate(R.layout.custom_row,parent,false);
        MyviewHolder myviewHolder= new MyviewHolder(row);

        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(MyviewHolder holder, int position) {


        memberlistModel = new MemberlistModel();

        holder.name.setText(list.get(position).getName());
        String img_url="http://portfolio.xero2pi.com/android/images/candidate/"+list.get(position).getImg();
        Picasso.with(context).load(img_url).into(holder.memberimg);
        //holder.memberimg.setImageResource(list.get(position).getImg());

    }

    @Override
    public int getItemCount() {
        if(list.size()!=0){

            return list.size();
        }
        return 0;

    }

    public static  class  MyviewHolder extends  RecyclerView.ViewHolder {
            TextView name;
            ImageView memberimg;


        public MyviewHolder(View itemView) {
            super(itemView);
            name= (TextView) itemView.findViewById(R.id.memberName);
            memberimg = (ImageView) itemView.findViewById(R.id.memberimg);
        }
    }
}
