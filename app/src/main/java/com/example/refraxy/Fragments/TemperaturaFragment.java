package com.example.refraxy.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.refraxy.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TemperaturaFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private EditText editTextCelsius;
    private Button buttonConvertToFahrenheit;
    private Button buttonConvertToKelvin;
    private TextView textViewResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_temperatura, container, false);

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

        editTextCelsius = view.findViewById(R.id.editTextCelsius);
        buttonConvertToFahrenheit = view.findViewById(R.id.buttonConvertToFahrenheit);
        buttonConvertToKelvin = view.findViewById(R.id.buttonConvertToKelvin);
        textViewResult = view.findViewById(R.id.textViewResult);

        buttonConvertToFahrenheit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String celsiusString = editTextCelsius.getText().toString();
                if (!celsiusString.isEmpty()) {
                    double celsius = Double.parseDouble(celsiusString);
                    double fahrenheit = celsius * 1.8 + 32;
                    textViewResult.setText(String.format("%.2f grados Fahrenheit", fahrenheit));
                }
            }
        });

        buttonConvertToKelvin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String celsiusString = editTextCelsius.getText().toString();
                if (!celsiusString.isEmpty()) {
                    double celsius = Double.parseDouble(celsiusString);
                    double kelvin = celsius + 273.15;
                    textViewResult.setText(String.format("%.2f grados Kelvin", kelvin));
                }
            }
        });

        return view;
    }

    private void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container_herramientas, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (bottomNavigationView != null) {
            bottomNavigationView = getActivity().findViewById(R.id.navigation2);
        }
    }
}
