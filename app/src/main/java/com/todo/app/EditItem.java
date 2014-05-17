package com.todo.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class EditItem extends Activity {

  private int position;
  private String title;
  private EditText editTextField;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_item);

    position = getIntent().getIntExtra("position", 0);
    title = getIntent().getStringExtra("title");

    editTextField = (EditText) findViewById(R.id.editItemTextField);
    editTextField.setText(title);
    editTextField.requestFocus();
    editTextField.setSelection(title.length());

  }

  public void editTodoItem(View v) {
    editTextField = (EditText) findViewById(R.id.editItemTextField);
    // check that the new item is not empty
    if (editTextField.getText().toString().length() == 0) return;

    Intent data = new Intent();
    data.putExtra("title", editTextField.getText().toString());
    data.putExtra("position", position);
    setResult(RESULT_OK, data);
    finish();
  }
}
