package com.example.refraxy.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.refraxy.R;

public class Volumen extends AppCompatActivity {

    private Spinner spinnerUnidadInicial;
    private Spinner spinnerUnidadFinal;
    private EditText editTextValor;
    private TextView textViewResultado;

    private String unidadInicial = "Mililitros";
    private String unidadFinal = "Mililitros";

    private static final double LITROS_A_METROS = 0.001;
    private static final double GALLONES_A_METROS = 0.00378541;
    private static final double PINTAS_A_METROS = 0.000473176;
    private static final double MILILITROS_A_METROS = 0.000001;
    private static final double METROS_A_LITROS = 1000;
    private static final double METROS_A_GALLONES = 264.172;
    private static final double METROS_A_PINTAS = 2113.38;
    private static final double METROS_A_MILILITROS = 1000000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volumen);

        // Obtener referencias a las vistas
        spinnerUnidadInicial = findViewById(R.id.spinnerUnidadInicial);
        spinnerUnidadFinal = findViewById(R.id.spinnerUnidadFinal);
        editTextValor = findViewById(R.id.editTextValor);
        textViewResultado = findViewById(R.id.textViewResultado);

        // Crear adaptador para los spinners
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.unidades_volumen, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Asignar adaptador a los spinners
        spinnerUnidadInicial.setAdapter(adapter);
        spinnerUnidadFinal.setAdapter(adapter);

        // Manejar eventos de selección de unidades
        spinnerUnidadInicial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                unidadInicial = (String) adapterView.getItemAtPosition(position);
                convertirValor();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // No hacer nada
            }
        });

        spinnerUnidadFinal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                unidadFinal = (String) adapterView.getItemAtPosition(position);
                convertirValor();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // No hacer nada
            }
        });

        // Manejar eventos de cambio de valor
        editTextValor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No hacer nada
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                convertirValor();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No hacer nada
            }
        });
    }

    private void convertirValor() {
        // Obtener valor a convertir
        double valor;
        try {
            valor = Double.parseDouble(editTextValor.getText().toString());
        } catch (NumberFormatException e) {
            valor = 0;
        }


        // Convertir valor a metros
        double metros;
        switch (unidadInicial) {
            case "Litros":
                metros = valor * 0.001;
                break;
            case "Gallones":
                metros = valor * 0.00378541;
                break;
            case "Pintas":
                metros = valor * 0.000473176;
                break;
            case "Mililitros":
            default:
                metros = valor * 0.000001;
                break;
        }

        // Convertir metros a unidad final
        double resultado;
        switch (unidadFinal) {
            case "Litros":
                resultado = metros * 1000;
                break;
            case "Gallones":
                resultado = metros * 264.172;
                break;
            case "Pintas":
                resultado = metros * 2113.38;
                break;
            case "Mililitros":
            default:
                resultado = metros * 1000000;
                break;
        }
        // Mostrar resultado
        textViewResultado.setText(String.format("%.2f", resultado));
    }
}
