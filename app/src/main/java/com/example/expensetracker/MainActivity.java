package com.example.expensetracker;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    EditText etAmount, etDescription;
    TextView tvTotal;
    ListView listView;
    Button btnAdd, btnRemove, btnClear;
    DBHelper dbHelper;
    ArrayList<Transaction> transactions;
    TransactionAdapter adapter;
    double totalAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etAmount = findViewById(R.id.etAmount);
        etDescription = findViewById(R.id.etDescription);
        tvTotal = findViewById(R.id.tvTotal);
        listView = findViewById(R.id.listView);
        btnAdd = findViewById(R.id.btnAdd);
        btnRemove = findViewById(R.id.btnRemove);
        btnClear = findViewById(R.id.btnClear);

        dbHelper = new DBHelper(this);
        transactions = dbHelper.getAllTransactions();
        adapter = new TransactionAdapter(this, transactions);
        listView.setAdapter(adapter);

        totalAmount = calculateTotal(transactions);
        updateTotal();

        btnAdd.setOnClickListener(v -> handleTransaction("add"));
        btnRemove.setOnClickListener(v -> handleTransaction("remove"));

        btnClear.setOnClickListener(v -> {
            dbHelper.clearAll();
            transactions.clear();
            adapter.notifyDataSetChanged();
            totalAmount = 0;
            updateTotal();
        });
    }

    private void handleTransaction(String type) {
        String desc = etDescription.getText().toString().trim();
        String amtStr = etAmount.getText().toString().trim();
        if (desc.isEmpty() || amtStr.isEmpty()) {
            Toast.makeText(this, "Enter description and amount", Toast.LENGTH_SHORT).show();
            return;
        }
        double amount = Double.parseDouble(amtStr);
        if (type.equals("remove")) amount *= -1;

        String dateTime = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(new Date());
        dbHelper.insertTransaction(desc, Math.abs(amount), type, dateTime);
        transactions.clear();
        transactions.addAll(dbHelper.getAllTransactions());
        adapter.notifyDataSetChanged();

        totalAmount += amount;
        updateTotal();
        etAmount.setText("");
        etDescription.setText("");
    }

    private double calculateTotal(ArrayList<Transaction> list) {
        double total = 0;
        for (Transaction t : list) {
            if (t.getType().equals("add")) total += t.getAmount();
            else total -= t.getAmount();
        }
        return total;
    }

    private void updateTotal() {
        tvTotal.setText("Total: ₹" + totalAmount);
    }
}