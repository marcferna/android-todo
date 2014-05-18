package com.todo.app.PersistentStore;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class that handles saving and reading the items for the todo list
 */
public class PersistentStore {

  /**
   * Path where where the todo items file will be saved
   */
  private static final String path = "/data/data/com.todo.app/files";

  /**
   * File name to save the todo items
   */
  private static final String fileName = "todo.txt";

  /**
   * Reads the items from the persistent store
   * @return a list of items
   */
  public static ArrayList<String> readItems() {
    File directory = new File(path);
    File todoFile = new File(directory, fileName);
    ArrayList<String> items = new ArrayList<String>();
    try {
      items = new ArrayList<String>(FileUtils.readLines(todoFile));

    } catch (IOException e) {
      e.printStackTrace();
    }
    return items;
  }

  /**
   * Saves the items into the persistent store
   * @param items List of items that you want to save
   */
  public static void saveItems(ArrayList<String> items) {
    File directory = new File(path);
    File todoFile = new File(directory, fileName);
    try {
      FileUtils.writeLines(todoFile, items);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
