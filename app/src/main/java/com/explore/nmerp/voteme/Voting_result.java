package com.explore.nmerp.voteme;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Voting_result extends AppCompatActivity {
private ListView listView;
    ProgressDialog dialog;
    private TextView showname;

    private  ArrayList<MemberlistModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_result);
        listView = (ListView) findViewById(R.id.list);
        showname = (TextView) findViewById(R.id.showname);


dialog = new ProgressDialog(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getResult();
    }

    public  void getResult () {
        dialog.setMessage("Loading");
        dialog.show();

        String Url ="http://portfolio.xero2pi.com/android/cadidatelist.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
               list= new ArrayList<>();

               final String []   candidate_name = new String [response.length()];
                final String []   candidate_img = new String [response.length()];
                final String []   candidate_vote = new String [response.length()];


                for(int i =0;i<response.length();i++) {


                    try {
                        JSONObject jsonObject = (JSONObject) response.get(i);



                        candidate_name[i] =jsonObject.getString("name");
                        candidate_img[i] = jsonObject.getString("image");
                        candidate_vote[i] = jsonObject.getString("vote_count");


                       MemberlistModel memberlist1 = new  MemberlistModel(candidate_name[i],candidate_img[i],candidate_vote[i]);

                       list.add(memberlist1);

                    dialog.dismiss();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //showname.setText(candidate_name[i]);

                }
                ResultAdapter resultAdapter = new ResultAdapter(Voting_result.this,list);
                listView.setAdapter(resultAdapter);



            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Volley LOg",error);
            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }
}
