package com.tna_team.key_kost;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tna_team.key_kost.model.User;

public class HomeActivity extends AppCompatActivity {

    public static User userLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
}
