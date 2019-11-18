package gittaSz.todolist.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import gittaSz.todolist.R;
import gittaSz.todolist.model.ToDoItem;

public class ToDoListAdapter extends ArrayAdapter<ToDoItem> {

    private int resource;

    public ToDoListAdapter(Context context, int resource, List<ToDoItem> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ToDoItem tdi = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(resource,null);
        }

        TextView tvTask = convertView.findViewById(R.id.tvTask);
        TextView tvDate = convertView.findViewById(R.id.tvDate);


        String status = tdi.getStatus();
        switch (status){
            case "completed":
                tvTask.setText("\u2713 "+tdi.getTask());
                break;
            default:
                tvTask.setText(tdi.getTask());
        }


        String priority = tdi.getPriority();
        switch (priority){
            case "low":
                tvTask.setTextColor(0xff0007cc);
                break;
            case "medium":
                tvTask.setTextColor(0xffff8c00);
                break;
            default:
                tvTask.setTextColor(0xffdd1923);
        }


        tvDate.setText(tdi.getDate());

        return convertView;
    }
}
