package com.example.refraxy.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.refraxy.R;

public class Longitud extends AppCompatActivity {

    private EditText editTextValor;
    private Spinner spinnerUnidadInicial;
    private Spinner spinnerUnidadFinal;
    private TextView textViewResultado;

    private String[] unidades = {"Millas", "Yardas", "Pies", "Pulgadas"};
    private ArrayAdapter<String> adapter;

    private double valor;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.longitud);

        // Referenciar elementos del layout
        editTextValor = findViewById(R.id.editTextValor);
        spinnerUnidadInicial = findViewById(R.id.spinnerUnidadInicial);
        spinnerUnidadFinal = findViewById(R.id.spinnerUnidadFinal);
        textViewResultado = findViewById(R.id.textViewResultado);

        // Inicializar adaptador para el spinner
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, unidades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnidadInicial.setAdapter(adapter);
        spinnerUnidadFinal.setAdapter(adapter);

        // Convertir unidades al presionar el botón
        Button buttonConvertir = findViewById(R.id.buttonConvertir);
        buttonConvertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    valor = Double.parseDouble(editTextValor.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(Longitud.this, "Ingrese un valor válido", Toast.LENGTH_SHORT).show();
                    return;
                }

                String unidadInicial = spinnerUnidadInicial.getSelectedItem().toString();
                String unidadFinal = spinnerUnidadFinal.getSelectedItem().toString();
                double resultado = convertirUnidades(valor, unidadInicial, unidadFinal);
                textViewResultado.setText(String.format("%.2f %s = %.2f %s", valor, unidadInicial, resultado, unidadFinal));
            }
        });
    }

    private double convertirUnidades(double valor, String unidadInicial, String unidadFinal) {
        // Convertir todas las unidades a metros
        double metros;
        switch (unidadInicial) {
            case "Millas":
                metros = valor * 1609.344;
                break;
            case "Yardas":
                metros = valor * 0.9144;
                break;
            case "Pies":
                metros = valor * 0.3048;
                break;
            case "Pulgadas":
                metros = valor * 0.0254;
                break;
            default:
                metros = valor;
                break;
        }

        // Convertir metros a la unidad final
        switch (unidadFinal) {
            case "Millas":
                return metros / 1609.344;
            case "Yardas":
                return metros / 0.9144;
            case "Pies":
                return metros / 0.3048;
            case "Pulgadas":
                return metros / 0.0254;
            default:
                return metros;
        }
    }
}
