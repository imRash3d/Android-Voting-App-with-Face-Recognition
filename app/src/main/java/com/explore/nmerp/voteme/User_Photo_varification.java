package com.explore.nmerp.voteme;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.contract.VerifyResult;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class User_Photo_varification extends AppCompatActivity {
    private FaceServiceClient faceServiceClient;
    private ImageView imgIV,imgIV2;
    private TextView text1,resultTv;
    private String user_nid;
    private String user_password;
    private String user_mob;
    ProgressDialog progressDialog ;
    private Button varifyBtn;
    Bitmap mybitmap;
    Bitmap mybitmap2;
    UUID faceId1;
    UUID faceId2;
    private static final int CAMERA_REQUEST = 1888;
    AlertDialog.Builder builder;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__photo_varification);
        imgIV = (ImageView) findViewById(R.id.imgIV);
        imgIV2 = (ImageView) findViewById(R.id.imgIV2);
        text1= (TextView) findViewById(R.id.text1);
        resultTv = (TextView) findViewById(R.id.result);
        varifyBtn = (Button) findViewById(R.id.varifyBtn);
        faceServiceClient = new FaceServiceRestClient("https://westcentralus.api.cognitive.microsoft.com/face/v1.0","fd3e676ec73944c5b0f58522160a79e7");

        progressDialog = new ProgressDialog(this);
        builder = new AlertDialog.Builder(this);
        Intent intent = getIntent();
        user_mob=intent.getStringExtra("User_mob");
        user_nid=intent.getStringExtra("User_nid");
        user_password=intent.getStringExtra("User_password");
        getUserPhoto();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void getUserPhoto () {

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

                        String user_img= jsonObject.getString("image");
                        LoadDbUserImg(user_img);

                    }
                    else {

                        builder.setMessage("Nid Card Is Not Matched");
                        builder.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(User_Photo_varification.this,RegistrationActivity.class));
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
                params.put("unserNid", user_nid);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);

    }




    public void addImg(View view) {

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == User_Photo_varification.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imgIV.setImageBitmap(photo);
            detectandFace(photo,1);

        }
    }

    public void detectandFace(final Bitmap bitmap , final int id) {

        ByteArrayOutputStream OutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,OutputStream);
        ByteArrayInputStream InputStream= new ByteArrayInputStream(OutputStream.toByteArray());

        AsyncTask<java.io.InputStream, String, Face[]> detectTast = new AsyncTask<InputStream, String, Face[]>() {
            ProgressDialog dialog = new ProgressDialog(User_Photo_varification.this);
            //FaceListAdapter faceListAdapter = new FaceListAdapter(result, index);

            @Override
            protected void onPostExecute(Face[] faces) {


                if(faces==null)
                {
                    text1.setText("No Face Detected");
                    resultTv.setText("");
                    varifyBtn.setVisibility(View.INVISIBLE);

                    return;
                }
                else if(id==1)
                {

                    dialog.dismiss();
                    text1.setText("Face 1 Detected");
                    varifyBtn.setVisibility(View.VISIBLE);



                } else  {

                    dialog.dismiss();
                    text1.setText("Face 2 Detected");
                }


            }

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
                dialog.show();
            }

            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);
                dialog.setMessage(values[0]);
            }

            @Override



            protected Face[] doInBackground(InputStream... params) {

                publishProgress("detecting....");

                try {
                    Face [] result = faceServiceClient.detect(params[0],true,false,null);
                    if(result==null) {

                        publishProgress("Detecting Finished.... Nothing Found");
                        dialog.dismiss();
                        return  null;

                    }


                    if(id==1) {
                        FaceListAdapter faceListAdapter = new FaceListAdapter(result,id);
                        faceId1 = faceListAdapter.faces.get(0).faceId;

                    }
                    else {
                        FaceListAdapter faceListAdapter = new FaceListAdapter(result,id);
                        faceId2 = faceListAdapter.faces.get(0).faceId;

                    }

                    publishProgress("Detecting FInished %d face(s) Detected ");
                    return result;



                } catch (Exception e) {

                    e.printStackTrace();
                    publishProgress("failed");
                    dialog.dismiss();
                    return null;

                }

            }

        };

        detectTast.execute(InputStream);
    }

    public void varefy(View view) {

        new VerificationTask(faceId1, faceId2).execute();
    }

    //FACE LIST ADAPTER //
    public  class  FaceListAdapter extends BaseAdapter {
        List<Face> faces=new ArrayList<Face>();

        int mIndex;

        FaceListAdapter(Face[] detectionResult, int index) {
            mIndex = index;
            faces = Arrays.asList(detectionResult);


        }
        @Override
        public int getCount() {
            return faces.size();
        }

        @Override
        public Object getItem(int position) {
            return faces.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }



    // PHOTO VAREFICATION  START//

    private class VerificationTask extends AsyncTask<Void, String, VerifyResult> {

        private UUID mFaceId0;
        private UUID mFaceId1;
        ProgressDialog dialog = new ProgressDialog(User_Photo_varification.this);

        VerificationTask(UUID faceId0, UUID faceId1) {
            mFaceId0 = faceId0;
            mFaceId1 = faceId1;

        }

        @Override
        protected VerifyResult doInBackground(Void... params) {

            dialog.setMessage("Varefying....");
            try {
                return faceServiceClient.verify(mFaceId0, mFaceId1);


            } catch (Exception e) {
                e.printStackTrace();

                return null;
            }


        }


        protected void onPreExecute() {
            dialog.show();
            dialog.setMessage("Request: Verifying face " + mFaceId0 + " and face " + mFaceId1);
        }

        protected void onPostExecute(VerifyResult result) {
            if (result != null) {

                //String text = (result.isIdentical?"": "dont")+ " belong to the same Person ";
                //resultTv.setText(text);
                if(result.isIdentical) {
                    //resultTv.setText("  belong to the same Person ");
                    register_user();
                }
                else  {


                    builder.setMessage("Don't belong to the same Person");
                    builder.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            resultTv.setText("");
                            varifyBtn.setVisibility(View.INVISIBLE);
                        }
                    });
                    builder.show();
                    builder.create();

                }




            }

            dialog.dismiss();

           // setUiAfterVerification(result);



        }

        private void setUiAfterVerification(VerifyResult result) {
            // Verification is done, hide the progress dialog.


            // Enable all the buttons.


            // Show verification result.
            //String text = (result.isIdentical? "": "dont")+ " belong to the same Person ";
            //resultTv.setText(text);
            if(result.isIdentical) {
               // resultTv.setText("  belong to the same Person ");
                register_user();
            }
            else  {


                builder.setMessage("Don't belong to the same Person");
                builder.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resultTv.setText("");
                        varifyBtn.setVisibility(View.INVISIBLE);
                    }
                });
                builder.show();
                builder.create();

            }

            dialog.dismiss();
        }
    }






    //PHOTO VAREFICATION END //

    public void LoadDbUserImg(String user_img) {



        Picasso.with(this)
                .load("http://portfolio.xero2pi.com/android/images/"+user_img)
                .into(new Target() {

                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        // loaded bitmap is here (bitmap)
                        detectandFace(bitmap,2);
                        imgIV2.setImageBitmap(bitmap);
                        progressDialog.dismiss();

                    }


                    public void onBitmapFailed(Drawable errorDrawable) {
                        Toast.makeText(User_Photo_varification.this, "Bitmap Failed", Toast.LENGTH_SHORT).show();

                    }


                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });




    }



    // REGISTER USER START //

    public void register_user () {

        String url ="http://portfolio.xero2pi.com/android/registerUser.php";

        progressDialog.setMessage("Registering ....");
        progressDialog.show();
        StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success= jsonObject.getBoolean("error");
                    if(success){
                        String message=  jsonObject.getString("message");

                        builder.setMessage(message);
                        builder.setNegativeButton("Retry",null);
                        builder.create();
                        builder.show();



                    }
                    else {

                        String message=  jsonObject.getString("message");

                        builder.setMessage(message);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(User_Photo_varification.this,LoginActivity.class));
                            }
                        });

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
                params.put("unserNid",user_nid);
                params.put("userMob",user_mob);
                params.put("password",user_password);

                return params;
            }
        };

        com.explore.nmerp.voteme.AppController.getInstance().addToRequestQueue(stringRequest);
    }
    // REFISTER USER END//
}
