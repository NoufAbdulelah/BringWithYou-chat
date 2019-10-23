package com.SWEProject.bringwithyou.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.SWEProject.bringwithyou.R;
import com.SWEProject.bringwithyou.model.Resturant;

import com.SWEProject.bringwithyou.ui.home.adapter.PlaceAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements PlaceAdapter.OnPlaceClickListner {


    RecyclerView resturantsRV;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("Shops");

    private PlaceAdapter adapter;


    ProgressDialog dialog;

    EditText searchET;
    Button searchBTN;
    Spinner dropdown;

    Boolean firstTime = true ;


    String filterName = "All";
//added by raghad
    private Button btnAddOrder;
//

    private ArrayList<Resturant> places;
    private ArrayList<Resturant> searchPlaces;

    private ArrayList<Resturant> allPlaces;
    private ArrayList<Resturant> filtered;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dialog = ProgressDialog.show(getContext(), "",
                "Loading. Please wait...", true);
        dialog.show();
        resturantsRV = view.findViewById(R.id.resturantsRV);
        searchET = view.findViewById(R.id.searchET);
        searchBTN = view.findViewById(R.id.searchBTN);
        resturantsRV.setLayoutManager
                (new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        places = new ArrayList<>();
        adapter = new PlaceAdapter(places, R.layout.item_place, getContext(), this);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        getResturants();


        dropdown = view.findViewById(R.id.spinner1);
        final String[] items = new String[]{"All", "Restaurant", "Supermarket", "Cafe"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(spinnerAdapter);


        searchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchET.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Enter text first..", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.show();
                    adapter.clearPlaces();

                    for (Resturant r : filtered) {
                        if (r.getKey().toLowerCase().contains(searchET.getText().toString().toLowerCase())) {
                            adapter.addPlace(r);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    resturantsRV.setAdapter(adapter);
                    dialog.dismiss();
                }
            }
        });


        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (items[position].equals("All")) {
                    if(!firstTime) {
                        dialog.show();
                        adapter.clearPlaces();
                        adapter.addPlaces(allPlaces);
                        adapter.notifyDataSetChanged();
                        resturantsRV.setAdapter(adapter);
                        dialog.dismiss();
                    }else{
                        firstTime = false ;
                    }
                } else if (items[position].equals("Restaurant")) {
                    dialog.show();
                    adapter.clearPlaces();
                    filtered = new ArrayList<>();

                    for (Resturant r : allPlaces) {
                        if (r.getType().equals("Restaurant")) {
                            adapter.addPlace(r);
                            filtered.add(r);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    resturantsRV.setAdapter(adapter);
                    dialog.dismiss();
                } else if (items[position].equals("Supermarket")) {
                    dialog.show();
                    adapter.clearPlaces();
                    filtered = new ArrayList<>();
                    for (Resturant r : allPlaces) {
                        if (r.getType().equals("Supermarket")) {
                            adapter.addPlace(r);
                            filtered.add(r);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    resturantsRV.setAdapter(adapter);
                    dialog.dismiss();
                } else if (items[position].equals("Cafe")) {
                    dialog.show();
                    adapter.clearPlaces();
                    filtered = new ArrayList<>();

                    for (Resturant r : allPlaces) {
                        if (r.getType().equals("Cafe")) {
                            adapter.addPlace(r);
                            filtered.add(r);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    resturantsRV.setAdapter(adapter);
                    dialog.dismiss();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//raghad
        btnAddOrder = (Button) view.findViewById(R.id.btnAddOrder);
      // int s =  allPlaces.get(0).getShopID();

      /*  btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v){
                openActivity2();
            }

        });*/
        // raghad
    }// end oncreate

    public void getResturants() {
        FirebaseDatabase
                .getInstance()
                .getReference("Shops")
                .addChildEventListener(new ChildEventListener() {// listen to child
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String prevKey) {
                        Resturant place = dataSnapshot.getValue(Resturant.class);
                        place.setKey(dataSnapshot.getKey());
                        if (place != null) {
//                            if (place.getType().equals("Restaurant")) {
                            adapter.addPlace(place);
                            resturantsRV.setAdapter(adapter);
                            dialog.dismiss();
                            searchPlaces = new ArrayList<>(places);
                            allPlaces = new ArrayList<>(places);
                            filtered = new ArrayList<>();
                            filtered.addAll(places);
//                            }
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override

                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    } //end read

    @Override
    public void onPlaceClick(Resturant place) {

    }

    public void openActivity2(){
        Intent intent = new Intent();
        startActivity(intent);


    }
}