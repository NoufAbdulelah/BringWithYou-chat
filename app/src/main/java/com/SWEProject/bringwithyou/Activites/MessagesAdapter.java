package com.SWEProject.bringwithyou.Activites;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.SWEProject.bringwithyou.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {

    private List<Messages> userMessagesList;
    private FirebaseAuth mAuth;
    private DatabaseReference usersDatabaseRef;
    private Context context;

    public MessagesAdapter(Context context) {
        this.context = context;
    }

    public MessagesAdapter(List<Messages> userMessagesList){
        this.userMessagesList=userMessagesList;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView senderMessageText,recieverMessageText;
        public CircleImageView recieverProfileImg;


        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            senderMessageText=(TextView)itemView.findViewById(R.id.sender_text_message);
            recieverMessageText=(TextView)itemView.findViewById(R.id.receiver_text_message);
            recieverProfileImg=(CircleImageView) itemView.findViewById(R.id.message_profile_image);
        }

    }


    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_lauput_of_users,parent,false);
        mAuth=FirebaseAuth.getInstance();
        return new MessageViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder holder, final int position) {
        String messageSenderID=mAuth.getCurrentUser().getUid();
        Messages messages=userMessagesList.get(position);
        String fromUserID = messages.getFrom();
        String fromMessageType= messages.getType();
        usersDatabaseRef= FirebaseDatabase.getInstance().getReference("users").child(messageSenderID);

        usersDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if(dataSnapshot.exists()){
                   // String img= dataSnapshot.child()

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(fromMessageType.equals("text")){
            holder.recieverMessageText.setVisibility(View.INVISIBLE);
            holder.recieverProfileImg.setVisibility(View.INVISIBLE);
            if(fromUserID.equals(messageSenderID)){
                holder.senderMessageText.setBackgroundResource(R.drawable.bubble_in);
                holder.senderMessageText.setTextColor(Color.WHITE);
                holder.senderMessageText.setGravity(Gravity.LEFT);
                holder.senderMessageText.setText(messages.getMessage());

            }
            else {

                holder.senderMessageText.setVisibility(View.INVISIBLE);
                holder.recieverMessageText.setVisibility(View.VISIBLE);
                holder.recieverProfileImg.setVisibility(View.VISIBLE);

                holder.recieverMessageText.setBackgroundResource(R.drawable.bubble_out);
                holder.recieverMessageText.setTextColor(Color.WHITE);
                holder.recieverMessageText.setGravity(Gravity.LEFT);
                holder.recieverMessageText.setText(messages.getMessage());


            }


        }


    }

    @Override
    public int getItemCount() {
        return userMessagesList.size();
    }


}
