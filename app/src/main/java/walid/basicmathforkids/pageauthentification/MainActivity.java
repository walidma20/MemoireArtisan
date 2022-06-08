package walid.basicmathforkids.pageauthentification;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView Username, craccount, Password;
    EditText Myusername, Mypassword;
    Button button;
    ProgressDialog progressDialog;
    public static String idClient="",idArtisan="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Username = findViewById(R.id.textView);
        Password = findViewById(R.id.textView2);
        button = findViewById(R.id.button);
        Myusername = findViewById(R.id.editText1);
        Mypassword = findViewById(R.id.editText2);
        craccount = findViewById(R.id.createAccount);
        progressDialog = new ProgressDialog(this);
    }

    public void test(View view) {
        String username = Myusername.getText().toString();
        String password = Mypassword.getText().toString();

        if (username.equals("walid") && password.equals("walid123")) {
            Toast.makeText(getApplicationContext(), "username and password is correct", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(getApplicationContext(), "username and password is incorrect", Toast.LENGTH_LONG).show();


    }
    public void Creaccount(View v) {
        Intent intent = new Intent(MainActivity.this, Create.class);
        startActivity(intent);
    }
        public void ForgotPassword(View v){

            Intent intent = new Intent(MainActivity.this, forgotPassword.class);
            startActivity(intent);
        }
    public void ChooseClient(View v){

            String UserName = Username.getText().toString().trim();
            String PASSWORD = Password.getText().toString().trim();

            String requete=Constants.ROOT_URL+Constants.URL_LoginClient;
            System.out.println(requete);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.ROOT_URL+Constants.URL_LoginClient,  new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        System.out.println("reponse : " + response);
                        JSONObject jsonObject = new JSONObject(response);

                        String error=jsonObject.getString("error");
                        System.out.println("erreur : " + error);
                        if(error.equals("false")){
                            idClient=jsonObject.getString("id");
                        Intent intent = new Intent(MainActivity.this, client.class);
                        startActivity(intent);}
    else Toast.makeText(getApplicationContext(), "username and password is incorrect", Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.hide();
                            System.out.println("enregistrement erreur: "+error.getMessage());
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username",Myusername.getText().toString().trim());

                    params.put("Password", Mypassword.getText().toString().trim());

                    return params;
                }
            };
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

        }



    public void  ChooseArtisan () {

        String UserName = Username.getText().toString().trim();
        String PASSWORD = Password.getText().toString().trim();

        String requete=Constants.ROOT_URL+Constants.URL_LoginArtisan;
        System.out.println(requete);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.ROOT_URL+Constants.URL_LoginArtisan,  new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    System.out.println("reponse : " + response);
                    JSONObject jsonObject = new JSONObject(response);
                    System.out.println("enregistrement correct : " + jsonObject.getString("id"));
                    String error=jsonObject.getString("error");
                    if(error.equals("false")){
                        idArtisan=jsonObject.getString("id");
                        Intent intent = new Intent(MainActivity.this, artisan.class);
                        startActivity(intent);}
                    else Toast.makeText(getApplicationContext(), "username and password is incorrect", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        System.out.println("enregistrement erreur: "+error.getMessage());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",UserName);
                params.put("Password", PASSWORD);

                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }
  public void client(View v){
      Intent intent = new Intent(MainActivity.this, client.class);
      startActivity(intent);
  }
}


