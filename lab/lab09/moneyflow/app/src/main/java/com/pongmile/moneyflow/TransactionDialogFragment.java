package com.pongmile.moneyflow;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class TransactionDialogFragment extends DialogFragment {

    public interface TransactionCallbackListener {
        void onAddNewTransaction(int mode, String name, double amount);
    }

    private int mode =-1;
    private View view;
    private TransactionCallbackListener listener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        view = inflater.inflate(R.layout.transaction, null);

        builder.setView(view)
                .setPositiveButton("เพิ่ม", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        EditText nameEditText = view.findViewById(R.id.nameTextInput);
                        EditText amountEditText = view.findViewById(R.id.amountTextInput);

                        listener.onAddNewTransaction(mode, String.valueOf(nameEditText.getText()), Double.parseDouble(String.valueOf(amountEditText.getText())));
                    }
                })
                .setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void onIncomeButtonPressed() {
        Button incomeButton = view.findViewById(R.id.incomeButton);
        Button expenseButton = view.findViewById(R.id.expenseButton);

        incomeButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
        incomeButton.setTextColor(Color.WHITE);
        expenseButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F5F5F5")));
        expenseButton.setTextColor(Color.BLACK);

        mode = 1;

    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void onExpenseButtonPressed() {
        Button incomeButton = view.findViewById(R.id.incomeButton);
        Button expenseButton = view.findViewById(R.id.expenseButton);

        expenseButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
        expenseButton.setTextColor(Color.WHITE);
        incomeButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F5F5F5")));
        incomeButton.setTextColor(Color.BLACK);

        mode = -1;
    }

    public void setListener(TransactionCallbackListener listener) {
        this.listener = listener;
    }
}
