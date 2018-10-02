package com.example.siddhantlad.chefiecompile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SpinnerAdapter extends BaseAdapter {

    private ArrayList<String> mSpinnerItems;
    private ArrayList<String> mSpinnerItemsPosition;
    private ArrayList<String> mData;
    private Context mContext;

    public SpinnerAdapter(ArrayList<String> data, ArrayList<String> spinnerItems,ArrayList<String> spinnerItemsPosition, Context context) {
        mData = data;
        mContext = context;
        mSpinnerItems = spinnerItems;
        mSpinnerItemsPosition = spinnerItemsPosition;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_item, null);
        }

        TextView textView = (TextView) view.findViewById(R.id.row_item_textview);
        Spinner spinner = (Spinner) view.findViewById(R.id.row_item_spinner);
        Spinner spinnerPos = (Spinner) view.findViewById(R.id.row_item_spinner_position);
        textView.setText(mData.get(position));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, mSpinnerItems);
        ArrayAdapter<String> adapterPos = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, mSpinnerItemsPosition);
        spinner.setAdapter(adapter);
        spinnerPos.setAdapter(adapterPos);
        return view;
    }
}
