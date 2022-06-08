package walid.basicmathforkids.pageauthentification;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

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
    Button Mapbutton, ConfButton;
    EditText location, Description;
     LocationListener locationListener;
     LocationManager locationManager;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Log.d("my Location" , location.toString());
            }
        };
        if(Build.VERSION.SDK_INT <23){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, locationListener);
        }else {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission( Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 3);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }


        requestQueue = Volley.newRequestQueue(this);
        Mapbutton=findViewById(R.id.idmap);
       ConfButton=findViewById(R.id.idConfirmation);
        spinnerMetie= findViewById(R.id.spmetier);
        spinnerMetie.setOnItemSelectedListener(this);
        spinnerArtisan= findViewById(R.id.spartisan);
        location=findViewById(R.id.idLocation);
       Description=findViewById(R.id.IdDescription);
        String url = Constants.ROOT_URL+Constants.URL_SpMetier;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
            @Override
            public void onResponse( String response) {
                try {

                    // On récupère le tableau d'objets qui nous concernent
                    System.out.println("url : " + url);
                    System.out.println("reponse : " + response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = new JSONArray(jsonObject);
                    // Pour tous les objets on récupère les infos
                    System.out.println("taille : " + array.length());
                    for(int i=0; i<array.length();i++){
                        JSONObject jsonObject1 = array.getJSONObject(i);
                        String MetieName = jsonObject1.getString("Nom_metier");
                        System.out.println("erreur : " + MetieName);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }
    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.M)


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.spmetier){
            artisanList.clear();
            String selectedmetier = adapterView.getSelectedItem().toString();
            String url=Constants.ROOT_URL+Constants.URL_SpArtisan ;
            requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {

                        // On récupère le tableau d'objets qui nous concernent
                        JSONArray array = new JSONArray(response.getString("effectue"));
                        // Pour tous les objets on récupère les infos

                        for(int i=0; i<array.length();i++){
                            JSONObject jsonObject = array.getJSONObject(i);
                            String MetieName = jsonObject.getString("Nom_artisan");
                            artisanList.add(MetieName);

                        }

                        artisanAdapter = new ArrayAdapter<>(client.this,
                                android.R.layout.simple_spinner_item, artisanList);
                        artisanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerArtisan.setAdapter( artisanAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(jsonObjectRequest);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}




