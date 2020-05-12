package com.example.hp.amajon;

import android.content.Intent;
import android.os.Bundle;

import com.example.hp.amajon.Model.Products;
import com.example.hp.amajon.Prevalent.Prevalent;
import com.example.hp.amajon.ViewHolder.ItemHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.*;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Toolbar toolbar;
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Product Info");
        Paper.init(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Item successfully added to your cart", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.username);
        CircleImageView profileImageView = headerView.findViewById(R.id.profile_image);
        userNameTextView.setText(Prevalent.currentUser.getName());
        //Picasso.get().load(Prevalent.currentUser.getImage()).placeholder(R.drawable.profile).into(profileImageView);


        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(ProductsRef, Products.class)
                        .build();


        FirebaseRecyclerAdapter<Products, ItemHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ItemHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ItemHolder holder, int position, @NonNull Products model)
                    {
                        holder.name.setText(model.getPname());
                        holder.description.setText(model.getDescription());
                        holder.price.setText("Price = " + model.getPrice() + "₹");
                        Picasso.get().load(model.getImage()).into(holder.img);
                    }

                    @NonNull
                    @Override
                    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, parent, false);
                        ItemHolder holder = new ItemHolder(view);
                        return holder;
                    }
                };
        Toast.makeText(this, "sssss", Toast.LENGTH_SHORT).show();
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

//        if (id == R.id.action_settings)
//        {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.cart)
        {

        }
        else if (id == R.id.orders)
        {

        }
        else if (id == R.id.categories)
        {

        }
        else if (id == R.id.settings)
        {
            Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.logout)
        {
            Paper.book().destroy();

            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}