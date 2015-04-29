package huji.ac.il.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Zahi on 24/04/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "todo_db";
    public static final String TABLE_NAME = "todo";
    public static final String COL_TITLE = "title";
    public static final String COL_TITLE_TYPE = "title";
    public static final String COL_DUE_DATE = "dueDate";
    public static final String COL_DUE_DATE_TYPE = "dueDate";

    public static final String CRT_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME + "(  _id INTEGER PRIMARY KEY, "
            + COL_TITLE + " " + COL_TITLE_TYPE + " , " + COL_DUE_DATE + " " + COL_DUE_DATE_TYPE + " );";
    public static final String DROP_TABLE= "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CRT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }


      public boolean insertTask  (String title, long dueDate)
     {
     SQLiteDatabase db = getWritableDatabase();
     ContentValues contentValues = new ContentValues();

     contentValues.put(COL_TITLE, title);
     contentValues.put(COL_DUE_DATE, dueDate);

     db.insert(TABLE_NAME, null, contentValues);
     return true;
     }

    public Cursor getData(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery( "select * from " + TABLE_NAME, null );
    }

     public Cursor getData(int id){
     SQLiteDatabase db = this.getReadableDatabase();
     return  db.rawQuery( "select * from " + TABLE_NAME + " where _id=" + id + "", null );
     }

     public int numberOfRows(){
     SQLiteDatabase db = this.getReadableDatabase();
     return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
     }

     public int deleteTask (long id)
     {
         SQLiteDatabase db = this.getWritableDatabase();
         return db.delete(TABLE_NAME, "_id = ? ", new String[] { Long.toString(id) });
     }

     public ArrayList getAllTasks() {
         ArrayList<Task> ret = new ArrayList<>();
         Cursor itr =  getData();
         itr.moveToFirst();
         while(!itr.isAfterLast()){
             Task toAdd = new Task(itr.getString(itr.getColumnIndex(COL_TITLE)), itr.getLong(itr.getColumnIndex(COL_DUE_DATE)));
             ret.add(toAdd);
            itr.moveToNext();
         }
         return ret;
     }
}