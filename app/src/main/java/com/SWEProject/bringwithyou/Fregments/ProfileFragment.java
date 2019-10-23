package com.SWEProject.bringwithyou.Fregments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.widget.ImageView;

import com.SWEProject.bringwithyou.Activites.Login;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.AuthResult;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.SWEProject.bringwithyou.R;
import com.SWEProject.bringwithyou.Activites.Home2;





import com.SWEProject.bringwithyou.Activites.Register;
import com.SWEProject.bringwithyou.model.Resturant;
import com.SWEProject.bringwithyou.model.User;
import com.SWEProject.bringwithyou.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.security.PrivateKey;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static android.widget.Toast.*;
import static com.SWEProject.bringwithyou.R.id.EditPhoto;
import static com.SWEProject.bringwithyou.R.id.emailbtn;
import static com.SWEProject.bringwithyou.R.id.image;
import static com.SWEProject.bringwithyou.R.id.nav_view;
//import static com.SWEProject.bringwithyou.R.id.reset;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
    String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    FirebaseAuth mAuth ;
    private TextView ProfEmail,ProfName ,ProfPhone ;
    private EditText EditName, editPhone;
    //private ProgressBar loadingProgress ;
    private Button ProfBtn , savechanges , cancel ,resetPass ;
    static int PReqCode=1;
    static int REQUESCODE=1;
    ImageView imageView ;
    Uri pickedImgUri ;








    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View v= inflater.inflate(R.layout.fragment_profile,container, false);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


        ProfEmail = (TextView) v.findViewById(R.id.ProfEmail);
        ProfName = (TextView) v.findViewById(R.id.Profname);
        ProfPhone= (TextView) v.findViewById(R.id.ProfPhone);
        editPhone =(EditText) v.findViewById(R.id.editPhone);
        resetPass = (Button) v.findViewById(R.id.resetPass);
        ProfBtn = (Button) v.findViewById(R.id.EditBtn);
        EditName =(EditText) v.findViewById(R.id.EditName);
        //confirmPass = (EditText) v.findViewById(R.id.confirmPass);

        savechanges =(Button) v.findViewById(R.id.Savechanges) ;
        cancel =(Button) v.findViewById(R.id.cancel) ;
        imageView=(ImageView) v.findViewById(R.id.EditPhoto);
       //reset=(Button) v.findViewById(R.id.reset) ;







        EditName.setVisibility(View.INVISIBLE);
//        EditPass.setVisibility(View.INVISIBLE);
        editPhone.setVisibility(View.INVISIBLE);
//        confirmPass.setVisibility(View.INVISIBLE);
        savechanges.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.INVISIBLE);
        resetPass.setVisibility(View.INVISIBLE);
      //  reset.setVisibility(View.INVISIBLE);









        FirebaseDatabase
                .getInstance()
                .getReference("users").child(userid).addListenerForSingleValueEvent(new ValueEventListener() {

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                // user.setKey();
                ProfName.setText(dataSnapshot.child("name").getValue().toString());
                ProfEmail.setText(dataSnapshot.child("email").getValue().toString());
                ProfPhone.setText(dataSnapshot.child("phone").getValue().toString());
              ImageView ImgUserPhoto= getActivity().findViewById(R.id.EditPhoto);

                ImgUserPhoto.setImageURI(pickedImgUri);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        ProfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditName.setVisibility(View.VISIBLE);
               // EditPass.setVisibility(View.VISIBLE);
                editPhone.setVisibility(View.VISIBLE);
              //  confirmPass.setVisibility(View.VISIBLE);


                ProfBtn.setVisibility(View.INVISIBLE);

                ProfEmail.setVisibility(View.INVISIBLE);
                ProfName.setVisibility(View.INVISIBLE);
                ProfPhone.setVisibility(View.INVISIBLE);
                savechanges.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);
                resetPass.setVisibility(View.VISIBLE);
                EditUser();

            }
        });




        return v;

    }


    public void getUser() {
       /* FirebaseDatabase
                .getInstance()
                .getReference("users")
                .addChildEventListener(new ChildEventListener() {// listen to child
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String prevKey) {
                        User user = dataSnapshot.getValue(User.class);
                        user.setKey(dataSnapshot.getKey());
                        ProfName.setText(dataSnapshot.child("name").getValue().toString());
                        ProfEmail.setText(dataSnapshot.child("email").getValue().toString());
                        ProfPhone.setText(dataSnapshot.child("phone").getValue().toString());

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
    }  *///end read

        DatabaseReference mdata;
        mdata = FirebaseDatabase.getInstance().getReference();

//        ValueEventListener postListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // Get Post object and use the values to update the UI
//                User user = dataSnapshot.getValue(User.class);
//                // [START_EXCLUDE]
//
////                ProfName.setText(user.name);
////                ProfEmail.setText(user.email);
////                ProfPhone.setText(user.phone);
////                // [END_EXCLUDE]
////            }
////
////            @Override
////            public void onCancelled(DatabaseError databaseError) {
////                // Getting Post failed, log a message
////                Log.w("UsersDetailActivity", "loadUsers:onCancelled", databaseError.toException());
////                // [START_EXCLUDE]
////                //Toast.makeText(ProfileFragment.this, "Failed to load users.", LENGTH_SHORT).show();
////                // [END_EXCLUDE]
////            }
////        };
////        FirebaseDatabase.getInstance().getReference().addValueEventListener(postListener);
//
//    }
//
//
    }

 public void EditUser() {


        final DatabaseReference mDatabase;

        mDatabase = FirebaseDatabase.getInstance().getReference();

        EditName.setText(ProfName.getText().toString());
        editPhone.setText(ProfPhone.getText().toString());


     resetPass.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             //nouf added this
             FirebaseAuth.getInstance().sendPasswordResetEmail(ProfEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                 @Override
                 public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getActivity(), "You can change your passowrd,Check your email! !", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        Intent login =new Intent(getActivity(), Login.class);
                        startActivity(login);

                    }


                 }
             });




         }
     });


        FirebaseDatabase
                .getInstance()
                .getReference("users").child(userid).addListenerForSingleValueEvent(new ValueEventListener() {

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                // user.setKey();
               // EditPass.setText(dataSnapshot.child("password").getValue().toString());
               // confirmPass.setText(dataSnapshot.child("password").getValue().toString());
//                ImageView ImgUserPhoto= getActivity().findViewById(R.id.EditPhoto);
//
//                String pathName = dataSnapshot.child("password").getValue().toString().trim();
//                Drawable image = Drawable.createFromPath(pathName);
//
//                ImgUserPhoto.setImageDrawable(image);
            }






            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //photo
        ImageView ImgUserPhoto= getActivity().findViewById(R.id.EditPhoto);

        ImgUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=22){
                    checkAndRequestForPermission();

                }

                else{
                    openGallery();

                }
            }
        });


//        updatePhoto();







        savechanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                final String name = EditName.getText().toString();
                final String phone = editPhone.getText().toString();
                //final String password = EditPass.getText().toString();
                //final String passwordConf = confirmPass.getText().toString();
                final String email = ProfEmail.getText().toString();
                String id = userid;
                final String authPhone = phone;
                String phoneV = phone;


//                if (!(phone.equals(authPhone) || name.equals(ProfName)) || !password.equals(passwordConf) || (!(phone.length() == 10) && !(phoneV.substring(0, 1).equals("05"))) ) {
//                    if (!password.equals(passwordConf)) {
//                        Toast.makeText(getActivity(), "Password does not match !", Toast.LENGTH_SHORT).show();
//
//                        if (password.length() < 3) {
//                            Toast.makeText(getActivity(), "Password is too short", Toast.LENGTH_SHORT).show();
//
//                        }
//
//                    }
//
//                    if (!(phone.length() == 10) && !(phoneV.substring(0, 1).equals("05"))) {
//                        Toast.makeText(getActivity(), "Invalid Phone number !", Toast.LENGTH_SHORT).show();
//
//                    }
//                }


//

                FirebaseDatabase.getInstance().getReference("users").child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

//                                          dataSnapshot.getRef().child("email").setValue(email);
//                                          dataSnapshot.getRef().child("name").setValue(name);
//                                          dataSnapshot.getRef().child("password").setValue(password);
//                                          dataSnapshot.getRef().child("phone").setValue(phone);
                        DatabaseReference mdata;
                        mdata=FirebaseDatabase.getInstance().getReference("users");

                        mdata.child(userid).child("email").setValue(email);
                        mdata.child(userid).child("name").setValue(name);
                       // mdata.child(userid).child("password").setValue(password);
                        mdata.child(userid).child("phone").setValue(phone);
                        //mdata.child("users").child("pickedImageUri").setValue(pickedImgUri.getPath().toString().trim());

                        checkVal(name,phone);

                        //user photo
                        // updateUserInfo(pickedImgUri,name, mAuth.getCurrentUser());


                        //update navegation
//                        updateNavegation();
//                        Toast.makeText(getActivity(), "Your Profile is saved successfully", Toast.LENGTH_SHORT).show();


                        //dialog.dismiss();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("User", databaseError.getMessage());
                    }
                });




                //Toast



            }
        }  );



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditName.setVisibility(View.INVISIBLE);
               // EditPass.setVisibility(View.INVISIBLE);
                editPhone.setVisibility(View.INVISIBLE);
              //  confirmPass.setVisibility(View.INVISIBLE);


                ProfBtn.setVisibility(View.VISIBLE);

                ProfEmail.setVisibility(View.VISIBLE);
                ProfName.setVisibility(View.VISIBLE);
                ProfPhone.setVisibility(View.VISIBLE);
                savechanges.setVisibility(View.INVISIBLE);
                cancel.setVisibility(View.INVISIBLE);

            }
        });




























//        FirebaseDatabase
//                .getInstance()
//                .getReference("users")
//                .addChildEventListener(new ChildEventListener() {// listen to child
//                    @Override
//                    public void onChildAdded(DataSnapshot dataSnapshot, String prevKey) {
//                        final User user = dataSnapshot.getValue(User.class);
//                        user.setKey(dataSnapshot.getKey());
//                        EditName.setText(dataSnapshot.child("name").getValue().toString());
//                        editPhone.setText(dataSnapshot.child("phone").getValue().toString());
//                        EditPass.setText(dataSnapshot.child("password").getValue().toString());
//                        confirmPass.setText(dataSnapshot.child("password").getValue().toString());
//
//                        savechanges.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//
//
//                               String name =  EditName.getText().toString();
//                                String phone =  editPhone.getText().toString();
//                                String pass =  EditPass.getText().toString();
//                                String passConf =  confirmPass.getText().toString();
//
//
//                                if(!pass.equals(passConf)){
//                                    //Toast.
//                                }
//
//                                else{
//                                user.setName(name);
//                                user.setPhone(phone);
//                                user.setPassword(pass);
//                                user.setPassword(passConf);
//
//
//                                //reference = reference.child(userid);
//                                    reference.setValue(user);
//                                }
//
//
//
//
//
//
//
//
//                            }
//                        });
//
//
//                    }
//
//
//                });
    } //end read

    private void checkVal(String name, final String phone) {


        if (name.isEmpty()) {
            EditName.setError("name required");
            EditName.requestFocus();
            return;
        }




//        if (password.isEmpty()) {
//            EditPass.setError("password required");
//            EditPass.requestFocus();
//
//            return;
//        }
//
//        if (passwordConf.isEmpty()) {
//            confirmPass.setError("confirm password required");
//            confirmPass.requestFocus();
//
//            return;
//        }
//
//        if (password.length() < 6) {
//            EditPass.setError("password should be atleast 6 characters long");
//            EditPass.requestFocus();
//
//            return;
//        }
//
//        if (!passwordConf.equals(password)) {
//            confirmPass.setError("password mismatch");
//            confirmPass.requestFocus();
//
//            return;
//        }

        if (phone.isEmpty()) {
            editPhone.setError("Phone required");
            editPhone.requestFocus();

            return;
        }

        if (phone.length() != 10) {
            editPhone.setError("enter a valid phone number");
            editPhone.requestFocus();

            return;
        }

        String phone2=phone;
        if (!phone2.substring(0,2).equals("05")) {
            editPhone.setError("phone should start with 05********");
            editPhone.requestFocus();

            return;
        }

        updateNavegation();
        Toast.makeText(getActivity(), "Your Profile is saved successfully", Toast.LENGTH_SHORT).show();
        Intent home =new Intent(getActivity(), Home2.class);
        startActivity(home);


    }

    private void updatePhoto() {


        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
        final StorageReference imageFilePath = mStorage.child(pickedImgUri.getLastPathSegment());
        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(mAuth.getCurrentUser().getDisplayName())
                                .setPhotoUri(uri)
                                .build();

                        mAuth.getCurrentUser().updateProfile(profileUpdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            // user info updated successful

                                        }

                                    }
                                });
                    }

                });


            }

        });

    }



    private void updateNavegation() {


        NavigationView navigationView = (NavigationView) getActivity().findViewById(nav_view);
        final View headerView =navigationView.getHeaderView(0);
        final TextView navUsername = headerView.findViewById(R.id.nav_username);
        FirebaseDatabase
                .getInstance()
                .getReference("users").child(userid).addListenerForSingleValueEvent(new ValueEventListener() {

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                // user.setKey();
                navUsername.setText(dataSnapshot.child("name").getValue().toString());
                ProfPhone.setText(dataSnapshot.child("phone").getValue().toString());
                ProfName.setText(dataSnapshot.child("name").getValue().toString());
                ImageView navUserPhot = headerView.findViewById(R.id.nav_user_photo);
                navUserPhot.setImageURI(pickedImgUri);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






    }



    private void updateUserInfo(Uri pickedImgUri ,final  String name , final FirebaseUser currentUser) {


        // first we need to upload user photo to firebase and get url

        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");

        final StorageReference imageFilePath = mStorage.child(pickedImgUri.getLastPathSegment());
        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {





                // image uploaded succefully
                // now we can get our image url
                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        // uri contain user image url
                       // FirebaseStorage.getInstance().getReference().



                        UserProfileChangeRequest profileUpdate =new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .setPhotoUri(uri)
                                .build();


                        currentUser.updateProfile(profileUpdate)

                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){
                                            // user info updated successful
                                            Toast.makeText(getActivity(),"Your Profile is saved successfully",Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });



                    }
                });



            }
        });

    }




    private void openGallery() {
        //TODO: open gallery intent and wait for user to pick an image !
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODE);
    }

    private void checkAndRequestForPermission() {
        if(ContextCompat.checkSelfPermission(getActivity() , Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)){

                Toast.makeText(getActivity() , "Please accept for required permission" ,Toast.LENGTH_SHORT).show();
            }

            else{
                ActivityCompat.requestPermissions(getActivity() ,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE },
                        PReqCode);
            }
        }

        else openGallery();

    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        ImageView ImgUserPhoto= getActivity().findViewById(R.id.EditPhoto);
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== RESULT_OK && requestCode == REQUESCODE && data != null){
            // the user ha successfully picked an image
            // we need to save its refrence to a Uri variable
            pickedImgUri = data.getData();
            ImgUserPhoto.setImageURI(pickedImgUri);
        }
    }

    private void setImage(String profUrl){



        ImageView Photo = getActivity().findViewById(EditPhoto);
        Glide.with(this)
                .asBitmap()
                .load(profUrl)
                .into(Photo);


    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

