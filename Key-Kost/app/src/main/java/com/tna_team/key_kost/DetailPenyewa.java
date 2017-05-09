package com.tna_team.key_kost;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DetailPenyewa extends AppCompatActivity {
    private Button btnUbah;
    private Button btnHapus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penyewa);
        getSupportActionBar().hide();

        btnUbah = (Button) findViewById(R.id.btnUbah);
        btnHapus = (Button) findViewById(R.id.btnHapus);

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Sorry on development",Toast.LENGTH_SHORT).show();
            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Sorry on development",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
