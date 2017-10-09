package com.skrzyneckik.hospitals;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by kskrzynecki on 09/10/2017.
 */

public class HospitalDetailsScreen extends AppCompatActivity {

    private TextView hospitalNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_details);

        hospitalNameView = findViewById(R.id.hospital_name);

        hospitalNameView.setText("Newquay Hospital");
    }
}
