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

    private AutoCompleteTextView voltageSpinner, codeOfConductorSpinner, weatherConditionSpinner;
    private TextInputEditText powerInput, powerFactorInput, lengthOfLineInput;
    private TextInputEditText distanceABInput, distanceBCInput, distanceACInput;
    private TextInputEditText temperatureInput, pressureInput;
    private TextView radiusValue, areaValue, resistanceValue; // Non-editable values
    private Button calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        voltageSpinner = findViewById(R.id.voltageSpinner);
        codeOfConductorSpinner = findViewById(R.id.codeOfConductorSpinner);
        weatherConditionSpinner = findViewById(R.id.weatherConditionSpinner);
        powerInput = findViewById(R.id.powerInput);
        powerFactorInput = findViewById(R.id.powerFactorInput);
        lengthOfLineInput = findViewById(R.id.lengthOfLineInput);
        distanceABInput = findViewById(R.id.distanceABInput);
        distanceBCInput = findViewById(R.id.distanceBCInput);
        distanceACInput = findViewById(R.id.distanceACInput);
        temperatureInput = findViewById(R.id.temperatureInput);
        pressureInput = findViewById(R.id.pressureInput);
        calculateButton = findViewById(R.id.calculateButton);

        // Non-editable TextViews for Radius, Area, and Resistance
        radiusValue = findViewById(R.id.radiusValue);
        areaValue = findViewById(R.id.areaValue);
        resistanceValue = findViewById(R.id.resistanceValue);

        // Set up Voltage dropdown
        String[] voltageOptions = {"132KV", "500KV"};
        ArrayAdapter<String> voltageAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, voltageOptions);
        voltageSpinner.setAdapter(voltageAdapter);
        voltageSpinner.setOnClickListener(v -> voltageSpinner.showDropDown());
        voltageSpinner.setOnItemClickListener((parent, view, position, id) ->
                Toast.makeText(this, "Voltage: " + voltageOptions[position], Toast.LENGTH_SHORT).show()
        );

        // Set up Conductor dropdown
        String[] conductorOptions = {"Alpha", "Beta"};
        ArrayAdapter<String> conductorAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, conductorOptions);
        codeOfConductorSpinner.setAdapter(conductorAdapter);
        codeOfConductorSpinner.setOnClickListener(v -> codeOfConductorSpinner.showDropDown());
        codeOfConductorSpinner.setOnItemClickListener((parent, view, position, id) -> {
            String selectedConductor = conductorOptions[position];
            Toast.makeText(this, "Conductor: " + selectedConductor, Toast.LENGTH_SHORT).show();

            // Update Radius, Area, and Resistance based on conductor selection
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

        // Set up Weather Condition dropdown
        String[] weatherOptions = {"Sunny", "Stormy"};
        ArrayAdapter<String> weatherAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, weatherOptions);
        weatherConditionSpinner.setAdapter(weatherAdapter);
        weatherConditionSpinner.setOnClickListener(v -> weatherConditionSpinner.showDropDown());
        weatherConditionSpinner.setOnItemClickListener((parent, view, position, id) ->
                Toast.makeText(this, "Weather: " + weatherOptions[position], Toast.LENGTH_SHORT).show()
        );

        // Handle Calculate button click
        calculateButton.setOnClickListener(v -> {
            String voltage = voltageSpinner.getText().toString().trim();
            String conductorCode = codeOfConductorSpinner.getText().toString().trim();
            String weather = weatherConditionSpinner.getText().toString().trim();
            String power = powerInput.getText().toString().trim();
            String powerFactor = powerFactorInput.getText().toString().trim();
            String lengthOfLine = lengthOfLineInput.getText().toString().trim();
            String distanceAB = distanceABInput.getText().toString().trim();
            String distanceBC = distanceBCInput.getText().toString().trim();
            String distanceAC = distanceACInput.getText().toString().trim();
            String temperature = temperatureInput.getText().toString().trim();
            String pressure = pressureInput.getText().toString().trim();

            // Check for empty fields and display specific messages
            if (voltage.isEmpty()) {
                Toast.makeText(this, "Please select Voltage!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (conductorCode.isEmpty()) {
                Toast.makeText(this, "Please select Code of Conductor!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (weather.isEmpty()) {
                Toast.makeText(this, "Please select Weather Condition!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (power.isEmpty()) {
                Toast.makeText(this, "Please enter Power!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (powerFactor.isEmpty()) {
                Toast.makeText(this, "Please enter Power Factor!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (lengthOfLine.isEmpty()) {
                Toast.makeText(this, "Please enter Length of Line!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (distanceAB.isEmpty()) {
                Toast.makeText(this, "Please enter Distance Between A and B!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (distanceBC.isEmpty()) {
                Toast.makeText(this, "Please enter Distance Between B and C!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (distanceAC.isEmpty()) {
                Toast.makeText(this, "Please enter Distance Between A and C!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (temperature.isEmpty()) {
                Toast.makeText(this, "Please enter Temperature!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (pressure.isEmpty()) {
                Toast.makeText(this, "Please enter Pressure!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double powerValue = Double.parseDouble(power);
                double pfValue = Double.parseDouble(powerFactor);
                double lineLength = Double.parseDouble(lengthOfLine);
                double distAB = Double.parseDouble(distanceAB);
                double distBC = Double.parseDouble(distanceBC);
                double distAC = Double.parseDouble(distanceAC);
                double tempValue = Double.parseDouble(temperature);
                double pressureValue = Double.parseDouble(pressure);

                if (pfValue < 0 || pfValue > 1) {
                    Toast.makeText(this, "Power Factor must be between 0 and 1!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, voltage + " | " + powerValue + "MW | PF: " + pfValue
                                    + " | Line: " + lineLength + "km | Conductor: " + conductorCode
                                    + " | Weather: " + weather
                                    + " | Temp: " + tempValue + "°C | Pressure: " + pressureValue + "Pa"
                                    + " | Distances: AB=" + distAB + "m, BC=" + distBC + "m, AC=" + distAC + "m",
                            Toast.LENGTH_LONG).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Enter valid numbers for numeric fields!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
