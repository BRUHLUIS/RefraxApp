package com.example.refraxy.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.refraxy.R;

public class ConversorFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversor, container, false);

        // Find the buttons
        Button buttonL = view.findViewById(R.id.buttonL);
        Button buttonP = view.findViewById(R.id.buttonP);
        Button buttonV = view.findViewById(R.id.buttonV);
        Button buttonT = view.findViewById(R.id.buttonT);

        // Set click listeners for the buttons
        buttonL.setOnClickListener(this);
        buttonP.setOnClickListener(this);
        buttonV.setOnClickListener(this);
        buttonT.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;

        // Handle button clicks
        switch (v.getId()) {
            case R.id.buttonL:
                fragment = new LongitudFragment();
                break;
            case R.id.buttonP:
                fragment = new PesoFragment();
                break;
            case R.id.buttonV:
                fragment = new VolumenFragment();
                break;
            case R.id.buttonT:
                fragment = new TemperaturaFragment();
                break;
        }

        // Navigate to the selected fragment
        if (fragment != null) {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment) // Replace "fragment_container" with the ID of the container where you want to display the new fragment
                    .addToBackStack(null) // Add the fragment to the back stack, so pressing back will navigate back to this fragment
                    .commit();
        }
    }
}
