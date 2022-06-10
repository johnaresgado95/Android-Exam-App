package com.myapplication.condingexam.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.myapplication.condingexam.R;
import com.myapplication.condingexam.model.Notification;
import com.myapplication.condingexam.model.Products;
import com.myapplication.condingexam.screens.single_product_screen;

import java.util.List;
import java.util.Objects;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    private Context context;
    private List<Notification> notifsList;

    public NotificationsAdapter(Context context, List<Notification> notifsList) {
        this.context = context;
        this.notifsList = notifsList;
    }

    @NonNull
    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);

        return new NotificationsAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NotificationsAdapter.ViewHolder holder, int position) {
        Notification notifs = notifsList.get(position);
        holder.n_title.setText(notifs.getTitle());
        holder.n_subtitle.setText(notifs.getDescription());
    }

    @Override
    public int getItemCount() {
        int limit = 12;
        if (notifsList.size() > limit) {
            return limit;
        }
        return notifsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView n_title, n_subtitle;
        ImageView n_remove;
        CardView nt_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            n_title = itemView.findViewById(R.id.n_title);
            n_subtitle = itemView.findViewById(R.id.n_subtitle);
            n_remove = itemView.findViewById(R.id.n_remove);
            nt_layout = itemView.findViewById(R.id.nt_layout);

            n_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notifsList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifsList.size();
                }
            });

            nt_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Notification notifs = notifsList.get(getAdapterPosition());
                    BottomSheetDialog bs = new BottomSheetDialog(context);
                    bs.setContentView(R.layout.bottom_sheet);

                    TextView bsnot_title = bs.findViewById(R.id.bsnot_title);
                    TextView bsnot_description = bs.findViewById(R.id.bsnot_description);

                    Objects.requireNonNull(bsnot_title).setText(notifs.getTitle());
                    Objects.requireNonNull(bsnot_description).setText(notifs.getDescription());

                    bs.show();
                }
            });
        }
    }
}
