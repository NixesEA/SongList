package com.nixesea.songlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddSong extends AppCompatActivity {

    private EditText editText_author;
    private EditText editText_name;
    private EditText editText_text;

    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);

        editText_author = findViewById(R.id.author);
        editText_name = findViewById(R.id.name);
        editText_text = findViewById(R.id.text);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("song");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String author = editText_author.getText().toString();
        String name = editText_name.getText().toString();
        String text = editText_text.getText().toString();

        //TODO добавить проверку на перезапись

        if (author.equals("")) {
            Toast.makeText(getApplicationContext(), "Необходимо добавить автора", Toast.LENGTH_LONG).show();
            return false;
        }
        if (name.equals("")) {
            Toast.makeText(getApplicationContext(),"Необходимо добавить назвение песни", Toast.LENGTH_LONG).show();
            return false;
        }
        if (text.equals("")) {
            Toast.makeText(getApplicationContext(),"Необходимо добавить текст песни", Toast.LENGTH_LONG).show();
            return false;
        }

        myRef.child(author).child(name).setValue(text);

        Toast.makeText(getApplicationContext(),"Песня добавлена", Toast.LENGTH_LONG).show();
        return true;
    }
}
