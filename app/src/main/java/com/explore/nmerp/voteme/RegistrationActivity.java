package com.explore.nmerp.voteme;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {
    private EditText NidET,MobET,PassWordET;
    AlertDialog.Builder builder;
    ProgressDialog progressDialog;
    UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().hide();
        progressDialog= new ProgressDialog(this);
        builder = new AlertDialog.Builder(this);
        userModel= new UserModel(this);
        NidET = (EditText) findViewById(R.id.nid);
        PassWordET = (EditText) findViewById(R.id.password);
        MobET = (EditText) findViewById(R.id.mob);
    }


    public void is_reg(View view) {

        Check_user();

        //Intent intent = new Intent(RegistrationActivity.this,Varefy_user_phone.class);

    }


    public void Check_user () {
        final String User_nid=NidET.getText().toString();
       final String User_password=PassWordET.getText().toString();
        final String User_mob=MobET.getText().toString();

        progressDialog.setMessage("Loading....");
        progressDialog.show();

        String URL="http://portfolio.xero2pi.com/android/finduserimg.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    boolean success= jsonObject.getBoolean("success");
                    if(success){

                        Intent intent = new Intent(RegistrationActivity.this,Varefy_user_phone.class);

                        intent.putExtra("User_nid",User_nid);
                        intent.putExtra("User_password",User_password);
                        intent.putExtra("User_mob",User_mob);
                        startActivity(intent);

                    }


                    else {
                        String message=jsonObject.getString("message");
                        builder.setMessage(message);
                        builder.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(RegistrationActivity.this,RegistrationActivity.class));
                            }
                        });
                        builder.create();
                        builder.show();
                        progressDialog.dismiss();
                    }



                } catch (JSONException e) {

                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("unserNid", NidET.getText().toString());
                params.put("userMob", MobET.getText().toString());

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);

    }





}
