package com.devmeharpk.transmissionlineanalysis;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView voltageSpinner, codeOfConductorSpinner;
    private TextInputLayout voltageLayout, powerLayout, powerFactorLayout, lengthOfLineLayout, codeOfConductorLayout;
    private TextInputEditText powerInput, powerFactorInput, lengthOfLineInput;
    private Button calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        voltageSpinner = findViewById(R.id.voltageSpinner);
        codeOfConductorSpinner = findViewById(R.id.codeOfConductorSpinner);
        voltageLayout = findViewById(R.id.voltageLayout);
        powerLayout = findViewById(R.id.powerLayout);
        powerFactorLayout = findViewById(R.id.powerFactorLayout);
        lengthOfLineLayout = findViewById(R.id.lengthOfLineLayout);
        codeOfConductorLayout = findViewById(R.id.codeOfConductorLayout);
        powerInput = findViewById(R.id.powerInput);
        powerFactorInput = findViewById(R.id.powerFactorInput);
        lengthOfLineInput = findViewById(R.id.lengthOfLineInput);
        calculateButton = findViewById(R.id.calculateButton);

        // Set up Voltage dropdown menu
        String[] voltageOptions = {"132KV", "500KV"};
        ArrayAdapter<String> voltageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, voltageOptions);
        voltageSpinner.setAdapter(voltageAdapter);

        voltageSpinner.setOnClickListener(v -> voltageSpinner.showDropDown());
        voltageSpinner.setOnItemClickListener((parent, view, position, id) ->
                Toast.makeText(this, "Voltage: " + voltageOptions[position], Toast.LENGTH_SHORT).show()
        );

        // Set up Code of Conductor dropdown menu
        String[] conductorOptions = {"Alpha", "Beta"};
        ArrayAdapter<String> conductorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, conductorOptions);
        codeOfConductorSpinner.setAdapter(conductorAdapter);

        codeOfConductorSpinner.setOnClickListener(v -> codeOfConductorSpinner.showDropDown());
        codeOfConductorSpinner.setOnItemClickListener((parent, view, position, id) ->
                Toast.makeText(this, "Conductor: " + conductorOptions[position], Toast.LENGTH_SHORT).show()
        );

        // Handle Calculate button click
        calculateButton.setOnClickListener(v -> {
            String voltage = voltageSpinner.getText().toString().trim();
            String conductorCode = codeOfConductorSpinner.getText().toString().trim();
            String power = powerInput.getText().toString().trim();
            String powerFactor = powerFactorInput.getText().toString().trim();
            String lengthOfLine = lengthOfLineInput.getText().toString().trim();

            if (voltage.isEmpty()) {
                Toast.makeText(this, "Select voltage!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (conductorCode.isEmpty()) {
                Toast.makeText(this, "Select Conductor Code!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (power.isEmpty() || powerFactor.isEmpty() || lengthOfLine.isEmpty()) {
                Toast.makeText(this, "Fill all fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double powerValue = Double.parseDouble(power);
                double pfValue = Double.parseDouble(powerFactor);
                double lineLength = Double.parseDouble(lengthOfLine);

                if (pfValue < 0 || pfValue > 1) {
                    Toast.makeText(this, "Power Factor: 0 - 1", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, voltage + " | " + powerValue + "MW | PF: " + pfValue
                                    + " | Line: " + lineLength + "km | Conductor: " + conductorCode,
                            Toast.LENGTH_LONG).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Enter valid numbers!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
