package com.gavinz.gbook;
import java.io.*;

import android.content.Context;  
import android.database.SQLException;
import android.database.sqlite.*;
import android.util.Log;
  
public class DBHelper extends SQLiteOpenHelper {  
  /*
    private static String dbname = "gbook.db"; 
    private static int version = 1;   
    private Context context;
    public DBHelper(Context context) {  
        super(context, dbname, null, version); 
        this.context=context;
        // TODO Auto-generated constructor stub  
    }  
    */
  
    String DB_PATH =null;
    
    private static String DB_NAME = "gbook.db";
  
    private SQLiteDatabase myDataBase; 
     
    private final Context myContext;
  
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DBHelper(Context context) {
  
        super(context, DB_NAME, null, 1);
        this.myContext = context;
        DB_PATH="/data/data/"+context.getPackageName()+"/"+"databases/";
    }   
  
  /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{
  
        boolean dbExist = checkDataBase();
  
        if(dbExist){
            //do nothing - database already exist
        }else{
  
            //By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
  
            try { 
                copyDataBase();
  
            } catch (IOException e) {
  
                throw new Error("Error copying database");
  
            }
        }
  
    }
  
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
  
        SQLiteDatabase checkDB = null;
  
        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
  
        }catch(SQLiteException e){
  
            //database does't exist yet.
  
        }
  
        if(checkDB != null){
  
            checkDB.close();
  
        }
  
        return checkDB != null ? true : false;
    }
  
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
  
        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);
  
        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;
  
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
  
        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
  
        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
  
    }
  
    public void openDataBase() throws SQLException{
  
        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
  
    }
  
    @Override
    public synchronized void close() {
  
            if(myDataBase != null)
                myDataBase.close();
  
            super.close();
  
    }
    @Override  
    public void onCreate(SQLiteDatabase db) { 

    	/*
    	byte[] buffer=new byte[1024];
    	int len;
    	OutputStream os;
    	try {
			os = new FileOutputStream(dbname);
			InputStream is=Utility.getDatabase(context);
			while((len=is.read(buffer))>0)
			{
				os.write(buffer, 0, len);
			}
			os.flush();
			os.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			Log.w("database","Failed to open the database.");
		}
    	catch (IOException e) {
			e.printStackTrace();
		}
    	*/

    }  
  
    @Override  
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  
        // TODO Auto-generated method stub  
    }  
  
}