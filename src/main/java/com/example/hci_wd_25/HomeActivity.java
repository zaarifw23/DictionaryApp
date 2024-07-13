package com.example.hci_wd_25;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button add_button,logoutButton;

    TextView textView;
    DatabaseHelper myDB;
    ArrayList<String> word, definition;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_homepage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.Search_def);
        logoutButton = findViewById(R.id.btnLogout);

        textView = findViewById(R.id.textView);
        // Retrieve email from intent
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        if (email != null) {
            // Split the email to get the user's name before '@'
            String user = email.split("@")[0];
            textView.setText("Hi, " + user+" !!");
        }

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish(); // Close the HomeActivity
            }
        });

        myDB = new DatabaseHelper(HomeActivity.this);
        word = new ArrayList<>();
        definition = new ArrayList<>();

        storeDataInArray();

        customAdapter = new CustomAdapter(HomeActivity.this, word, definition);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));

    }

    @Override
    protected void onResume() {
        super.onResume();
        word.clear();
        definition.clear();
        storeDataInArray();
        customAdapter.notifyDataSetChanged();
    }

    void storeDataInArray() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Previous Data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                word.add(cursor.getString(1));
                definition.add(cursor.getString(2));
            }
        }
    }
}