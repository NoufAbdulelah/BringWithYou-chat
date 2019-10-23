package com.SWEProject.bringwithyou.Activites;
import com.SWEProject.bringwithyou.Fregments.RequestFragment;
import com.SWEProject.bringwithyou.Fregments.myOrders;
import com.SWEProject.bringwithyou.R;

import android.content.Intent;
import android.os.Bundle;

import com.SWEProject.bringwithyou.Fregments.ProfileFragment;
import com.SWEProject.bringwithyou.Fregments.AboutUsFragment;


import com.SWEProject.bringwithyou.model.User;
import com.SWEProject.bringwithyou.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class Home2 extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mAuth =FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chat = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(chat);
            }
        });
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this
                ,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        updateNavHeader();

    }
    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
       /* mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }*/



    @Override
    public void onBackPressed(){
        DrawerLayout drawer =(DrawerLayout)findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home2, menu);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("statmentWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item){

        // Fragment fragment = null;
        int id=item.getItemId();

        if(id==R.id.nav_home) {
            getSupportActionBar().setTitle("Home");
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();
        }else if (id==R.id.nav_profile){
            getSupportActionBar().setTitle("Profile");
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new ProfileFragment()).commit();
        }
        else if (id==R.id.nav_AboutUs){
            getSupportActionBar().setTitle("About Us");
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new AboutUsFragment()).commit();

        }
        else if (id==R.id.nav_request){

            // fragment = new RequestFragment();
            getSupportActionBar().setTitle("Requests");
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new RequestFragment()).commit();


        }else if (id==R.id.nav_myOrder){

            // fragment = new RequestFragment();
            getSupportActionBar().setTitle("My Orders");
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new myOrders()).commit();


        }else if(id==R.id.nav_signout){
            FirebaseAuth.getInstance().signOut();
            Intent login =new Intent(getApplicationContext(),Login.class);
            startActivity(login);
            finish();
        }
        DrawerLayout drawer =(DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    /* @Override
     public boolean onSupportNavigateUp() {
         NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
         return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                 || super.onSupportNavigateUp();
     }*/
    public void updateNavHeader(){

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        View headerView =navigationView.getHeaderView(0);
        final TextView navUsername = headerView.findViewById(R.id.nav_username);
        final TextView navUserMail = headerView.findViewById(R.id.nav_user_mail);
        ImageView navUserPhot = headerView.findViewById(R.id.nav_user_photo);

//        navUserMail.setText(currentUser.getEmail());
//        navUsername.setText(currentUser.getDisplayName());


        FirebaseDatabase
                .getInstance()
                .getReference("users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                // user.setKey();
                navUsername.setText(dataSnapshot.child("name").getValue().toString());
                navUserMail.setText(dataSnapshot.child("email").getValue().toString());

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        if(currentUser.getPhotoUrl() != null){


            Glide.with(this).load(currentUser.getPhotoUrl()).into(navUserPhot);}

        else {
            Glide.with(this).load(R.drawable.logo3).into(navUserPhot);

        }


    }


}

