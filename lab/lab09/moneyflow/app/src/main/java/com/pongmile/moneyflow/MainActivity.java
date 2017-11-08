package com.pongmile.moneyflow;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TransactionDialogFragment.TransactionCallbackListener, TransactionViewAdapter.TransactionViewOnClickListener, TransactionDialogEdit.TransactionEditCallbackListener{

    private TransactionDialogFragment transactionDialogFragment;
    private TransactionDialogEdit transactionDialogEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateListView();
    }

    public void onAddButtonPressed(View view) {
        transactionDialogFragment = new TransactionDialogFragment();
        transactionDialogFragment.setListener(this);
        transactionDialogFragment.show(getSupportFragmentManager(), "Add new Transaction");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onExpenseButtonPressed(View view) {
        if(transactionDialogFragment != null) {
            transactionDialogFragment.onExpenseButtonPressed();
        }

        if(transactionDialogEdit != null) {
            transactionDialogEdit.onExpenseButtonPressed();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onIncomeButtonPressed(View view) {
        if(transactionDialogFragment != null) {
            transactionDialogFragment.onIncomeButtonPressed();
        }

        if(transactionDialogEdit != null) {
            transactionDialogEdit.onIncomeButtonPressed();
        }
    }

    @Override
    public void onAddNewTransaction(int mode, String name, double amount) {
        Toast.makeText(this, name + " " + amount, Toast.LENGTH_SHORT).show();
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setMode(mode);
        transaction.setName(name);
        AppDatabase.getAppDatabase(this).transactionDao().insertAll(transaction);
        updateListView();
    }

    private String formatNumberString(double number) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(true);

        return  numberFormat.format(number);
    }

    private void updateListView() {
        TextView balanceTextView = findViewById(R.id.balanceTextView);

        Double sum = 0.0;
        Double income = 0.0;

        String output = "";

        List<Transaction> allTransaction = AppDatabase.getAppDatabase(this).transactionDao().getAll();

        for(Transaction transaction: allTransaction) {
            output += transaction.getName() + " " + transaction.getAmount()*transaction.getMode();
            sum += transaction.getAmount()*transaction.getMode();
            if(transaction.getMode() == 1 ) {
                income += transaction.getAmount();
            }
        }

        balanceTextView.setText(formatNumberString(sum));

        if(sum > income * 0.5) {
            balanceTextView.setTextColor(Color.GREEN);
        } else if (sum >= income * 0.25) {
            balanceTextView.setTextColor(Color.YELLOW);
        } else {
            balanceTextView.setTextColor(Color.RED);
        }

        TransactionViewAdapter transactionViewAdapter = new TransactionViewAdapter();
        transactionViewAdapter.setAllTransaction(allTransaction);
        transactionViewAdapter.setListener(this);

        RecyclerView recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(transactionViewAdapter);


    }

    @Override
    public void onItemClicked(int position) {
        List<Transaction> allTransaction = AppDatabase.getAppDatabase(this).transactionDao().getAll();
        Transaction currentTransaction = allTransaction.get(position);

        transactionDialogEdit = new TransactionDialogEdit();
        transactionDialogEdit.setListener(this);
        transactionDialogEdit.setupExistingDataField(position, currentTransaction.getMode(), currentTransaction.getName(), currentTransaction.getAmount());
        transactionDialogEdit.show(getSupportFragmentManager(), "Edit Transaction");
    }

    @Override
    public void onEditTransaction(int position, int mode, String name, double amount) {
        List<Transaction> allTransaction = AppDatabase.getAppDatabase(this).transactionDao().getAll();
        Transaction currentTransaction = allTransaction.get(position);
        currentTransaction.setName(name);
        currentTransaction.setMode(mode);
        currentTransaction.setAmount(amount);

        AppDatabase.getAppDatabase(this).transactionDao().update(currentTransaction);

        updateListView();
    }

    @Override
    public void onDeleteTransaction(int position) {
        List<Transaction> allTransaction = AppDatabase.getAppDatabase(this).transactionDao().getAll();
        Transaction currentTransaction = allTransaction.get(position);
        AppDatabase.getAppDatabase(this).transactionDao().delete(currentTransaction);

        updateListView();
    }
}
