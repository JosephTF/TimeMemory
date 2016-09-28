package activity;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import activity.AddItem.MyAsyTask;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.imooc.arcmenu.view.ArcMenu;
import com.imooc.arcmenu.view.ArcMenu.OnMenuItemClickListener;
import com.joseph.timememory.R;

import data.DBOpenHelper;

public class SelectAct extends Activity implements OnClickListener {
	private ImageButton back_btn;
	private ArcMenu mArcMenu;
	private ImageView s_bg, rewrite, imgdown, sina, delete;
	private TextView s_time, s_title, s_daycount, s_day;
	private SQLiteDatabase dbWriter;
	private DBOpenHelper dboh;
	private int position, dayCount;
	private Cursor cursor;
	private int id;
	private String title, begintime, datestring;
	private ByteArrayInputStream stream;
	private String mFilePath;
	private FileOutputStream fos;
	private Bitmap bitmap;
	private ProgressDialog pd;

	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		// 初始化shareSDK
		ShareSDK.initSDK(getApplicationContext());
		setContentView(R.layout.select);
		WindowManager winManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		Intent intent = getIntent();
		position = intent.getIntExtra("position", 0);
		dboh = new DBOpenHelper(this);
		dbWriter = dboh.getWritableDatabase();
		cursor = dbWriter.rawQuery("SELECT * FROM note", null);
		cursor.moveToPosition(position);
		getData();
		initView();
		initEvent();
		// 保存拍摄图片
		mFilePath = getImageDir() + File.separator + getTime() + ".png";
		progressdialog();
	}

	private void progressdialog() {
		pd = new ProgressDialog(SelectAct.this);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setTitle("下载背景图片中~");
		pd.setIcon(R.drawable.saveimg);
		pd.setIndeterminate(false);
		pd.setCancelable(false);
		pd.setProgress(100);
	}

	// 创建Image文件夹
	public String getImageDir() {
		// SD卡指定文件夹
		String sdcardPath = Environment.getExternalStorageDirectory()
				.toString();
		// 指定文件夹创建路径
		File dir = new File(sdcardPath + File.separator + "TimeMemory"
				+ File.separator + "ImageDownload");
		if (dir.exists()) {
			return dir.toString();
		} else {
			dir.mkdirs();
			Log.d("BAG", "保存路径不存在,");
			return dir.toString();
		}
	}

	public String getTime() {
		// 获取年月日时分秒
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String str = format.format(date);
		return str;
	}
	/**获取数据**/
	public void getData() {
		id = cursor.getInt(cursor.getColumnIndex("_id"));
		title = cursor.getString(cursor.getColumnIndex("title"));
		begintime = cursor.getString(cursor.getColumnIndex("date"));
		stream = new ByteArrayInputStream(cursor.getBlob(cursor
				.getColumnIndex("path")));
		// 计算日期差
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date1 = null;
		try {
			date1 = sdf.parse(begintime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 获取现在日期
		Date date = new Date(System.currentTimeMillis());
		String endtime = sdf.format(date);
		Date date2 = null;
		try {
			date2 = sdf.parse(endtime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 计算日期差
		GregorianCalendar cal1 = new GregorianCalendar();
		GregorianCalendar cal2 = new GregorianCalendar();
		cal1.setTime(date1);
		cal2.setTime(date2);
		dayCount = (int) ((cal2.getTimeInMillis() - cal1.getTimeInMillis()) / (1000 * 3600 * 24));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.backbtn:
			Intent intent = new Intent(SelectAct.this, Main.class);
			startActivity(intent);
			overridePendingTransition(R.anim.activity_new,
					R.anim.activity_finish);
			finish();
			break;

		default:
			break;
		}
	}

	public void deleteDate() {
		dbWriter.delete("note", "_id=" + id, null);
		Intent intent = new Intent(SelectAct.this, Main.class);
		startActivity(intent);
		overridePendingTransition(R.anim.activity_new, R.anim.activity_finish);
		finish();
	}

	private void initEvent() {
		mArcMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public void onClick(View view, int pos) {
				// TODO Auto-generated method stub
				switch (view.getId()) {
				case R.id.rewrite:
					Intent intent = new Intent(SelectAct.this, Rewrite.class);
					intent.putExtra("id", position);
					startActivity(intent);
					overridePendingTransition(R.anim.activity_new,
							R.anim.activity_finish);
					finish();
					break;
				case R.id.imgdown:
					MyAsyTask task = new MyAsyTask();
					task.execute();
					break;
				case R.id.sina:
					// 一键分享到新浪微博
					OnekeyShare oneKeyShare = new OnekeyShare();
					// 设置分享标题
					oneKeyShare.setTitle(title);
					// 设置时间
					oneKeyShare.setText(datestring + "这一天很有意义，因为这是：" + title
							+ "的日子~");
					// 显示分享列表
					oneKeyShare.show(SelectAct.this);
					break;
				case R.id.delete:
					deleteDate();
					finish();
				}
			}
		});
	}

	private void initView() {
		Typeface FZLTHJW = Typeface.createFromAsset(getAssets(),
				"fonts/FZLTHJW.TTF");
		Typeface FZLTZHUNHK = Typeface.createFromAsset(getAssets(),
				"fonts/FZLTZHUNHK.TTF");
		mArcMenu = (ArcMenu) findViewById(R.id.id_menu);
		s_bg = (ImageView) findViewById(R.id.select_bg);
		s_bg.setImageDrawable(Drawable.createFromStream(stream, "img"));
		s_time = (TextView) findViewById(R.id.select_time);
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = (Date) sdf1.parse(begintime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		datestring = sdf2.format(date);
		s_time.setText(datestring);
		s_time.setTypeface(FZLTHJW);
		s_title = (TextView) findViewById(R.id.select_title);
		s_title.setText(title);
		s_title.setTypeface(FZLTHJW);
		s_daycount = (TextView) findViewById(R.id.select_daycount);
		s_daycount.setText(Integer.toString(dayCount));
		s_title.setTypeface(FZLTZHUNHK);
		s_day = (TextView) findViewById(R.id.select_day);
		if(dayCount==0){
			s_daycount.setText("Today");
			s_daycount.setTypeface(FZLTZHUNHK);
			s_day.setText("");
		}else if (dayCount ==1) {
			s_day.setText("DAY");
		} else {
			s_day.setText("DAYS");
		}
		s_title.setTypeface(FZLTZHUNHK);
		rewrite = (ImageView) findViewById(R.id.rewrite);
		rewrite.setOnClickListener(this);
		imgdown = (ImageView) findViewById(R.id.imgdown);
		imgdown.setOnClickListener(this);
		sina = (ImageView) findViewById(R.id.sina);
		sina.setOnClickListener(this);
		delete = (ImageView) findViewById(R.id.delete);
		delete.setOnClickListener(this);
		back_btn = (ImageButton) findViewById(R.id.backbtn);
		back_btn.setOnClickListener(this);
	}

	/**异步任务**/
	class MyAsyTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// mprogressbar.setVisibility(View.VISIBLE);
			pd.show();
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// mprogressbar.setVisibility(View.GONE);
			pd.cancel();
			// 通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
						AlertDialog.Builder builder = new AlertDialog.Builder(
								SelectAct.this);
						// 设置Title的图标
						builder.setIcon(R.drawable.imgsuccess);
						// 设置Title的内容
						builder.setTitle("图片保存成功！");
						// 设置Content来显示一个信息
						builder.setMessage("该图片保存在手机“TimeMemory/ImageDownload”文件夹中哦~");
						// 设置一个PositiveButton
						builder.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
									}
								});
						// 显示出该对话框
						builder.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			pd.setProgress(20);
			try {
				fos = new FileOutputStream(mFilePath);
				s_bg.setDrawingCacheEnabled(true);
				pd.setProgress(40);
				bitmap = Bitmap.createBitmap(s_bg.getDrawingCache());
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pd.setProgress(80);
			try {
				fos.flush();
				pd.setProgress(90);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fos.close();
				pd.setProgress(100);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

	}

	protected void onDestroy() {
		super.onDestroy();
		// 应用的最后一个Activity关闭时应释放DB
		dboh.close();
		dbWriter.close();
		cursor.close();
		try {
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
