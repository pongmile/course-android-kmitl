package com.pongmile.moneyflow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.nex3z.togglebuttongroup.SingleSelectToggleGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddActivity extends AppCompatActivity {
    private int position;
    private int SQL_ID;

    @BindView(R.id.Items_add)
    EditText item;

    @BindView(R.id.Add_Amount)
    EditText amount;


    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpage);
        ButterKnife.bind(this, this);
        Constant.ADD_STATE = "Income";
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        Log.d("addcheckposition", String.valueOf(position));

        if (Constant.Checkedit == 1) {
            new AsyncTask<Void, Void, List<Money>>() {
                @Override
                protected List<Money> doInBackground(Void... voids) {
                    List<Money> result = MainActivity.moneyDB.moneyDAO().allItem();
                    return result;
                }

                @Override
                protected void onPostExecute(List<Money> monies) {
                    item.setText(monies.get(position).getItem());
                    amount.setText(String.valueOf(monies.get(position).getAmount()));
                    SQL_ID = monies.get(position).getId();
                    Log.d("check ID", "onPostExecute: " + monies.get(position).getId());
                }
            }.execute();

        }

        final SingleSelectToggleGroup single = (SingleSelectToggleGroup) findViewById(R.id.group_choices);
        single.setOnCheckedChangeListener(new SingleSelectToggleGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SingleSelectToggleGroup group, int checkedId) {
                if (group.getCheckedId() == R.id.choice_a) {
                    //Income
                    Constant.ADD_STATE = "Income";
                    Log.d("state", Constant.ADD_STATE);
                } else if (group.getCheckedId() == R.id.choice_b) {
                    //Payout
                    Constant.ADD_STATE = "Payout";
                    Log.d("state", Constant.ADD_STATE);
                }
            }
        });
    }

    @SuppressLint("StaticFieldLeak")


    @OnClick(R.id.Save)
    public void add() {
        if(!item.getText().toString().equals("") && !amount.getText().toString().equals("")) {
            if (Constant.Checkedit == 1) {
                new AsyncTask<Void, Void, List<Money>>() {
                    @Override
                    protected List<Money> doInBackground(Void... voids) {
                        List<Money> result = MainActivity.moneyDB.moneyDAO().allItem();
                        return result;
                    }

                    @Override
                    protected void onPostExecute(List<Money> monies) {
                        MainActivity.moneyDB.moneyDAO().UpdateColumn(Constant.ADD_STATE,
                                item.getText().toString(),
                                Integer.valueOf(amount.getText().toString()), SQL_ID);
                    }
                }.execute();

            } else {
                final Money money = new Money();
                money.setItem(item.getText().toString());
                money.setAmount(Integer.valueOf(amount.getText().toString()));
                money.setType(Constant.ADD_STATE);
                new AsyncTask<Void, Void, List<Money>>() {
                    @Override
                    protected List<Money> doInBackground(Void... voids) {
                        List<Money> result = MainActivity.moneyDB.moneyDAO().allItem();
                        return result;
                    }

                    @Override
                    protected void onPostExecute(List<Money> monies) {
                        MainActivity.moneyDB.moneyDAO().Insert(money);
                    }
                }.execute();
            }
            Intent intent = new Intent(this, MainActivity.class);
            Constant.activity.finish();
            startActivity(intent);
            finish();
        }
    }
}
