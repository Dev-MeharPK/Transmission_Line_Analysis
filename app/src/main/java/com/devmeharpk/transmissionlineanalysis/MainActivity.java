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
        voltageSpinner.setOnClickListener(v -> {
            voltageSpinner.requestFocus();
            voltageSpinner.showDropDown();
        });

        // Show Toast when voltage is selected
        voltageSpinner.setOnItemClickListener((parent, view, position, id) ->
                Toast.makeText(this, "Voltage: " + voltageOptions[position], Toast.LENGTH_SHORT).show()
        );

        // Handle calculate button click
        calculateButton.setOnClickListener(v -> {
            String voltage = voltageSpinner.getText().toString().trim();
            String power = powerInput.getText().toString().trim();
            String powerFactor = powerFactorInput.getText().toString().trim();

            if (voltage.isEmpty()) {
                Toast.makeText(this, "Select voltage!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (power.isEmpty() || powerFactor.isEmpty()) {
                Toast.makeText(this, "Fill all fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double powerValue = Double.parseDouble(power);
                double pfValue = Double.parseDouble(powerFactor);

                if (pfValue < 0 || pfValue > 1) {
                    Toast.makeText(this, "Power Factor: 0 - 1", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, voltage + " | " + powerValue + "MW | PF: " + pfValue, Toast.LENGTH_LONG).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Enter valid numbers!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
