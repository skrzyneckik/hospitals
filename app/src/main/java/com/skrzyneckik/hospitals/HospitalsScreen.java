package com.skrzyneckik.hospitals;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.skrzyneckik.domain.Hospital;

import java.util.Arrays;

public class HospitalsScreen extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private HospitalsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton mSearchButton;

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
