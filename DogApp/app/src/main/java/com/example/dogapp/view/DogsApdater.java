package com.example.dogapp.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogapp.R;
import com.example.dogapp.model.DogBreed;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DogsApdater extends RecyclerView.Adapter<DogsApdater.ViewHolder> {

    private ArrayList<DogBreed> dogDataset;

    public DogsApdater(ArrayList<DogBreed> dogDataset) {
        this.dogDataset = dogDataset;
    }

    @NonNull
    @Override
    public DogsApdater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dog_library, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogsApdater.ViewHolder holder, int position) {
        holder.tvDogDesc.setText(dogDataset.get(position).getName());
        try {
            URL url = new URL(dogDataset.get(position).getUrl());
            holder.ivDogAvatar.setImageURL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        //holder.ivDogAvatar.setImageResource(R.drawable.ic_dog_1_background);
    }

    @Override
    public int getItemCount() {
        if(dogDataset != null) {
            return dogDataset.size();
        }
        return 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

    public UrlImageView ivDogAvatar;
    public TextView tvDogDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivDogAvatar = (UrlImageView)itemView.findViewById(R.id.imv_dog);
            tvDogDesc = itemView.findViewById(R.id.tv_desc);
        }
    }
}
