package com.example.pract16;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecActivity extends AppCompatActivity {

    private EditText editId,editTextName, editTextAuthor;
    private Button getButton,addButton, delButton, updButton;
    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);

        editId = findViewById(R.id.id);
        editTextName = findViewById(R.id.name);
        editTextAuthor = findViewById(R.id.author);
        addButton = findViewById(R.id.add_element);
        delButton = findViewById(R.id.del_element);
        getButton = findViewById(R.id.get_element);
        updButton = findViewById(R.id.upd_element);

        dbHelper = new DataBaseHelper(this);
            addButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    addBookToDatabase();
                }
            });
            delButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delBookFromDatabase();
                }
            });
            getButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getBookFromDatabase();
                }
            });
            updButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateBookInDatabase();
                }
            });
    }

    private void addBookToDatabase() {
        String bookName = editTextName.getText().toString().trim();
        String bookAuthor = editTextAuthor.getText().toString().trim();

        if(bookName.isEmpty() || bookAuthor.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        long result = dbHelper.addBook(bookName, bookAuthor);

        if(result > 0) {
            Toast.makeText(this, "Книга добавлена", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
        }
    }

    private void delBookFromDatabase() {
        String bookId = editId.getText().toString().trim();
        int id = Integer.parseInt(bookId);
        int result = dbHelper.deleteBookById(id);
        if(result >= 1){
            Toast.makeText(this, "Удалено "+result+" строк", Toast.LENGTH_SHORT).show();
        }
    }

    private void getBookFromDatabase() {
        String bookId = editId.getText().toString().trim();
        int id = Integer.parseInt(bookId);
        Book book = dbHelper.getBookById(id);
        editTextName.setText(book.getBook_Name());
        editTextAuthor.setText(book.getBook_Author());
    }

    private void updateBookInDatabase() {
        Book book = new Book(Integer.parseInt(editId.getText().toString().trim()),editTextName.getText().toString().trim(),editTextAuthor.getText().toString().trim());
        dbHelper.updateBook(book);
    }
}