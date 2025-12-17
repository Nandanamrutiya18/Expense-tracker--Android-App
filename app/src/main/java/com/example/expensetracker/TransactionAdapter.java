package com.example.expensetracker;

import android.content.Context;
import android.view.*;
import android.widget.*;
import java.util.ArrayList;

public class TransactionAdapter extends ArrayAdapter<Transaction>
{
    public TransactionAdapter(Context context, ArrayList<Transaction> transactions) {
        super(context, 0, transactions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Transaction transaction = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        }

        TextView line1 = convertView.findViewById(android.R.id.text1);
        TextView line2 = convertView.findViewById(android.R.id.text2);

        String prefix = transaction.getType().equals("add") ? "+ " : "- ";
        line1.setText(prefix + transaction.getDescription() + " ₹" + transaction.getAmount());
        line2.setText(transaction.getDateTime());

        line1.setTextSize(18);
        line2.setTextSize(14);

        return convertView;
    }
}