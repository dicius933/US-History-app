package com.example.android.ushistory;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    private static Button button_play;
    private static Button button_settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        onClickButtonListener();
    }

    /**called when the user clicks Play button */
    public void onClickButtonListener() {
        //Do something in response to button
        button_play = (Button) findViewById(R.id.button);
        button_play.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.example.android.ushistory.QuizActivity");
                        startActivity(intent);
                    }
                }
        );
        button_settings = (Button) findViewById(R.id.button2);
        button_settings.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.example.android.ushistory.SettingsActivity");
                        startActivity(intent);
                    }
                }
        );
    }
}
