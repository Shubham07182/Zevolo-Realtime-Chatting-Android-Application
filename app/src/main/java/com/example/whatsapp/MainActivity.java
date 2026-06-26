package com.example.whatsapp;

import static android.widget.Toast.makeText;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.whatsapp.Adapter.FragmentAdapter;
import com.example.whatsapp.Adapter.FragmentAdapter;
import com.example.whatsapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("TalkTonic");


        mAuth = FirebaseAuth.getInstance();
        binding.mainSearchBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchUserActivity.class);
            startActivity(intent);
        });



        binding.viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));



        binding.mainSearchBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchUserActivity.class);
            startActivity(intent);
        });


        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
         if (id == R.id.settings) {
            //Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show();
            Intent intent2=new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent2);
            return true;
        } else if (id == R.id.groupChat) {
            //Toast.makeText(this, "Group Chat Started", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(MainActivity.this,GroupChatActivity.class);
            startActivity(intent1);
            return true;
        } else if (id == R.id.logout) {
            mAuth.signOut();
            Intent intent = new Intent(MainActivity.this,SignInActivity.class);
            startActivity(intent);

            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }



}