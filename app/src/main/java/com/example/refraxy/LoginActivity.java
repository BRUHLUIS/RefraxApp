package com.example.refraxy;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtPass;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.Email);
        txtPass = findViewById(R.id.password1);
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);

        Button signInButton = findViewById(R.id.btnsingin1);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickBtnIniciarSesion(view);
            }
        });

        Button signUpButton = findViewById(R.id.btnsingup1);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectToMainActivity(); // Redirect to the MainActivity
            }
        });

        Button forgotPasswordButton = findViewById(R.id.forgot_password_button);
        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openForgotPassActivity();
            }
        });

        // Check if the user is already logged in
        if (isLoggedIn()) {
            redirectToHomeApp();
        }
    }

    public void clickBtnIniciarSesion(View view) {
        final View finalView = view; // Declare a final copy of the view variable

        String email = txtEmail.getText().toString().trim();
        String password = txtPass.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Snackbar.make(view, "Por favor completa todos los campos", Snackbar.LENGTH_LONG).show();
            return;
        }

        // Perform user authentication
        authenticateUser(email, password, finalView); // Pass the finalView variable
    }

    private void authenticateUser(final String email, final String password, final View view) {
        String url = "http://192.168.100.3/refaxy/login.php"; // Update the URL with the correct path to your PHP file

        // Create a request queue using Volley
        RequestQueue queue = Volley.newRequestQueue(this);

        // Create a string request to send the user credentials to the PHP script
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response from the PHP script
                        if (response.equals("Login successful")) {
                            // User login successful, perform desired actions
                            Snackbar.make(view, "Login successful", Snackbar.LENGTH_LONG).show();
                            saveLoginStatus(); // Save the login status
                            redirectToHomeApp(); // Redirect to the HomeApp activity
                        } else {
                            // Invalid credentials
                            Snackbar.make(view, "Invalid credentials", Snackbar.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                        Snackbar.make(view, "Error: " + error.toString(), Snackbar.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Send the email and password as parameters to the PHP script
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        // Add the request to the queue
        queue.add(stringRequest);
    }

    private void redirectToHomeApp() {
        Intent intent = new Intent(LoginActivity.this, MainActivity1.class);
        startActivity(intent);
        finish(); // Optional, depending on your use case
    }

    private void redirectToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void openForgotPassActivity() {
        Intent intent = new Intent(LoginActivity.this, ForgotPass.class);
        startActivity(intent);
    }

    private void saveLoginStatus() {
        // Save the login status as logged in
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.apply();
    }

    private boolean isLoggedIn() {
        // Check if the user is already logged in
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

}