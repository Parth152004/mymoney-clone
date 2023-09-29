package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity2 extends AppCompatActivity {
    private RadioGroup radioGroup;
    private EditText noteEditText;
    private EditText amountEditText;
    private Button saveButton;
    private Button cancle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        radioGroup = findViewById(R.id.group);
        noteEditText = findViewById(R.id.editTextText);
        amountEditText = findViewById(R.id.editTextNumber);
        saveButton = findViewById(R.id.button3);
        cancle = findViewById(R.id.button2);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToInternalStorage();
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity2.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
    private void saveDataToInternalStorage() {
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        String note = noteEditText.getText().toString();
        String amount = amountEditText.getText().toString();

        if (selectedRadioButtonId == -1 || note.isEmpty() || amount.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
        String transactionType = selectedRadioButton.getText().toString();

        String dataToStore = "Transaction Type: " + transactionType + "  "
                + "Note: " + note + "  "
                + "Amount: " + amount + "  \n"; // Add a separator (e.g., newline)

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

