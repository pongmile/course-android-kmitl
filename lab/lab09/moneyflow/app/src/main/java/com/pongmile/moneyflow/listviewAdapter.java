package com.pongmile.moneyflow;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static com.pongmile.moneyflow.Constant.AMOUNT_COLUMN;
import static com.pongmile.moneyflow.Constant.ITEM_COLUMN;
import static com.pongmile.moneyflow.Constant.TYPE_COLUMN;

public class listviewAdapter extends BaseAdapter
{
    public ArrayList<HashMap> list;
    Activity activity;

    public listviewAdapter(Activity activity, ArrayList<HashMap> list) {
        super();
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    private class ViewHolder {
        TextView textType;
        TextView textItem;
        TextView textAmount;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater =  activity.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.listview_row, null);
            holder = new ViewHolder();
            holder.textType = convertView.findViewById(R.id.type);
            holder.textItem = convertView.findViewById(R.id.item);
            holder.textAmount = convertView.findViewById(R.id.amount);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        HashMap map = list.get(position);
        holder.textType.setText((CharSequence) map.get(TYPE_COLUMN));
        holder.textItem.setText((CharSequence) map.get(ITEM_COLUMN));
        holder.textAmount.setText('฿' + String.valueOf(map.get(AMOUNT_COLUMN)));

        convertView.measure(0, 0);
        holder.textType.setHeight(convertView.getMeasuredHeight());
        holder.textItem.setHeight(convertView.getMeasuredHeight());
        holder.textAmount.setHeight(convertView.getMeasuredHeight());

        return convertView;
    }

}
