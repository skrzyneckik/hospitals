package com.skrzyneckik.hospitals;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.skrzyneckik.domain.Hospital;

import java.util.Arrays;

public class HospitalsScreen extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private HospitalsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitals);

        mRecyclerView = findViewById(R.id.hospitals_list);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new HospitalsAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAdapter.update(Arrays.asList(
                new Hospital("Newquay Hospital"),
                new Hospital("Bridgewater Hospital")
        ));
    }
}
