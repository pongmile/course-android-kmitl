package com.pongmile.moneyflow;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pongmile.moneyflow.Constant.AMOUNT_COLUMN;
import static com.pongmile.moneyflow.Constant.ITEM_COLUMN;
import static com.pongmile.moneyflow.Constant.TYPE_COLUMN;

public class MainActivity extends AppCompatActivity{

    public static MoneyDB moneyDB;
    private int allamount = 0;
    private AlertDialog.Builder builderSingle;
    private ArrayList<HashMap> list;

    @BindView(R.id.amountTextView)
    TextView amountTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this, this);
        builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle("เลือก");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("แก้ไข");
        arrayAdapter.add("ลบ");
        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        moneyDB = Room.databaseBuilder(this, MoneyDB.class, "MONEY")
                .allowMainThreadQueries()
                .build();

        ListView lview = (ListView) findViewById(R.id.listview);
        Constant.keep_income = 0;
        populateList();

        listviewAdapter adapter = new listviewAdapter(this, list);
        lview.setAdapter(adapter);
        Constant.activity = this;

        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {

                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @SuppressLint("StaticFieldLeak")
                    @Override
                    public void onClick(DialogInterface dialog, int menu) {
                        if (menu == 0){
                            Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                            intent.putExtra("position", position-1);
                            Constant.Checkedit = 1;
                            startActivity(intent);
                        }else{

                            new AsyncTask<Void, Void, List<Money>>() {
                                @Override
                                protected List<Money> doInBackground(Void... voids) {
                                    List<Money> result = moneyDB.moneyDAO().allItem();
                                    return result;
                                }

                                @Override
                                protected void onPostExecute(List<Money> money) {
                                    moneyDB.moneyDAO().delete(money.get(position-1).getId());
                                }

                                @Override
                                protected void onProgressUpdate(Void... values) {
                                }
                            }.execute();

                            reload();
                        }

                    }
                });
                builderSingle.show();
            }
        });
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    @SuppressLint("StaticFieldLeak")
    private void populateList() {
        list = new ArrayList<HashMap>();
        HashMap temp = new HashMap();

        temp.put(TYPE_COLUMN,"ประเภท");
        temp.put(ITEM_COLUMN, "รายการ");
        temp.put(AMOUNT_COLUMN, "จำนวน");

        list.add(temp);

        new AsyncTask<Void, Void, List<Money>>() {
            @Override
            protected List<Money> doInBackground(Void... voids) {
                List<Money> result = moneyDB.moneyDAO().allItem();
                return result;
            }

            @Override
            protected void onPostExecute(List<Money> monies) {
                for (Money items: monies){
                    HashMap temp = new HashMap();
                    if (items.getType().equals("Income")) {
                        temp.put(TYPE_COLUMN, "+");
                        Constant.keep_income += items.getAmount();
                        allamount += items.getAmount();

                    }else{
                        temp.put(TYPE_COLUMN, "-");
                        allamount -= items.getAmount();
                    }
                    temp.put(ITEM_COLUMN, items.getItem());
                    temp.put(AMOUNT_COLUMN, items.getAmount());
                    list.add(temp);

                }
                ChangeColor(Constant.keep_income, allamount);
                amountTV.setText('฿' + String.valueOf(allamount));
            }

            @Override
            protected void onProgressUpdate(Void... values) {
            }
        }.execute();
    }

    private void ChangeColor(int allmoneyincome, int moneyleft) {
        if(allmoneyincome != 0) {
            if (moneyleft * 100 / allmoneyincome > 50)
                amountTV.setTextColor(getResources().getColor(R.color.green));
            else if (moneyleft * 100 / allmoneyincome > 25)
                amountTV.setTextColor(getResources().getColor(R.color.yellow));
            else
                amountTV.setTextColor(getResources().getColor(R.color.red));
        }
    }


    @OnClick(R.id.AddButton)
    public void add(){
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }
}
