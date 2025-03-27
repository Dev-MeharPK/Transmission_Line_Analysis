package com.devmeharpk.transmissionlineanalysis;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
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

        // Ensure dropdown menu opens on click
        voltageSpinner.setOnClickListener(v -> voltageSpinner.showDropDown());

        // Fix border color issue for voltage selection
        voltageSpinner.setOnItemClickListener((parent, view, position, id) -> {
            voltageLayout.setBoxStrokeColor(Color.parseColor("#6200EE")); // Set border to purple
            voltageLayout.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#6200EE"))); // Hint text color
        });

        // Fix border color issue for EditText fields
        setFocusChangeListener(powerInput, powerLayout);
        setFocusChangeListener(powerFactorInput, powerFactorLayout);

        // Handle button click
        calculateButton.setOnClickListener(v -> {
            String selectedVoltage = voltageSpinner.getText().toString();
            String power = powerInput.getText().toString();
            String powerFactor = powerFactorInput.getText().toString();

            if (selectedVoltage.isEmpty() || power.isEmpty() || powerFactor.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Calculation Started!", Toast.LENGTH_SHORT).show();
                // Add calculation logic here
            }
        });
    }

    // Function to change border color on focus
    private void setFocusChangeListener(TextInputEditText editText, TextInputLayout layout) {
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                layout.setBoxStrokeColor(Color.parseColor("#6200EE")); // Purple Border on focus
                layout.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#6200EE"))); // Change hint color
            } else {
                layout.setBoxStrokeColor(Color.parseColor("#808080")); // Gray Border on blur
                layout.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#808080"))); // Reset hint color
            }
        });
    }
}
