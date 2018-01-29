package com.explore.nmerp.voteme;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by NMERP on 26-Nov-17.
 */

public class ResultAdapter extends ArrayAdapter<MemberlistModel> {

  private Context context;
    private  ArrayList<MemberlistModel> list;

    public ResultAdapter(Context context, ArrayList<MemberlistModel> items) {

        super(context, R.layout.result_laout,items);
        this.context=context;
        this.list=items;

    }




    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View customRow = layoutInflater.inflate(R.layout.result_laout,parent,false);

        TextView name = (TextView) customRow.findViewById(R.id.member_name);
        TextView vote = (TextView) customRow.findViewById(R.id.vote_show);

        name.setText(list.get(position).getName());
        vote.setText(list.get(position).getVote());

        return  customRow;
    }
}
