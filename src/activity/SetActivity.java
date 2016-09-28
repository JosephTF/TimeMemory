package activity;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.joseph.timememory.R;

public class SetActivity extends Activity implements OnClickListener {
	private ImageButton back_btn;
	private TableRow tr1, tr2, tr3, tr4;
	private TextView textView1, textView2, title;
	private PackageInfo pi;
	private PackageManager pm;
	private Button exit_btn;
	private static String mFilePath;
	private Typeface typeFace;

	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_main);
		pm = getPackageManager();
		try {
			pi = pm.getPackageInfo(getPackageName().toString(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		typeFace = Typeface.createFromAsset(getAssets(), "fonts/FZLTHJW.TTF");
		initView();
		mFilePath = getImageDir();
	}

	public void initView() {
		// ���ذ�ť
		back_btn = (ImageButton) findViewById(R.id.back2);
		back_btn.setOnClickListener(this);
		tr1 = (TableRow) findViewById(R.id.row1);
		tr1.setOnClickListener(this);
		tr2 = (TableRow) findViewById(R.id.row2);
		tr2.setOnClickListener(this);
		tr3 = (TableRow) findViewById(R.id.row3_1);
		tr3.setOnClickListener(this);
		tr4 = (TableRow) findViewById(R.id.row3_2);
		tr4.setOnClickListener(this);
		exit_btn = (Button) findViewById(R.id.exitbtn);
		exit_btn.setOnClickListener(this);
		textView1 = (TextView) findViewById(R.id.version);
		textView1.setText("��ǰ�汾  " + pi.versionName);
		textView1.setTypeface(typeFace);
		textView2 = (TextView) findViewById(R.id.cache);
		textView2.setText("���");
		textView2.setTypeface(typeFace);
		title = (TextView) findViewById(R.id.settitlename);
		title.setText("�� ��");
		title.setTypeface(typeFace);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back2:
			Intent intent = new Intent(SetActivity.this, Main.class);
			startActivity(intent);
			overridePendingTransition(R.anim.activity_new,
					R.anim.activity_finish);
			finish();
			break;
		case R.id.row1:
			// ����СT
			Intent intent2 = new Intent(SetActivity.this, Robot.class);
			startActivity(intent2);
			overridePendingTransition(R.anim.activity_new,
					R.anim.activity_finish);
			break;
		case R.id.row2:
			// About
			Intent intent3 = new Intent(SetActivity.this, About.class);
			startActivity(intent3);
			overridePendingTransition(R.anim.activity_new,
					R.anim.activity_finish);
			break;
		case R.id.row3_1:
			run();
			break;
		case R.id.row3_2:
			cleanDatabases();
			textView2.setText("�����");
			break;
		case R.id.exitbtn:
			finish();
			break;
		}
	}

	public void run() {
		try {
			Toast.makeText(this, "����°汾�����Ե�", Toast.LENGTH_SHORT).show();
			Toast.makeText(this, "��ǰ�汾Ϊ���°汾", Toast.LENGTH_SHORT).show();
			Thread.sleep(1000);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// �������
	private static void deleteFilesByDirectory(File directory) {
		if (directory != null && directory.exists() && directory.isDirectory()) {
			for (File item : directory.listFiles()) {
				item.delete();
			}
		}
	}

	// ����Image�ļ���
	public String getImageDir() {
		// SD��ָ���ļ���
		String sdcardPath = Environment.getExternalStorageDirectory()
				.toString();
		// ָ���ļ��д���·��
		File dir = new File(sdcardPath + File.separator + "TimeMemory"
				+ File.separator + "imgCache");
		if (dir.exists()) {
			return dir.toString();
		} else {
			dir.mkdirs();
			Log.d("BAG", "����·��������,");
			return dir.toString();
		}
	}

	public static void cleanDatabases() {
		deleteFilesByDirectory(new File(mFilePath));
	}
}
