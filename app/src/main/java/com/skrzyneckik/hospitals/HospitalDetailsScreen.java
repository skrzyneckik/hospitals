package com.skrzyneckik.hospitals;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.skrzyneckik.domain.Hospital;

/**
 * Created by kskrzynecki on 09/10/2017.
 */

public class HospitalDetailsScreen extends AppCompatActivity {

    private static final String KEY_HOSPITAL = "KEY_HOSPITAL";

    private TextView hospitalNameView;
    private Hospital mHospital;

    public static void navigate(@NonNull Context context, @NonNull Hospital hospital) {
        Intent intent = new Intent(context, HospitalDetailsScreen.class);
        intent.putExtra(KEY_HOSPITAL, hospital);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_details);

        Intent intent = getIntent();
        if (intent == null) {
            finish();
        } else {
            mHospital = (Hospital) intent.getSerializableExtra(KEY_HOSPITAL);
        }

        hospitalNameView = findViewById(R.id.organisationName);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mHospital != null) {
            hospitalNameView.setText(mHospital.organisationName());
        }
    }
}
