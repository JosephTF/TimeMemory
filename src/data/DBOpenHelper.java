package data;

import java.io.ByteArrayInputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "tmdb.db";
	private static final String TABLE_NAME = "note";
	private static final int DATABASE_VERSION = 1;
	


	public DBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// 数据库第一次被创建时onCreate会被调用
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table if not exists note(_id integer primary key autoincrement,title text,date text,color integer,path blob);");
	}

	
	// 如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("ALTER TABLE notes ADD COLUMN other STRING");
	}

}
