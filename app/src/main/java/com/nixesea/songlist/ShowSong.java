package com.nixesea.songlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ShowSong extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_song);

        setTitle(getIntent().getStringExtra(MainActivity.AUTHOR) + ": " + getIntent().getStringExtra(MainActivity.NAME));

        textView = findViewById(R.id.show_song_text);
        textView.setText(getIntent().getStringExtra(MainActivity.SONG_TEXT));
    }
}
