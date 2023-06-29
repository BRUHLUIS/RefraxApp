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

public class LongitudFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private EditText editTextValor;
    private Spinner spinnerUnidadInicial;
    private Spinner spinnerUnidadFinal;
    private TextView textViewResultado;

    private String[] unidades = {"Millas", "Yardas", "Pies", "Pulgadas"};
    private ArrayAdapter<String> adapter;

    private double valor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_longitud, container, false);

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

        // Referencing elements of the layout
        editTextValor = view.findViewById(R.id.editTextValor);
        spinnerUnidadInicial = view.findViewById(R.id.spinnerUnidadInicial);
        spinnerUnidadFinal = view.findViewById(R.id.spinnerUnidadFinal);
        textViewResultado = view.findViewById(R.id.textViewResultado);

        // Initializing adapter for the spinner
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, unidades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnidadInicial.setAdapter(adapter);
        spinnerUnidadFinal.setAdapter(adapter);

        // Convert units when the button is clicked
        Button buttonConvertir = view.findViewById(R.id.buttonConvertir);
        buttonConvertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    valor = Double.parseDouble(editTextValor.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(requireContext(), "Ingrese un valor válido", Toast.LENGTH_SHORT).show();
                    return;
                }

                String unidadInicial = spinnerUnidadInicial.getSelectedItem().toString();
                String unidadFinal = spinnerUnidadFinal.getSelectedItem().toString();
                double resultado = convertirUnidades(valor, unidadInicial, unidadFinal);
                textViewResultado.setText(String.format("%.2f %s = %.2f %s", valor, unidadInicial, resultado, unidadFinal));

                // You can choose to open a fragment here if needed
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

    private double convertirUnidades(double valor, String unidadInicial, String unidadFinal) {
        // Convert all units to meters
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

        // Convert meters to the final unit
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Reattach the bottom navigation view to the HerramientasFragment when the PesoFragment is destroyed
        if (bottomNavigationView != null) {
            bottomNavigationView = getActivity().findViewById(R.id.navigation2);
        }
    }
}
