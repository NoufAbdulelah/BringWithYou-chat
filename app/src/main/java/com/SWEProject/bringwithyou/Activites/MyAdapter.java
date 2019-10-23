package com.SWEProject.bringwithyou.Activites;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.SWEProject.bringwithyou.R;
import com.SWEProject.bringwithyou.model.Order;
import com.SWEProject.bringwithyou.Activites.Activity2;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import android.widget.Toast;
import android.content.Intent;
import android.content.Context;
import android.widget.Button;


import android.app.AlertDialog;
import android.content.DialogInterface;

import android.app.Activity;
import android.view.View.OnClickListener;
import android.view.View;

import android.content.pm.PackageManager;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        Context context;
        ArrayList<Order> orders;


        public MyAdapter(Context c, ArrayList<Order> o) {
            context = c;
            orders = o;
        }

        @Override
        public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.orderName.setText(orders.get(position).getShopName());
        holder.orderUserId.setText(orders.get(position).getUserId());
        holder.orderDecription.setText(orders.get(position).getDescription());
        //for adding picture
        // Picasso.get().load(orders.get(position).getOrderImage().into(holder.orderImage);


        }

        @Override
        public int getItemCount() {
            return orders.size();
        }


        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView orderName, orderUserId, orderDecription;
          //  ImageView orderImage;
            Button AcceptOrderBtn;

            public MyViewHolder(View itemView) {
                super(itemView);
                orderName = (TextView) itemView.findViewById(R.id.orderNameTV);
             //   orderImage = (ImageView) itemView.findViewById(R.id.orderIV);
                //orderUserId = (TextView) itemView.findViewById(R.id.OrderUserId);
                orderDecription = (TextView) itemView.findViewById(R.id.OrderDescription);
               //AcceptOrderBtn = (Button) itemView.findViewById(R.id.AcceptOrderBtn);


            }
        }

}