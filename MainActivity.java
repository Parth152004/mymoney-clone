package com.example.khatabook;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Button b1;
    ListView dataListView;

    float totalExpense;
    TextView expenseValue;
    float totalIncome;
    float totalTransfer;
    TextView incomeValue;
    TextView transferValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        b1=findViewById(R.id.b1);
        dataListView=findViewById(R.id.lv);
        expenseValue=findViewById(R.id.expenseValue);
        incomeValue=findViewById(R.id.incomeValue);
        transferValue=findViewById(R.id.transferValue);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, addItems.class);
                startActivity(i);

            }
        });
        ArrayList<String> data = readDataFromFile();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.listviewlayout,R.id.textView2,data);
        dataListView.setAdapter(adapter);

    }

    private ArrayList<String> readDataFromFile() {
        ArrayList<String> data = new ArrayList<>();
        try {
            FileInputStream fis = openFileInput("transaction_data.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            totalExpense=0;
            totalIncome=0;
            totalTransfer=0;
            String line = "";
            while ((line = br.readLine()) != null) {
                if(line.contains("EXPENCE")) {
                    int ind = line.indexOf("Amount:");
                    if (ind != -1) {
                        ind = ind + 7; // Move to the start of the amount
                        String amountStr = line.substring(ind).trim(); // Extract the amount
                        float amount = Float.parseFloat(amountStr);
                        totalExpense += amount; // Add the amount to the total expense
                        expenseValue.setText(String.valueOf(totalExpense)); // Update the TextView
                    }
                } else if (line.contains("INCOME")) {
                    int ind = line.indexOf("Amount:");
                    if (ind != -1) {
                        ind = ind + 7; // Move to the start of the amount
                        String amountStr = line.substring(ind).trim(); // Extract the amount
                        float amount = Float.parseFloat(amountStr);
                        totalIncome += amount; // Add the amount to the total expense
                        incomeValue.setText(String.valueOf(totalIncome)); // Update the TextView
                    }
                } else if (line.contains("TRANSFER")) {
                    int ind = line.indexOf("Amount:");
                    if (ind != -1) {
                        ind = ind + 7; // Move to the start of the amount
                        String amountStr = line.substring(ind).trim(); // Extract the amount
                        float amount = Float.parseFloat(amountStr);
                        totalTransfer += amount; // Add the amount to the total expense
                        transferValue.setText(String.valueOf(totalTransfer)); // Update the TextView
                    }
                }
                data.add(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;

    }




}