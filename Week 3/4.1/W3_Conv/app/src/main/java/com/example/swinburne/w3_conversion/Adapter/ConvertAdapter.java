package com.example.swinburne.w3_conversion.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.swinburne.w3_conversion.R;

/**
 * Created by iOSDev on 8/10/18.
 */

public class ConvertAdapter extends ArrayAdapter<String> {
    private String[] name;
    private Activity context;

    public ConvertAdapter(Activity context, String[] name) {
        super(context, R.layout.activity_listview, R.id.convert_title, name);
        this.context = context;
        this.name = name;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            convertView = layoutInflater.inflate(R.layout.activity_listview,null, true);
            viewHolder = new ViewHolder();
            viewHolder.title = convertView.findViewById(R.id.convert_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(name[position]);

        return convertView;
    }

    private class ViewHolder {
        public TextView title;

    }
}
