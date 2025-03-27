package com.devmeharpk.transmissionlineanalysis;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView voltageSpinner;
    private TextInputLayout voltageLayout, powerLayout, powerFactorLayout;
    private TextInputEditText powerInput, powerFactorInput;
    private Button calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        voltageSpinner = findViewById(R.id.voltageSpinner);
        voltageLayout = findViewById(R.id.voltageLayout);
        powerLayout = findViewById(R.id.powerLayout);
        powerFactorLayout = findViewById(R.id.powerFactorLayout);
        powerInput = findViewById(R.id.powerInput);
        powerFactorInput = findViewById(R.id.powerFactorInput);
        calculateButton = findViewById(R.id.calculateButton);

        // Set up dropdown menu
        String[] voltageOptions = {"132KV", "500KV"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, voltageOptions);
        voltageSpinner.setAdapter(adapter);

        // Ensure dropdown opens on click
        voltageSpinner.setOnClickListener(v -> voltageSpinner.showDropDown());

        // Change border color when selecting voltage
        voltageSpinner.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                setPurpleBorder(voltageLayout);
            }
        });

        voltageSpinner.setOnItemClickListener((parent, view, position, id) -> {
            setPurpleBorder(voltageLayout);
        });

        // Apply border change for power & power factor inputs
        setFocusChangeListener(powerInput, powerLayout);
        setFocusChangeListener(powerFactorInput, powerFactorLayout);

        // Handle calculate button click
        calculateButton.setOnClickListener(v -> {
            String selectedVoltage = voltageSpinner.getText().toString();
            String power = powerInput.getText().toString();
            String powerFactor = powerFactorInput.getText().toString();

            if (selectedVoltage.isEmpty() || power.isEmpty() || powerFactor.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    double powerValue = Double.parseDouble(power);
                    double powerFactorValue = Double.parseDouble(powerFactor);

                    // Validate Power Factor is between 0 and 1
                    if (powerFactorValue < 0 || powerFactorValue > 1) {
                        Toast.makeText(MainActivity.this, "Power Factor should be between 0 and 1", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Calculation Started!", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Set purple border and hint
    private void setPurpleBorder(TextInputLayout layout) {
        layout.setBoxStrokeColor(Color.parseColor("#6200EE")); // Purple Border
        layout.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#6200EE"))); // Purple Hint
    }

    // Handle focus change for EditText fields
    private void setFocusChangeListener(TextInputEditText editText, TextInputLayout layout) {
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                setPurpleBorder(layout);
            }
        });
    }
}
