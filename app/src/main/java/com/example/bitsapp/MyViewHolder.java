package com.example.bitsapp;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    CircleImageView profileImage;
    ImageView postImage, likeImage , dislikeImage , commentsImage;
    TextView username, timeAgo, postDesc , likeCounter , dislikeCounter , commentsCounter;
    public MyViewHolder (@NonNull View itemView) {
        super(itemView);
        profileImage=itemView.findViewById(R.id.profileImagePost);
        postImage=itemView.findViewById(R.id.postImage);
        username=itemView.findViewById(R.id.profileUsernamePost);
        timeAgo=itemView.findViewById(R.id.timeAgo);
        postDesc=itemView.findViewById(R.id.postDesc);
        likeImage=itemView.findViewById(R.id.likeImage);
        dislikeImage=itemView.findViewById(R.id.dislikeImage);
        commentsImage=itemView.findViewById(R.id.commentsImage);
        likeCounter=itemView.findViewById(R.id.likeCounter);
        dislikeCounter=itemView.findViewById(R.id.dislikeCounter);
        commentsCounter=itemView.findViewById(R.id.commentsCounter);
    }

    public void countLikes(String postKey, String uid, DatabaseReference LikeRef) {

        LikeRef.child(postKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    int totalLikes = (int )snapshot.getChildrenCount();
                    likeCounter.setText(totalLikes+"");
                }
                else
                {
                    likeCounter.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        LikeRef.child(postKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(uid).exists())
                {
                    likeImage.setColorFilter(Color.GREEN);
                }
                else
                {
                    likeImage.setColorFilter(Color.BLACK);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void countDislikes(String postKey, String uid, DatabaseReference DislikeRef) {
        DislikeRef.child(postKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    int totalDislikes = (int )snapshot.getChildrenCount();
                    dislikeCounter.setText(totalDislikes+"");
                }
                else
                {
                    dislikeCounter.setText("0");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DislikeRef.child(postKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(uid).exists())
                {
                    dislikeImage.setColorFilter(Color.RED);
                }
                else
                {
                    likeImage.setColorFilter(Color.BLACK);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
