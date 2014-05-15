package com.todo.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class Todo extends Activity {
  ArrayList<String> items;
  ArrayAdapter<String> itemsAdapter;
  ListView lvItems;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_todo);

    lvItems = (ListView) findViewById(R.id.lvItems);
    items = new ArrayList<String>();
    itemsAdapter = new ArrayAdapter<String>(this,
      android.R.layout.simple_list_item_1, items);
    lvItems.setAdapter(itemsAdapter);
    items.add("First Item");
    items.add("Second Item");
    setupListViewListener();
  }

  public void addTodoItem(View v) {
    EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
    // check that the new item is not empty
    if (etNewItem.getText().toString().length() == 0) return;
    itemsAdapter.add(etNewItem.getText().toString());
    etNewItem.setText("");
  }

  private void setupListViewListener() {
    lvItems.setOnItemLongClickListener(
      new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(
          AdapterView<?> parent,
          View view,
          int position,
          long id
        ) {
          items.remove(position);
          itemsAdapter.notifyDataSetChanged();
          return true;
        }
    });
  }
}
