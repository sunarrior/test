package com.example.dogapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.dogapp.databinding.ActivityMainBinding;
import com.example.dogapp.model.DogBreed;
import com.example.dogapp.view.DogsApdater;
import com.example.dogapp.viewmodel.DogApiService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private DogApiService apiService;
    private ArrayList<DogBreed> dogsList;
    private DogsApdater dogsApdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        dogsList = new ArrayList<>();
        binding.rvDogList.setLayoutManager(new GridLayoutManager(this, 2));

        apiService = new DogApiService();
        apiService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
                    @Override
                    public void onSuccess(@NonNull List<DogBreed> dogBreeds) {
                        for(DogBreed d : dogBreeds) {
                            //Log.d("DEBUG1", d.getName());
                            DogBreed dog = new DogBreed(
                                    d.getId(),
                                    d.getName(),
                                    d.getLifeSpan(),
                                    d.getOrigin(),
                                    d.getUrl()
                            );
                            dogsList.add(dog);
                        }
                        dogsApdater = new DogsApdater(dogsList);
                        binding.rvDogList.setAdapter(dogsApdater);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("DEBUG1", e.getMessage());
                    }
                });

    }
}