package com.example.refraxy.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.refraxy.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PesoFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private EditText editTextValor;
    private Spinner spinnerUnidadInicial;
    private Spinner spinnerUnidadFinal;
    private Button buttonConvertir;
    private TextView textViewResultado;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_peso, container, false);

        editTextValor = view.findViewById(R.id.editTextValor);
        spinnerUnidadInicial = view.findViewById(R.id.spinnerUnidadInicial);
        spinnerUnidadFinal = view.findViewById(R.id.spinnerUnidadFinal);
        buttonConvertir = view.findViewById(R.id.buttonConvertir);
        textViewResultado = view.findViewById(R.id.textViewResultado);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.unidades_peso, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnidadInicial.setAdapter(adapter);
        spinnerUnidadFinal.setAdapter(adapter);

        buttonConvertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valorString = editTextValor.getText().toString();
                if (valorString.isEmpty()) {
                    Toast.makeText(getActivity(),
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

        return view;
    }

    private void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container_herramientas, fragment); // Replace "fragment_container_herramientas" with the actual ID of the container in HerramientasFragment
        transaction.addToBackStack(null); // Add the transaction to the back stack
        transaction.commit(); // Commit the transaction
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Reattach the bottom navigation view to the HerramientasFragment when the PesoFragment is destroyed
        if (bottomNavigationView != null) {
            bottomNavigationView = getActivity().findViewById(R.id.navigation2);
        }
    }
}
