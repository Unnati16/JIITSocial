package com.example.shivangikanchan.navigation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Shivangi Kanchan on 16-10-2016.
 */


public class UserDbHelper extends SQLiteOpenHelper {
    public static final String Hub_id="Hubid";
    public static final String Hub_Name="HubName";

    public static final String CREATE_QUERY=
            "CREATE TABLE "+Usercontract.Newuserinfo.TABLE_NAME+"("+Usercontract.Newuserinfo.User_name+" TEXT, "+Usercontract.Newuserinfo.Enroll+" INTEGER PRIMARY KEY, "+Usercontract.Newuserinfo.Year+" TEXT, "+Usercontract.Newuserinfo.Branch+" TEXT, "+Usercontract.Newuserinfo.Email+" TEXT, "+Usercontract.Newuserinfo.Contact+" INTEGER, "+Usercontract.Newuserinfo.PASS+" TEXT, "+Usercontract.Newuserinfo.Relation+" TEXT);";
    public static final String New_Query=
            "CREATE TABLE "+Usercontract.Newuserinfo.HUB_Table+"(Hubid TEXT PRIMARY KEY,HubName TEXT, "+Usercontract.Newuserinfo.Enroll+" INTEGER);";
    public static final String CREATE_QUERY1=
            "CREATE TABLE "+Usercontract.Newuserinfo.TABLE_NAME1+"("+Usercontract.Newuserinfo.Enroll1+" INTEGER, "+Usercontract.Newuserinfo.PASS1+" TEXT);";
    public static final String New1=
            "INSERT INTO "+Usercontract.Newuserinfo.HUB_Table+" VALUES(1,'Knuth Programming Hub',0);";
    public static final String New2=
            "INSERT INTO "+Usercontract.Newuserinfo.HUB_Table+" VALUES(2,'Robotics Hub',0);";
    public static final String New3=
            "INSERT INTO "+Usercontract.Newuserinfo.HUB_Table+" VALUES(3,'GDG',0);";
    public static final String New4=
            "INSERT INTO "+Usercontract.Newuserinfo.HUB_Table+" VALUES(4,'IEEE',0);";
    public static final String New5=
            "INSERT INTO "+Usercontract.Newuserinfo.HUB_Table+" VALUES(5,'CICE',0);";
    public static final String New6=
            "INSERT INTO "+Usercontract.Newuserinfo.HUB_Table+" VALUES(6,'OSDC',0);";
    public static final String New7=
            "INSERT INTO "+Usercontract.Newuserinfo.HUB_Table+" VALUES(7,'Ribose Hub',0);";
    public static final String New8=
            "INSERT INTO "+Usercontract.Newuserinfo.HUB_Table+" VALUES(8,'Parola Literary Hub',0);";
    public static final String New9=
            "INSERT INTO "+Usercontract.Newuserinfo.HUB_Table+" VALUES(9,'Page Turner Society',0);";
    public static final String New10=
            "INSERT INTO "+Usercontract.Newuserinfo.HUB_Table+" VALUES(10,'JCED',0);";
    public static final String New11=
            "INSERT INTO "+Usercontract.Newuserinfo.HUB_Table+" VALUES(11,'Jaypee Economics Hub',0);";
    public static final String New12=
            "INSERT INTO "+Usercontract.Newuserinfo.HUB_Table+" VALUES(12,'Graphicas Animation Hub',0);";
    public static final String New13=
            "INSERT INTO "+Usercontract.Newuserinfo.HUB_Table+" VALUES(13,'Crescendo Hub',0);";
    public static final String New14=
            "INSERT INTO "+Usercontract.Newuserinfo.HUB_Table+" VALUES(14,'Jhankaar Hub',0);";
    public static final String New15=
            "INSERT INTO "+Usercontract.Newuserinfo.HUB_Table+" VALUES(15,'Thespian Hub',0);";
    public static final String New16=
            "INSERT INTO "+Usercontract.Newuserinfo.HUB_Table+" VALUES(16,'Radiance Hub',0);";
    public static final String New17=
            "INSERT INTO "+Usercontract.Newuserinfo.HUB_Table+" VALUES(17,'JIIT Film and Photography',0);";
    public static final String New18=
            "INSERT INTO "+Usercontract.Newuserinfo.HUB_Table+" VALUES(18,'Expressions Painting Hub',0);";
    public static final String New19=
            "INSERT INTO "+Usercontract.Newuserinfo.HUB_Table+" VALUES(19,'JYC',0);";
    public static final String New20=
            "INSERT INTO "+Usercontract.Newuserinfo.HUB_Table+" VALUES(20,'Sports',0);";

    public UserDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context,name,factory,version);

        // android.util.Log.e("DATABASE OPERATIONS","Database created...");
    }

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_QUERY);
        db.execSQL(New_Query);
        db.execSQL(New1);
        db.execSQL(New2);
        db.execSQL(New3);
        db.execSQL(New4);
        db.execSQL(New5);
        db.execSQL(New6);
        db.execSQL(New7);
        db.execSQL(New8);
        db.execSQL(New9);
        db.execSQL(New10);
        db.execSQL(New11);
        db.execSQL(New12);
        db.execSQL(New13);
        db.execSQL(New14);
        db.execSQL(New15);
        db.execSQL(New16);
        db.execSQL(New17);
        db.execSQL(New18);
        db.execSQL(New19);
        db.execSQL(New20);
        db.execSQL(CREATE_QUERY1);
        Log.e("hi","xdb");
    }
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+Usercontract.Newuserinfo.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+Usercontract.Newuserinfo.HUB_Table);
        db.execSQL("DROP TABLE IF EXISTS "+Usercontract.Newuserinfo.TABLE_NAME1);
        onCreate(db);
    }
    public boolean insertdata(String name, String e, String y, String b, String em, String c, String pass,String re){

        ContentValues contentValues=new ContentValues();
        contentValues.put(Usercontract.Newuserinfo.User_name,name);
        contentValues.put(Usercontract.Newuserinfo.Enroll,e);
        contentValues.put(Usercontract.Newuserinfo.Year,y);
        contentValues.put(Usercontract.Newuserinfo.Branch,b);
        contentValues.put(Usercontract.Newuserinfo.Email,em);
        contentValues.put(Usercontract.Newuserinfo.Contact,c);
        contentValues.put(Usercontract.Newuserinfo.PASS,pass);
        contentValues.put(Usercontract.Newuserinfo.Relation,re);
        //contentValues.put(Usercontract.Newuserinfo.Hub_id,hid);
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(Usercontract.Newuserinfo.TABLE_NAME, null, contentValues);
        Log.e("result",result+"");
        db.close();
        if(result==-1)
        {
            return false;
        }
        else{
            return true;
        }

    }
    public boolean insertdata1(int e,String pass){

        ContentValues contentValues=new ContentValues();

        contentValues.put(Usercontract.Newuserinfo.Enroll1,e);

        contentValues.put(Usercontract.Newuserinfo.PASS1,pass);

        //contentValues.put(Usercontract.Newuserinfo.Hub_id,hid);
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(Usercontract.Newuserinfo.TABLE_NAME1, null, contentValues);
        Log.e("result",result+"");
        db.close();
        if(result==-1)
        {
            return false;
        }
        else{
            return true;
        }

    }

   public Student readMyDb() {
        Student s=null;
        SQLiteDatabase db = this.getReadableDatabase();
        Log.e("ddd","in readmydb");
        Cursor c = db.rawQuery("SELECT "+Usercontract.Newuserinfo.Enroll1+" FROM "+Usercontract.Newuserinfo.TABLE_NAME1+";", null);
        Log.e("www","c returned");

        if(!c.isLast())
        {
            while (c.moveToNext())
            {
                s = new Student(Integer.parseInt(c.getString(0)));
            }
        }
        return s;
    }
}
