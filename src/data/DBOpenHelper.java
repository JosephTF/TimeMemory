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

	// ���ݿ��һ�α�����ʱonCreate�ᱻ����
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table if not exists note(_id integer primary key autoincrement,title text,date text,color integer,path blob);");
	}

	
	// ���DATABASE_VERSIONֵ����Ϊ2,ϵͳ�����������ݿ�汾��ͬ,�������onUpgrade
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("ALTER TABLE notes ADD COLUMN other STRING");
	}

}
