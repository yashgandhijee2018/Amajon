package com.example.hp.amajon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import io.paperdb.Paper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hp.amajon.Model.Users;
import com.example.hp.amajon.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.utilities.Utilities;

public class MainActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    Vibrator v;
    public void login_function(View view)
    {
        Intent i=new Intent(this,LoginActivity.class);
        startActivity(i);
    }
    public void join_now_function(View view)
    {
        Intent i=new Intent(this,JoinNowActivity.class);
        startActivity(i);
    }
    public void vibrate_function(View view)
    {
        Toast.makeText(this, "I am here for no reason! Call PETA...", Toast.LENGTH_LONG).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(600);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Paper.init(this);
        String phonekey = Paper.book().read(Prevalent.phonekey);
        String passwordkey = Paper.book().read(Prevalent.passwordkey);
        progressDialog=new ProgressDialog(this);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (phonekey !=""&&passwordkey!="")
        {
            if((!TextUtils.isEmpty(phonekey))&&(!TextUtils.isEmpty(passwordkey)))
            {
                    progressDialog.setTitle("Verifying the user details...");
                    progressDialog.setMessage("You are already logged in, We are verifying your details");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    validate(phonekey, passwordkey);
            }
        }
    }

    private void validate(final String phone, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(phone).exists())
                {
                    Users data=dataSnapshot.child("Users").child(phone).getValue(Users.class);
                    if(data.getPhone().equals(phone))
                    {
                        if(data.getPassword().equals(password))
                        {
                            Toast.makeText( MainActivity.this, "You are successfully logged in!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            Prevalent.currentUser = data;
                            Intent i=new Intent(MainActivity.this,HomeActivity.class);
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Incorrect Password!! Click forget password button instead of wasting my time cause you are oblivious!", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Why am I seeing this? Report to the developers,cause it's a loop hole", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "You are not registered amigo! Register kar ke dekho aacha lagta hai.. And it will not cost you a dime!", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
