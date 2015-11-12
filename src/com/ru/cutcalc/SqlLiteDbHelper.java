package com.ru.cutcalc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;



import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqlLiteDbHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_PATH = "/data/data/com.ru.cutcalc/databases/";
    
  
    // Database Name
    private static final String DATABASE_NAME = "mydatabase.sqlite";
                                               
    private static final String TABLE_CONTACT = "my_table";
    private static final String MILLS_TABLE_CONTACT = "mills_cal";
    private SQLiteDatabase db;
    // Contacts Table Columns names.
    
    
    
    public static final String ASSETS_DB_FOLDER = "mydb";
   
    Context ctx;
    public SqlLiteDbHelper(Context context) {
       super(context, DATABASE_NAME, null, DATABASE_VERSION);
       ctx = context;
    }
  
   
    
    public void CopyDataBaseFromAsset() throws IOException{
        InputStream in  = ctx.getAssets().open(ASSETS_DB_FOLDER+"/"+DATABASE_NAME);
        Log.e("sample", "Starting copying" );
        String outputFileName = DATABASE_PATH+DATABASE_NAME;
        File databaseFile = new File( "/data/data/com.ru.cutcalc/databases");
         // check if databases folder exists, if not create one and its subfolders
         if (!databaseFile.exists()){
             databaseFile.mkdir();
         }
       
        OutputStream out = new FileOutputStream(outputFileName);
       
        byte[] buffer = new byte[1024];
        int length;
       
       
        while ((length = in.read(buffer))>0){
               out.write(buffer,0,length);
        }
        Log.e("sample", "Completed" );
        out.flush();
        out.close();
        in.close();
       
     }
    
    
    
  
    
    
    
    
    
    public List<String> getDiaP(String ProductNo){
        List<String> labels = new ArrayList<String>();
        
       // List<String> labels1 = new ArrayList<String>();
       
         
        // Select All Query
        String selectQuery = "SELECT   Dia_Meter FROM " + TABLE_CONTACT +" WHERE  Product_No="+ProductNo ;
        System.out.println("selectQuery"+selectQuery);
      
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
      
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
        //	labels.add("Select diameter");
            do {
            	
                labels.add(cursor.getString(0));
               
            
            } while (cursor.moveToNext());
        }
         
        // closing connection
        cursor.close();
        db.close();
         
        // returning lables
        return labels;
        
    }
    
    
    public List<String> getFultecountP(String ProductNo){
        List<String> flutecount = new ArrayList<String>();
        
       // List<String> labels1 = new ArrayList<String>();
        
        
        // Select All Query
        String selectQuery = "SELECT Distinct Fulte_Count  FROM " + TABLE_CONTACT +" WHERE Product_No="+ProductNo ;
        System.out.println("selectQuery"+selectQuery);
      
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
      
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
        	
        //	flutecount.add("Select fultecount");
            do {
            	flutecount.add(cursor.getString(0));
               
            
            } while (cursor.moveToNext());
        }
         
        // closing connection
        cursor.close();
        db.close();
         
        // returning lables
        return flutecount;
        
    }
    
    public List<String> getMillstypeP(String ProductNo){
        List<String> productid = new ArrayList<String>();
        
       // List<String> labels1 = new ArrayList<String>();
        
        
        // Select All Query
        String selectQuery = "SELECT  *  FROM " + TABLE_CONTACT +" WHERE Product_No="+ProductNo ;
        System.out.println("selectQuery"+selectQuery);
      
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
      
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
           
        	productid.add(cursor.getString(1));
               
            
           
        }
         
        // closing connection
        cursor.close();
        db.close();
         
        // returning lables
        return productid;
        
    }
    
   
    
    
    public String getmillsformula(String millsid,String Diameter,String matrialid)
	{
    	
    	  System.out.println("millsid"+millsid);
    	  System.out.println("Diameter"+Diameter);
    	  System.out.println("matrialid"+matrialid);
    	
    	//  String VcValue;
    	  String selectQuery = "SELECT Vc,Fz  FROM mills_cal where Mills_id="+millsid+" AND Dia_Meter="+Diameter+" AND Matrial_id="+matrialid;
    	  System.out.println("selectQuery"+selectQuery);
  		SQLiteDatabase db = this.getWritableDatabase();
  		Cursor cursor = db.rawQuery(selectQuery, null);
  		// looping through all rows and adding to list
  		 cursor.moveToFirst();
  			
  				String  VcValue= cursor.getString(cursor.getColumnIndex("Vc"));
  				String FzValue= cursor.getString(cursor.getColumnIndex("Fz"));
  				
  				String VcFzValues=VcValue+","+FzValue;
  			
  	
  		// close inserting data from database
  		db.close();
  		// return contact list
  		cursor.close();
  		return VcFzValues;
			
	}
    
    public String getmillsvalue(String millsid,String Diameter,String matrialid,String shapid)
	{
    	
    	  System.out.println("millsid"+millsid);
    	  System.out.println("Diameter"+Diameter);
    	  System.out.println("matrialid"+matrialid);
    	  System.out.println("shapid"+shapid);
    	
    	//  String VcValue;
    	  String selectQuery = "SELECT Vc,Fz  FROM mills_cal where Mills_id="+millsid+" AND Dia_Meter="+Diameter+" AND Matrial_id="+matrialid+" AND Shape_Id="+shapid;
    	  System.out.println("selectQuery"+selectQuery);
  		 SQLiteDatabase db = this.getWritableDatabase();
  		 Cursor cursor = db.rawQuery(selectQuery, null);
  		// looping through all rows and adding to list
  		 cursor.moveToFirst();
  			
  				String  VcValue= cursor.getString(cursor.getColumnIndex("Vc"));
  				String FzValue= cursor.getString(cursor.getColumnIndex("Fz"));
  				
  				String VcFzValues=VcValue+","+FzValue;
  			
  	
  		// close inserting data from database
  		db.close();
  		// return contact list
  		cursor.close();
  		return VcFzValues;
				
	}
   
    public void openDataBase () throws SQLException{
       String path = DATABASE_PATH+DATABASE_NAME;
       db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    }

       @Override
       public void onCreate(SQLiteDatabase db) {
             
             
       }

       @Override
       public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
              // TODO Auto-generated method stub
       
    	   
       }

      
	
}
