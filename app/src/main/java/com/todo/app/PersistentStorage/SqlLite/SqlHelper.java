package com.todo.app.PersistentStorage.SqlLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by marc on 5/17/14.
 */
public class SqlHelper extends SQLiteOpenHelper {

  /**
   * Database table name
   */
  public static final String DATABASE_TABLE = "TODO_ITEMS";

  /**
   * Table column names
   */
  public static final String COLUMN_ID = "id";
  public static final String COLUMN_TITLE = "title";
  public static final String COLUMN_DESCRIPTION = "description";
  public static final String COLUMN_DONE = "done";

  /**
   * SQL script to create the database
   */
  private static final String SCRIPT_CREATE_DATABASE = "create table "
    + DATABASE_TABLE + " (" + COLUMN_ID
    + " integer primary key autoincrement, " + COLUMN_TITLE
    + " text, " + COLUMN_DESCRIPTION
    + " text, " + COLUMN_DONE
    + " boolean)";


  public SqlHelper(Context context, String database,
                   SQLiteDatabase.CursorFactory factory, int version) {
    super(context, database, factory, version);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(SCRIPT_CREATE_DATABASE);

  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
    onCreate(db);
  }
}
