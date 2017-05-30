package com.tna_team.key_kost;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tna_team.key_kost.model.Penyewa;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TambahAtauUbahPenyewaKos extends AppCompatActivity {
    public static String flagEditTambah;
    private Button btnOk;
    private Button btnBatal;
    private EditText edNama;
    private EditText edNoHp;
    private EditText edEmail;
    private EditText edNomorKamar;
    private EditText edRfid;
    private EditText edAlamat,edPassword;
    private ProgressDialog progressDialog;
    public static Penyewa penyewa;

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"You cannot back using back press",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_atau_ubah_penyewa_kos);
        getSupportActionBar().hide();

        progressDialog = new ProgressDialog(TambahAtauUbahPenyewaKos.this);

        btnOk = (Button) findViewById(R.id.btnOK);
        btnBatal = (Button) findViewById(R.id.btnBatal);

        edNama = (EditText) findViewById(R.id.edNama);
        edNoHp = (EditText) findViewById(R.id.edNomorHandphone);
        edEmail = (EditText) findViewById(R.id.edEmail);
        edNomorKamar = (EditText) findViewById(R.id.edNoKamar);

        edRfid = (EditText) findViewById(R.id.edRfid);
        edAlamat = (EditText) findViewById(R.id.edAlamat);

        edPassword = (EditText) findViewById(R.id.edPassword);

        if(TambahAtauUbahPenyewaKos.flagEditTambah.equalsIgnoreCase("UBAH")){
            edNama.setText(TambahAtauUbahPenyewaKos.penyewa.getFullName());
            edNoHp.setText(TambahAtauUbahPenyewaKos.penyewa.getTelp());
            edEmail.setText(TambahAtauUbahPenyewaKos.penyewa.getEmail());
            edNomorKamar.setText(TambahAtauUbahPenyewaKos.penyewa.getNoKamar());
            edPassword.setText(TambahAtauUbahPenyewaKos.penyewa.getPassword());
            edRfid.setText(TambahAtauUbahPenyewaKos.penyewa.getRfidId());
            edAlamat.setText(TambahAtauUbahPenyewaKos.penyewa.getAlamat());
        }

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
//                    Toast.makeText(view.getContext(),"Sorry Add Function on development",Toast.LENGTH_SHORT).show();
                    try {
                        tambahPenyewa();
                    } catch (JSONException e) {
                        Log.e("error",e.getMessage());
                    }
                }
                else if(TambahAtauUbahPenyewaKos.flagEditTambah=="UBAH"){
//                    Toast.makeText(view.getContext(),"Sorry Edit Function on development",Toast.LENGTH_SHORT).show();
                    ubahPenyewa();
                }
            }
        });
    }

    private void ubahPenyewa(){
        RequestQueue requestQueue = Volley.newRequestQueue(TambahAtauUbahPenyewaKos.this);
        StringRequest ubahPenyewa = new StringRequest(Method.PATCH, "http://ditoraharjo.co/keykost/api/v1/penyewa",
                new Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(TambahAtauUbahPenyewaKos.this,"Updating Data Success",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Intent intent = new Intent(TambahAtauUbahPenyewaKos.this,KelolaPenyewa.class);
                        startActivity(intent);
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error Update : ",error.getMessage());
                        Toast.makeText(TambahAtauUbahPenyewaKos.this,error.toString(),Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",TambahAtauUbahPenyewaKos.penyewa.getId());
                params.put("fullname",String.valueOf(edNama.getText()));
                params.put("rfid_id",String.valueOf(edRfid.getText()));
                params.put("no_kamar",String.valueOf(edNomorKamar.getText()));
                params.put("email",String.valueOf(edEmail.getText()));
                params.put("telp",String.valueOf(edNoHp.getText()));
                params.put("alamat",String.valueOf(edAlamat.getText()));
                params.put("password",String.valueOf(edPassword.getText()));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Accept","application/json");
                return params;
            }
        };
        progressDialog.show();
        requestQueue.add(ubahPenyewa);
    }

    private void tambahPenyewa() throws JSONException{
        RequestQueue requestQueue = Volley.newRequestQueue(TambahAtauUbahPenyewaKos.this);

        JSONObject params = new JSONObject();
        params.put("id_pemilikKost",String.valueOf(HomeActivity.userLogin.getPemilikKost_id()));
        params.put("fullname",String.valueOf(edNama.getText()));
        params.put("rfid_id",String.valueOf(edRfid.getText()));
        params.put("no_kamar",String.valueOf(edNomorKamar.getText()));
        params.put("email",String.valueOf(edEmail.getText()));
        params.put("telp",String.valueOf(edNoHp.getText()));
        params.put("alamat",String.valueOf(edAlamat.getText()));
        params.put("password",String.valueOf(edPassword.getText()));

        JsonObjectRequest tambahPenyewa = new JsonObjectRequest(Method.POST, "http://ditoraharjo.co/keykost/api/v1/penyewa",params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(TambahAtauUbahPenyewaKos.this,"Adding Data Success",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Intent intent = new Intent(TambahAtauUbahPenyewaKos.this,KelolaPenyewa.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error Adding : ",error.toString());
                        Toast.makeText(TambahAtauUbahPenyewaKos.this,error.getMessage(),Toast.LENGTH_SHORT).show();
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
        requestQueue.add(tambahPenyewa);
    }
}
