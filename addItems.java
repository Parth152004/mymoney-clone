package com.example.khatabook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class addItems extends AppCompatActivity {

    RadioGroup radioGroup;
    EditText noteEditText;
    EditText amountEditText;
    Button saveButton;
    Button cancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);
        radioGroup = findViewById(R.id.group);
        noteEditText = findViewById(R.id.editTextText);
        amountEditText = findViewById(R.id.editTextNumber);
        saveButton = findViewById(R.id.button3);
        cancle = findViewById(R.id.button2);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void save(View v)
    {
        saveDataToInternalStorage();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();

    }
    public void cancel(View v)
    {
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();

    }

    private void saveDataToInternalStorage() {

        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        String note = noteEditText.getText().toString();
        String amount = amountEditText.getText().toString();
        float amountF=Float.parseFloat(amount);

        if (selectedRadioButtonId == -1 || note.isEmpty() || amount.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
//        Float amount1=Float.parseFloat(amount);
        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
        String transactionType = selectedRadioButton.getText().toString();

        String dataToStore = "Transaction Type: " + transactionType + "  "
                + "Note: " + note + "  "
                + "Amount: " + amountF + "  \n"; // Add a separator (e.g., newline)

        try {
            FileOutputStream fos = openFileOutput("transaction_data.txt", MODE_APPEND); // Use MODE_APPEND
            fos.write(dataToStore.getBytes());
            fos.close();
            Toast.makeText(this, "Data saved to internal storage", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show();
        }

    }
}