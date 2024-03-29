package com.snltech.siddhantlad.chefiecompile;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SpinnerAdapter extends BaseAdapter {

    private Context context;
    ArrayList<String> selected;
    public static ArrayList<EditModel> editModelArrayList;
    TextView textview;
    public SpinnerAdapter(Context context, ArrayList<EditModel> editModelArrayList,ArrayList<String>selected) {

        this.context = context;
        this.editModelArrayList = editModelArrayList;
        this.selected=selected;
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

        //holder.editText.setText(editModelArrayList.get(position).getEditTextValue());
        //textview.setText(editModelArrayList.get(position).getEditTextValue());
        if (position<selected.size()) {
            int step=position+1;
            textview.setText("Step "+Integer.toString(step)+":");
        }else if (position==selected.size()){
            textview.setText("Extra:");
            LinearLayout layoutId=(LinearLayout) convertView.findViewById(R.id.layoutid);
            holder.editText.setText(editModelArrayList.get(position).getEditTextValue());
            layoutId.setVisibility(View.GONE);
        }
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

