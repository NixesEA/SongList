package com.nixesea.songlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ShowSong extends AppCompatActivity {

    private TextView textView;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_song);

        relativeLayout = findViewById(R.id.layout_show_song);
        setTitle(getIntent().getStringExtra(MainActivity.AUTHOR) + ": " + getIntent().getStringExtra(MainActivity.NAME));

        textView = findViewById(R.id.show_song_text);
        textView.setText(getIntent().getStringExtra(MainActivity.SONG_TEXT));

    }
}
