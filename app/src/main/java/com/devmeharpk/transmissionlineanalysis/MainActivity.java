package com.devmeharpk.transmissionlineanalysis;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView voltageSpinner, codeOfConductorSpinner;
    private TextInputEditText powerInput, powerFactorInput, lengthOfLineInput;
    private TextView radiusValue, areaValue, resistanceValue;
    private Button calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        voltageSpinner = findViewById(R.id.voltageSpinner);
        codeOfConductorSpinner = findViewById(R.id.codeOfConductorSpinner);
        powerInput = findViewById(R.id.powerInput);
        powerFactorInput = findViewById(R.id.powerFactorInput);
        lengthOfLineInput = findViewById(R.id.lengthOfLineInput);
        calculateButton = findViewById(R.id.calculateButton);

        // TextViews for displaying values
        radiusValue = findViewById(R.id.radiusValue);
        areaValue = findViewById(R.id.areaValue);
        resistanceValue = findViewById(R.id.resistanceValue);

        // Set up Voltage dropdown menu
        String[] voltageOptions = {"132KV", "500KV"};
        ArrayAdapter<String> voltageAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, voltageOptions);
        voltageSpinner.setAdapter(voltageAdapter);
        voltageSpinner.setOnClickListener(v -> voltageSpinner.showDropDown());

        // Set up Code of Conductor dropdown menu
        String[] conductorOptions = {"Alpha", "Beta"};
        ArrayAdapter<String> conductorAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, conductorOptions);
        codeOfConductorSpinner.setAdapter(conductorAdapter);
        codeOfConductorSpinner.setOnClickListener(v -> codeOfConductorSpinner.showDropDown());

        // Update values when conductor code is selected
        codeOfConductorSpinner.setOnItemClickListener((parent, view, position, id) -> {
            String selectedConductor = conductorOptions[position];
            if (selectedConductor.equals("Alpha")) {
                radiusValue.setText("Radius: 1 cm");
                areaValue.setText("Area: 1 m²");
                resistanceValue.setText("Resistance: 1 Ω/km");
            } else if (selectedConductor.equals("Beta")) {
                radiusValue.setText("Radius: 2 cm");
                areaValue.setText("Area: 2 m²");
                resistanceValue.setText("Resistance: 2 Ω/km");
            }
        });

        // Handle Calculate button click
        calculateButton.setOnClickListener(v -> {
            String voltage = voltageSpinner.getText().toString().trim();
            String conductorCode = codeOfConductorSpinner.getText().toString().trim();
            String power = powerInput.getText().toString().trim();
            String powerFactor = powerFactorInput.getText().toString().trim();
            String lengthOfLine = lengthOfLineInput.getText().toString().trim();

            // Check for empty fields
            if (voltage.isEmpty() || conductorCode.isEmpty() || power.isEmpty() ||
                    powerFactor.isEmpty() || lengthOfLine.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double powerValue = Double.parseDouble(power);
                double pfValue = Double.parseDouble(powerFactor);
                double lineLength = Double.parseDouble(lengthOfLine);

                if (pfValue < 0 || pfValue > 1) {
                    Toast.makeText(this, "Power Factor must be between 0 and 1!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, voltage + " | " + powerValue + "MW | PF: " + pfValue
                                    + " | Line: " + lineLength + "km | Conductor: " + conductorCode,
                            Toast.LENGTH_LONG).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Enter valid numbers for Power, Power Factor, and Length of Line!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
