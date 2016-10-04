package com.example.android.ushistory;

/**
 * Created by Jeffrey Smith on 10/2/2016.
 */

public class USHistDBConstants {
    public static final String DB_NAME = "USHistoryQuiz.SQLite";
    public static final int DB_VERSION = 1;

    public static final String USERS_TABLE = "USERS";
    public static final String USER_ID = "user_id";
    public static final String USER_PWD = "user_pwd";
    public static final String CREATED_DT = "created_dt";
    public static final String LAST_ACCESS_DT = "last_access_dt";
    public static final String CURR_LEVEL = "curr_level";
    public static final String CURR_PT_TOTAL = "curr_pt_total";
    public static final String USER_NUM_RNDS = "user_num_rnds";
    public static final String USER_NUM_QUES = "user_num_ques";

    public static final String QUES_TABLE = "QUESTIONS";
    public static final String QUES_ID = "ques_id";
    public static final String QUES_LEVEL = "ques_level";
    public static final String QUES_TEXT = "ques_text";

    public static final String ANS_TABLE = "ANSWERS";
    public static final String ANS_ID = "answer_id";
    public static final String ANS_QUES_ID = "ques_id";
    public static final String ANS_QUES_CD = "answer_ques_cd";
    public static final String ANS_TEXT = "answer_text";

    public static final String CREATE_USER_TABLE = "create table if not exists " + USERS_TABLE
            + " (" + USER_ID + " varchar(15) primary key not null,"
            + USER_PWD + " varchar(15),"
            + CREATED_DT + " text,"
            + LAST_ACCESS_DT + " text, "
            + CURR_LEVEL + " integer,"
            + CURR_PT_TOTAL + " integer,"
            + USER_NUM_RNDS + " integer,"
            + USER_NUM_QUES + " integer" + ");";

    public static final String CREATE_QUES_TABLE = "create table if not exists " + QUES_TABLE
            + " (" + QUES_ID + " integer primary key autoincrement not null,"
            + QUES_LEVEL + " integer,"
            + QUES_TEXT + " text" + ");";

    public static final String CREATE_ANS_TABLE = "create table if not exists " + ANS_TABLE
            + " (" + ANS_ID + " integer primary key autoincrement not null,"
            + ANS_QUES_ID + " integer,"
            + ANS_QUES_CD + " integer,"
            + ANS_TEXT + " text, "
            + "foreign key(" + ANS_QUES_ID + ") references "
            + QUES_TABLE + "(" + QUES_ID + ")" + ");";

    public static final String GET_ALL_QUESTIONS = "select * from " + QUES_TABLE + ";";

    public static final String GET_QUES_BY_LEVEL = "select " + QUES_TEXT
            + " from " + QUES_TABLE
            + " where " + QUES_LEVEL + " = ";

    public static final String GET_ANS_BY_QUES_ID = "select " + ANS_TEXT
            + " from " + ANS_TABLE
            + " where " + ANS_QUES_ID + " = ";

    public static final String TABLE_EXISTS = "select count(*) from sqlite_master "
            + "where type = 'table' and name = '";
}
