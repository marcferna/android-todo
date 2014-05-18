package com.todo.app.TodoItem;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.todo.app.R;
import com.todo.app.Todo;

import java.util.List;

public class TodoItemAdapter extends ArrayAdapter<TodoItem> {
  Context context;
  int layoutResourceId;
  List<TodoItem> data;

  public TodoItemAdapter(Context context, int layoutResourceId,
                         List<TodoItem> data) {

    super(context, layoutResourceId, data);
    this.layoutResourceId = layoutResourceId;
    this.context = context;
    this.data = data;
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    View row = convertView;
    TodoItemHolder holder;

    if (row == null) {
      LayoutInflater inflater = ((Activity)context).getLayoutInflater();
      row = inflater.inflate(layoutResourceId, parent, false);
      holder = new TodoItemHolder();
      holder.titleTextView = (TextView)row.findViewById(R.id.todoTitleTextView);
      holder.doneCheckbox = (CheckBox)row.findViewById(R.id.doneCheckbox);

      row.setTag(holder);
    } else {
      holder = (TodoItemHolder)row.getTag();
    }

    TodoItem item = data.get(position);
    holder.titleTextView.setText(item.title);
    holder.doneCheckbox.setChecked(item.done);
    holder.doneCheckbox.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        ((Todo)context).todoItemChecked(position,
            ((CompoundButton) v).isChecked());
      }
    });

    return row;
  }

  @Override
  public int getCount() {
    if (data == null) {
      return 0;
    }
    return data.size();
  }

  @Override
  public TodoItem getItem(int position) {
    return data.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  class TodoItemHolder {
    public TextView titleTextView;
    public CheckBox doneCheckbox;
  }
}
