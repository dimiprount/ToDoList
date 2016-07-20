package com.dimiprount.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbDatabase {
	
	// Set up the columns of the database
	public static final String KEY_ROWID = "_id";
	public static final String KEY_NOTES = "notes";	
	public static final String KEY_ROWIDPASS = "passid";
	public static final String KEY_PASS = "pass";

	// Set up the database
	public static final String DATABASE_NAME = "NotesDatabase"; // The name of the database
	public static final String DATABASE_TABLE = "notesTable"; // The table for the notes of the database
	public static final String DATABASE_TABLE_PASSWORD = "passTable";// The table for the password of the database
	public static final int DATABASE_VERSION = 1;
	
	private final Context myContext;
	private DbHelper myHelper;
	private SQLiteDatabase myDatabase;
		
	// Set up a subclass to help set up the database
	public static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			// TODO Auto-generated constructor stub
			super(context, DATABASE_NAME, null, 1);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NOTES + " TEXT NOT NULL);");
			db.execSQL("CREATE TABLE " + DATABASE_TABLE_PASSWORD + " (" + KEY_ROWIDPASS
					+ " INTEGER PRIMARY KEY, " + KEY_PASS + " TEXT NOT NULL);");
			/*db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NOTES + " TEXT NOT NULL)" + " (" + KEY_ROWIDPASS
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_PASS + " TEXT NOT NULL)");*/
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			// If the table exists, drop it and than call onCreate method
			db.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE + DATABASE_TABLE_PASSWORD);
			onCreate(db);

		}
	}
	public DbDatabase(Context c) {
		// TODO Auto-generated constructor stub
		myContext = c;
		myHelper = new DbHelper(c);	// Instantiate myHelper variable (create the object)
	}

	// Open the database
	public DbDatabase open() throws SQLException {
		// TODO Auto-generated method stub
		myHelper = new DbHelper(myContext); // Pass in the context
											// Because the constructor of the
											// class takes in a context we are
											// going to give it the context we
											// set up in the class above
		myDatabase = myHelper.getWritableDatabase(); // Open the database
														// through ourHelper,
														// now it is writable
		return this;

	}

	// Write to the database
	public long createEntry(String notes) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_NOTES, notes);
		return myDatabase.insert(DATABASE_TABLE, null, cv);
	}

	// Close the database
	public void close() {
		// TODO Auto-generated method stub
		myHelper.close();
	}

	public ArrayList<Notepad> getData() {
		ArrayList<Notepad> notepad = new ArrayList<Notepad>();
		// Select All Query
		String selectQuery = "SELECT * FROM notesTable";

		SQLiteDatabase db = myHelper.getWritableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				Notepad mynotepad = new Notepad();
				mynotepad.setID(Integer.parseInt(c.getString(0)));
				mynotepad.setNote(c.getString(1));

				// Adding data to list
				notepad.add(mynotepad);
			} while (c.moveToNext());
		}
		// close inserting data from database
		db.close();
		// return contact list
		return notepad;

	}

	public void delete(Notepad selectedItem) {
		SQLiteDatabase db = myHelper.getWritableDatabase();
		db.delete(DATABASE_TABLE, KEY_ROWID + " = ? ", new String[] { String.valueOf(selectedItem.getID()) });
		db.close();
	}
	
	public void updateEntry(String mnotes, String newnotes) {
        // TODO Auto-generated method stub
        SQLiteDatabase dbupd = myHelper.getWritableDatabase();
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(KEY_NOTES, newnotes);
		// FETCHING STARTS HERE - the following code is fetching all _id and notes from table
		String selectQuery = "SELECT _id, notes from " + DATABASE_TABLE;
        Cursor cursor = dbupd.rawQuery(selectQuery, null);
        int count = cursor.getCount();
		//FETCHING ENDS HERE - count will contain number of notes present in database
		String note; // Store temporary values
        String[] rowId = new String[count];	// Create an array to store all ids to refer
        int counter = 0;	// A counter to keep track of the ids in do while loop
        if(cursor.moveToFirst()){
            do{
                note = cursor.getString(1);	// This will get the note from fetched table
              
                if(note.equals(mnotes)){	// This will check if current note is matching with the mnotes 
                    rowId[counter] = cursor.getString(0);	// Get the id from the fetched table values.
                    counter++;
                    break;
                }
            }
        while(cursor.moveToNext());
        }
        
        int minid = Integer.parseInt(rowId[0]);	// After storing all the ids whose notes matched with mnotes, only one value needs to be changed
        for(int i = 0; i < counter; i++){
            if(Integer.parseInt(rowId[i])<minid)
                minid= Integer.parseInt(rowId[i]);
        }
        dbupd.update(DATABASE_TABLE , cvUpdate , KEY_ROWID + " =  " + minid ,null);
        dbupd.close();
    }

	public void insertPassword(Password p) {
		// TODO Auto-generated method stub
		SQLiteDatabase dbip = myHelper.getWritableDatabase();
		ContentValues cvip = new ContentValues();
		
		String query = "SELECT * FROM passTable";
		Cursor c = dbip.rawQuery(query, null);
		
		int count = c.getCount();
		cvip.put(KEY_ROWIDPASS, count);
		cvip.put(KEY_PASS, p.getPass());
		
		dbip.insert(DATABASE_TABLE_PASSWORD, null, cvip);
		
		dbip.close();
	}

	
	public String searchpassword(String pass) {
		SQLiteDatabase dbsp = myHelper.getReadableDatabase();
		
		// Select All Query
		String query = "SELECT pass FROM passTable";

		Cursor cur = dbsp.rawQuery(query, null);
		
		String a, b;
		b = "not found";
		if (cur.moveToFirst()) {

			do {
				a = cur.getString(0);

				if (a.equals(pass)) {
					b = cur.getString(0);
					break;
				}

			} while (cur.moveToNext());
		}
		return b;
		}

	public void deleteItem(String sData) throws SQLException {
		// TODO Auto-generated method stub
		SQLiteDatabase dbdel = myHelper.getWritableDatabase();
		
		String selectQuery = "SELECT _id, notes from " + DATABASE_TABLE;
		Cursor cursor = dbdel.rawQuery(selectQuery, null);
		String note;
		int count = cursor.getCount();
		String[] rowId = new String[count];
		int counter = 0;	// Variable to track the array
		if(cursor.moveToFirst()){ 
			do{
				note= cursor.getString(1);
				
				if(note.equals(sData)){	// If it is matching with sData then store the respective ID into that rowid array.
					rowId[counter] = cursor.getString(0);
					counter++;
					break;
				}
			}
		while(cursor.moveToNext());
		}
		int minid = Integer.parseInt(rowId[0]);	// Check the minimum rowid from the array by making use of a for loop.
		for(int i = 0; i < counter; i++){
			if(Integer.parseInt(rowId[i])<minid)
				minid= Integer.parseInt(rowId[i]);
		}
		dbdel.delete(DATABASE_TABLE, KEY_ROWID + " = ? ", new String[] { "" + minid });	// Delete minid
		dbdel.close();
	}

	public int getCount() {
		// TODO Auto-generated method stub
		SQLiteDatabase dbgc = myHelper.getReadableDatabase();
        String query = "select * from " + DATABASE_TABLE_PASSWORD;
        Cursor cursor = dbgc.rawQuery(query , null);
        int count = cursor.getCount();

        return count;
        }
}