package com.nixesea.songlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton floatingActionButton;
    private ListView listView;

    private DatabaseReference myRef;

    public static final String TAG = "TRY_GET_ARRAY";
    public static final String AUTHOR = "author";          // Верхний текст
    public static final String NAME = "name";              // ниже главного
    public static final String SONG_TEXT = "song_text";    // будущая картинка

    ArrayList<HashMap<String, Object>> songList;
    HashMap<String, Object> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);

        listView = findViewById(R.id.list);
        listView.setOnItemClickListener(itemClickListener);

        // создаем массив списков
        songList = new ArrayList<>();

        // Write a message to the database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.d(TAG, "Value is: ");
                songList.clear();

                Iterable<DataSnapshot> arrayList = dataSnapshot.child("song").getChildren();
                for (DataSnapshot arrL:arrayList) {
                    //print author
                    Log.d(TAG, "author = " + arrL.getKey());
                    String author = arrL.getKey();

                    Iterable<DataSnapshot> arrChildren = arrL.getChildren();
                    for (DataSnapshot arrC:arrChildren) {
                        Log.d(TAG, "name = " + arrC.getKey());
                        Log.d(TAG, "text = " + arrC.getValue());

                        hashMap = new HashMap<>();
                        hashMap.put(AUTHOR,author );                // Название
                        hashMap.put(NAME, arrC.getKey());           // Описание
                        hashMap.put(SONG_TEXT, arrC.getValue());    // Описание
                        songList.add(hashMap);
                    }
                }

                updateUI();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void updateUI(){

        SimpleAdapter adapter = new SimpleAdapter(this, songList,
                R.layout.list_item, new String[]{AUTHOR, NAME,},
                new int[]{R.id.list_author, R.id.list_name});

        listView.setAdapter(adapter);
    }

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            HashMap<String, Object> itemHashMap = (HashMap<String, Object>) parent.getItemAtPosition(position);
            String authorItem = itemHashMap.get(AUTHOR).toString();
            String nameItem = itemHashMap.get(NAME).toString();
            String songTextItem = itemHashMap.get(SONG_TEXT).toString();

            Intent intent = new Intent(getApplicationContext(),ShowSong.class);
            intent.putExtra(AUTHOR, authorItem);
            intent.putExtra(NAME, nameItem);
            intent.putExtra(SONG_TEXT, songTextItem);
            startActivity(intent);

        }
    };

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,AddSong.class);
        startActivity(intent);
    }
}
