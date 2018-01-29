package com.explore.nmerp.voteme;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by NMERP on 28-Oct-17.
 */

public class UserModel {


    private  static final String NAME ="name";
    private  static final String NID="nid";
    private  static final String FNAME="fname";
    private  static final String MNAME="nmae";
    private  static final String MOB="mob";
    private  static final String DOB="dob";
    private  static final String IMG="img";
    private static  final  String File_NAME="VOTE_ME";
    private static  final  String DEFAULT_MGS="N/A";
    private  static final String VOTE_STATUS="NO";
    private Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    public UserModel(Context context) {


        this.context = context;
        sharedPreferences =  context.getSharedPreferences(File_NAME, context.MODE_PRIVATE);
        editor= sharedPreferences.edit();
    }





    public  void Storedata(String name,String nid,String dob, String fname,String mname,String img, String Mob) {

        String u_name=name;
        String u_mname=mname;
        String u_fname=fname;
        String u_dob=dob;
        String u_nid=nid;
        String u_img=img;
        String u_mob=Mob;

        editor.putString(NAME,u_name);
        editor.putString(MNAME,u_mname);
        editor.putString(FNAME,u_fname);
        editor.putString(DOB,u_dob);
        editor.putString(IMG,u_img);
        editor.putString(NID,u_nid);
        editor.putString(MOB,u_mob);
        editor.commit();



    }

    public String getName() {
        return sharedPreferences.getString(NAME,DEFAULT_MGS);
    }
    public String getImg() {
        return sharedPreferences.getString(IMG,DEFAULT_MGS);
    }



    public String getNid() {
        return sharedPreferences.getString(NID,DEFAULT_MGS);
    }



    public String getFname() {
        return sharedPreferences.getString(FNAME,DEFAULT_MGS);
    }



    public String getMname() {
        return sharedPreferences.getString(MNAME,DEFAULT_MGS);
    }



    public String getMob() {
        return sharedPreferences.getString(MOB,DEFAULT_MGS);
    }



    public String getDob() {
        return sharedPreferences.getString(DOB,DEFAULT_MGS);
    }



    public void storeVoteStatus () {


        editor.putString(VOTE_STATUS,"VOTTED");
        editor.commit();


    }


    public void clearData() {

        editor.remove(VOTE_STATUS);
        editor.commit();

    }

    public String getVoteStatus() {



        return   sharedPreferences.getString(VOTE_STATUS,DEFAULT_MGS);


    }













}
