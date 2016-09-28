package activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import pickdate.wheel.widget.method.ChangeBirthDialog;
import pickdate.wheel.widget.method.ChangeBirthDialog.OnBirthListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.authorize.f;

import com.joseph.timememory.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import data.DBManager;
import data.Note;

public class AddItem extends Activity implements OnClickListener {
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
	private int color;
	private String myear, mmonth, mday;
	private String mFilePath;
	private FileInputStream fis;
	private DBManager dbmanager;
	private String getTitle, getDate, getPath;
	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_main);
		WindowManager winManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		// 初始化DBManager
		dbmanager = new DBManager(AddItem.this);
		// 获取年月日
		Calendar c = Calendar.getInstance();
		myear = Integer.toString(c.get(Calendar.YEAR));
		mmonth = Integer.toString(c.get(Calendar.MONTH) + 1);
		mday = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
		// 控件初始化
		init();
		popupwindow();
		// 获取当前窗口的宽度
		wm_width = winManager.getDefaultDisplay().getWidth();
		// 保存拍摄图片
		mFilePath = getImageDir() + File.separator + getTime() + ".png";
		progressdialog();
	}

	private void progressdialog() {
		pd = new ProgressDialog(AddItem.this);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setTitle("保存信息中~");
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
				+ File.separator + "ImageSave");
		if (dir.exists()) {
			return dir.toString();
		} else {
			dir.mkdirs();
			Log.d("BAG", "保存路径不存在,");
			return dir.toString();
		}
	}

	private void init() {
		// Popwindow布局加载，及内部控件初始化
		LayoutInflater inflater = LayoutInflater.from(this);
		popwindowView = inflater.inflate(R.layout.pop_title, null);
		// 将"方正兰亭黑简体"字体保存在assets/fonts/目录下，创建Typeface对象
		Typeface typeFace = Typeface.createFromAsset(getAssets(),
				"fonts/FZLTHJW.TTF");
		ettext = (EditText) popwindowView.findViewById(R.id.n_title_e);
		textView1 = (TextView) findViewById(R.id.new_text1);
		textView1.setTypeface(typeFace);
		textView2 = (TextView) findViewById(R.id.new_text2);
		textView2.setTypeface(typeFace);
		textView3 = (TextView) findViewById(R.id.new_text3);
		textView3.setTypeface(typeFace);
		// 顶部返回、保存按钮初始化声明
		back_btn = (ImageButton) findViewById(R.id.back);
		back_btn.setOnClickListener(this);
		save_btn = (ImageButton) findViewById(R.id.save);
		save_btn.setOnClickListener(this);
		// 标题按钮
		title_btn = (Button) findViewById(R.id.n_title);
		title_btn.setOnClickListener(this);
		// 日期选择按钮
		date_btn = (Button) findViewById(R.id.n_time);
		date_btn.setText(myear + "-" + mmonth + "-" + mday);
		date_btn.setOnClickListener(this);
		mChangeBirthDialog = new ChangeBirthDialog(this);
		// 颜色拾取器
		color_btn = (Button) findViewById(R.id.n_color);
		color_btn.setOnClickListener(this);
		Random random = new Random();
		color = -random.nextInt(1670)*10000-50000;
		color_btn.setBackgroundColor(color);
		// 背景图片
		image = (ImageView) findViewById(R.id.new_bg);
		// 相册相机、更多按钮
		camera_btn = (ImageView) findViewById(R.id.camerabtn);
		camera_btn.setOnClickListener(this);
		more_btn = (ImageView) findViewById(R.id.morebtn);
		more_btn.setOnClickListener(this);
	}
	/**初始化popupwindow属性方法**/
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
	/**界面各控件单击事件处理**/
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			intent = new Intent(AddItem.this, Main.class);
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
			// 获取标题的宽和高
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
			break;
		case R.id.n_color:
			intent = new Intent(AddItem.this, ColorPicker.class);
			startActivityForResult(intent, 1);
			overridePendingTransition(R.anim.activity_new,
					R.anim.activity_finish);
			break;
		case R.id.camerabtn:
			String[] item_list = { "相册选取", "相机拍摄" };
			AlertDialog.Builder builder = new AlertDialog.Builder(AddItem.this);
			builder.setTitle("自定义背景");
			builder.setItems(item_list, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int item) {
					if (item == 0) {
						// 相册调用
						intent = new Intent(Intent.ACTION_PICK,
								MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						startActivityForResult(intent, 2);
					}
					if (item == 1) {
						// 相机调用
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
			intent = new Intent(AddItem.this, PicSelect.class);
			startActivityForResult(intent, 4);
			overridePendingTransition(R.anim.activity_new,
					R.anim.activity_finish);
			break;
		default:
			break;
		}
	}

	public String getTime() {
		// 获取年月日时分秒
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String str = format.format(date);
		return str;
	}

	/*
	 * 更换背景图片
	 */
	public void Click(View view) {
		view.setDrawingCacheEnabled(true);
		bitmap = ((BitmapDrawable) ((ImageView) view).getDrawable())
				.getBitmap();
		view.setDrawingCacheEnabled(false);
		image.setImageBitmap(bitmap);
	}
	//保存数据
	private void addDB() {

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
		ArrayList<Note> notes = new ArrayList<Note>();
		Note note = new Note(getTitle, getDate, color, os.toByteArray());
		notes.add(note);
		pd.setProgress(90);
		dbmanager.add(notes);
		pd.setProgress(100);
		intent = new Intent(AddItem.this, Main.class);
		startActivity(intent);
		overridePendingTransition(R.anim.activity_new, R.anim.activity_finish);
		try {
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finish();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Color拾取器
		if (requestCode == 1 && resultCode == 2) {
			color = data.getIntExtra("color", 0);
			color_btn.setBackgroundColor(color);
		} else if (requestCode == 2 && resultCode == RESULT_OK) {
			// 获取相册图片
			Uri uri = data.getData();
			ContentResolver cr = this.getContentResolver();
			try {
				bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
				image.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else if (requestCode == 3 && resultCode == RESULT_OK) {
			// 存储并获取相机图片
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
		} else if (requestCode == 4 && resultCode == 4) {
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
			getPath = data.getStringExtra("picpath");
			loader.displayImage(getPath, image, ILL);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	// 添加标题对话框
	protected void dialog() {
		// 通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
		AlertDialog.Builder builder = new AlertDialog.Builder(AddItem.this);
		// 设置Title的图标
		builder.setIcon(R.drawable.forgettitle);
		// 设置Title的内容
		builder.setTitle("标题还没有填写哦！");
		// 设置Content来显示一个信息
		builder.setMessage("添加标题 Or 放弃退出？");
		// 设置一个PositiveButton
		builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		// 设置一个NegativeButton
		builder.setNegativeButton("添加", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		// 显示出该对话框
		builder.show();
	}
	//异步任务
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
			addDB();
			return null;
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 应用的最后一个Activity关闭时应释放DB
		dbmanager.closeDB();
	}
}
