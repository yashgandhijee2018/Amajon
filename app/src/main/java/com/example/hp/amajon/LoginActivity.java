package com.example.hp.amajon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.amajon.Model.Users;
import com.example.hp.amajon.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText InputPhone,InputPassword;
    CheckBox checkBox;
    TextView forget,admin,non_admin;
    Button login;

    String parentDBname="Users";
    Vibrator v;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        InputPhone=(EditText)findViewById(R.id.phone_no_text_view);
        InputPassword=(EditText)findViewById(R.id.password_text_view);
        checkBox=(CheckBox)findViewById(R.id.checkBox);
        forget=(TextView)findViewById(R.id.forget_password_link);
        admin=(TextView)findViewById(R.id.register_as_admin_link);
        non_admin=(TextView)findViewById(R.id.register_as_non_admin_link);
        login=(Button)findViewById(R.id.login_button);

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        progressDialog=new ProgressDialog(this);
        Paper.init(this);
    }

    public void login_function(View view)
    {
        String phone,password;
        password=InputPassword.getText().toString();
        phone=InputPhone.getText().toString();

        if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Phone number is required!", Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(300);
            }
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Password is required!", Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(300);
            }
        }
        else
        {
            //DO HERE
            progressDialog.setTitle("Validating Details");
            progressDialog.setMessage("Hold back amigo while we verify your details..");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            validate(phone,password);
        }

    }

    private void validate(final String phone, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        //Writing to data to android for auto login.
        if(checkBox.isChecked())
        {
            Paper.book().write(Prevalent.phonekey,phone);
            Paper.book().write(Prevalent.passwordkey,password);
        }
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDBname).child(phone).exists())
                {
                    Users data=dataSnapshot.child(parentDBname).child(phone).getValue(Users.class);
                    Prevalent.currentUser=data;
                    if(data.getPhone().equals(phone))
                    {
                        if(data.getPassword().equals(password))
                        {
                            if(parentDBname.equals("Admins"))
                            {
                                Toast.makeText(LoginActivity.this, "You are successfully logged in as Admin!", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                Intent i=new Intent(LoginActivity.this,AdminCategoryActivity.class);
                                startActivity(i);
                            }
                            else if(parentDBname.equals("Users"))
                            {
                                Toast.makeText(LoginActivity.this, "You are successfully logged in!", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                Intent i=new Intent(LoginActivity.this,HomeActivity.class);
                                startActivity(i);
                            }
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Incorrect Password!! Click forget password button instead of wasting my time cause you are oblivious!", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Why am I seeing this? Report to the developers,cause it's a loop hole", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "You are not registered amigo! Register kar ke dekho aacha lagta hai.. And it will not cost you a dime!", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void login_as_admin_function(View view)
    {
        login.setText("Login as Admin");
        admin.setVisibility(View.INVISIBLE);
        non_admin.setVisibility(View.VISIBLE);
        checkBox.setVisibility(View.INVISIBLE);
        parentDBname="Admins";
        Toast.makeText(this, "For security reasons,I cannot remember information of Admins...", Toast.LENGTH_SHORT).show();
    }

    public void login_as_non_admin_function(View view)
    {
        login.setText("Login");
        admin.setVisibility(View.VISIBLE);
        non_admin.setVisibility(View.INVISIBLE);
        parentDBname="Users";
        checkBox.setVisibility(View.VISIBLE);
    }
}
