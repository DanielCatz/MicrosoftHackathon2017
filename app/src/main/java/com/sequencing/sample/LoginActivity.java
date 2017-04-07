package com.sequencing.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.sequencing.androidoauth.core.ISQAuthCallback;
import com.sequencing.androidoauth.core.SQUIoAuthHandler;
import com.sequencing.oauth.config.AuthenticationParameters;
import com.sequencing.oauth.core.Token;

public class LoginActivity extends AppCompatActivity implements ISQAuthCallback{

    private static final String TAG = "LoginActivity";
    private Button btnSignInSequencing;
    private SQUIoAuthHandler ioAuthHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnSignInSequencing = (Button) findViewById(R.id.btnSignInSequencing);

        AuthenticationParameters parameters = new AuthenticationParameters.ConfigurationBuilder()
                .withRedirectUri("wmw://login")
                .withClientId("Mooo")
                .withClientSecret("xaZGQ1Qd3QmfDeO1qbiKI8ypTpSCsUpAkOxCVWfxZfDDPdtfGPben5SVz6XFDUmtmlfLeIM45DEQhChskvwoqw")
                .build();

        ioAuthHandler = new SQUIoAuthHandler(this);
        ioAuthHandler.authenticate(btnSignInSequencing, this, parameters);
    }

    @Override
    public void onAuthentication(Token token) {
        Log.i(TAG, "Authenticated");
        Toast.makeText(getApplicationContext(), "You has been authenticated", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), SQApiActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
    }

    @Override
    public void onFailedAuthentication(Exception e) {
        Log.w(TAG, "Failure to authenticate user");
    }
}
