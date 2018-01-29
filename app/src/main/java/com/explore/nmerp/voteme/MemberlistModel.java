package com.explore.nmerp.voteme;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by NMERP on 28-Oct-17.
 */

public class MemberlistModel {
    private String name;
    private String img;
    private String vote;

    public MemberlistModel(String name, String img) {
        this.name = name;
        this.img = img;
    }

    public MemberlistModel(String name, String img,String vote) {
        this.name = name;
        this.img = img;
        this.vote=vote;
    }



    public MemberlistModel() {

    }

    public  void setvote( String  vote) {

        this.vote=vote;
    }

    public String getVote () {
        return this.vote;
    }


    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }


   public ArrayList<MemberlistModel> getmemr(String name, String img) {
       /*

String member_name=name;
String member_img=img;


       MemberlistModel memberlist1 = new  MemberlistModel(member_name,member_img);


       list.add(memberlist1);
   */
ArrayList<MemberlistModel> list = new ArrayList<>();
       return list;

   }










}
