package com.example.refraxy.Fragments;

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

public class Peso extends AppCompatActivity {

    private EditText editTextValor;
    private Spinner spinnerUnidadInicial;
    private Spinner spinnerUnidadFinal;
    private Button buttonConvertir;
    private TextView textViewResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peso);

        editTextValor = findViewById(R.id.editTextValor);
        spinnerUnidadInicial = findViewById(R.id.spinnerUnidadInicial);
        spinnerUnidadFinal = findViewById(R.id.spinnerUnidadFinal);
        buttonConvertir = findViewById(R.id.buttonConvertir);
        textViewResultado = findViewById(R.id.textViewResultado);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.unidades_peso, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnidadInicial.setAdapter(adapter);
        spinnerUnidadFinal.setAdapter(adapter);

        buttonConvertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valorString = editTextValor.getText().toString();
                if (valorString.isEmpty()) {
                    Toast.makeText(Peso.this,
                            "Ingrese un valor para convertir", Toast.LENGTH_SHORT).show();
                    return;
                }

                double valor = Double.parseDouble(valorString);
                String unidadInicial = spinnerUnidadInicial.getSelectedItem().toString();
                String unidadFinal = spinnerUnidadFinal.getSelectedItem().toString();
                double resultado = convertirPeso(valor, unidadInicial, unidadFinal);

                String resultadoString = String.format("%.2f %s son %.2f %s",
                        valor, unidadInicial, resultado, unidadFinal);
                textViewResultado.setText(resultadoString);
            }
        });
    }

    private double convertirPeso(double valor, String unidadInicial, String unidadFinal) {
        double resultado = 0;

        if (unidadInicial.equals("Kilogramos")) {
            if (unidadFinal.equals("Libras")) {
                resultado = valor * 2.20462;
            } else if (unidadFinal.equals("Onzas")) {
                resultado = valor * 35.274;
            } else {
                resultado = valor;
            }
        } else if (unidadInicial.equals("Libras")) {
            if (unidadFinal.equals("Kilogramos")) {
                resultado = valor * 0.453592;
            } else if (unidadFinal.equals("Onzas")) {
                resultado = valor * 16;
            } else {
                resultado = valor;
            }
        } else if (unidadInicial.equals("Onzas")) {
            if (unidadFinal.equals("Kilogramos")) {
                resultado = valor * 0.0283495;
            } else if (unidadFinal.equals("Libras")) {
                resultado = valor * 0.0625;
            } else {
                resultado = valor;
            }
        }

        return resultado;
    }
}
