package com.SWEProject.bringwithyou.model;

import java.net.URI;
import android.net.Uri;

public class User {
    private String email;
    private String password;
    private String name;
    private String phone;
    private String id;
    //private Uri pickedImageUri ;
    private String pickedImageUri ;


    // private Integer Building;


    public User() {
        email = "";
        name = "";
        phone = "";
        id = "";
        password="";
        pickedImageUri= null ;

    }




    /*public User(String email, String name, String phone, String id , Uri pickedImageUri) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.id = id;
       this.pickedImageUri = pickedImageUri;
    }
*/

    public User(String email, String name, String phone, String id , String password , String pickedImageUri) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.id = id;
        this.password=password;
        this.pickedImageUri = pickedImageUri;

    }
    public User(String email, String name, String phone, String id , String password ) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.id = id;
        this.password=password;
       // this.pickedImageUri = pickedImageUri;

    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPhone() {
        return this.phone;
    }


    public String getPassword() {
        return this.password;
    }


    public void setPhone(String phone) {
        this.phone = phone ;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    String Key;
    public void setKey(String key) {
        Key = key;
    }
   /* public Uri getPickedImageUri() {
        return this.pickedImageUri;
    }

    public void setPickedImageUri(Uri pickedImageUri) {
        this.pickedImageUri = pickedImageUri;
    }*/

}
