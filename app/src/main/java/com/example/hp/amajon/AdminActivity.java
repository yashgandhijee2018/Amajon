package com.example.hp.amajon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hp.amajon.Model.Users;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminActivity extends AppCompatActivity {
    String CategoryName,name,description,price;
    String date,time,key;
    String downloadImageUrl;
    static final int pick=1;
    Uri ImageUri;
    ImageView img;
    EditText InputProductName, InputProductDescription, InputProductPrice;
    Vibrator v;
    ProgressDialog loadingBar;
    StorageReference ImageReference;
    DatabaseReference DataReference;
    public void add_product_function(View view)
    {
        name=InputProductName.getText().toString();
        description=InputProductDescription.getText().toString();
        price=InputProductPrice.getText().toString();

        if(ImageUri==null)
        {
            Toast.makeText(this, "Image is required amigo!", Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(300);
            }
        }
        else if(TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Can you sell without a name? Join MBA instead!", Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(300);
            }
        }
        else if(TextUtils.isEmpty(description))
        {
            Toast.makeText(this, "No description means no customers!! Do MBA... ", Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(300);
            }
        }
        else if(TextUtils.isEmpty(price))
        {
            Toast.makeText(this, "Are you going to sell that for free?? hahaha...", Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(300);
            }
        }
        else
        {
            store_image_in_firebase_function();
        }
    }

    private void store_image_in_firebase_function()
    {
        loadingBar.setTitle("Adding Product...");
        loadingBar.setMessage("Hold on! You haven't paid a penny to us anyways...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        date = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        time = currentTime.format(calendar.getTime());

        key=date+time;

        final StorageReference filePath = ImageReference.child(ImageUri.getLastPathSegment() + key + ".jpg");
        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(AdminActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(AdminActivity.this, "Uploaded Image successfully! Now I want 50% of the profit!", Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    v.vibrate(300);
                }

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(AdminActivity.this, "Now I want 70% of the profit...", Toast.LENGTH_SHORT).show();

                            store_image_information_in_firebase_function();
                        }
                    }
                });
            }
        });
    }

    private void store_image_information_in_firebase_function() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("ProductID", key);
        map.put("date", date);
        map.put("time", time);
        map.put("description", description);
        map.put("image", downloadImageUrl);
        map.put("category", CategoryName);
        map.put("price", price);
        map.put("pname", name);

        DataReference.child(key).updateChildren(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(AdminActivity.this, AdminCategoryActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(AdminActivity.this, "Product is added successfully... \nHey what about 90% ?", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AdminActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void log_out_function(View view) //NO ONE IS CALLING ME...
    {
        Paper.book().destroy();
        Intent i=new Intent(AdminActivity.this,MainActivity.class);
        startActivity(i);
    }

    public void add_image_function(View view)
    {
        Intent galleryIntent=new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,pick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==pick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            ImageUri = data.getData();
            img.setImageURI(ImageUri);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toast.makeText(this, "Inside admin activity", Toast.LENGTH_SHORT).show();
        Paper.init(this);
        CategoryName=getIntent().getExtras().get("category").toString();

        img=(ImageView)findViewById(R.id.select_product_image);
        InputProductName=(EditText)findViewById(R.id.product_name);
        InputProductDescription=(EditText)findViewById(R.id.product_description);
        InputProductPrice=(EditText)findViewById(R.id.product_price);

        loadingBar=new ProgressDialog(this);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        ImageReference= FirebaseStorage.getInstance().getReference().child("Product Image");
        DataReference= FirebaseDatabase.getInstance().getReference().child("Product Info");
    }
}
