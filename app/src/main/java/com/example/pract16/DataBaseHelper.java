package com.example.pract16;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static Context context;
    private static final String DATABASE_NAME = "book_db";
    private static final int SCHEMA = 1;
    private final String TABLE_NAME = "books";
    public static final String COLUMN_ID = "id_book";
    public static final String COLUMN_NAME = "book_name";
    public static final String COLUMN_AUTHOR = "book_author";

    public DataBaseHelper(@Nullable Context context){
        super(context, DATABASE_NAME, null, SCHEMA);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (\n" +
                "    id_book INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    book_name TEXT,\n" +
                "    book_author TEXT\n" +
                ");";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean existsBookById(long bookId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(bookId)};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        return cursor.moveToFirst();
    }

    public long addBook(String bookName, String bookAuthor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, bookName);
        values.put(COLUMN_AUTHOR, bookAuthor);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }

    public Cursor getAllBook() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null,null, null, null, null);
    }

    public int deleteBookById(long bookId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(bookId)});
        db.close();
        return result;
    }

    public Book getBookById(long bookId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_NAME, COLUMN_AUTHOR};
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(bookId)};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            @SuppressLint("Range") String author = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR));
            cursor.close();
            return new Book((int) id, name, author);
        } else {
            cursor.close();
            return null;
        }
    }

    public void updateBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, book.getBook_Name());
        values.put(COLUMN_AUTHOR, book.getBook_Author());
        String whereClause = COLUMN_ID + " = ?";
        String[] whereArgs = {String.valueOf(book.getID_Book())};
        db.update(TABLE_NAME, values, whereClause, whereArgs);
        db.close();
    }
}
