package com.tna_team.key_kost;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tna_team.key_kost.model.Log;
import com.tna_team.key_kost.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogActivity extends AppCompatActivity {

    public static User userLogin;
    private List<Log> dataLog = new ArrayList<>();
    private ProgressDialog progressDialog = null;

    ListView listView = null;
    ArrayAdapter<String> adapter;

    Button btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        setTitle("Show Log");

        listView = (ListView) findViewById(R.id.listLog);
        btnRefresh = (Button) findViewById(R.id.btnRefreshLog);

        progressDialog = new ProgressDialog(LogActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Getting Log Data...");
        progressDialog.show();
        getLog();

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(LogActivity.this,
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Getting Log Data...");
                progressDialog.show();
                getLog();
            }
        });
    }

    public void getLog(){
        RequestQueue requestQueueActive = Volley.newRequestQueue(this);
        StringRequest endpointActive = new StringRequest(Request.Method.GET, "http://ditoraharjo.co/keykost/api/v1/log-kost/"+userLogin.getKost_id(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            dataLog.clear();
                            JSONArray result = new JSONArray(response);
                            for (int i = 0; i < result.length(); i++) {
                                Log log = new Log();
                                log.setNama(result.getJSONObject(i).getString("nama"));
                                log.setWaktu(result.getJSONObject(i).getString("waktu"));
                                dataLog.add(log);

                                adapter = new ArrayAdapter<String>
                                        (getApplicationContext(), android.R.layout.simple_list_item_1, parseLog()){
                                    @Override
                                    public View getView(int position, View convertView, ViewGroup parent){
                                        View view = super.getView(position, convertView, parent);

                                        TextView tv = (TextView) view.findViewById(android.R.id.text1);

                                        tv.setTextColor(getResources().getColor(R.color.primary_dark));
                                        return view;
                                    }
                                };
                                listView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                progressDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        requestQueueActive.add(endpointActive);
    }

    public ArrayList<String> parseLog(){
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i < dataLog.size(); i++) {
            String tempView = dataLog.get(i).getNama()+" - "+dataLog.get(i).getWaktu();
            temp.add(tempView);
        }
        return temp;
    }
}
