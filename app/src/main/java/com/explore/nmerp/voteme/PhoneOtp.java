package com.explore.nmerp.voteme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.raycoarana.codeinputview.CodeInputView;

import java.util.concurrent.TimeUnit;

public class PhoneOtp extends AppCompatActivity {
    FirebaseAuth auth;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    String vareification_code;
    ProgressDialog progressDialog;
    CodeInputView codeInputView;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_otp);
        codeInputView= (CodeInputView) findViewById(R.id.codeInputView);
        progressDialog = new ProgressDialog(this);
        Intent intent = getIntent();


        auth=FirebaseAuth.getInstance();
        callbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                vareification_code=s;
                Toast.makeText(PhoneOtp.this, "Code Sent", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        };

        Code_send();
    }


    public void Varyfi_code(View view) {


        String input_number=codeInputView.getCode();
        progressDialog.setMessage("Varyfing....");
        progressDialog.show();
        VarifyPhoneNumber(vareification_code,input_number);


    }

    public  void VarifyPhoneNumber(String varify_code,String input_code) {

        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(varify_code,input_code);
        SignInwithPhon(credential);

    }



    public void SignInwithPhon(PhoneAuthCredential credential) {

        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {

                    Toast.makeText(PhoneOtp.this, "Vareify Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PhoneOtp.this,Selectmemberlayout.class));
                    progressDialog.dismiss();


                }
                else {
                    progressDialog.dismiss();

                    codeInputView.setError("Ups! Try with other code.");

                }
            }
        });
    }



    public void Code_send(){
        UserModel userModel = new UserModel(this);

        String user_mob= userModel.getMob();
        String number="+88"+user_mob;
    progressDialog.setMessage("Sending");
        progressDialog.show();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                this,
                callbacks


        );

    }


}
