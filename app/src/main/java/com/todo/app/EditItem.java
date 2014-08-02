package com.todo.app;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.todo.app.PersistentStorage.PersistentStorage;
import com.todo.app.TodoItem.TodoItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class EditItem extends Activity implements DatePickerDialog.OnDateSetListener {

  /**
   * Item's position that will need to be send back to the previous intent
   * in order to update the correct item
   */
  private int position;

  /**
   * Text field with the item's title
   */
  private EditText titleEditText;
  /**
   * Text field with the item's description
   */
  private EditText descriptionEditText;
  /**
   * Text field with the item's due date
   */
  private EditText dueDateText;

  /**
   * Calendar used to extract the year, month and day from the dates
   */
  final private Calendar calendar = Calendar.getInstance();

  /**
   * Date format used for the todo item due date
   */
  final private String dateFormat = "MM/dd/yy";

  /**
   * Date formatter to convert dates into strings and vice-versa
   */
  final private SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat, Locale.US);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_item);

    // get the item's position and id from the intent extra data
    position = getIntent().getIntExtra("position", 0);
    long id = getIntent().getLongExtra("id", -1);

    // load the todo item info from the database
    TodoItem todoItem;
    if (id != -1) {
      PersistentStorage persistentStorage = new PersistentStorage(getApplicationContext());
      todoItem = persistentStorage.getItem(id);
    } else {
      todoItem = new TodoItem();
    }

    // update the edit text fields and set focus on the title
    titleEditText = (EditText) findViewById(R.id.titleEditText);
    titleEditText.setText(todoItem.title);
    titleEditText.requestFocus();
    titleEditText.setSelection(todoItem.title.length());

    descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
    descriptionEditText.setText(todoItem.description);

    dueDateText = (EditText) findViewById(R.id.dueDateText);
    dueDateText.setText(todoItem.dueDate);

    // set an onFocus listener to the due date field to display a DatePicker
    dueDateText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus) {
          int year, month, day;
          String dueDateString = dueDateText.getText().toString();
          if (dueDateString.length() > 0) {
            try {
              Date date = dateFormatter.parse(dueDateString);
              calendar.setTime(date);
            } catch (Exception e) {}
          }
          year = calendar.get(Calendar.YEAR);
          month = calendar.get(Calendar.MONTH);
          day = calendar.get(Calendar.DAY_OF_MONTH);

          DatePickerDialog datePicker = new DatePickerDialog(EditItem.this, EditItem.this, year, month, day);
          datePicker.show();
        }
      }
    });
  }

  public void onDateSet(DatePicker view, int year, int month, int day) {
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH, month);
    calendar.set(Calendar.DAY_OF_MONTH, day);
    dueDateText.setText(dateFormatter.format(calendar.getTime()));
    dueDateText.clearFocus();
  }

  public void saveEditTodoItem(View v) {
    // check that the new item title is not empty
    if (titleEditText.getText().toString().length() == 0) return;

    // finish the current intent and add the modified item info as extra data
    Intent data = new Intent();
    data.putExtra("title", titleEditText.getText().toString());
    data.putExtra("description", descriptionEditText.getText().toString());
    data.putExtra("dueDate", dueDateText.getText().toString());
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
