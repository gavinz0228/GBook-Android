package com.gavinz.gbook;
import java.io.IOException;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;
public class BookInfo 
{
	static boolean hasLoaded=false;
	private static String title;
	private static String introduction;
	private static String author;
	public static void Load(Context context)
	{
		DBHelper dbh=new DBHelper(context);
		
		try {
			dbh.createDataBase();
			dbh.openDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SQLiteDatabase db= dbh.getReadableDatabase();
		String sql="SELECT title,intro,author FROM gbook_info";
		Cursor result= db.rawQuery(sql, new String[]{});
		result.moveToFirst();
		BookInfo.introduction=result.getString(1);
		BookInfo.title=result.getString(0);
		BookInfo.author=result.getString(2);
	}
	public static String getTitle(Context context)
	{
		if (BookInfo.hasLoaded==false)
			Load(context);
		return BookInfo.title;
	}
	public static String getAuthor(Context context)
	{
		if (BookInfo.hasLoaded==false)
			Load(context);
		return BookInfo.author;
	}
	public static String getIntro(Context context)
	{
		if (BookInfo.hasLoaded==false)
			Load(context);
		return BookInfo.introduction;
	}

}
