package com.example.android.ushistory;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class QuizActivity extends Activity {
    private ArrayList<String> quesByLevel;
    private ArrayList<String> ansByQuesID;
    private TextView txtVwQues;
    private Button button_FirstA;
    private Button button_SecondA;
    private Button button_ThirdA;
    private Button button_FourthA;
    private SQLiteDatabase usHistDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_quiz);

        usHistDB = USHistApplication.getUSHistDB();

        quesByLevel = USHistDBUtility.getQuesByLevel(usHistDB, 1);

        setupViews();
    }

    public void setupViews() {
        int randomNum = new Random().nextInt(3);
        ansByQuesID = USHistDBUtility.getAnsByQuesID(usHistDB, randomNum + 1);

        txtVwQues = (TextView) findViewById(R.id.txtVwQues);
        button_FirstA = (Button) findViewById(R.id.button_FirstA);
        button_SecondA = (Button) findViewById(R.id.button_SecondA);
        button_ThirdA = (Button) findViewById(R.id.button_ThirdA);
        button_FourthA = (Button) findViewById(R.id.button_FourthA);

        txtVwQues.setText(quesByLevel.get(randomNum));

        button_FirstA.setText(ansByQuesID.get(0));
        button_SecondA.setText(ansByQuesID.get(1));
        button_ThirdA.setText(ansByQuesID.get(2));
        button_FourthA.setText(ansByQuesID.get(3));
    }
}
