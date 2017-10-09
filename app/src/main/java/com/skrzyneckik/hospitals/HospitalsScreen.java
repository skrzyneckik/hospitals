package com.skrzyneckik.hospitals;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.skrzyneckik.domain.Hospital;
import com.skrzyneckik.repository.HospitalsRepository;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class HospitalsScreen extends AppCompatActivity {

    private Toolbar mToolbar;
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

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

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
                .doOnSubscribe(() -> mToolbar.setTitle(R.string.loading))
                .subscribe(hospitals -> {
                    mAdapter.update(hospitals);
                    mToolbar.setTitle(R.string.hospitals_title);
                    mToolbar.setSubtitle(getString(R.string.hospitals_count, hospitals.size()));
                        },
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
