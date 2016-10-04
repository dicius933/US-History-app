package com.example.android.ushistory;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.content.ContextCompat;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Jeffrey Smith on 9/29/2016.
 */

/*DB Utility to Create, Open, Populate,
 *and Update the Database
*/
public class USHistDBUtility extends SQLiteOpenHelper {

    private final Context resContext;

    public USHistDBUtility(Context context) {
        super(context, USHistDBConstants.DB_NAME, null, USHistDBConstants.DB_VERSION);
        resContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase usHistDB) {

        if(!tableExists(usHistDB, USHistDBConstants.USERS_TABLE) &&
                !tableExists(usHistDB, USHistDBConstants.QUES_TABLE) &&
                !tableExists(usHistDB, USHistDBConstants.ANS_TABLE)) {

            //Create tables
            createDBTable(usHistDB, USHistDBConstants.CREATE_USER_TABLE);
            createDBTable(usHistDB, USHistDBConstants.CREATE_QUES_TABLE);
            createDBTable(usHistDB, USHistDBConstants.CREATE_ANS_TABLE);

            //Populate USERS Table
            Resources resources = resContext.getResources();
            XmlResourceParser xmlResourceParser = resources.getXml(R.xml.users);

            populateDBUsersTable(usHistDB, xmlResourceParser, USHistDBConstants.USERS_TABLE,
                    "id", "pwd", "level", "points", "rounds", "questions_old");

            //Populate QUESTIONS Table
            resources = resContext.getResources();
            xmlResourceParser = resources.getXml(R.xml.questions);

            populateDBQuesTable(usHistDB, xmlResourceParser, USHistDBConstants.QUES_TABLE,
                    "level", "text");

            //Populate ANSWERS Table
            resources = resContext.getResources();
            xmlResourceParser = resources.getXml(R.xml.answers);

            populateDBAnsTable(usHistDB, xmlResourceParser, USHistDBConstants.ANS_TABLE,
                    "code", "text");

            AppLog.dbValidation(usHistDB);
        }
    }

    public static boolean tableExists (SQLiteDatabase db, String tableName) {
        Cursor resultSet = db.rawQuery(USHistDBConstants.TABLE_EXISTS + tableName + "';", null);

        resultSet.moveToFirst();

        if (resultSet.getInt(0) == 0) {
            return false;
        }

        return true;
    }

    public static void createDBTable (SQLiteDatabase db, String createDBTableScript) {
        AppLog.dbLogDebug("Create " + createDBTableScript + " table: " + createDBTableScript);
        db.execSQL(createDBTableScript);
    }

    public void populateDBUsersTable(SQLiteDatabase db, XmlResourceParser xmlResourceParser,
                                        String tableName, String col1Name, String col2Name,
                                        String col3Name, String col4Name, String col5Name,
                                        String col6Name) {

        ContentValues contentValues = new ContentValues();
        AppLog.dbLogInfo("Starting population of " + tableName + " table...");

        try {
            int eventType = xmlResourceParser.getEventType();
            String createdDt = "datetime('now', 'localtime')";
            String lastAccessDt = "datetime('now', 'localtime')";

//            if(eventType != XmlPullParser.END_DOCUMENT) {
//                AppLog.dbLogDebug("eventType is NOT EQUAL to XmlPullParser.END_DOCUMENT");
//            } else {
//                AppLog.dbLogDebug("eventType IS EQUAL to XmlPullParser.END_DOCUMENT");
//            }
//
//            if(eventType == XmlPullParser.START_TAG) {
//                AppLog.dbLogDebug("eventType IS EQUAL to XmlPullParser.START_TAG");
//            } else {
//                AppLog.dbLogDebug("eventType is NOT EQUAL to XmlPullParser.START_TAG");
//            }
//
//            if(xmlResourceParser.getName().equals("record")) {
//                AppLog.dbLogDebug("xmlResourceParser getName IS EQUAL to record");
//            } else {
//                AppLog.dbLogDebug("xmlResourceParser getName is NOT EQUAL to record");
//            }

            while(eventType != XmlPullParser.END_DOCUMENT) {
                if((eventType == XmlPullParser.START_TAG)
                        && (xmlResourceParser.getName().equals("record"))) {

                    AppLog.dbLogInfo("Parsing USERS XML...");

                    String id = xmlResourceParser.getAttributeValue(null, col1Name);
                    String pwd = xmlResourceParser.getAttributeValue(null, col2Name);
                    String level = xmlResourceParser.getAttributeValue(null, col3Name);
                    String points = xmlResourceParser.getAttributeValue(null, col4Name);
                    String rounds = xmlResourceParser.getAttributeValue(null, col5Name);
                    String questions = xmlResourceParser.getAttributeValue(null, col6Name);

                    contentValues.put(USHistDBConstants.USER_ID, id);
                    contentValues.put(USHistDBConstants.USER_PWD, pwd);
                    contentValues.put(USHistDBConstants.CREATED_DT, createdDt);
                    contentValues.put(USHistDBConstants.LAST_ACCESS_DT, lastAccessDt);
                    contentValues.put(USHistDBConstants.CURR_LEVEL, level);
                    contentValues.put(USHistDBConstants.CURR_PT_TOTAL, points);
                    contentValues.put(USHistDBConstants.USER_NUM_RNDS, rounds);
                    contentValues.put(USHistDBConstants.USER_NUM_QUES, questions);

                    AppLog.dbLogDebug(contentValues.toString());

                    db.insert(tableName, null, contentValues);
                }

                eventType = xmlResourceParser.next();

            }
        } catch (XmlPullParserException xmlEx) {
            AppLog.dbLogError("xmlEx Error: " + xmlEx.getMessage());
        } catch (IOException ioEx) {
            AppLog.dbLogError("ioEx Error: " + ioEx.getMessage());
        } finally {
            xmlResourceParser.close();
            AppLog.dbLogInfo("USERS XML Parser closed.");
        }

        AppLog.dbLogInfo("Finished population of " + tableName + " table.");
    }

    public void populateDBQuesTable(SQLiteDatabase db, XmlResourceParser xmlResourceParser,
                                String tableName, String col1Name, String col2Name) {

        ContentValues contentValues = new ContentValues();
        AppLog.dbLogInfo("Starting population of " + tableName + " table...");

        try {
            int eventType = xmlResourceParser.getEventType();

//            if(eventType != XmlPullParser.END_DOCUMENT) {
//                AppLog.dbLogDebug("eventType is NOT EQUAL to XmlPullParser.END_DOCUMENT");
//            } else {
//                AppLog.dbLogDebug("eventType IS EQUAL to XmlPullParser.END_DOCUMENT");
//            }
//
//            if(eventType == XmlPullParser.START_TAG) {
//                AppLog.dbLogDebug("eventType IS EQUAL to XmlPullParser.START_TAG");
//            } else {
//                AppLog.dbLogDebug("eventType is NOT EQUAL to XmlPullParser.START_TAG");
//            }
//
//            if(xmlResourceParser.getName().equals("record")) {
//                AppLog.dbLogDebug("xmlResourceParser getName IS EQUAL to record");
//            } else {
//                AppLog.dbLogDebug("xmlResourceParser getName is NOT EQUAL to record");
//            }
            while(eventType != XmlPullParser.END_DOCUMENT) {
                if((eventType == XmlPullParser.START_TAG)
                        && (xmlResourceParser.getName().equals("record"))) {

                    AppLog.dbLogInfo("Parsing QUESTIONS XML...");

                    String level = xmlResourceParser.getAttributeValue(null, col1Name);
                    String text = xmlResourceParser.getAttributeValue(null, col2Name);

                    contentValues.put(USHistDBConstants.QUES_LEVEL, level);
                    contentValues.put(USHistDBConstants.QUES_TEXT, text);

                    AppLog.dbLogDebug(contentValues.toString());

                    db.insert(tableName, null, contentValues);
                }

                eventType = xmlResourceParser.next();
            }
        } catch (XmlPullParserException xmlEx) {
            AppLog.dbLogError("xmlEx Error: " + xmlEx.getMessage());
        } catch (IOException ioEx) {
            AppLog.dbLogError("ioEx Error: " + ioEx.getMessage());
        } finally {
            xmlResourceParser.close();
            AppLog.dbLogInfo("QUESTIONS XML Parser closed.");
        }

        AppLog.dbLogInfo("Finished population of " + tableName + " table.");
    }

    public void populateDBAnsTable(SQLiteDatabase db, XmlResourceParser xmlResourceParser,
                                   String tableName, String col1Name, String col2Name) {

        ContentValues contentValues = new ContentValues();
        AppLog.dbLogInfo("Starting population of " + tableName + " table...");

        try {
            int eventType = xmlResourceParser.getEventType();
            int foreignKyCounter = 1;
            int counter = 1;

            while(eventType != XmlPullParser.END_DOCUMENT) {
                if((eventType == XmlPullParser.START_TAG)
                        && (xmlResourceParser.getName().equals("record"))) {

                    AppLog.dbLogInfo("Parsing ANSWERS XML...");

                    String code = xmlResourceParser.getAttributeValue(null, col1Name);
                    String text = xmlResourceParser.getAttributeValue(null, col2Name);

                    contentValues.put(USHistDBConstants.ANS_QUES_ID, foreignKyCounter);
                    contentValues.put(USHistDBConstants.ANS_QUES_CD, code);
                    contentValues.put(USHistDBConstants.ANS_TEXT, text);

                    AppLog.dbLogDebug(contentValues.toString());

                    db.insert(tableName, null, contentValues);
                    counter++;

                    if(counter > 4) {
                        foreignKyCounter++;
                        counter = 1;
                    }
                }

                eventType = xmlResourceParser.next();
            }
        } catch (XmlPullParserException xmlEx) {
            AppLog.dbLogError("xmlEx Error: " + xmlEx.getMessage());
        } catch (IOException ioEx) {
            AppLog.dbLogError("ioEx Error: " + ioEx.getMessage());
        } finally {
            xmlResourceParser.close();
            AppLog.dbLogInfo("ANSWERS XML Parser closed.");
        }

        AppLog.dbLogInfo("Finished population of " + tableName + " table.");
    }

    public static ArrayList<String> getQuesByLevel(SQLiteDatabase db, int level) {
        ArrayList<String> questionsByLevel = new ArrayList<>();

        Cursor resultSet = db.rawQuery(USHistDBConstants.GET_QUES_BY_LEVEL + level, null);
        resultSet.moveToFirst();
        if(!resultSet.isAfterLast()) {
            do {
                String text = resultSet.getString(0);
                questionsByLevel.add(text);
            } while(resultSet.moveToNext());
        }

        AppLog.appLogInfo("Questions by level array: " + questionsByLevel.toString());

        resultSet.close();
        return questionsByLevel;
    }

    public static ArrayList<String> getAnsByQuesID (SQLiteDatabase db, int quesID) {
        ArrayList<String> ansByQuesID = new ArrayList<>();

        Cursor resultSet = db.rawQuery(USHistDBConstants.GET_ANS_BY_QUES_ID + quesID, null);
        resultSet.moveToFirst();
        if(!resultSet.isAfterLast()) {
            do {
                String ansText = resultSet.getString(0);
                ansByQuesID.add(ansText);
            } while(resultSet.moveToNext());
        }

        AppLog.appLogInfo("Answers by ques_id array: " + ansByQuesID.toString());

        resultSet.close();
        return ansByQuesID;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + USHistDBConstants.USERS_TABLE);
        db.execSQL("drop table if exists " + USHistDBConstants.QUES_TABLE);
        db.execSQL("drop table if exists " + USHistDBConstants.ANS_TABLE);
        onCreate(db);
    }
}