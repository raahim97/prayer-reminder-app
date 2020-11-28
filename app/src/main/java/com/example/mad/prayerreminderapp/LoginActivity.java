package com.example.mad.prayerreminderapp;
import com.example.mad.prayerreminderapp.R;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LoginActivity extends AppCompatActivity {
    EditText editUsername, editPassword;
   // TextView tvTest;
    Button btnLogin;
    String username = "";
    String password = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //tvTest = findViewById(R.id.textView2);
        String data = "";
       // StringBuffer stringBuffer = new StringBuffer();
        InputStream is = this.getResources().openRawResource(R.raw.admin_credentials);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        if (is != null){
            try {
                while ((data = reader.readLine()) != null){
                    //stringBuffer.append(data + "\n");
                    if (data.contains("username:")){
                        username = data.substring(data.indexOf(" "));
//                        Toast.makeText(this, username, Toast.LENGTH_SHORT).show();
                       // tvTest.setText(username);
                    }
                    else if (data.contains("password:")){
                        password = data.substring(data.indexOf(" "));
//                        Toast.makeText(this, password, Toast.LENGTH_SHORT).show();

                        //tvTest.append(password);
                    }

                }
//                tvTest.setText(stringBuffer);
                is.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String entered_username = editUsername.getText().toString().trim();
                String entered_password = editPassword.getText().toString().trim();

                if (entered_username.equals(username.trim()) && entered_password.equals(password.trim())){
                    Toast.makeText(LoginActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                }



            }
        });

    }
}
