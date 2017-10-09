package com.skrzyneckik.hospitals;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.skrzyneckik.domain.Hospital;
import com.skrzyneckik.repository.HospitalsRepository;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class HospitalsScreen extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private HospitalsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton mSearchButton;
    private HospitalsRepository hospitalsRepository;

    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitals);

        mRecyclerView = findViewById(R.id.hospitals_list);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new HospitalsAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mSearchButton = findViewById(R.id.search);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO show search screen
            }
        });

        hospitalsRepository = new HospitalsRepository();
    }

    @Override
    protected void onStart() {
        super.onStart();

        subscription = hospitalsRepository.hospitals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Hospital>>() {
                               @Override
                               public void call(List<Hospital> hospitals) {
                                   mAdapter.update(hospitals);
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                //TODO inform user that reading hospital failed
                            }
                        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        subscription.unsubscribe();
    }
}
