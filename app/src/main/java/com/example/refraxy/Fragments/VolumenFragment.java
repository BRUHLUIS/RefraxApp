package com.example.refraxy.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.refraxy.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class VolumenFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private Spinner spinnerUnidadInicial;
    private Spinner spinnerUnidadFinal;
    private EditText editTextValor;
    private TextView textViewResultado;

    private String unidadInicial = "Mililitros";
    private String unidadFinal = "Mililitros";

    private static final double LITROS_A_METROS = 1000;
    private static final double GALLONES_A_METROS = 3785.41;
    private static final double PINTAS_A_METROS = 473.176;
    private static final double MILILITROS_A_METROS = 0.001;
    private static final double METROS_A_LITROS = 0.001;
    private static final double METROS_A_GALLONES = 0.000264172;
    private static final double METROS_A_PINTAS = 0.00211338;
    private static final double METROS_A_MILILITROS = 1000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_volumen, container, false);

        bottomNavigationView = view.findViewById(R.id.navigation2);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_calculadora:
                        openFragment(new CalculadoraFragment());
                        return true;
                    case R.id.navigation_conversor:
                        openFragment(new ConversorFragment());
                        return true;
                    case R.id.navigation_lista:
                        openFragment(new ListaFragment());
                        return true;
                }
                return false;
            }
        });

        spinnerUnidadInicial = view.findViewById(R.id.spinnerUnidadInicial);
        spinnerUnidadFinal = view.findViewById(R.id.spinnerUnidadFinal);
        editTextValor = view.findViewById(R.id.editTextValor);
        textViewResultado = view.findViewById(R.id.textViewResultado);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.unidades_volumen, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerUnidadInicial.setAdapter(adapter);
        spinnerUnidadFinal.setAdapter(adapter);

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

        return view;
    }

    private void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container_herramientas, fragment); // Replace "fragment_container_herramientas" with the actual ID of the container in HerramientasFragment
        transaction.addToBackStack(null); // Add the transaction to the back stack
        transaction.commit(); // Commit the transaction
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Reattach the bottom navigation view to the HerramientasFragment when the PesoFragment is destroyed
        if (bottomNavigationView != null) {
            bottomNavigationView = getActivity().findViewById(R.id.navigation2);
        }
    }

    private void convertirValor() {
        double valor;
        try {
            valor = Double.parseDouble(editTextValor.getText().toString());
        } catch (NumberFormatException e) {
            valor = 0;
        }

        double metros;
        switch (unidadInicial) {
            case "Litros":
                metros = valor * LITROS_A_METROS;
                break;
            case "Gallones":
                metros = valor * GALLONES_A_METROS;
                break;
            case "Pintas":
                metros = valor * PINTAS_A_METROS;
                break;
            case "Mililitros":
                metros = valor * MILILITROS_A_METROS;
                break;
            default:
                metros = valor;
                break;
        }

        double resultado;
        switch (unidadFinal) {
            case "Litros":
                resultado = metros * METROS_A_LITROS;
                break;
            case "Gallones":
                resultado = metros * METROS_A_GALLONES;
                break;
            case "Pintas":
                resultado = metros * METROS_A_PINTAS;
                break;
            case "Mililitros":
                resultado = metros * METROS_A_MILILITROS;
                break;
            default:
                resultado = metros;
                break;
        }

        textViewResultado.setText(String.format("%.2f", resultado));
    }
}
