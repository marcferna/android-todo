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

import java.util.Collections;
import java.util.Comparator;
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
    final TodoItemHolder holder;

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
    updateView(holder.titleTextView, item.done);
    holder.doneCheckbox.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        boolean isChecked = ((CompoundButton) v).isChecked();
        ((Todo)context).todoItemChecked(position, isChecked);
        updateView(holder.titleTextView, isChecked);
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

  @Override
  public void notifyDataSetChanged() {
    Collections.sort(data, new Comparator<TodoItem>() {
      @Override
      public int compare(TodoItem todo1, TodoItem todo2) {
        return ((Integer) (todo1.priority)).compareTo(todo2.priority);
      }

    });

    super.notifyDataSetChanged();
  }

  /**
   * Updates the TextView for the title based on if the item is done or not
   * @param textView TextView with the TodoItem title
   * @param isChecked true if the item is done, false otherwise
   */
  private void updateView(TextView textView, boolean isChecked) {
    if (isChecked) {
      textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    } else {
      textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
    }
  }

  class TodoItemHolder {
    public TextView titleTextView;
    public CheckBox doneCheckbox;
  }
}
