package data;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
	private DBOpenHelper helper;
	private SQLiteDatabase db;

	public DBManager(Context context) {
		helper = new DBOpenHelper(context);
		// ��ΪgetWritableDatabase�ڲ�������mContext.openOrCreateDatabase(mName,
		// 0,mFactory);
		// ����Ҫȷ��context�ѳ�ʼ��,���ǿ��԰�ʵ����DBManager�Ĳ������Activity��onCreate�
		db = helper.getWritableDatabase();
	}

	/*
	 * �������
	 */
	public void add(List<Note> notes) {
		db.beginTransaction(); // ��ʼ����
		try {
			for (Note note : notes) {
				db.execSQL("INSERT INTO note VALUES(null, ?, ?, ?, ?)",
						new Object[] { note.title, note.date, note.color, note.path });
			}
			db.setTransactionSuccessful(); // ��������ɹ����
		} finally {
			db.endTransaction(); // ��������
		}
	}

	/*
	 * ��������
	 */
	public void update(Note note) {
		ContentValues cv = new ContentValues();
		cv.put("title", note.title);
		cv.put("date", note.date);
		cv.put("color", note.color);
		cv.put("path", note.path);
		db.update("note", cv, "_id = ?",
				new String[] { String.valueOf(note._id) });
	}

	/*
	 * ɾ������
	 */
	public void delete(Note note) {
		db.delete("note", "_id = ?", new String[] { String.valueOf(note._id) });
	}

	/*
	 * ��ѯ�������ݣ��г��б�
	 */
	public List<Note> query() {
		ArrayList<Note> notes = new ArrayList<Note>();
		Cursor c = queryTheCursor();
		while (c.moveToNext()) {
			Note note = new Note();
			note._id = c.getInt(c.getColumnIndex("_id"));
			note.title = c.getString(c.getColumnIndex("title"));
			note.date = c.getString(c.getColumnIndex("date"));
			note.color = c.getInt(c.getColumnIndex("color"));
			byte[] in=c.getBlob(c.getColumnIndex("path"));  
			notes.add(note);
		}
		c.close();
		return notes;
	}

	private Cursor queryTheCursor() {
		Cursor c = db.rawQuery("SELECT * FROM note", null);
		return c;
	}

	/*
	 * �ر����ݿ�
	 */
	public void closeDB() {
		db.close();
	}
}
