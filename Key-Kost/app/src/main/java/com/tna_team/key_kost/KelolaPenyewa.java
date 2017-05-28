package com.tna_team.key_kost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.tna_team.key_kost.model.User;

import java.util.ArrayList;

public class KelolaPenyewa extends AppCompatActivity {
    private Button btnTambahPenyewaKos;
    private ListView listView;
    private EditText edSearch;

    public static User userLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_penyewa);
        getSupportActionBar().hide();

        btnTambahPenyewaKos = (Button) findViewById(R.id.btnTambahPenyewa);
        listView = (ListView) findViewById(R.id.listPenyewa);
        edSearch = (EditText) findViewById(R.id.edSearch);

        setDummyListView();

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

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"You cannot back to login",Toast.LENGTH_SHORT).show();
    }
}
