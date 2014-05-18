package com.todo.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.todo.app.PersistentStore.PersistentStore;

import java.util.ArrayList;

public class Todo extends Activity {

  /**
   * List with all the todo items
   */
  private ArrayList<String> items;

  /**
   * Adapter for the todo list view
   */
  private ArrayAdapter<String> itemsAdapter;

  /**
   * Request code for the edit item intent
   */
  private final int EDIT_ITEM_REQUEST_CODE = 1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_todo);

    // read the items from the persistent store
    items = PersistentStore.readItems();

    setupListView();
  }

  public void addTodoItem(View v) {
    EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
    // check that the new item is not empty
    if (etNewItem.getText().toString().length() == 0) return;
    itemsAdapter.add(etNewItem.getText().toString());
    etNewItem.setText("");
    PersistentStore.saveItems(items);
  }

  private void setupListView() {
    ListView lvItems = (ListView) findViewById(R.id.lvItems);

    // setup the items adapter
    itemsAdapter = new ArrayAdapter<String>(
      this,
      android.R.layout.simple_list_item_1,
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
          items.remove(position);
          itemsAdapter.notifyDataSetChanged();
          PersistentStore.saveItems(items);
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
        long id)
      {
        // create a new intent to edit the item
        Intent editItemIntent = new Intent(Todo.this, EditItem.class);

        // add the item's position and description to the intent
        editItemIntent.putExtra("position", position);
        editItemIntent.putExtra("description", items.get(position));

        startActivityForResult(editItemIntent, EDIT_ITEM_REQUEST_CODE);
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    // edit item activity result
    if (resultCode == RESULT_OK && requestCode == EDIT_ITEM_REQUEST_CODE) {
      // get the item's position and description from the intent extra data
      int position =  data.getExtras().getInt("position");
      String newDescription = data.getExtras().getString("description");

      // update the item's description
      items.set(position, newDescription);
      itemsAdapter.notifyDataSetChanged();
      PersistentStore.saveItems(items);
    }
  }
}
