package walid.basicmathforkids.pageauthentification;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.channels.AcceptPendingException;
import java.util.HashMap;
import java.util.Map;

public class Create extends AppCompatActivity implements View.OnClickListener {
    EditText username, email, password, phone, addrese;
    Button REGISTER;
    TextView signin;
    ProgressDialog progressDialog;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        username = findViewById(R.id.Username);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        phone = findViewById(R.id.Phone);
        addrese = findViewById(R.id.Address);
        progressDialog = new ProgressDialog(this);
        REGISTER = findViewById(R.id.REGISTER);
        REGISTER.setOnClickListener(this);

    }
    public void registerUser() {
        String UserName = username.getText().toString().trim();
        String EMAIL = email.getText().toString().trim();
        String PASSWORD = password.getText().toString().trim();
        String PHONE = phone.getText().toString().trim();
        String ADDRESS = addrese.getText().toString().trim();
        progressDialog.setMessage("Registering user ...");
        progressDialog.show();
String requete=Constants.ROOT_URL+Constants.URL_REGISTER+"/?"+"&username="+UserName+"&Email="+EMAIL+"&Password="+PASSWORD+"&Phone="+PHONE+"&Address="+ADDRESS;
System.out.println(requete);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.ROOT_URL+Constants.URL_REGISTER,  new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            System.out.println("reponse : " + response);
                            JSONObject jsonObject = new JSONObject(response);
                            System.out.println("enregistrement correct : " + jsonObject.getString("message"));
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Create.this, MainActivity.class);
                            startActivity(intent);

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
                params.put("Email", EMAIL);
                params.put("Password", PASSWORD);
                params.put("Phone", PHONE);
                params.put("Address", ADDRESS);
                return params;
            }
        };
       RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    public void signin(View v) {
        Intent intent = new Intent(Create.this, MainActivity.class);
        startActivity(intent);

    }



    @Override
    public void onClick(View view) {
          if(view ==REGISTER)
              registerUser();


    }


}