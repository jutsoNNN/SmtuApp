package com.example.smtuapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smtuapp.R;

public class AddNoteActivity extends AppCompatActivity {

    private EditText etNoteTitle, etNoteText;
    private Button btnCancel, btnSave;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        etNoteTitle = findViewById(R.id.etNoteTitle);
        etNoteText = findViewById(R.id.etNoteText);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());

        btnCancel.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> {
            String title = etNoteTitle.getText().toString().trim();
            String text = etNoteText.getText().toString().trim();

            if (title.isEmpty() && text.isEmpty()) {
                Toast.makeText(this, "Заметка пуста", Toast.LENGTH_SHORT).show();
                return;
            }

            // Пример возврата данных (можно адаптировать)
            Intent resultIntent = new Intent();
            resultIntent.putExtra("note", title + ": " + text);
            setResult(RESULT_OK, resultIntent);
            Toast.makeText(this, "Заметка успешно сохранена", Toast.LENGTH_SHORT).show();
            finish();
        });

    }
}
