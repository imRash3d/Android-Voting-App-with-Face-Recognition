package com.explore.nmerp.voteme;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Userdetails extends AppCompatActivity {
    UserModel userModel;
    private TextView nameTV,nidTV,fnameTV,mnameTV,dobTV,mobTV;
    private ImageView imgIV;
    private Button resultBTN;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return  true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);
        userModel = new UserModel(this);
        resultBTN = (Button) findViewById(R.id.resultBTN);
        nameTV = (TextView) findViewById(R.id.name);
        mobTV = (TextView) findViewById(R.id.mob);
        nidTV = (TextView) findViewById(R.id.nid);
        fnameTV = (TextView) findViewById(R.id.fname);
        mnameTV = (TextView) findViewById(R.id.mname);
        dobTV = (TextView) findViewById(R.id.dob);
        imgIV = (ImageView) findViewById(R.id.imageView3);

        String name = userModel.getName();
        String nid = userModel.getNid();
        String fname= userModel.getFname();
        String mname=userModel.getMname();
        String Dob= userModel.getDob();
        String mob= userModel.getMob();
        if(nid.equals("1234567890")) {
            resultBTN.setVisibility(View.VISIBLE);

        }
        String img = "http://portfolio.xero2pi.com/android/images/"+userModel.getImg();


        nameTV.setText(name);
        mobTV.setText(mob);
        nidTV.setText(nid);
        fnameTV.setText(fname);
        mnameTV.setText(mname);
        dobTV.setText(Dob);
        Picasso.with(this).load(img).into(imgIV);




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.menu_mobile:

                break;

            case R.id.menu_password:
                break;

            case R.id.menu_exit:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure want to close the App ");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("EXIT", true);
                        startActivity(intent);
                    }
                });
                builder.create();
                builder.show();

                break;


        }
        return super.onOptionsItemSelected(item);
    }

    public void showMember(View view) {


        Intent intent = new Intent(Userdetails.this,MemberList.class);
        startActivity(intent);

    }


    public void getResult(View view) {

        startActivity(new Intent(Userdetails.this,Voting_result.class));
    }
}
