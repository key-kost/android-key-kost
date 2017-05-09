package com.tna_team.key_kost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TambahAtauUbahPenyewaKos extends AppCompatActivity {
    public static String flagEditTambah;
    private Button btnOk;
    private Button btnBatal;
    private EditText edNama;
    private EditText edNoHp;
    private EditText edEmail;
    private EditText edInstansi;
    private EditText edTanggal;
    private EditText edUang;

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"You cannot back using back press",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_atau_ubah_penyewa_kos);
        getSupportActionBar().hide();

        btnOk = (Button) findViewById(R.id.btnOK);
        btnBatal = (Button) findViewById(R.id.btnBatal);

        edNama = (EditText) findViewById(R.id.edNama);
        edNoHp = (EditText) findViewById(R.id.edNomorHandphone);
        edEmail = (EditText) findViewById(R.id.edEmail);
        edInstansi = (EditText) findViewById(R.id.edInstansi);
        edTanggal = (EditText) findViewById(R.id.edTanggal);
        edUang = (EditText) findViewById(R.id.edUang);

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TambahAtauUbahPenyewaKos.flagEditTambah="";
                Intent back = new Intent(view.getContext(),KelolaPenyewa.class);
                startActivity(back);
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TambahAtauUbahPenyewaKos.flagEditTambah=="TAMBAH"){
                    Toast.makeText(view.getContext(),"Sorry Add Function on development",Toast.LENGTH_SHORT).show();
                }
                else if(TambahAtauUbahPenyewaKos.flagEditTambah=="UBAH"){
                    Toast.makeText(view.getContext(),"Sorry Edit Function on development",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
