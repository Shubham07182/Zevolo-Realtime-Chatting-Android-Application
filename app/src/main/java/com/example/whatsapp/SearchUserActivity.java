package com.example.whatsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.Adapter.SearchUserRecyclerAdapter;
import com.example.whatsapp.Models.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchUserActivity extends AppCompatActivity {

    EditText searchInput;
    ImageButton searchButton;
    ImageButton backButton;
    RecyclerView recyclerView;
    SearchUserRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        searchInput = findViewById(R.id.search_user_name_input);
        searchButton = findViewById(R.id.search_button);
        backButton = findViewById(R.id.backArrow);
        recyclerView = findViewById(R.id.search_user_recycler_view);

        searchInput.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }

        adapter = new SearchUserRecyclerAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(SearchUserActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        searchButton.setOnClickListener(v -> {
            String searchTerm = searchInput.getText().toString().trim();
            if (searchTerm.isEmpty() || searchTerm.length() < 3) {
                searchInput.setError("Enter at least 3 characters");
            } else {
                setupSearchRecyclerView(searchTerm);
            }
        });
    }

    void setupSearchRecyclerView(String searchTerm) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");

        usersRef.orderByChild("userName")
                .startAt(searchTerm)
                .endAt(searchTerm + "\uf8ff")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<Users> userList = new ArrayList<>();
                        for (DataSnapshot userSnap : snapshot.getChildren()) {
                            Users user = userSnap.getValue(Users.class);
                            if (user != null) {
                                user.setUserID(userSnap.getKey());  // 🟢 Set Firebase UID
                                userList.add(user);
                            }

                        }

                        adapter.updateData(userList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(SearchUserActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
