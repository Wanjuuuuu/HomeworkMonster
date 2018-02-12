package com.example.wanjukim.homeworkmonster.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.wanjukim.homeworkmonster.R;
import com.example.wanjukim.homeworkmonster.models.Subject;

import java.util.List;

/**
 * Created by Wanju Kim on 2018-02-12.
 */

public class SubjectArrayAdapter extends ArrayAdapter<Subject> {
    private static class ViewHolder{
        private TextView textView;
    }

    public SubjectArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Subject> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(this.getContext()).inflate(android.R.layout.simple_list_item_1,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.textView=(TextView)convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder=(ViewHolder)convertView.getTag();
        }

        Subject subject=getItem(position);
        if(subject!=null){
            viewHolder.textView.setText(subject.getSubject());
        }

        return convertView;
    }
}
