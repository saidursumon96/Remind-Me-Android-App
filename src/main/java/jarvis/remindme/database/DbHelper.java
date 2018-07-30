package jarvis.remindme.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper { //*** A helper class to manage database creation and version management.
    public static final String DATABASE_NAME = "remind_me.db";
    public static final String TABLE_NAME = "remind_me_table";
    public static final String COL1 = "ID";
    public static final String COL2 = "ITEM1";

    public DbHelper(Context context){ //Context : to use to open or create the database.
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){ //*** onCreate is called for the first time when creation of tables are needed.

        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +"ITEM1 TEXT)";
        db.execSQL(createTable);  //*** Call database execSQL(createTable) to execute the query.
                                  //*** Require a space after CREATE TABLE (Space). If it's not put table won't be create.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  //*** onUpgrade method is called when
                                                                                //          database version is upgraded.
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME); //*** Call database execSQL(query) to execute the query.
        onCreate(db);
    }

    public boolean addData (String item1){ //*** For check data is inserted or not to database.
        SQLiteDatabase db = this.getWritableDatabase();  //*** Gets the data repository in write mode.
        ContentValues contentValues = new ContentValues(); //*** Create a new map of values, where column names are the keys.
        contentValues.put(COL2, item1);
        long result = db.insert(TABLE_NAME, null, contentValues); //*** Insert the new row, returning the primary key value of the new row.

        if (result == -1 ){
            return false;  //*** Return -1, if data not insert.
        }
        else {
            return true;  //*** Return 1 or insert data, if data insert is ok.
        }
    }

    public Cursor getListContents(){ //*** To get all item from database.
        SQLiteDatabase db = this.getWritableDatabase();  //*** Declare SQLite database object.
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null); //*** rawQuery method is used for SELECT statement.
        return data;
    }

    public Cursor getItemID(String item1){ //*** Return only the ID that matches the item1 passed in.
                                            //getItemID method will take the name & search in database & return the ID associated with name.
        SQLiteDatabase db = this.getWritableDatabase();  //*** Declare SQLite database object.
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + item1 + "'";
        Cursor data = db.rawQuery(query, null); //*** Cursor - interface provides random read-write access to the result
                                                //       set returned by a database query.
        return data;
    }

    public void delete(int id, String item1){ //*** For delete an item from database.
        SQLiteDatabase db = this.getWritableDatabase();  //*** Declare SQLite database object.
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + item1 + "'";
        db.execSQL(query); //*** Call database execSQL(query) to execute the query.
    }
    public void update(String newName, int id, String oldName){ //*** For update an item to database.
        SQLiteDatabase db = this.getWritableDatabase();  //*** Declare SQLite database object.
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
                " = '" + newName + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + oldName + "'";
        db.execSQL(query); //*** Call database execSQL(query) to execute the query.
    }
}