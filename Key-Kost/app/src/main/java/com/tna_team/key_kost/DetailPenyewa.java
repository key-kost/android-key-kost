package com.tna_team.key_kost;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tna_team.key_kost.model.Penyewa;

import java.util.HashMap;
import java.util.Map;

public class DetailPenyewa extends AppCompatActivity {
    private Button btnUbah;
    private Button btnHapus;
    private TextView nama,expDate,biaya,noKamar,noHp,email,alamat;
    public static Penyewa penyewa;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penyewa);
        getSupportActionBar().hide();

        progressDialog = new ProgressDialog(DetailPenyewa.this);

        nama = (TextView) findViewById(R.id.fieldNama);
            nama.setText(penyewa.getFullName().toString());
        expDate = (TextView) findViewById(R.id.fieldTanggal);
            expDate.setText(penyewa.getExpDate());
        biaya = (TextView) findViewById(R.id.fieldRfid);
            biaya.setText(penyewa.getRfidId());
        noKamar = (TextView) findViewById(R.id.fieldNoKamar);
            noKamar.setText(penyewa.getNoKamar());
        noHp = (TextView) findViewById(R.id.fieldNoHp);
            noHp.setText(penyewa.getTelp());
        email = (TextView) findViewById(R.id.fieldEmail);
            email.setText(penyewa.getEmail());
        alamat = (TextView) findViewById(R.id.fieldAlamat);
            alamat.setText(penyewa.getAlamat());

        btnUbah = (Button) findViewById(R.id.btnUbah);
        btnHapus = (Button) findViewById(R.id.btnHapus);

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TambahAtauUbahPenyewaKos.penyewa = DetailPenyewa.penyewa;
                Intent intent = new Intent(DetailPenyewa.this,TambahAtauUbahPenyewaKos.class);
                TambahAtauUbahPenyewaKos.flagEditTambah="UBAH";
                startActivity(intent);
//                Toast.makeText(view.getContext(),"Sorry on development",Toast.LENGTH_SHORT).show();
            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePenyewa();
            }
        });

    }

    private void deletePenyewa(){
        RequestQueue requestQueue = Volley.newRequestQueue(DetailPenyewa.this);
        StringRequest deletePenyewa = new StringRequest(Method.DELETE, "http://ditoraharjo.co/keykost/api/v1/penyewa/" + DetailPenyewa.penyewa.getId(),
                new Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(DetailPenyewa.this,"Hapus Data Penyewa Sukses",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Intent intent = new Intent(DetailPenyewa.this,KelolaPenyewa.class);
                        startActivity(intent);
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetailPenyewa.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Accept","application/json");
                return params;
            }
        };
        progressDialog.show();
        requestQueue.add(deletePenyewa);
    }
}
