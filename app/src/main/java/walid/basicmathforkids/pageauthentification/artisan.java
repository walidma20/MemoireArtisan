package walid.basicmathforkids.pageauthentification;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class artisan extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Button Validation,Annuler;
    TextView Description;
    Spinner spinnerNomCle;
    ArrayList<String> ClientList = new ArrayList<>();
    ArrayList<String> idclientlist = new ArrayList<>();
    ArrayList<String> idservicelist = new ArrayList<>();
    ArrayAdapter<String> ClientAdapter;
    RequestQueue requestQueue;
    String idservice1;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artisan);
        Validation = findViewById(R.id.finTravail);
        Annuler = findViewById(R.id.button3);
        Description =findViewById(R.id.idDescription);
        spinnerNomCle = findViewById(R.id.spinnerS);
        spinnerNomCle.setOnItemSelectedListener(this);
        progressDialog = new ProgressDialog(this);
        afficher_demandes();

    }

    private void afficher_demandes() {
        String url = Constants.ROOT_URL+Constants.URL_ServiceCle;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
            @Override
            public void onResponse( String response) {
                try {

                    // On récupère le tableau d'objets qui nous concernent
                    System.out.println("url : " + url);
                    System.out.println("reponse : " + response);
                    JSONArray array = new JSONArray(response);

                    System.out.println("taille : " + array.length());
                    for(int i=0; i<array.length();i++){
                        JSONObject jsonObject1 = array.getJSONObject(i);
                        String ClientName = jsonObject1.getString("Nom_utilisateur").trim();

                        String idclient = jsonObject1.getString("Id_client").trim();

                        String idservice = jsonObject1.getString("Id_service").trim();

                        System.out.println("client : " +ClientName);
                        System.out.println("idclient : " +idclient);
                        System.out.println("idservice : " +idservice);
                        ClientList.add(ClientName);
                        idclientlist.add(idclient);
                        idservicelist.add(idservice);
                    }
                    ClientAdapter = new ArrayAdapter<>(artisan.this,
                            android.R.layout.simple_spinner_item, ClientList);
                    ClientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerNomCle.setAdapter( ClientAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idartisan",MainActivity.idArtisan);
                return params;
            }
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }



    public void validation(View v) {
        String url = Constants.ROOT_URL+Constants.URL_ConfermAr;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
            @Override
            public void onResponse( String response) {


                    // On récupère le tableau d'objets qui nous concernent
                    System.out.println("url : " + url);
                    System.out.println("reponse : " + response);
                String res=response.trim();
                System.out.println(res);

                    if (res.equals("1"))
                        Toast.makeText(getApplicationContext(), "Confirmation validée", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), "Confirmation non validée", Toast.LENGTH_LONG).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idservice",idservice1);
                return params;
            }
        };



        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }






    public void annuler(View v){
        String url = Constants.ROOT_URL + Constants.URL_ConferAnnuler;

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
                @Override
                public void onResponse( String response) {


                    // On récupère le tableau d'objets qui nous concernent
                    System.out.println("url : " + url);
                    System.out.println("reponse : " + response);
                    String res=response.trim();
                    System.out.println(res);
                    if (res.equals("1"))
                        Toast.makeText(getApplicationContext(), "Annulation validée", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), "Annulation non validée", Toast.LENGTH_LONG).show();


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("idartisan",MainActivity.idArtisan);
                    return params;
                }
            };


            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        }





    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.spinnerS){
            System.out.println("spinner action");
            Description.setText("");


           int index= adapterView.getSelectedItemPosition();System.out.println(index);
            idservice1=idservicelist.get(index);
            String idclient1=idclientlist.get(index);
            String url=Constants.ROOT_URL+Constants.URL_InfoServiceCle ;
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
                            String location = jsonObject.getString("location");
                            String tel = jsonObject.getString("Num_tel");
                            String descrption = jsonObject.getString("description");
                            Description.setText(location+" \n \n" +tel+ " \n \n"+ descrption);
                        }


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
                    params.put("idservice",idservice1);
                    params.put("idclient",idclient1);
                    return params;
                }
            };
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
