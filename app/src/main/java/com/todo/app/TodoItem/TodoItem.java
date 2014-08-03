package com.todo.app.TodoItem;

import java.util.Date;

public class TodoItem {
  public long id;
  public String title;
  public String description;
  public boolean done = false;
  public String dueDate;
  public int priority;

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

  public TodoItem(String title, String description, String dueDate) {
    this.title = title;
    this.description = description;
    this.dueDate = dueDate;
  }
}
