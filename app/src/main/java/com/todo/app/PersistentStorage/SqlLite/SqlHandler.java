package com.todo.app.PersistentStorage.SqlLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SqlHandler {

  /**
   * Name of the database to use
   */
  public static final String DATABASE_NAME = "TODO_DATABASE";

  /**
   * Version of the database
   */
  public static final int DATABASE_VERSION = 3;

  /**
   * Instance of the SQLIte database
   */
  private SQLiteDatabase sqlDatabase;

  /**
   * SQLiteDatabaseHelper
   */
  private SqlHelper dbHelper;

  public SqlHandler(Context context) {

    dbHelper = new SqlHelper(context, DATABASE_NAME, null,
      DATABASE_VERSION);
    sqlDatabase = dbHelper.getWritableDatabase();
  }

  /**
   * Query the given table, returning a {@link Cursor} over the result set.
   *
   * @param table The table name to compile the query against.
   * @param columns A list of which columns to return. Passing null will
   *            return all columns, which is discouraged to prevent reading
   *            data from storage that isn't going to be used.

   * @return A {@link Cursor} object, which is positioned before the first entry. Note that
   * {@link Cursor}s are not synchronized, see the documentation for more details.
   * @see Cursor
   */
  public Cursor selectAll(String table, String[] columns) {
    try {
      if (sqlDatabase.isOpen()) {
        sqlDatabase.close();
      }

      sqlDatabase = dbHelper.getWritableDatabase();
      return sqlDatabase.query(table, columns, null, null,
        SqlHelper.COLUMN_PRIORITY, null, null);

    } catch (Exception e) {

      System.out.println("DATABASE ERROR " + e);
    }
    return null;
  }

  /**
   * Query the given table for an specific id, returning a {@link Cursor} over the result set.
   *
   * @param table The table name to compile the query against.
   * @param columns A list of which columns to return. Passing null will
   *            return all columns, which is discouraged to prevent reading
   *            data from storage that isn't going to be used.
   * @param todoId The id of the item to fetch from the database

   * @return A {@link Cursor} object, which is positioned before the first entry. Note that
   * {@link Cursor}s are not synchronized, see the documentation for more details.
   * @see Cursor
   */
  public Cursor selectOne(String table, String[] columns, long todoId) {
    try {
      if (sqlDatabase.isOpen()) {
        sqlDatabase.close();
      }

      sqlDatabase = dbHelper.getWritableDatabase();
      return sqlDatabase.query(table, columns, SqlHelper.COLUMN_ID + " = " + todoId, null,
                                  null, null, null);

    } catch (Exception e) {

      System.out.println("DATABASE ERROR " + e);
    }
    return null;
  }

  /**
   * Convenience method for inserting a row into the database.
   *
   * @param table the table to insert the row into
   * @param values this map contains the initial column values for the
   *            row. The keys should be the column names and the values the
   *            column values
   * @return the row ID of the newly inserted row, or -1 if an error occurred
   */
  public long insert(String table, ContentValues values) {
    try {
      if (sqlDatabase.isOpen()) {
        sqlDatabase.close();
      }

      sqlDatabase = dbHelper.getWritableDatabase();
      return sqlDatabase.insert(table, null, values);

    } catch (Exception e) {

      System.out.println("DATABASE ERROR " + e);
    }
    return -1;
  }

  /**
   * Convenience method for updating rows in the database.
   *
   * @param table the table to update in
   * @param values a map from column names to new column values. null is a
   *            valid value that will be translated to NULL.
   * @param whereClause the optional WHERE clause to apply when updating.
   *            Passing null will update all rows.

   * @return the number of rows affected
   */
  public int update(String table, ContentValues values, String whereClause) {
    try {
      if (sqlDatabase.isOpen()) {
        sqlDatabase.close();
      }

      sqlDatabase = dbHelper.getWritableDatabase();
      return sqlDatabase.update(table, values, whereClause, null);

    } catch (Exception e) {

      System.out.println("DATABASE ERROR " + e);
    }
    return 0;
  }

  /**
   * Convenience method for deleting rows in the database.
   *
   * @param table the table to delete from
   * @param whereClause the optional WHERE clause to apply when deleting.
   *            Passing null will delete all rows.
   * @return the number of rows affected if a whereClause is passed in, 0
   *         otherwise. To remove all rows and get a count pass "1" as the
   *         whereClause.
   */
  public int delete(String table, String whereClause) {
    try {
      if (sqlDatabase.isOpen()) {
        sqlDatabase.close();
      }

      sqlDatabase = dbHelper.getWritableDatabase();
      return sqlDatabase.delete(table, whereClause, null);

    } catch (Exception e) {

      System.out.println("DATABASE ERROR " + e);
    }
    return 0;
  }
}
