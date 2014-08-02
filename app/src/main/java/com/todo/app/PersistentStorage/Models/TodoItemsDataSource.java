package com.todo.app.PersistentStorage.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.todo.app.PersistentStorage.SqlLite.SqlHandler;
import com.todo.app.PersistentStorage.SqlLite.SqlHelper;
import com.todo.app.TodoItem.TodoItem;

import java.util.ArrayList;
import java.util.List;

public class TodoItemsDataSource {
  /**
   * SqlHandler instance to make the database requests
   */
  private SqlHandler dbHandler;

  /**
   * Columns that the TodoItem has
   */
  private String[] allColumns = { SqlHelper.COLUMN_ID,
    SqlHelper.COLUMN_TITLE, SqlHelper.COLUMN_DESCRIPTION,
    SqlHelper.COLUMN_DONE, SqlHelper.COLUMN_DUEDATE};


  public TodoItemsDataSource(Context context) {
    dbHandler = new SqlHandler(context);
  }

  /**
   * Creates an new item in the database
   * @param item TodoItem to create
   * @return id of the new TodoItem created
   */
  public long createItem(TodoItem item) {
    ContentValues values = new ContentValues();
    values.put(SqlHelper.COLUMN_TITLE, item.title);
    values.put(SqlHelper.COLUMN_DESCRIPTION, item.description);
    return dbHandler.insert(SqlHelper.DATABASE_TABLE, values);
  }

  /**
   * Updates a TodoItem on the database
   * @param item TodoItem to update
   * @return true if the update was successful, false otherwise
   */
  public boolean updateItem(TodoItem item) {
    ContentValues values = new ContentValues();
    values.put(SqlHelper.COLUMN_TITLE, item.title);
    values.put(SqlHelper.COLUMN_DESCRIPTION, item.description);
    values.put(SqlHelper.COLUMN_DONE, item.done);
    values.put(SqlHelper.COLUMN_DUEDATE, item.dueDate);
    int rowsAffected = dbHandler.update(
      SqlHelper.DATABASE_TABLE,
      values,
      SqlHelper.COLUMN_ID + " = " + item.id
    );
    return rowsAffected > 0;
  }

  /**
   * Deletes a TodoItem from the database
   * @param item TodoItem to delete
   * @return true if the deletion was successful, false otherwise
   */
  public boolean deleteTodoItem(TodoItem item) {
    int rowsAffected = dbHandler.delete(
      SqlHelper.DATABASE_TABLE,
      SqlHelper.COLUMN_ID + " = " + item.id
    );
    return rowsAffected > 0;
  }

  /**
   * Finds a todo item from the database with the id specified
   * @param todoId id of the item to fetch from the database
   * @return TodoItem fetched from the database or null if not found
   */
  public TodoItem getTodoItem(long todoId) {
    Cursor cursor = dbHandler.selectOne(SqlHelper.DATABASE_TABLE, allColumns, todoId);

    if (cursor != null) {
      cursor.moveToFirst();
      TodoItem todoItem = cursorToTodoItem(cursor);
      cursor.close();
      return todoItem;
    }
    return null;
  }

  /**
   * Gets all the items from the database
   * @return List of TodoItems
   */
  public List<TodoItem> getAllTodoItems() {
    List<TodoItem> items = new ArrayList<TodoItem>();

    Cursor cursor = dbHandler.selectAll(SqlHelper.DATABASE_TABLE, allColumns);
    if (cursor != null) {
      cursor.moveToFirst();
      while (!cursor.isAfterLast()) {
        TodoItem todoItem = cursorToTodoItem(cursor);
        items.add(todoItem);
        cursor.moveToNext();
      }
      cursor.close();
    }
    return items;
  }

  /**
   * Transforms a Cursor into a TodoItem
   * @param cursor to transform into TodoItem
   * @return TodoItem from the database
   */
  private TodoItem cursorToTodoItem(Cursor cursor) {
    TodoItem todoItem = new TodoItem();
    todoItem.id = cursor.getLong(0);
    todoItem.title = cursor.getString(1);
    todoItem.description = cursor.getString(2);
    todoItem.done = cursor.getInt(3) > 0;
    todoItem.dueDate = cursor.getString(4);
    return todoItem;
  }
}
