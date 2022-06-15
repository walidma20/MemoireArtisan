package walid.basicmathforkids.pageauthentification;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Api;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class client extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerMetie, spinnerArtisan;
    ArrayList<String> metieList = new ArrayList<>();
    ArrayList<String> artisanList = new ArrayList<>();
    ArrayAdapter<String> metieAdapter;
    ArrayAdapter<String> artisanAdapter;
    RequestQueue requestQueue;
    Button ConfButton,Check;
    EditText  Description;
    EditText location;
    String MetieName;
    String artisan;
    String idservice;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);


        requestQueue = Volley.newRequestQueue(this);
        ConfButton=findViewById(R.id.idConfirmation);
        spinnerMetie= findViewById(R.id.spmetier);
        spinnerMetie.setOnItemSelectedListener(this);
        spinnerArtisan= findViewById(R.id.spartisan);
        Description=findViewById(R.id.IdDescription);
        location = findViewById(R.id.idLocation);
        Check = findViewById(R.id.check);
        progressDialog = new ProgressDialog(this);
        String url = Constants.ROOT_URL+Constants.URL_SpMetier;
        String Location = location.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
            @Override
            public void onResponse( String response) {
                try {

                    // On récupère le tableau d'objets qui nous concernent
                    System.out.println("url : " + url);
                    System.out.println("reponse : " + response);
                    JSONArray array = new JSONArray(response);

                    //JSONObject jsonObject = new JSONObject(response);
                    //JSONArray array = new JSONArray(jsonObject);
                    // Pour tous les objets on récupère les infos
                    System.out.println("taille : " + array.length());
                    for(int i=0; i<array.length();i++){
                        JSONObject jsonObject1 = array.getJSONObject(i);
                         MetieName = jsonObject1.getString("Nom_metier");
                        System.out.println("metier : " + MetieName);
                        metieList.add(MetieName);

                    }
                    metieAdapter = new ArrayAdapter<>(client.this,
                            android.R.layout.simple_spinner_item, metieList);
                    metieAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerMetie.setAdapter(metieAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.spmetier){
            System.out.println("spinner action");
            artisanList.clear();
            String selectedmetier = adapterView.getSelectedItem().toString();
            System.out.println("selection : "+selectedmetier);

            String url=Constants.ROOT_URL+Constants.URL_SpArtisan ;
            requestQueue = Volley.newRequestQueue(this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        System.out.println("reponse : " + response);

                        JSONArray array = new JSONArray(response);


                        System.out.println("taille : " + array.length());
                        for(int i=0; i<array.length();i++){
                            JSONObject jsonObject = array.getJSONObject(i);
                             artisan = jsonObject.getString("Nom_artisan");
                            System.out.println("selection : "+ artisan);

                            artisanList.add(artisan);

                        }



                        artisanAdapter = new ArrayAdapter<>(client.this,
                                android.R.layout.simple_spinner_item, artisanList);
                        artisanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerArtisan.setAdapter( artisanAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("metier",selectedmetier);
                    return params;
                }
            };
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }







    public void confirmation(View v) {
        String url = Constants.ROOT_URL + Constants.URL_creerService;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                //System.out.println("url : " + url);
                //System.out.println("response");
                String res = response.trim();
                System.out.println(res);

                if (res.equals("1"))
                    Toast.makeText(getApplicationContext(), "Demande envoyée", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Demande  non envoyée", Toast.LENGTH_LONG).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //System.out.println("client "+MainActivity.idClient);
                params.put("idclient", MainActivity.idClient);
                //System.out.println("artisan "+spinnerArtisan.getSelectedItem().toString());
                params.put("artisan", spinnerArtisan.getSelectedItem().toString());
                //System.out.println("MetieName "+spinnerMetie.getSelectedItem().toString());
                params.put("metier", spinnerMetie.getSelectedItem().toString());
                //System.out.println("Description "+Description.getText().toString());
                params.put("description", Description.getText().toString());
                //System.out.println("location "+location.getText().toString());
                params.put("location", location.getText().toString());
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

        public void Check(View v){
            Intent intent =new Intent(client.this,ListService.class);
            startActivity(intent);
        }


}