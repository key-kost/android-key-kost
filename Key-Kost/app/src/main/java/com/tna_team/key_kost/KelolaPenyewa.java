package com.tna_team.key_kost;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import com.tna_team.key_kost.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class KelolaPenyewa extends AppCompatActivity {
    private Button btnTambahPenyewaKos;
    private ListView listView;
    private EditText edSearch;
    private List<Penyewa> listOfPenyewa = new ArrayList<>();
    private ProgressDialog progressDialog = null;

    public static User userLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_penyewa);
        getSupportActionBar().hide();

        btnTambahPenyewaKos = (Button) findViewById(R.id.btnTambahPenyewa);
        listView = (ListView) findViewById(R.id.listPenyewa);
        edSearch = (EditText) findViewById(R.id.edSearch);

        progressDialog = new ProgressDialog(KelolaPenyewa.this);
        getAllPenyewa();
//        setDummyListView();

        btnTambahPenyewaKos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToAdd = new Intent(view.getContext(),TambahAtauUbahPenyewaKos.class);
                TambahAtauUbahPenyewaKos.flagEditTambah="TAMBAH";
                startActivity(goToAdd);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent next = new Intent(view.getContext(),DetailPenyewa.class);
                startActivity(next);
            }
        });
    }

    public void setDummyListView(){
        ArrayList<String> dummySet = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dummySet.add("Data Dummmy "+(i+1));
        }
        listView.setAdapter(new ArrayAdapter<String>(getApplication(),android.R.layout.simple_list_item_1,dummySet));
    }

    public void setListPenyewa(){
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < listOfPenyewa.size(); i++) {
            result.add(listOfPenyewa.get(i).getFullName());
        }
        listView.setAdapter(new ArrayAdapter<String>(getApplication(),android.R.layout.simple_list_item_1,result));
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"You cannot back to login",Toast.LENGTH_SHORT).show();
    }

    public void getAllPenyewa(){
        RequestQueue requestQueue = Volley.newRequestQueue(KelolaPenyewa.this);
        StringRequest request = new StringRequest(Method.GET, "http://ditoraharjo.co/keykost/api/v1/penyewa?kost_id=" + HomeActivity.userLogin.getKost_id(),
                new Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray result = new JSONArray(response);
                            for (int i = 0; i < result.length(); i++) {
                                Penyewa penyewa = new Penyewa();
                                JSONObject indexObject = result.getJSONObject(i);
                                penyewa.setEmail(indexObject.getString("email"));
                                penyewa.setAlamat(indexObject.getString("alamat"));
                                penyewa.setExpDate(indexObject.getString("exp_date"));
                                penyewa.setFullName(indexObject.getString("fullname"));
                                penyewa.setId(indexObject.getString("id"));;
                                penyewa.setImage(indexObject.getString("image"));
                                penyewa.setJenisKelamin(indexObject.getString("jenis_kelamin"));
                                penyewa.setNoKamar(indexObject.getString("no_kamar"));
                                penyewa.setRegisterDate(indexObject.getString("register_date"));
                                penyewa.setRfidId(indexObject.getString("rfid_id"));
                                penyewa.setTelp(indexObject.getString("telp"));

                                listOfPenyewa.add(penyewa);
                            }
                            Toast.makeText(KelolaPenyewa.this,"Success Get Data",Toast.LENGTH_SHORT)
                                    .show();
                            setListPenyewa();
                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(KelolaPenyewa.this,"Sorry, network error, please fix your connection first",Toast.LENGTH_SHORT)
                                    .show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(KelolaPenyewa.this,"Failed to connect to server",Toast.LENGTH_SHORT)
                                .show();
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
        requestQueue.add(request);
    }
}