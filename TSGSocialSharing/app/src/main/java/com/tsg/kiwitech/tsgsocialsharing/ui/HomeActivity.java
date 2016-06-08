package com.tsg.kiwitech.tsgsocialsharing.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tsg.kiwitech.tsgsocialsharing.R;

public class HomeActivity extends Activity implements View.OnClickListener {

    private Button btnFacebook, btnGooglePlus, btnLinkedIn, btnTwitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnFacebook = (Button) findViewById(R.id.btnFacebook);
        btnGooglePlus = (Button) findViewById(R.id.btnGooglePlus);
        btnLinkedIn = (Button) findViewById(R.id.btnLinkedIn);
        btnTwitter = (Button) findViewById(R.id.btnTwitter);

        btnFacebook.setOnClickListener(this);
        btnGooglePlus.setOnClickListener(this);
        btnLinkedIn.setOnClickListener(this);
        btnTwitter.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == btnFacebook) {
            startActivity(new Intent(this, FacebookActivity.class));
        } else if (v == btnTwitter) {
            startActivity(new Intent(this, TwitterActivity.class));
        } else if (v == btnGooglePlus) {

        } else if (v == btnLinkedIn) {


        }
    }
}
