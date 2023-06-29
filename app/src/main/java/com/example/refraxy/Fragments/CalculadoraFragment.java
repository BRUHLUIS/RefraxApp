package com.example.refraxy.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.refraxy.R;
import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculadoraFragment extends Fragment implements View.OnClickListener {
    TextView resultTv, solutionTv;
    MaterialButton buttonC, buttonBrackOpen, buttonBrackClose;
    MaterialButton buttonDivide, buttonMultiply, buttonPlus, buttonMinus, buttonEquals;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonAC, buttonDot;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculadora, container, false);

        resultTv = view.findViewById(R.id.result_tv);
        solutionTv = view.findViewById(R.id.solution_tv);

        buttonC = view.findViewById(R.id.button_c);
        buttonBrackOpen = view.findViewById(R.id.button_open_bracket);
        buttonBrackClose = view.findViewById(R.id.button_close_bracket);
        buttonDivide = view.findViewById(R.id.button_divide);
        buttonMultiply = view.findViewById(R.id.button_multiply);
        buttonPlus = view.findViewById(R.id.button_plus);
        buttonMinus = view.findViewById(R.id.button_minus);
        buttonEquals = view.findViewById(R.id.button_equals);
        button0 = view.findViewById(R.id.button_0);
        button1 = view.findViewById(R.id.button_1);
        button2 = view.findViewById(R.id.button_2);
        button3 = view.findViewById(R.id.button_3);
        button4 = view.findViewById(R.id.button_4);
        button5 = view.findViewById(R.id.button_5);
        button6 = view.findViewById(R.id.button_6);
        button7 = view.findViewById(R.id.button_7);
        button8 = view.findViewById(R.id.button_8);
        button9 = view.findViewById(R.id.button_9);
        buttonAC = view.findViewById(R.id.button_ac);
        buttonDot = view.findViewById(R.id.button_dot);

        buttonC.setOnClickListener(this);
        buttonBrackOpen.setOnClickListener(this);
        buttonBrackClose.setOnClickListener(this);
        buttonDivide.setOnClickListener(this);
        buttonMultiply.setOnClickListener(this);
        buttonPlus.setOnClickListener(this);
        buttonMinus.setOnClickListener(this);
        buttonEquals.setOnClickListener(this);
        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        buttonAC.setOnClickListener(this);
        buttonDot.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        if (buttonText.equals("AC")) {
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }

        if (buttonText.equals("=")) {
            String finalResult = getResult(dataToCalculate);
            if (!finalResult.equals("Err")) {
                resultTv.setText(finalResult);
            }
            solutionTv.setText("");
            return;
        }

        if (buttonText.equals("C")) {
            if (dataToCalculate.length() > 0) {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
            }
        } else {
            dataToCalculate = dataToCalculate + buttonText;
        }
        solutionTv.setText(dataToCalculate);
    }

    String getResult(String data) {
        try {
            if (data.isEmpty()) {
                return "";
            }

            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            data = data.replace("x", "*");

            // Find adjacent sets of parentheses and multiply their values together
            Pattern pattern = Pattern.compile("\\)\\(");
            Matcher matcher = pattern.matcher(data);
            while (matcher.find()) {
                String matched = matcher.group();
                int index = matcher.start() + 1;
                String left = data.substring(0, index);
                String right = data.substring(index + matched.length() - 1);
                String leftResult = context.evaluateString(scriptable, left, "Javascript", 1, null).toString();
                String rightResult = context.evaluateString(scriptable, right, "Javascript", 1, null).toString();
                data = leftResult + "*" + rightResult;
            }

            // Evaluate the final expression and format the result
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {
            return "Err";
        }
    }
}
