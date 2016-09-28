package data;

import java.sql.Blob;

public class Note {
	public int _id;
	public String title;
	public String date;
	public int color;
	public byte[] path;

	public Note() {
	}

	public Note(String title, String date, int color,byte[] path) {
		this.title = title;
		this.date = date;
		this.color = color;
		this.path = path;
	}
}
