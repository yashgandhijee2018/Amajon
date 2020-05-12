package com.example.hp.amajon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {

    ImageView i00,i01,i02,i03;
    ImageView i10,i11,i12,i13;
    ImageView i20,i21,i22,i23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        i00=(ImageView)findViewById(R.id.im00);
        i01=(ImageView)findViewById(R.id.im01);
        i02=(ImageView)findViewById(R.id.im02);
        i03=(ImageView)findViewById(R.id.im03);

        i10=(ImageView)findViewById(R.id.im10);
        i11=(ImageView)findViewById(R.id.im11);
        i12=(ImageView)findViewById(R.id.im12);
        i13=(ImageView)findViewById(R.id.im13);

        i20=(ImageView)findViewById(R.id.im20);
        i21=(ImageView)findViewById(R.id.im21);
        i22=(ImageView)findViewById(R.id.im22);
        i23=(ImageView)findViewById(R.id.im23);

        //___________________________________________________________________________________________________________________________________________________________________________________________________________//
        i00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategoryActivity.this,AdminActivity.class);
                i.putExtra("category","tShirts");
                startActivity(i);
            }
        });

        i01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategoryActivity.this,AdminActivity.class);
                i.putExtra("category","Sports tShirts");
                startActivity(i);
            }
        });

        i02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategoryActivity.this,AdminActivity.class);
                i.putExtra("category","Female Dresses");
                startActivity(i);
            }
        });

        i03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategoryActivity.this,AdminActivity.class);
                i.putExtra("category","Sweaters");
                startActivity(i);
            }
        });
        //___________________________________________________________________________________________________________________________________________________________________________________________________________//

        i10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategoryActivity.this,AdminActivity.class);
                i.putExtra("category","Glasses");
                startActivity(i);
            }
        });

        i11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategoryActivity.this,AdminActivity.class);
                i.putExtra("category","Purse");
                startActivity(i);
            }
        });

        i12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategoryActivity.this,AdminActivity.class);
                i.putExtra("category","Hats");
                startActivity(i);
            }
        });

        i13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategoryActivity.this,AdminActivity.class);
                i.putExtra("category","Shoes");
                startActivity(i);
            }
        });
        //___________________________________________________________________________________________________________________________________________________________________________________________________________//

        i20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategoryActivity.this,AdminActivity.class);
                i.putExtra("category","Headphone");
                startActivity(i);
            }
        });

        i21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategoryActivity.this,AdminActivity.class);
                i.putExtra("category","Laptops");
                startActivity(i);
            }
        });

        i22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategoryActivity.this,AdminActivity.class);
                i.putExtra("category","Watches");
                startActivity(i);
            }
        });

        i23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategoryActivity.this,AdminActivity.class);
                i.putExtra("category","Mobile Phone");
                startActivity(i);
            }
        });
        //___________________________________________________________________________________________________________________________________________________________________________________________________________//
    }
}
