package com.SWEProject.bringwithyou.Activites;

import androidx.appcompat.app.AppCompatActivity;
import com.SWEProject.bringwithyou.R;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import com.SWEProject.bringwithyou.model.Order;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;
import com.google.firebase.database.FirebaseDatabase;
import android.view.LayoutInflater;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.SWEProject.bringwithyou.R;

import android.os.Bundle;

public class Activity4 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Order> arrayList;
    private FirebaseRecyclerOptions<Order> options;
    private FirebaseRecyclerAdapter<Order,FirebaseViewHolder> adapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);
        recyclerView = findViewById(R.id.RecyclerV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<Order>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Order");
        databaseReference.keepSynced(true);
        options = new FirebaseRecyclerOptions.Builder<Order>().setQuery(databaseReference,Order.class).build();

        adapter = new FirebaseRecyclerAdapter<Order,FirebaseViewHolder>(options) {
            @Override
            protected void onBindViewHolder( @NotNull FirebaseViewHolder holder, int position, @NotNull final Order model){
            holder.name.setText(model.getShopName());
            holder.name.setText(model.getDescription());
            holder.itemView.setOnClickListener(new View.OnClickListener(){
              @Override
              public void onClick(View view){
                  Intent intent = new Intent (Activity4.this, Main2Activity.class);
                  intent.putExtra("name",model.getShopName());
                  intent.putExtra("description",model.getDescription());
                  startActivity(intent);
              }
            });

            }

            @NotNull
            @Override
            public FirebaseViewHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, int i){
                return new FirebaseViewHolder(LayoutInflater.from(Activity4.this).inflate(R.layout.row,viewGroup,false));
            }
        };


        recyclerView.setAdapter(adapter);



    }


}
