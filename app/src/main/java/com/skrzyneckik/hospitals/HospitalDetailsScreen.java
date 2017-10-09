package com.skrzyneckik.hospitals;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.skrzyneckik.domain.Hospital;

/**
 * Created by kskrzynecki on 09/10/2017.
 */

public class HospitalDetailsScreen extends AppCompatActivity {

    private static final String KEY_HOSPITAL = "KEY_HOSPITAL";

    private Hospital mHospital;

    private Toolbar mToolbar;
    private TextView hospitalNameView;
    private TextView parentODSCodeView;

    public static void navigate(@NonNull Context context, @NonNull Hospital hospital) {
        Intent intent = new Intent(context, HospitalDetailsScreen.class);
        intent.putExtra(KEY_HOSPITAL, hospital);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_details);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent == null) {
            finish();
        } else {
            mHospital = (Hospital) intent.getSerializableExtra(KEY_HOSPITAL);
        }

        hospitalNameView = findViewById(R.id.organisationName);
        parentODSCodeView = findViewById(R.id.parentODSCode);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mHospital != null) {
            hospitalNameView.setText(mHospital.organisationName());
            parentODSCodeView.setText(mHospital.parentODSCode());
            mToolbar.setTitle(mHospital.organisationName());
            mToolbar.setSubtitle(mHospital.county());
        }
    }
}
