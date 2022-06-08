package walid.basicmathforkids.pageauthentification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class forgotPassword extends AppCompatActivity {
    TextView signin;
    Button sendemail;
    EditText enteremail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        signin = findViewById(R.id.Signin);
        sendemail = findViewById(R.id.sendlink);
        enteremail = findViewById(R.id.enteremail);
    }
        public void Forgotpass(View view){

            Intent intent = new Intent(forgotPassword.this, MainActivity.class);
            startActivity(intent);
        }

    }
