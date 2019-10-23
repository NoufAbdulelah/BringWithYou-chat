package com.SWEProject.bringwithyou.Activites;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.SWEProject.bringwithyou.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import de.hdodenhof.circleimageview.CircleImageView;


public class ChatActivity extends AppCompatActivity {

    private ImageButton sendMessageButton,sendImageFileButton;
    private EditText userMessageInput;
    private RecyclerView usersMessagesList;
    private final List<Messages> messagesList=new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private MessagesAdapter messageAdapter;

    private String messageRecieverID="ELubbo1IcOaqShoSK93Syq0Ymep1",messageRecieverName,messageSenderID,saveCurrentDate,saveCurrentTime;
    private TextView receiverName;
    private CircleImageView receiverProfileImage;
    private DatabaseReference root;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat2);
        mAuth= FirebaseAuth.getInstance();
        messageSenderID=mAuth.getCurrentUser().getUid();





         root= FirebaseDatabase.getInstance().getReference();

//        messageRecieverID=getIntent().getExtras().get("userID").toString();
//        messageRecieverName=getIntent().getExtras().get("userName").toString();


        initializedFields();
        DisplayReceiverInfo();

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();

            }
        });

       // fetchMessages();
        readMessage();


    }

    private void readMessage() {
       // messagesList = new ArrayList<>();
        usersMessagesList=(RecyclerView) findViewById(R.id.messages_list_users);
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Messages").child(messageSenderID).child(messageRecieverID);
            try {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        messagesList.clear();
                        Messages chat = null;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            chat = snapshot.getValue(Messages.class);
                            assert chat != null;
                           // if (chat.getReceiver().equals(myId) && chat.getSender().equals(userId) ||
                                  //  chat.getReceiver().equals(userId) && chat.getSender().equals(myId)) {
                            messagesList.add(chat);
                            }
                        messageAdapter = new MessagesAdapter(messagesList);
                        usersMessagesList.setAdapter(messageAdapter);
                        }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }


                });
            } catch (Exception e) {
                Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }


//    private void fetchMessages() {
//        root.child("Messages").child("UL04U1JRqRX3YHWKhW3C4Gsaetn1").child("ELubbo1IcOaqShoSK93Syq0Ymep1").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                    if(dataSnapshot.exists()){
//                    Messages messages= dataSnapshot.getValue(Messages.class);
//                    messagesList.add(messages);
//                    messageAdapter.notifyDataSetChanged();
//                    }
//
//
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }

    private void sendMessage() {
        String messageText= userMessageInput.getText().toString();
        if(TextUtils.isEmpty(messageText)){
            Toast.makeText(this,"please Type a message first ..",Toast.LENGTH_SHORT).show();

        }
        else{
            String messageSenderRef = "Messages/"+ messageSenderID +"/"+messageRecieverID;
            String messageReceiverRef = "Messages/"+ messageRecieverID +"/"+messageSenderID;

            DatabaseReference userMessageKey= root.child("Messages").child("messageSenderID").
                    child("messageReceiverID").push();

            String messagePushID =userMessageKey.getKey();




            Calendar calForDate= Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-YYYY");
            saveCurrentDate = currentDate.format(calForDate.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm aa");
            saveCurrentTime = currentTime.format(calForDate.getTime());

            Map messageTextBody = new HashMap();
            messageTextBody.put("message",messageText);
            messageTextBody.put("time",saveCurrentTime);
            messageTextBody.put("date",saveCurrentDate);
            messageTextBody.put("type","text");
            messageTextBody.put("from",messageSenderID);


            Map messageBodyDetails = new HashMap();
            messageBodyDetails.put(messageSenderRef+"/"+ messagePushID,messageTextBody);
            messageBodyDetails.put(messageReceiverRef+"/"+ messagePushID,messageTextBody);
            root.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {

                    if(task.isSuccessful()){
                        //Toast.makeText(ChatActivity.this,"message sent succesfully  ..",Toast.LENGTH_SHORT).show();
                        userMessageInput.setText("");

                    }
                    else{
                        String message= task.getException().getMessage();
                        Toast.makeText(ChatActivity.this,"Error "+message,Toast.LENGTH_SHORT).show();
                        userMessageInput.setText("");

                    }



                }
            }) ;








        }
    }

    private void DisplayReceiverInfo() {

        receiverName.setText(messageRecieverName);
        root.child("users").child(messageRecieverID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                receiverName.setText(dataSnapshot.child("name").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void initializedFields(){
        sendMessageButton=(ImageButton)findViewById(R.id.send_message_buttons);
        sendImageFileButton=(ImageButton)findViewById(R.id.send_message_file);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater layoutInflater=(LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actionBarView=layoutInflater.inflate(R.layout.chat_custom_bar,null);
        actionBar.setCustomView(actionBarView);


        userMessageInput=(EditText)findViewById(R.id.input_message);
        receiverProfileImage=(CircleImageView) findViewById(R.id.custom_profile_img);
        receiverName=(TextView)findViewById(R.id.custome_profile_name);


        //adapter
        messageAdapter= new MessagesAdapter(messagesList);
        usersMessagesList=(RecyclerView) findViewById(R.id.messages_list_users);
        linearLayoutManager= new LinearLayoutManager(this);
        usersMessagesList.setHasFixedSize(true);
        //usersMessagesList.setOrientation(linearLayoutManager.VERTICAL);
        usersMessagesList.setLayoutManager(linearLayoutManager);

        usersMessagesList.setAdapter(messageAdapter);






    }



}
