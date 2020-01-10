package projet.version.thebookworm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class LibraryDatabase extends SQLiteOpenHelper {

    private static final String TAG = "LibraryDatabase";

    public static class FeedEntry implements BaseColumns {

        //nom des tables
        private static final String TABLE_NAME = "libraries_table";
        private static final String TABLE_TITLE = "books_table";

        private static final String COL1 = "ID";
        private static final String COL2 = "name";
        private static final String COLU1 = "ID";
        private static final String COLU2 = "Title";
        private static final String COLU3 = "Author";
        private static final String COLU4 = "Library";


    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry.COL1 + " INTEGER PRIMARY KEY," +
                    FeedEntry.COL2 + " TEXT)";

    private static final String SQL_CREATE_BOOKS =
            "CREATE TABLE " + FeedEntry.TABLE_TITLE + " (" +
                    FeedEntry.COLU1 + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLU2 + " TEXT," +
                    FeedEntry.COLU3 + " TEXT, " +
                    FeedEntry.COLU4+ " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    private static final String SQL_DELETE_BOOKS =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_TITLE;


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "LibraryDatabase.db";

    public LibraryDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_BOOKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion1) {
        if (newVersion1 > oldVersion) {
            db.execSQL(SQL_CREATE_BOOKS);
        }

        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_BOOKS);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }



    public boolean addData(String name){

        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COL2, name );


        long newRowId = db.insert(FeedEntry.TABLE_NAME, null, values);

        if (newRowId == - 1){
            return false;
        }

        else {
            return true;
        }

    }


    public boolean addBook(String name, String auteur, String Library){

        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLU2, name );
        values.put(FeedEntry.COLU3, auteur );
        values.put(FeedEntry.COLU4, Library );

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(FeedEntry.TABLE_TITLE, null, values);

        if (newRowId == - 1){
            return false;
        }

        else {
            return true;
        }

    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + FeedEntry.TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;

    }

    public Cursor getBook() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + FeedEntry.TABLE_TITLE;
        Cursor data = db.rawQuery(query, null);
        return data;

    }

    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + FeedEntry.COL1 + " FROM " + FeedEntry.TABLE_NAME +
                " WHERE " + FeedEntry.COL2 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getBookID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + FeedEntry.COLU1 + " FROM " + FeedEntry.TABLE_TITLE +
                " WHERE " + FeedEntry.COLU2 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getAuthor(int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + FeedEntry.COLU3 + " FROM " + FeedEntry.TABLE_TITLE +
                " WHERE " + FeedEntry.COLU2 + " = '" + name + "'" + " AND "
                + FeedEntry.COLU1 + " = ' " + id + " ' ";
        Cursor data = db.rawQuery(query, null);
        return data;

    }

    public Cursor getBookLibrary(String library) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " SELECT " + FeedEntry.COLU2 + " FROM " + FeedEntry.TABLE_TITLE +
        " WHERE " + FeedEntry.COLU4 + " = '" + library + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateName(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + FeedEntry.TABLE_NAME + " SET " + FeedEntry.COL2 +
                " = '" + newName + "' WHERE " + FeedEntry.COL1 + " = '" + id + "'" +
                " AND " + FeedEntry.COL2 + " = '" + oldName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }

    public void deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + FeedEntry.TABLE_NAME + " WHERE "
                + FeedEntry.COL1 + " = '" + id + "'" +
                " AND " + FeedEntry.COL2 + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }

    public void deleteBook (int id, String name, String Auteur, String Library){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + FeedEntry.TABLE_TITLE + " WHERE "
                + FeedEntry.COLU1 + " = '" + id + "'" +
                " AND " + FeedEntry.COLU2 + " = '" + name + "'" +
                "AND" + FeedEntry.COLU3 + "= '" + Auteur + "'" +
                "AND" + FeedEntry.COLU4 + "= '" + Library + "'" ;
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }




}
