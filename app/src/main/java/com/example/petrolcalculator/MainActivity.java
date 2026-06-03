package com.example.petrolcalculator;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Spinner idPetrolType;
    private EditText petrolPrice, fuelUsage;
    private Switch idBudi;
    private TextView tvTotalCost, tvRebate, tvTotalSaving;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        idPetrolType = findViewById(R.id.idPetrolType);
        petrolPrice = findViewById(R.id.petrolPrice);
        fuelUsage = findViewById(R.id.fuelUsage);
        idBudi = findViewById(R.id.idBudi);
        tvTotalCost = findViewById(R.id.tvTotalCost);
        tvRebate = findViewById(R.id.tvRebate);
        tvTotalSaving = findViewById(R.id.tvTotalSaving);
        Button btnCalculate = findViewById(R.id.btnCalculate);

        idPetrolType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedPetrol = parent.getItemAtPosition(position).toString();

                if (selectedPetrol.equals("RON95")) {
                    idBudi.setVisibility(View.VISIBLE);
                } else {
                    idBudi.setVisibility(View.GONE);
                    idBudi.setChecked(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnCalculate.setOnClickListener(v -> calculateCosts());
    }

    private void calculateCosts() {
        String priceStr = petrolPrice.getText().toString();
        String usageStr = fuelUsage.getText().toString();
        String petrolType = idPetrolType.getSelectedItem().toString();
        boolean isBudiEligible = idBudi.isChecked();

        if (priceStr.isEmpty() || usageStr.isEmpty()) {
            Toast.makeText(this, "Please enter price and usage", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);
        double usage = Double.parseDouble(usageStr);

        double totalCost = usage * price;
        double rebate = 0.0;

        if (isBudiEligible && petrolType.equals("RON95")) {
            rebate = usage * 1.99;
        }

        double totalSaving = totalCost - rebate;

        tvTotalCost.setText(String.format("Total Cost: RM %.2f", totalCost));
        tvRebate.setText(String.format("BUDI Rebate: RM %.2f", rebate));
        tvTotalSaving.setText(String.format("Total Saving: RM %.2f", totalSaving));
    }
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == R.id.nav_about) {
            android.content.Intent intent = new android.content.Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}