package activity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import pickdate.wheel.widget.method.ChangeBirthDialog;
import pickdate.wheel.widget.method.ChangeBirthDialog.OnBirthListener;
import activity.AddItem.MyAsyTask;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.joseph.timememory.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import data.DBManager;
import data.DBOpenHelper;
import data.Note;

public class Rewrite extends Activity implements OnClickListener {
	private ImageButton back_btn, save_btn;
	private Button title_btn, date_btn, color_btn;
	private PopupWindow popupWindow;
	private TextView textView1, textView2, textView3;
	private EditText ettext;
	private ImageView image, camera_btn, more_btn;
	private Bitmap bitmap;
	private View popwindowView;
	private Intent intent;
	private int wm_width;
	private ChangeBirthDialog mChangeBirthDialog;
	private int id, id2, color;
	private String myear, mmonth, mday;
	private String mFilePath;
	private FileInputStream fis;
	private DBManager dbmanager;
	private String getTitle, getDate;
	private SQLiteDatabase dbWriter;
	private DBOpenHelper dboh;
	private Cursor cursor;
	private ByteArrayInputStream stream;
	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_main);
		WindowManager winManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		// ��ʼ��DBManager
		dbmanager = new DBManager(Rewrite.this);
		// ��ȡ��ǰ���ڵĿ��
		wm_width = winManager.getDefaultDisplay().getWidth();
		// ��������ͼƬ
		mFilePath = getImageDir() + File.separator + getTime() + ".png";
		Intent getid = getIntent();
		id = getid.getIntExtra("id", 0);
		dboh = new DBOpenHelper(this);
		dbWriter = dboh.getWritableDatabase();
		cursor = dbWriter.rawQuery("SELECT * FROM note", null);
		cursor.moveToPosition(id);
		// �ؼ���ʼ��
		init();
		popupwindow();
		progressdialog();
	}

	private void progressdialog() {
		pd = new ProgressDialog(Rewrite.this);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setTitle("������Ϣ��~");
		pd.setIcon(R.drawable.saveimg);
		pd.setIndeterminate(false);
		pd.setCancelable(false);
		pd.setProgress(100);
	}

	// ����Image�ļ���
	public String getImageDir() {
		// SD��ָ���ļ���
		String sdcardPath = Environment.getExternalStorageDirectory()
				.toString();
		// ָ���ļ��д���·��
		File dir = new File(sdcardPath + File.separator + "TimeMemory"
				+ File.separator + "ImageSave");
		if (dir.exists()) {
			return dir.toString();
		} else {
			dir.mkdirs();
			Log.d("BAG", "����·��������,");
			return dir.toString();
		}
	}

	private void init() {
		// Popwindow���ּ��أ����ڲ��ؼ���ʼ��
		LayoutInflater inflater = LayoutInflater.from(this);
		popwindowView = inflater.inflate(R.layout.pop_title, null);
		// ��"������ͤ�ڼ���"���屣����assets/fonts/Ŀ¼�£�����Typeface����
		Typeface typeFace = Typeface.createFromAsset(getAssets(),
				"fonts/FZLTHJW.TTF");
		ettext = (EditText) popwindowView.findViewById(R.id.n_title_e);
		textView1 = (TextView) findViewById(R.id.new_text1);
		textView1.setTypeface(typeFace);
		textView2 = (TextView) findViewById(R.id.new_text2);
		textView2.setTypeface(typeFace);
		textView3 = (TextView) findViewById(R.id.new_text3);
		textView3.setTypeface(typeFace);
		// �������ء����水ť��ʼ������
		back_btn = (ImageButton) findViewById(R.id.back);
		back_btn.setOnClickListener(this);
		save_btn = (ImageButton) findViewById(R.id.save);
		save_btn.setOnClickListener(this);
		// ���ⰴť
		title_btn = (Button) findViewById(R.id.n_title);
		String titlename = cursor.getString(cursor.getColumnIndex("title"));
		title_btn.setText(titlename);
		title_btn.setOnClickListener(this);
		// ����ѡ��ť
		date_btn = (Button) findViewById(R.id.n_time);
		String begintime = cursor.getString(cursor.getColumnIndex("date"));
		// ת�����ڸ�ʽ
		SimpleDateFormat sdf0 = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = (Date) sdf0.parse(begintime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdf3 = new SimpleDateFormat("MM");
		SimpleDateFormat sdf4 = new SimpleDateFormat("dd");
		myear = sdf2.format(date);
		mmonth = sdf3.format(date);
		mday = sdf4.format(date);
		date_btn.setText(sdf1.format(date));
		date_btn.setOnClickListener(this);
		mChangeBirthDialog = new ChangeBirthDialog(this);
		// ��ɫʰȡ��
		color_btn = (Button) findViewById(R.id.n_color);
		color_btn.setOnClickListener(this);
		color = cursor.getInt(cursor.getColumnIndex("color"));
		color_btn.setBackgroundColor(color);
		// ����ͼƬ
		stream = new ByteArrayInputStream(cursor.getBlob(cursor
				.getColumnIndex("path")));
		image = (ImageView) findViewById(R.id.new_bg);
		image.setImageDrawable(Drawable.createFromStream(stream, "img"));
		// �����������ఴť
		camera_btn = (ImageView) findViewById(R.id.camerabtn);
		camera_btn.setOnClickListener(this);
		more_btn = (ImageView) findViewById(R.id.morebtn);
		more_btn.setOnClickListener(this);
		id2 = cursor.getInt(cursor.getColumnIndex("_id"));
	}

	private void popupwindow() {
		popupWindow = new PopupWindow(popwindowView,
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
		popupWindow.setTouchable(true);
		popupWindow.setOutsideTouchable(false);
		popupWindow.setBackgroundDrawable(new ColorDrawable());
		popupWindow.setAnimationStyle(R.style.AnimationPreview);
		popupWindow.update();
		popupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				getTitle = ettext.getText().toString().trim();
				title_btn.setText(getTitle);
				title_btn.setVisibility(View.VISIBLE);

			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			intent = new Intent(Rewrite.this, Main.class);
			startActivity(intent);
			overridePendingTransition(R.anim.activity_new,
					R.anim.activity_finish);
			finish();
			break;
		case R.id.save:
			getTitle = title_btn.getText().toString().trim();
			if ((getTitle.equals("")) || (getTitle.length() == 0)) {
				dialog();
			} else {
				MyAsyTask task = new MyAsyTask();
				task.execute();
			}
			break;
		case R.id.n_title:
			title_btn.setVisibility(View.GONE);
			// ��ȡ����Ŀ�͸�
			int width,
			height;
			width = textView1.getMeasuredWidth();
			height = textView1.getMeasuredHeight();
			popupWindow.setWidth(wm_width - width);
			popupWindow.showAsDropDown(v, 0, -height);
			break;
		case R.id.n_time:
			mChangeBirthDialog.setDate(Integer.parseInt(myear),
					Integer.parseInt(mmonth), Integer.parseInt(mday));
			mChangeBirthDialog.show();
			mChangeBirthDialog.setBirthdayListener(new OnBirthListener() {

				@Override
				public void onClick(String year, String month, String day) {
					date_btn.setText(year + "-" + month + "-" + day);
					myear = year;
					mmonth = month;
					mday = day;
				}
			});
			date_btn.setText(myear + "-" + mmonth + "-" + mday);
			break;
		case R.id.n_color:
			intent = new Intent(Rewrite.this, ColorPicker.class);
			startActivityForResult(intent, 1);
			overridePendingTransition(R.anim.activity_new,
					R.anim.activity_finish);
			break;
		case R.id.camerabtn:
			String[] item_list = { "���ѡȡ", "�������" };
			AlertDialog.Builder builder = new AlertDialog.Builder(Rewrite.this);
			builder.setTitle("�Զ��屳��");
			builder.setItems(item_list, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int item) {
					if (item == 0) {
						// ������
						intent = new Intent(Intent.ACTION_PICK,
								MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						startActivityForResult(intent, 2);
					}
					if (item == 1) {
						// �������
						intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						Uri photoUri = Uri.fromFile(new File(mFilePath));
						intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
						startActivityForResult(intent, 3);
					}
				}
			});
			AlertDialog dialog = builder.create();
			dialog.show();
			break;
		case R.id.morebtn:
			intent = new Intent(Rewrite.this, PicSelect.class);
			startActivityForResult(intent, 4);
			overridePendingTransition(R.anim.activity_new,
					R.anim.activity_finish);
			break;
		default:
			break;
		}
	}

	public String getTime() {
		// ��ȡ������ʱ����
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String str = format.format(date);
		return str;
	}

	/*
	 * ��������ͼƬ
	 */
	public void Click(View view) {
		view.setDrawingCacheEnabled(true);
		bitmap = ((BitmapDrawable) ((ImageView) view).getDrawable())
				.getBitmap();
		view.setDrawingCacheEnabled(false);
		image.setImageBitmap(bitmap);
	}

	private void updateDB() {
		if (mmonth.length() < 2) {
			mmonth = "0" + mmonth;
		}
		if (mday.length() < 2) {
			mday = "0" + mday;
		}
		pd.setProgress(20);
		getDate = myear + mmonth + mday;
		pd.setProgress(40);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		// bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
		image.setDrawingCacheEnabled(true);
		bitmap = Bitmap.createBitmap(image.getDrawingCache());
		pd.setProgress(60);
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
		pd.setProgress(80);
		// ContentValues�Լ�ֵ�Ե���ʽ�������
		ContentValues cv = new ContentValues();
		cv.put("title", getTitle);
		cv.put("date", getDate);
		cv.put("color", color);
		cv.put("path", os.toByteArray());
		pd.setProgress(90);
		dbWriter.update("note", cv, "_id = ?",
				new String[] { Integer.toString(id2) });
		pd.setProgress(100);
		intent = new Intent(Rewrite.this, Main.class);
		startActivity(intent);
		overridePendingTransition(R.anim.activity_new, R.anim.activity_finish);
		finish();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Colorʰȡ��
		if (requestCode == 1 && resultCode == 2) {
			color = data.getIntExtra("color", 0);
			color_btn.setBackgroundColor(color);
		} else if (requestCode == 2 && resultCode == RESULT_OK) {
			// ��ȡ���ͼƬ
			Uri uri = data.getData();
			ContentResolver cr = this.getContentResolver();
			try {
				bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
				image.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else if (requestCode == 3 && resultCode == RESULT_OK) {
			// �洢����ȡ���ͼƬ
			try {
				fis = new FileInputStream(mFilePath);
				bitmap = BitmapFactory.decodeStream(fis);
				image.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}else if (requestCode == 4 && resultCode == 4) {
			ImageLoader loader = ImageLoader.getInstance();
			ImageLoadingListener ILL;
			ILL = new ImageLoadingListener() {

				@Override
				public void onLoadingStarted(String arg0, View arg1) {
					Log.i("info", "onLoadingStarted");
				}

				@Override
				public void onLoadingFailed(String arg0, View arg1,
						FailReason arg2) {
					Log.i("info", "onLoadingFailed");
				}

				@Override
				public void onLoadingComplete(String arg0, View arg1,
						Bitmap arg2) {
					Log.i("info", "onLoadingComplete");
				}

				@Override
				public void onLoadingCancelled(String arg0, View arg1) {
					Log.i("info", "onLoadingCancelled");
				}
			};
			String getPath = data.getStringExtra("picpath");
			loader.displayImage(getPath, image, ILL);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	// ��ӱ���Ի���
	protected void dialog() {
		// ͨ��AlertDialog.Builder�������ʵ�������ǵ�һ��AlertDialog�Ķ���
		AlertDialog.Builder builder = new AlertDialog.Builder(Rewrite.this);
		// ����Title��ͼ��
		builder.setIcon(R.drawable.forgettitle);
		// ����Title������
		builder.setTitle("���⻹û����дŶ��");
		// ����Content����ʾһ����Ϣ
		builder.setMessage("��ӱ��� Or �����˳���");
		// ����һ��PositiveButton
		builder.setPositiveButton("�˳�", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		// ����һ��NegativeButton
		builder.setNegativeButton("���", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		// ��ʾ���öԻ���
		builder.show();
	}

	class MyAsyTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd.show();
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			pd.cancel();
		}

		@Override
		protected Void doInBackground(Void... params) {
			updateDB();
			return null;
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// Ӧ�õ����һ��Activity�ر�ʱӦ�ͷ�DB
		dbmanager.closeDB();
	}
}
