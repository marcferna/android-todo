package com.todo.app.PersistentStorage;

import android.content.Context;

import com.todo.app.PersistentStorage.Models.TodoItemsDataSource;
import com.todo.app.TodoItem.TodoItem;

import java.util.ArrayList;

/**
 * Class that handles saving and reading the items for the todo list
 */
public class PersistentStorage {


  private TodoItemsDataSource dataSource;

  public PersistentStorage(Context context){
    dataSource = new TodoItemsDataSource(context);
  }

  /**
   * Reads the items from the persistent storage
   * @return a list of items
   */
  public ArrayList<TodoItem> readItems() {
    return (ArrayList)dataSource.getAllTodoItems();
  }

  /**
   * Reads a single item specified from its id from the persistent storage
   * @return a TodoItem with the id specified
   */
  public TodoItem getItem(long itemId) {
    return dataSource.getTodoItem(itemId);
  }

  /**
   * Saves the new item into the persistent storage
   *
   * @param item TodoItem to be stored
   * @return the id assigned to the new item
   */
  public long saveItem(TodoItem item) {
    return dataSource.createItem(item);
  }

  /**
   * Updates the item record from the persistent storage
   * @param item TodoItem to update
   * @return true if the update was successful, false otherwise
   */
  public boolean updateItem(TodoItem item) {
    return dataSource.updateItem(item);
  }

  /**
   * Deletes an item from the persistent storage
   * @param item The TodoItem to be deleted
   * @return true if the item was deleted, false otherwise
   */
  public boolean deleteItem(TodoItem item) {
    return dataSource.deleteTodoItem(item);
  }
}
