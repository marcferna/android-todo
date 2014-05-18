package com.todo.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class EditItem extends Activity {

  /**
   * Item's position that will need to be send back to the previous intent
   * in order to update the correct item
   */
  private int position;

  /**
   * Text field with the item's description
   */
  private EditText editTextField;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_item);

    // get the item's position and description from the intent extra data
    position = getIntent().getIntExtra("position", 0);
    String description = getIntent().getStringExtra("description");

    // update the edit text field and set focus
    editTextField = (EditText) findViewById(R.id.editItemTextField);
    editTextField.setText(description);
    editTextField.requestFocus();
    editTextField.setSelection(description.length());

  }

  public void saveEditTodoItem(View v) {
    // check that the new item description is not empty
    if (editTextField.getText().toString().length() == 0) return;

    // finish the current intent and add the modified item info as extra data
    Intent data = new Intent();
    data.putExtra("description", editTextField.getText().toString());
    data.putExtra("position", position);
    setResult(RESULT_OK, data);
    finish();
  }

  public void cancelEditTodoItem(View v) {
    // finish the current intent with a cancelled result
    setResult(RESULT_CANCELED);
    finish();
  }
}
