package com.example.siddhantlad.chefiecompile;
/*

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
    private ArrayList<String> mData;
    private Context mContext;

    public SpinnerAdapter(ArrayList<String> data, ArrayList<String> spinnerItems, Context context) {
        mData = data;
        mContext = context;
        mSpinnerItems = spinnerItems;
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
        //Spinner spinnerPos = (Spinner) view.findViewById(R.id.row_item_spinner_position);
        textView.setText(mData.get(position));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, mSpinnerItems);
        ArrayAdapter<String> adapterPos = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
       // spinnerPos.setAdapter(adapterPos);
        return view;
    }
}
*/

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class SpinnerAdapter extends BaseAdapter {

    private Context context;
    public static ArrayList<EditModel> editModelArrayList;
    TextView textview;
    public SpinnerAdapter(Context context, ArrayList<EditModel> editModelArrayList) {

        this.context = context;
        this.editModelArrayList = editModelArrayList;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return editModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return editModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_item, null, true);

            holder.editText = (EditText) convertView.findViewById(R.id.editid);
            textview=(TextView)convertView.findViewById(R.id.ingName);
            convertView.setTag(holder);
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder) convertView.getTag();
        }

      //  holder.editText.setText(editModelArrayList.get(position).getEditTextValue());
        textview.setText(editModelArrayList.get(position).getEditTextValue());
        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editModelArrayList.get(position).setEditTextValue(holder.editText.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return convertView;
    }

    private class ViewHolder {

        protected EditText editText;

    }
}

