package com.explore.nmerp.voteme;

import android.app.ProgressDialog;
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

public class LoginActivity extends AppCompatActivity {

    EditText nidET,passwordET;
    AlertDialog.Builder builder;
    ProgressDialog progressDialog;
    UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
// For EXIT APP
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        // For EXIT APP

        getSupportActionBar().hide();
        nidET = (EditText) findViewById(R.id.nid);
        passwordET = (EditText) findViewById(R.id.password);
progressDialog= new ProgressDialog(this);
        builder = new AlertDialog.Builder(this);
        userModel= new UserModel(this);

    }

    public void registration(View view) {

        Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);
        startActivity(intent);

    }

    public void login(View view) {


     is_login();
        //Toast.makeText(this, result, Toast.LENGTH_SHORT).show();



    }


    public void is_login () {
final String User_nid=nidET.getText().toString();
final String User_password=passwordET.getText().toString();
        String url ="http://portfolio.xero2pi.com/android/userLogin.php";

progressDialog.setMessage("Login....");
        progressDialog.show();
        StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success= jsonObject.getBoolean("success");
                    if(success){

                        String name = jsonObject.getString("UserName");
                        String nid = User_nid;
                        String fname= jsonObject.getString("UserFatherName");
                        String mname= jsonObject.getString("UserMotherName");
                        String Dob= jsonObject.getString("UserDateofBirth");
                        String Img= jsonObject.getString("imageurl");
                        String MOb= jsonObject.getString("userMob");
                        userModel.Storedata(name,nid,Dob,fname,mname,Img,MOb);
                        Intent intent = new Intent(LoginActivity.this,Userdetails.class);
                        startActivity(intent);


                    }
                    else {


                        String message=  jsonObject.getString("message");

                        builder.setMessage(message);
                        builder.setNegativeButton("Retry",null);
                        builder.create();
                        builder.show();
                        //Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                VolleyLog.d(String.valueOf(error));
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "No Internet Connection!", Toast.LENGTH_SHORT).show();

                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(getApplicationContext(), "Authentication Error!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(), "Server Side Error!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(), "Network Error!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parse Error!", Toast.LENGTH_SHORT).show();
                }

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
               Map<String ,String > params= new HashMap<>();
                params.put("unserNid",User_nid);
                params.put("password",User_password);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);
    }


}
