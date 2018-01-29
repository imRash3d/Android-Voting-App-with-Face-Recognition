package com.explore.nmerp.voteme;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Selectmemberlayout extends AppCompatActivity {
    private String id;
    UserModel userModel;
    String nid;
    ProgressDialog progressDialog;
    AlertDialog.Builder builder;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectmemberlayout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


         userModel = new UserModel(this);
        progressDialog = new ProgressDialog(this);
        builder = new AlertDialog.Builder(this);

    }



    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MemberList.class);
        startActivityForResult(myIntent, 0);
        return true;

    }




    public void submitVote(View view) {
        if(id!=null) {

            nid = userModel.getNid();
            String url = "http://portfolio.xero2pi.com/android/insertvote.php";

            progressDialog.setMessage("Vote Submitted..");
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String message = jsonObject.getString("message");
                        boolean result = jsonObject.getBoolean("result");
                        if (result) {


                            userModel.storeVoteStatus();
                            builder.setMessage(message);
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Selectmemberlayout.this, Userdetails.class);
                                    startActivity(intent);
                                }
                            });
                            builder.create();
                            builder.show();

                        } else {

                            builder.setMessage(message);
                            builder.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Selectmemberlayout.this, Userdetails.class);
                                    startActivity(intent);
                                }
                            });
                            builder.create();
                            builder.show();


                        }


                    } catch (JSONException e) {
                        progressDialog.hide();
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Volley Error", error);
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {


                    Map<String, String> params = new HashMap<>();
                    params.put("memberID", id);
                    params.put("Nid", nid);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);

/*
        //boolean result= usermodel.storeVoteStatus();
        //String voteresult= usermodel.getVoteStatus();
        if(result) {
            Toast.makeText(this, voteresult, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Selectmemberlayout.this,Userdetails.class);
            startActivity(intent);

        }

*/
        }

        else {
            builder.setMessage("Select Your Candidate");

            builder.setNegativeButton("Retry",null);
            builder.create();
            builder.show();

        }
    }






    public void selectmember(View view) {

        boolean staus = ((RadioButton)view).isChecked();
        switch (view.getId()){

            case R.id.member1:
                if(staus){
                    id="1";

                }
                break;
            case R.id.member2:
                if(staus){
                    id="2";

                }
                break;
            case R.id.member3:
                if(staus){
                    id="3";

                }
                break;
            case R.id.member4:
                if(staus){
                    id="4";

                }
                break;

        }

    }


}
