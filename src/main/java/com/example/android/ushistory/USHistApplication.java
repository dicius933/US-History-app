package com.example.android.ushistory;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Jeffrey Smith on 10/3/2016.
 */

public class USHistApplication extends Application {

    private static SQLiteDatabase usHistDB;

    @Override
    public void onCreate() {
        super.onCreate();

        USHistDBUtility dbUtility = new USHistDBUtility(this);
        usHistDB = dbUtility.getWritableDatabase();

    }

    public static SQLiteDatabase getUSHistDB() {
        return usHistDB;
    }
}
