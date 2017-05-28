package com.tna_team.key_kost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tna_team.key_kost.model.User;

public class HomeActivity extends AppCompatActivity {

    public static User userLogin;

    Button btnPenyewa, btnLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Beranda Key-Kost");

        btnPenyewa = (Button) findViewById(R.id.btn_penyewa);
        btnLog = (Button) findViewById(R.id.btn_log);

        btnPenyewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KelolaPenyewa.userLogin = userLogin;
                Intent intent = new Intent(getApplicationContext(), KelolaPenyewa.class);
                startActivity(intent);
            }
        });

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogActivity.userLogin = userLogin;
                Intent intent = new Intent(getApplicationContext(), LogActivity.class);
                startActivity(intent);
            }
        });
    }
}
