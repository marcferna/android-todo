package com.todo.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.todo.app.PersistentStorage.PersistentStorage;
import com.todo.app.TodoItem.TodoItem;
import com.todo.app.TodoItem.TodoItemAdapter;

import java.util.ArrayList;

public class Todo extends Activity {

  /**
   * List with all the todo items
   */
  private ArrayList<TodoItem> items = new ArrayList<TodoItem>();

  /**
   * Adapter for the todo list view
   */
  private TodoItemAdapter itemsAdapter;

  /**
   * Request code for the edit item intent
   */
  private final int EDIT_ITEM_REQUEST_CODE = 1;

  private PersistentStorage persistentStorage;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_todo);

    persistentStorage = new PersistentStorage(Todo.this);
    // read the items from the persistent store
    items = persistentStorage.readItems();

    setupListView();
  }

  private void setupListView() {
    ListView lvItems = (ListView) findViewById(R.id.lvItems);

    // setup the items adapter
    itemsAdapter = new TodoItemAdapter(
      this,
      R.layout.listview_item_row,
      items
    );
    lvItems.setAdapter(itemsAdapter);

    // set up list view listeners
    // remove item on long click
    lvItems.setOnItemLongClickListener(
      new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(
          AdapterView<?> parent,
          View view,
          int position,
          long id
        ) {

          TodoItem item = items.get(position);
          if (persistentStorage.deleteItem(item)) {
            items.remove(position);
            itemsAdapter.notifyDataSetChanged();
          }
          return true;
        }
    });

    // edit item on click
    lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(
        AdapterView<?> parent,
        View view,
        int position,
        long id) {
        // create a new intent to edit the item
        Intent editItemIntent = new Intent(Todo.this, EditItem.class);

        // add the item's position and info to the intent
        TodoItem selectedItem = items.get(position);

        editItemIntent.putExtra("position", position);
        editItemIntent.putExtra("title", selectedItem.title);
        editItemIntent.putExtra("description", selectedItem.description);

        startActivityForResult(editItemIntent, EDIT_ITEM_REQUEST_CODE);
      }
    });
  }

  public void addTodoItem(View v) {
    EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
    // check that the new item is not empty
    if (etNewItem.getText().toString().length() == 0) return;

    TodoItem newItem = new TodoItem(etNewItem.getText().toString());
    long insertedId = persistentStorage.saveItem(newItem);
    if (insertedId != -1) {
      newItem.id = insertedId;
      itemsAdapter.add(newItem);
      etNewItem.setText("");
    }
  }

  public void todoItemChecked(int position, boolean checked) {
    TodoItem item = items.get(position);
    item.done = checked;
    if (persistentStorage.updateItem(item)) {
      // update the item's info
      items.set(position, item);
      itemsAdapter.notifyDataSetChanged();
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    // edit item activity result
    if (resultCode == RESULT_OK && requestCode == EDIT_ITEM_REQUEST_CODE) {
      // get the item's position and info from the intent extra data
      int position =  data.getExtras().getInt("position");
      String newTitle = data.getExtras().getString("title");
      String newDescription = data.getExtras().getString("description");

      TodoItem modifiedItem = items.get(position);
      modifiedItem.title = newTitle;
      modifiedItem.description = newDescription;

      if (persistentStorage.updateItem(modifiedItem)) {
        // update the item's info
        items.set(position, modifiedItem);
        itemsAdapter.notifyDataSetChanged();
      }
    }
  }
}
