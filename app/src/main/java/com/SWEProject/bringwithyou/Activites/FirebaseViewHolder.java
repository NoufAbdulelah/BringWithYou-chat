package com.SWEProject.bringwithyou.Activites;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import android.view.View;
import com.SWEProject.bringwithyou.R;

import android.widget.TextView;
public class FirebaseViewHolder extends RecyclerView.ViewHolder {

    public TextView name, description;

    public FirebaseViewHolder(@NotNull View itemView){
        super(itemView);

        name = itemView.findViewById(R.id.orderNameTV);
        description = itemView.findViewById(R.id.OrderDescription);
    }

}
