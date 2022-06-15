package walid.basicmathforkids.pageauthentification;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
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

public class ListService extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerService;
    TextView description;
    Button FinTravail;
    String idservice1;
    ArrayList<String> MetierList = new ArrayList<>();
    ArrayList<String> idmetierlist = new ArrayList<>();
    ArrayList<String> idservicelist = new ArrayList<>();
    ArrayAdapter<String> MetierAdapter;
    RequestQueue requestQueue;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_service);
        spinnerService = findViewById(R.id.spinnerService);
        description = findViewById(R.id.AffichDES);
        FinTravail = findViewById(R.id.finTravail);
        spinnerService.setOnItemSelectedListener(this);
        progressDialog = new ProgressDialog(this);
        Afficher_service();

    }

    public void l(View v) {
        RateUsDialog rateUsDialog = new RateUsDialog(ListService.this);
        rateUsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        rateUsDialog.setCancelable(false);
        rateUsDialog.show();
    }
    public void FinTravail(View v) {
        String url = Constants.ROOT_URL+Constants.URL_ConfermFinTR;

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

    public void Afficher_service() {
        String url = Constants.ROOT_URL+Constants.URL_ListService;

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
                        String MetierName = jsonObject1.getString("Nom_metier");

                        String idmetier = jsonObject1.getString("Id_metier");

                        String idservice = jsonObject1.getString("Id_service");


                        MetierList.add(MetierName);
                        idmetierlist.add(idmetier);
                        idservicelist.add(idservice);
                    }
                    MetierAdapter = new ArrayAdapter<>(ListService.this,
                            android.R.layout.simple_spinner_item, MetierList);
                    MetierAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerService.setAdapter(MetierAdapter);

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
                params.put("idclient",MainActivity.idClient);
                return params;
            }
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
    public void RateUS(View v) {
        RateUsDialog rateUsDialog = new RateUsDialog(ListService.this);
        rateUsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        rateUsDialog.setCancelable(false);
        rateUsDialog.show();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.spinnerService){
            System.out.println("spinner action");
            description.setText("");

            int index= adapterView.getSelectedItemPosition();
            System.out.println(index);
            idservice1=idservicelist.get(index);
            String url=Constants.ROOT_URL+Constants.URL_InfoServiceDes ;
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
                            String descrip = jsonObject.getString("description");
                            description.setText(descrip.trim());
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