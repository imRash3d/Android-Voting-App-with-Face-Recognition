package com.explore.nmerp.voteme;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MemberList extends AppCompatActivity {
    private  MemberlistModel memberlistModel;

    private  UserModel userModel;


    private  ArrayList<MemberlistModel> list ;
    String status;
    private Button voteBTn;


private RecyclerView memberlistRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        memberlistRV = (RecyclerView) findViewById(R.id.list);
        memberlistRV.setHasFixedSize(true);

        userModel = new UserModel(this);
        voteBTn= (Button) findViewById(R.id.voteBtn);

         status= userModel.getVoteStatus();
        if(status=="VOTTED") {
            voteBTn.setVisibility(View.GONE);

        }
        else  {
            voteBTn.setVisibility(View.VISIBLE);
            voteBTn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startVote();
                }
            });

        }






        //cv = (CardView) findViewById(R.id.cv);
        memberlistModel = new MemberlistModel();

        //list = memberlistModel.getmember ();
        getmem();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        memberlistRV.setLayoutManager(layoutManager);







    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), Userdetails.class);
        startActivityForResult(myIntent, 0);
        return true;

    }

    public void startVote() {

        Intent intent= new Intent(MemberList.this,PhoneOtp.class);
        startActivity(intent);

       // MemberlistModel memberlistModel= new MemberlistModel();



    }

    public void clear(View view) {

        userModel.clearData();
    }


    public void  getmem() {
        String data;



        String url="http://portfolio.xero2pi.com/android/cadidatelist.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                list= new ArrayList<>();

                final String []   candidate_name = new String [response.length()];
                final String []   candidate_img = new String [response.length()];


                for(int i =0;i<response.length();i++) {


                    try {
                        JSONObject jsonObject = (JSONObject) response.get(i);



                        candidate_name[i] = jsonObject.getString("name");
                        candidate_img[i] = jsonObject.getString("image");

                        MemberlistModel memberlist1 = new  MemberlistModel(candidate_name[i],candidate_img[i]);
                        list.add(memberlist1);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(),list);
                memberlistRV.setAdapter(customAdapter);



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
