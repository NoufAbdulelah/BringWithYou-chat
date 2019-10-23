package com.SWEProject.bringwithyou.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;

import java.text.SimpleDateFormat;
import java.util.Date;

//import com.SWEProject.bringwithyou.Fregments.myRequest;
import com.SWEProject.bringwithyou.R;

import com.SWEProject.bringwithyou.model.Order;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;

import android.view.View;

import com.bumptech.glide.Glide;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;

public class Activity2 extends AppCompatActivity {
    private Button btn1;
    private EditText text;

   // private Firebase firebaseRef;

    //   private Firebase firebaseRef;
 //   private String mUserId;
  //  private Context context = this.getContext();


    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Order");

    // private Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_order);

        text = (EditText) findViewById(R.id.text);
        btn1 = (Button) findViewById(R.id.btn1);
        getIncomingIntent();



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (text.getText().length() == 0)
                    Toast.makeText(getApplicationContext(), "You forgot to write your order!", Toast.LENGTH_LONG).show();
else {
                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity2.this);

                // Set a title for alert dialog
                builder.setTitle("Confirmation Order");

                // Ask the final question
                builder.setMessage("Want to submit order?");

                // Set click listener for alert dialog buttons
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE: {
                                // User clicked the Yes button
                                Order od = new Order();
                                String des = text.getText().toString();
                                od.setDescription(des);

                                if (getIntent().hasExtra("ShopName") && getIntent().hasExtra("shopId") && getIntent().hasExtra("image")) {
                                    String ShopName = getIntent().getStringExtra("ShopName");
                                    int shopId = getIntent().getIntExtra("shopId",0);
                                   // int shopId = getIntent().getIntExtra("shopId",0);

                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
                                    String currentDateandTime = sdf.format(new Date());
                                    String BuyerID = "";
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    String name = user.getUid();


                                    od.setUserId(name);
                                    od.setShopId(shopId);
                                    od.setShopName(ShopName);
                                    od.setDate(currentDateandTime);

                                    od.setStatus("UnderReview");
                                    myRef.push().setValue(od);

                                    Toast.makeText(getApplicationContext(), "Your order is submitted succesfully", Toast.LENGTH_LONG).show();


                                            Intent home = new Intent(getApplicationContext(), Home2.class);
                                            startActivity(home);
                                            finish();

                                }

                                break;
                            }
                            case DialogInterface.BUTTON_NEGATIVE:
                                // User clicked the No button
                                // !!!!!!!!!Supposed to go to the home page!!!!!!!!!!!
                                break;
                        }
                    }
                };

                // Set the alert dialog yes button click listener
                builder.setPositiveButton("Submit", dialogClickListener);

                // Set the alert dialog no button click listener
                builder.setNegativeButton("Cancel", dialogClickListener);

                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();
            }
        }
        });
    }





    public void getIncomingIntent(){
    if (getIntent().hasExtra("ShopName")&& getIntent().hasExtra("shopId") && getIntent().hasExtra("image") && getIntent().hasExtra("menu")){
    String imageUrl = getIntent().getStringExtra("image");
    String shopId = getIntent().getStringExtra("shopId");
    String ShopName = getIntent().getStringExtra("ShopName");
    String menuUrl = getIntent().getStringExtra("menu");

        setImage(menuUrl,ShopName);

    }

    }



    private void setImage(String menuUrl,String ShName){

        TextView name = findViewById(R.id.ShopName);
        name.setText(ShName);

        ImageView ShopImage = findViewById(R.id.ShopImage);
        Glide.with(this)
                .asBitmap()
                .load(menuUrl)
                .into(ShopImage);


    }
}
