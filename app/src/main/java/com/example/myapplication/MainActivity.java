package com.example.myapplication;

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

public class MainActivity extends AppCompatActivity {
    Button b1;
    static long total_Expance = 0;
    static long total_transfar = 0;
    static long total_Income = 0;
    TextView t2,t4,t5;
    private ListView dataListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 =(Button) findViewById(R.id.button);
        dataListView = findViewById(R.id.dataListView);
        t2 = (TextView)findViewById(R.id.textView2);
        t4 = (TextView)findViewById(R.id.textView4);
        t5 = (TextView)findViewById(R.id.textView6);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(i);
            }
        });
        ArrayList<String> data = readDataFromFile();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        dataListView.setAdapter(adapter);
    }
    private ArrayList<String> readDataFromFile() {
        ArrayList<String> data = new ArrayList<>();
        try {
            FileInputStream fis = openFileInput("transaction_data.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            total_Expance=0;
            total_Income=0;
            total_transfar=0;
            while ((line = br.readLine()) != null) {
                if(line.contains("EXPENCE")) {
                    int ind = line.indexOf("Amount:");
                    if (ind != -1) {
                        ind = ind + 7; // Move to the start of the amount
                        String amountStr = line.substring(ind).trim(); // Extract the amount
                        long amount = Integer.parseInt(amountStr);
                        total_Expance += amount; // Add the amount to the total expense
                        t2.setText(String.valueOf(total_Expance)); // Update the TextView
                    }
                } else if (line.contains("INCOME")) {
                    int ind = line.indexOf("Amount:");
                    if (ind != -1) {
                        ind = ind + 7; // Move to the start of the amount
                        String amountStr = line.substring(ind).trim(); // Extract the amount
                        int amount = Integer.parseInt(amountStr);
                        total_Income += amount; // Add the amount to the total expense
                        t4.setText(String.valueOf(total_Income)); // Update the TextView
                    }
                } else if (line.contains("TRANSFER")) {
                    int ind = line.indexOf("Amount:");
                    if (ind != -1) {
                        ind = ind + 7; // Move to the start of the amount
                        String amountStr = line.substring(ind).trim(); // Extract the amount
                        int amount = Integer.parseInt(amountStr);
                        total_transfar += amount; // Add the amount to the total expense
                        t5.setText(String.valueOf(total_transfar)); // Update the TextView
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