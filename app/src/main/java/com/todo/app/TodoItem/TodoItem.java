package com.todo.app.TodoItem;

import java.util.Date;

public class TodoItem {
  public long id;
  public String title;
  public String description;
  public boolean done = false;

  public TodoItem() {
    super();
  }

  public TodoItem(String title) {
    this.title = title;
  }

  public TodoItem(String title, String description) {
    this.title = title;
    this.description = description;
  }
}
