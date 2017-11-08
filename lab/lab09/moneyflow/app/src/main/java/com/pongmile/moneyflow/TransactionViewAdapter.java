package com.pongmile.moneyflow;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class TransactionViewAdapter extends RecyclerView.Adapter<TransactionViewAdapter.Holder> {

    public interface TransactionViewOnClickListener {
        void onItemClicked(int position);
    }

    private List<Transaction> allTransaction;
    private TransactionViewOnClickListener listener;

    public void setListener(TransactionViewOnClickListener listener) {
        this.listener = listener;
    }

    public void setAllTransaction(List<Transaction> allTransaction) {
        this.allTransaction = allTransaction;
    }

    public TransactionViewAdapter() {

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item, null);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        Transaction transaction = allTransaction.get(position);
        holder.amountTextView.setText(String.valueOf(transaction.getAmount()));
        holder.nameTextView.setText(transaction.getName());
        holder.nameTextView.setTextColor(Color.WHITE);

        if(transaction.getMode() == 1) {
            holder.modeTextView.setText("in");
            holder.amountTextView.setTextColor(Color.GREEN);
            holder.modeTextView.setTextColor(Color.GREEN);
        } else {
            holder.modeTextView.setText("out");
            holder.amountTextView.setTextColor(Color.RED);
            holder.modeTextView.setTextColor(Color.RED);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return allTransaction.size();
    }

    class Holder extends RecyclerView.ViewHolder  {

        public TextView modeTextView;
        TextView nameTextView;
        TextView amountTextView;

        public Holder(View itemView) {
            super(itemView);

            modeTextView = itemView.findViewById(R.id.itemmode);
            nameTextView = itemView.findViewById(R.id.itemname);
            amountTextView = itemView.findViewById(R.id.itemamount);

        }
    }
}
