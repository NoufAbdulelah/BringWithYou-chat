package com.SWEProject.bringwithyou.Activites;

import android.os.Bundle;

import com.SWEProject.bringwithyou.R;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import android.widget.Button;


public class AddOrder extends AppCompatActivity {
    private Button btnAddOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        btnAddOrder = (Button) findViewById(R.id.btnAddOrder);
        btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v){
            }

        });


    }



}