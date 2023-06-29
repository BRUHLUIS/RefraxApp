package com.example.refraxy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    private EditText txtNombre;
    private EditText txtEmail;
    private EditText txtTelefono;
    private EditText txtPass;
    private EditText txtConfirmPass;

    private boolean passwordShowing = false;
    private boolean conPasswordShowin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNombre = findViewById(R.id.username);
        txtEmail = findViewById(R.id.useremail);
        txtTelefono = findViewById(R.id.telefono);
        txtPass = findViewById(R.id.password);
        txtConfirmPass = findViewById(R.id.repassword);

        Button signUpButton = findViewById(R.id.btnsingup);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickBtnInsertar(view);
            }
        });

        Button signInButton = findViewById(R.id.btnlogin);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        TextView signInText = findViewById(R.id.Txtsignin);
        signInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform action for signing in
            }
        });

        // Set up animation for signUpButton
        signUpButton.post(new Runnable() {
            @Override
            public void run() {
                Button signUpButton = findViewById(R.id.btnsingup);

                // Set the initial position of the button outside the screen
                signUpButton.setTranslationX(+signUpButton.getWidth());

                // Create and configure the animation
                signUpButton.animate()
                        .translationX(0f)
                        .setDuration(1000)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                // Perform desired action after the animation, such as starting another activity
                            }
                        })
                        .start(); // Start the animation
            }
        });
    }

    public void clickBtnInsertar(View view) {
        String nombre = txtNombre.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String telefono = txtTelefono.getText().toString().trim();
        String password = txtPass.getText().toString().trim();
        String confirmarPassword = txtConfirmPass.getText().toString().trim();

        if (nombre.isEmpty() || email.isEmpty() || telefono.isEmpty() || password.isEmpty()) {
            Snackbar.make(view, "Por favor completa todos los campos", Snackbar.LENGTH_LONG).show();
            return;
        }
        if (!password.equals(confirmarPassword)) {
            Snackbar.make(view, "Lo sentimos, las contraseñas no coinciden", Snackbar.LENGTH_LONG)
                    .show();
            return;
        }

        String url = "http://192.168.100.3/refaxy/insertar.php";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest resultadoPost = new StringRequest(
                Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Snackbar.make(view, "Felicidades Usuario agregado exitosamente", Snackbar.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar.make(view, "Error: " + error.toString(), Snackbar.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("name", txtNombre.getText().toString());
                parametros.put("email", txtEmail.getText().toString());
                parametros.put("phone", txtTelefono.getText().toString());
                parametros.put("password", txtPass.getText().toString());
                return parametros;
            }
        };
        queue.add(resultadoPost);
    }
}

