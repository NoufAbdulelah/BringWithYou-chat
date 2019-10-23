package com.SWEProject.bringwithyou.Activites;

import androidx.appcompat.app.AppCompatActivity;
import com.SWEProject.bringwithyou.R;
import android.widget.Toast;
import android.util.Log;

import android.os.Bundle;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("description");
        Log.i("OUR VALUE", name);
        Log.i("OUR VALUE 2", description);
        Toast.makeText(this,""+name, Toast.LENGTH_SHORT).show();
    }
}
