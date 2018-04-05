package com.hm.application.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.hm.application.R;

public class DestinationInfoActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_info);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
