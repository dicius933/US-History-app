package com.example.android.ushistory;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static com.example.android.ushistory.USHistDBConstants.ANS_ID;
import static com.example.android.ushistory.USHistDBConstants.ANS_QUES_CD;
import static com.example.android.ushistory.USHistDBConstants.ANS_QUES_ID;
import static com.example.android.ushistory.USHistDBConstants.ANS_TABLE;
import static com.example.android.ushistory.USHistDBConstants.ANS_TEXT;
import static com.example.android.ushistory.USHistDBConstants.CREATED_DT;
import static com.example.android.ushistory.USHistDBConstants.CURR_LEVEL;
import static com.example.android.ushistory.USHistDBConstants.CURR_PT_TOTAL;
import static com.example.android.ushistory.USHistDBConstants.LAST_ACCESS_DT;
import static com.example.android.ushistory.USHistDBConstants.QUES_ID;
import static com.example.android.ushistory.USHistDBConstants.QUES_LEVEL;
import static com.example.android.ushistory.USHistDBConstants.QUES_TABLE;
import static com.example.android.ushistory.USHistDBConstants.QUES_TEXT;
import static com.example.android.ushistory.USHistDBConstants.USERS_TABLE;
import static com.example.android.ushistory.USHistDBConstants.USER_ID;
import static com.example.android.ushistory.USHistDBConstants.USER_NUM_QUES;
import static com.example.android.ushistory.USHistDBConstants.USER_NUM_RNDS;
import static com.example.android.ushistory.USHistDBConstants.USER_PWD;

/**
 * Created by Jeffrey Smith on 10/1/2016.
 */

public class AppLog {

    private static final String LOG_APP_TAG = "[US HIST APP]";
    private static final String LOG_DB_TAG = "[US HIST DB]";

    public static final String DEBUG_TAG = "[DEBUG]";
    public static final String ERROR_TAG = "[ERROR]";
    public static final String INFO_TAG = "[INFO]";

    public static int dbLogInfo(String message) {
        return Log.i(LOG_DB_TAG, LOG_DB_TAG + " " + INFO_TAG + " " + message);
    }

    public static int dbLogDebug(String message) {
        return Log.d(LOG_DB_TAG, LOG_DB_TAG + " " + DEBUG_TAG + " " + message);
    }

    public static int dbLogError(String message) {
        return Log.i(LOG_DB_TAG, LOG_DB_TAG + " " + ERROR_TAG + " " + message);
    }

    public static int appLogInfo(String message){
        return Log.i(LOG_APP_TAG, LOG_APP_TAG + " " + INFO_TAG + " " + message);
    }

    public static int appLogDebug(String message){
        return Log.d(LOG_APP_TAG, LOG_APP_TAG + " " + DEBUG_TAG + " " + message);
    }

    public static int appLogError(String message){
        return Log.d(LOG_APP_TAG, LOG_APP_TAG + " " + ERROR_TAG + " " + message);
    }

    //Method is used to validate XML parsing and Database values
    //Expensive method... use only for debugging
    public static void dbValidation(SQLiteDatabase db) {
        Cursor resultsCursor;

        //Query USERS table and return results in AppLog
        resultsCursor = db.query(USERS_TABLE,
                new String[]{USER_ID, USER_PWD, CREATED_DT, LAST_ACCESS_DT, CURR_LEVEL,
                                CURR_PT_TOTAL, USER_NUM_RNDS, USER_NUM_QUES},
                null,
                null,
                null,
                null,
                USER_ID);

        resultsCursor.moveToFirst();
        AppLog.dbLogInfo("USER table row count: " + resultsCursor.getCount());

        if (!resultsCursor.isAfterLast()) {
            do {
                AppLog.dbLogDebug("USERS row data: "
                        + resultsCursor.getString(0) + ", "
                        + resultsCursor.getString(1) + ", "
                        + resultsCursor.getString(2) + ", "
                        + resultsCursor.getString(3) + ", "
                        + String.valueOf(resultsCursor.getInt(4)) + ", "
                        + String.valueOf(resultsCursor.getInt(5)) + ", "
                        + String.valueOf(resultsCursor.getInt(6)) + ", "
                        + String.valueOf(resultsCursor.getInt(7)));
            } while (resultsCursor.moveToNext());
        }

        //Query QUESTIONS table and return results in AppLog
        resultsCursor = db.query(QUES_TABLE,
                new String[]{QUES_ID, QUES_LEVEL, QUES_TEXT},
                null,
                null,
                null,
                null,
                QUES_ID);

        resultsCursor.moveToFirst();
        AppLog.dbLogInfo("QUESTIONS table row count: " + resultsCursor.getCount());

        if (!resultsCursor.isAfterLast()) {
            do {
                AppLog.dbLogDebug("QUESTIONS row data: "
                        + String.valueOf(resultsCursor.getInt(0)) + ", "
                        + String.valueOf(resultsCursor.getInt(1)) + ", "
                        + resultsCursor.getString(2));
            } while (resultsCursor.moveToNext());
        }

        //Query ANSWERS table and return results in AppLog
        resultsCursor = db.query(ANS_TABLE,
                new String[]{ANS_ID, ANS_QUES_ID, ANS_QUES_CD, ANS_TEXT},
                null,
                null,
                null,
                null,
                ANS_ID);

        resultsCursor.moveToFirst();
        AppLog.dbLogInfo("ANSWERS table row count: " + resultsCursor.getCount());

        if (!resultsCursor.isAfterLast()) {
            do {
                AppLog.dbLogDebug("ANSWERS row data: "
                        + String.valueOf(resultsCursor.getInt(0)) + ", "
                        + String.valueOf(resultsCursor.getInt(1)) + ", "
                        + String.valueOf(resultsCursor.getInt(2)) + ", "
                        + resultsCursor.getString(3));
            } while (resultsCursor.moveToNext());
        }

        if (!resultsCursor.isClosed()) {
            resultsCursor.close();
        }
    }

    /**
     * This method is used for release - easy to turn off logging which is required for deployment
     * Un-comment this method for release.
     *
     * @param message  the messages are ignored
     * @return  always 0
    */
//    public static int dbLogInfo(String message) {
//        return 0;
//    }
//
//    public static int dbLogDebug(String message) {
//        return 0;
//    }
//
//    public static int dbLogError(String message) {
//        return 0;
//    }
//
//    public static int appLogInfo(String message){
//        return 0;
//    }
//
//    public static int appLogDebug(String message){
//        return 0;
//    }
//
//    public static int appLogError(String message){
//        return 0;
//    }
//
//    public static void dbValidation(SQLiteDatabase db) {
//
//    }
}
