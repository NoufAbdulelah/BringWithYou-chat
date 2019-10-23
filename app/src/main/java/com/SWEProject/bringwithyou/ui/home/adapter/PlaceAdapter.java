package com.SWEProject.bringwithyou.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.SWEProject.bringwithyou.R;
import com.SWEProject.bringwithyou.model.Resturant;
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


public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.VH>{

    private static final String TAG = "PlaceAdapter";
    private int rowLayout;
    private Context context;
    private OnPlaceClickListner onPlaceClick;
    private ArrayList<Resturant> places;


    public PlaceAdapter(Context context) {
        this.context = context;
    }

    public static class VH extends RecyclerView.ViewHolder {

        TextView placeReiewsTV, placeLocationTV, placeNameTV;
        ImageView placeIV;
        Button btnAddOrder;

        public VH(View v) {
            super(v);
            placeReiewsTV = v.findViewById(R.id.placeReiewsTV);
            placeLocationTV = v.findViewById(R.id.placeLocationTV);
            placeNameTV = v.findViewById(R.id.placeNameTV);
            placeIV = v.findViewById(R.id.placeIV);
            btnAddOrder = v.findViewById(R.id.btnAddOrder);

        }



    }

    public PlaceAdapter(ArrayList<Resturant> places, int rowLayout, Context context, OnPlaceClickListner listner) {

        this.places = places;
        this.rowLayout = rowLayout;
        this.context = context;
        this.onPlaceClick = listner;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(final VH holder, final int position) {
        holder.placeReiewsTV.setText("Building : " + places.get(position).getBuilding());
        holder.placeLocationTV.setText(places.get(position).getBuildingName());
        holder.placeNameTV.setText(places.get(position).getKey());
        Glide.with(context).load(places.get(position).getImage()).into(holder.placeIV);
        // Glide.with(context).load(places.get(position).getMenu()).into(holder.placeIV);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPlaceClick.onPlaceClick(places.get(position));}
        });

        holder.btnAddOrder.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), Activity2.class);

                String strName = places.get(position).getKey();
                intent.putExtra("ShopName", strName);

                int Shopid = places.get(position).getShopID();
                intent.putExtra("shopId", Shopid);

                String image2 = places.get(position).getImage();
                intent.putExtra("image", image2);

                String menu2 = places.get(position).getMenu();
                intent.putExtra("menu", menu2);

                context.startActivity(intent);


                //  startActivity(intent);

                //Intent intent = new Intent(Intent.ACTION_VIEW);
                //       if (intent.resolveActivity(PlaceAdapter.this != null)) {




                //         }

                //  Intent activity2Intent = new Intent(context, Activity2.class);
                // startActivity(activity2Intent);
            }

        });
    /*   holder.btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Toast.makeText(context, "The position is: " + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }
        });*/



    }
    public void addPlace(Resturant place) {
        places.add(place);
        notifyDataSetChanged();
    }

    public void addPlaces(ArrayList<Resturant> place) {
        places.addAll(place);
        notifyDataSetChanged();
    }

    public void clearPlaces() {
        places.clear();
        notifyDataSetChanged();
    }

    public interface OnPlaceClickListner {
        public void onPlaceClick(Resturant place);
    }

    public void gj() {

        Context oContext;
        //oContext= mContext;
        //    Intent i = new Intent(android.content.Intent.ACTION_VIEW,Activity2.class);
        //oContext.startActivity(i);

        /*
        Intent intent = new Intent ( mContext, Activity2.class  );
        startActivity(intent);*/
    }


    @Override
    public int getItemCount() {
        return places.size();
    }
}

