package com.skrzyneckik.hospitals;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.skrzyneckik.domain.Hospital;
import com.skrzyneckik.repository.HospitalsRepository;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class HospitalsScreen extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private HospitalsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton mSearchButton;
    private HospitalsRepository hospitalsRepository;

    private CompositeSubscription subscriptions;

    List<String> osdCodes;

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
        mSearchButton.setOnClickListener(v -> {
            //TODO show search screen
        });

        hospitalsRepository = new HospitalsRepository();

        osdCodes = Collections.emptyList();
    }

    @Override
    protected void onStart() {
        super.onStart();

        subscriptions = new CompositeSubscription();

        Observable<List<Hospital>> hospitalsObservable = hospitalsRepository.hospitals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .share();

        subscriptions.add(hospitalsObservable
                .subscribe(hospitals -> mAdapter.update(hospitals),
                        throwable -> {
                            //TODO inform user that reading hospital failed
                        }));

        subscriptions.add(hospitalsObservable
                .compose(HospitalsRepository.odsCodes())
                .subscribe(codes -> this.osdCodes = codes));
    }

    @Override
    protected void onStop() {
        super.onStop();
        subscriptions.unsubscribe();
    }
}
