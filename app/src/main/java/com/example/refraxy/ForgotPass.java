package com.example.refraxy;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ForgotPass extends AppCompatActivity {

    private static final String TAG = "ForgotPass";

    private EditText editTextEmail;
    private EditText editTextNewPassword;
    private Button buttonResetPassword;
    private Button buttonChangePassword;
    private TextView textViewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextNewPassword = findViewById(R.id.newpassword);
        buttonResetPassword = findViewById(R.id.buttonResetPassword);
        buttonChangePassword = findViewById(R.id.buttonChangePassword);
        textViewPassword = findViewById(R.id.textViewPassword);

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                resetPassword(email);
            }
        });

        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String newPassword = editTextNewPassword.getText().toString();
                changePassword(email, newPassword);
            }
        });
    }

    private void resetPassword(String email) {
        String url = "http://192.168.137.81/refaxy/forgotpass.php";

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Error al enviar la solicitud HTTP: " + e.getMessage());
                // Manejar el error de conexión
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseBody = response.body().string();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textViewPassword.setText("Contraseña: " + responseBody);
                        }
                    });

                    // Manejar la respuesta exitosa
                } else {
                    throw new IOException("Unexpected response code: " + response.code());
                }
            }
        });
    }

    private void changePassword(String email, String newPassword) {
        String url = "http://192.168.137.81/refaxy/forgotpass.php";

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("new_password", newPassword)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Error al enviar la solicitud HTTP: " + e.getMessage());
                // Manejar el error de conexión
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseBody = response.body().string();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Manejar la respuesta exitosa, si es necesario
                            // Por ejemplo, mostrar un mensaje de éxito o redirigir a otra actividad
                            // Aquí puedes agregar el código para actualizar la contraseña en tu base de datos
                            // Puedes usar la variable 'email' y 'newPassword' para ejecutar la consulta SQL correspondiente
                            if (responseBody.equals("Contraseña cambiada exitosamente!")) {
                                textViewPassword.setText("Error al cambiar la contraseña :(");
                            } else {
                                textViewPassword.setText("¡Contraseña Cambiada!");
                            }
                        }
                    });

                } else {
                    throw new IOException("Unexpected response code: " + response.code());
                }
            }
        });
    }
}