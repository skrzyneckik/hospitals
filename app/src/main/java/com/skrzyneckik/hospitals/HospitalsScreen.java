package com.skrzyneckik.hospitals;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.skrzyneckik.domain.Hospital;
import com.skrzyneckik.repository.HospitalsRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.CompositeSubscription;

public class HospitalsScreen extends AppCompatActivity {

    private Spinner mSpinner;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private HospitalsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton mSearchButton;
    private HospitalsRepository hospitalsRepository;
    private BehaviorSubject<String> odsCodeSubject = BehaviorSubject.create("ALL");

    private CompositeSubscription subscriptions;

    List<String> osdCodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitals);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mSpinner = findViewById(R.id.spinner);

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

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView odsCodeView = (TextView) view;
                odsCodeSubject.onNext((String) odsCodeView.getText());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                odsCodeSubject.onNext("ALL");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        subscriptions = new CompositeSubscription();

        Observable<List<Hospital>> hospitalsObservable = hospitalsRepository.hospitals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observable.
                combineLatest(odsCodeSubject, hospitalsObservable, (odsCode, hospitals) -> {
                    List list = new ArrayList();
                    for (Hospital hospital : hospitals) {
                        if (TextUtils.equals("ALL", odsCode) || TextUtils.equals(hospital.parentODSCode(), odsCode)) {
                            list.add(hospital);
                        }
                    }
                    return list;
                })
                .doOnSubscribe(() -> mToolbar.setTitle(R.string.loading))
                .subscribe(hospitals -> {
                            mAdapter.update(hospitals);
                            mToolbar.setTitle(R.string.hospitals_title);
                            mToolbar.setSubtitle(getString(R.string.hospitals_count, hospitals.size()));
                        },
                        throwable -> {
                            //TODO inform user that reading hospital failed
                        });

        subscriptions.add(hospitalsObservable
                .compose(HospitalsRepository.odsCodes())
                .subscribe(codes -> {
                    codes.add(0, "ALL");
                    ArrayAdapter adapter = new ArrayAdapter(this, R.layout.item_ods_code, R.id.odsCodeView, codes);
                    mSpinner.setAdapter(adapter);
                }));
    }

    @Override
    protected void onStop() {
        super.onStop();
        subscriptions.unsubscribe();
    }
}
