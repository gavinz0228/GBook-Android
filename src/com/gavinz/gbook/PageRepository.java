package com.gavinz.gbook;
import android.content.*;

import android.database.*;
import android.database.sqlite.*;
public class PageRepository {

	public static Page getNextPage(Context context,int chapter,int section)
	{
		Page page=PageRepository.getPage(context,chapter,section+1);
		if(page!=null)
		{
			return page;
		}
		else
		{
			return PageRepository.getChapter(context, chapter+1);
		}
	}
	public static Page getPrePage(Context context,int chapter,int section)
	{
		Page page=PageRepository.getPage(context,chapter,section-1);
		if(page!=null)
		{
			return page;
		}
		else
		{
			return PageRepository.getChapter(context, chapter-1);
		}
	}
	
	public static Page getPage(Context context,int chapter,int section)
	{
		DBHelper dbh=new DBHelper(context);
		SQLiteDatabase db=dbh.getReadableDatabase();
		String sql="SELECT pid,title,content,chapter,section,format FROM gbook_page WHERE chapter=? and section=?";
		Cursor result=db.rawQuery(sql, new String[]{Integer.valueOf(chapter).toString(),Integer.valueOf(section).toString()});
		Page page=null;
		if(result.getCount()>0)
		{
			page=new Page();
			result.moveToFirst();
			page.id=result.getInt(0);
			page.title=result.getString(1);
			page.content=result.getString(2);
			page.chapter=result.getInt(3);
			page.section=result.getInt(4);
			page.format=result.getString(5);
			db.close();
			return page;
		}
		db.close();
		return page;
		
	}
	public static Page getChapter(Context context,int chapter)
	{
		DBHelper dbh=new DBHelper(context);
		SQLiteDatabase db=dbh.getReadableDatabase();
		//try getting the next section of the same chapter
		String sql="SELECT pid,title,content,chapter,section,format FROM gbook_page WHERE chapter=? and section=1";
		Cursor result=db.rawQuery(sql, new String[]{Integer.valueOf(chapter).toString()});
		Page page=null;
		if(result.getCount()>0)
		{
			result.moveToFirst();
			page=new Page();
			page.id=result.getInt(0);
			page.title=result.getString(1);
			page.content=result.getString(2);
			page.chapter=result.getInt(3);
			page.section=result.getInt(4);
			page.format=result.getString(5);
			//if there is a next page in the current chapter then return the page
			db.close();
			return page;
		}
		db.close();
		return page;
	}
	public static Chapter[] getAllChapterInfo(Context context)
	{
		Chapter[] chapters=null;
		DBHelper dbh=new DBHelper(context);
		SQLiteDatabase db=dbh.getReadableDatabase();
		String sql="SELECT id, title FROM gbook_chapter";
		Cursor result=db.rawQuery(sql, new String[]{});

		int count=result.getCount();
		if(count>0)
		{
			chapters=new Chapter[count];
			result.moveToFirst();
			for(int i=0;i<count;i++)
			{
				chapters[i]=new Chapter();
				chapters[i].id=result.getInt(0);
				chapters[i].title=result.getString(1);
				chapters[i].sections=PageRepository.getSectionsByChapter(context,chapters[i].id);
				result.moveToNext();
			}
		}
		db.close();
		return chapters;
	}
	public static Section[] getSectionsByChapter(Context context,int chapter)
	{
		Section[] sections=null;
		DBHelper dbh=new DBHelper(context);
		SQLiteDatabase db=dbh.getReadableDatabase();
		String sql="SELECT section,title FROM gbook_page WHERE chapter=? ORDER BY section ASC";
		Cursor result=db.rawQuery(sql, new String[]{Integer.valueOf(chapter).toString()});
		int count=result.getCount();
		if(count>0)
		{
			result.moveToFirst();
			sections=new Section[count];
			for(int i=0;i<count;i++)
			{
				sections[i]=new Section();
				sections[i].id=result.getInt(0);
				sections[i].title=result.getString(1);
				result.moveToNext();
			}
		}
		db.close();
		return sections;
	}
	public static BookMark[]  getAllBookMarks(Context context)
	{
		BookMark[] bookMark=null;
		DBHelper dbh=new DBHelper(context);
		SQLiteDatabase db=dbh.getReadableDatabase();
		String sql="SELECT id,title,chapter,section,y FROM gbook_bookmark ORDER BY id DESC";
		Cursor result=db.rawQuery(sql, new String[]{});
		int count=result.getCount();
		if(count>0)
		{
			result.moveToFirst();
			bookMark =new BookMark[count];
			for(int i=0;i<count;i++)
			{
				bookMark[i]=new BookMark();
				bookMark[i].id=result.getInt(0);
				bookMark[i].title=result.getString(1);
				bookMark[i].chapter=result.getInt(2);
				bookMark[i].section=result.getInt(3);
				bookMark[i].y=result.getInt(4);
				result.moveToNext();
			}
		}
		db.close();
		return bookMark;
	}
	public static void addABookmark(Context context,BookMark bm)
	{
		BookMark[] bookMark=null;
		DBHelper dbh=new DBHelper(context);
		SQLiteDatabase db=dbh.getReadableDatabase();
		String sql="insert into gbook_bookmark (title,chapter,section,y)values('"+bm.title+"',"+bm.chapter+","+bm.section+","+bm.y+")";
		db.execSQL(sql);
		db.close();
	}
	public static void deleteBookmark(Context context,BookMark bm)
	{
		BookMark[] bookMark=null;
		DBHelper dbh=new DBHelper(context);
		SQLiteDatabase db=dbh.getReadableDatabase();
		String sql="delete from gbook_bookmark where id="+bm.id;
		db.execSQL(sql);
		db.close();
		
	}
	public static String getChapterName(Context context,int chapter)
	{
		DBHelper dbh=new DBHelper(context);
		SQLiteDatabase db=dbh.getReadableDatabase();
		String sql="SELECT title From gbook_chapter WHERE chapter=?";
		Cursor result=db.rawQuery(sql, new String[]{Integer.valueOf(chapter).toString()});
		int count=result.getCount();
		String name=null;
		if(count>0)
		{
			result.moveToFirst();
			name= result.getString(0);
		}
		db.close();
		return name;
		
		
	}
}
