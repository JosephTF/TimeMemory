package activity;
/**
 * author:��Խ
 * ������
 */
import adapter.MyAdapter;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.joseph.timememory.R;

import data.DBOpenHelper;

public class Main extends Activity implements OnClickListener,
		OnItemClickListener {
	private TextView tv;
	private ListView lv;
	private MyAdapter myAdpter;
	private ImageButton addbtn,setbtn;
	private DBOpenHelper dboh;
	private Cursor cursor;
	private Animation loadAnimation;
	private Intent intent;
	private SQLiteDatabase dbReader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		sign();
		initview();
		dboh = new DBOpenHelper(this);
		dbReader = dboh.getReadableDatabase();
	}

	/*
	 * �޸ĳ���ʹ�õ�ֵ
	 */
	public void sign() {
		SharedPreferences preferences = getSharedPreferences("isFirstUse",
				MODE_WORLD_READABLE);
		// ʵ����Editor����
		Editor editor = preferences.edit();
		// ��������
		editor.putBoolean("isFirstUse", false);
		// �ύ�޸�
		editor.commit();
	}

	/*
	 * �ؼ�����
	 */
	public void initview() {
		// ��"������ͤ�ڼ���"���屣����assets/fonts/Ŀ¼�£�����Typeface����
		Typeface typeFace = Typeface.createFromAsset(getAssets(),
				"fonts/FZLTHJW.TTF");
		tv = (TextView) findViewById(R.id.t_name);
		tv.setTypeface(typeFace);
		addbtn = (ImageButton) findViewById(R.id.add_btn);
		addbtn.setOnClickListener(this);
		setbtn=(ImageButton) findViewById(R.id.t_set);
		setbtn.setOnClickListener(this);
		lv = (ListView) findViewById(R.id.list);
		lv.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_btn:
			loadAnimation = AnimationUtils.loadAnimation(this, R.anim.add);
			addbtn.startAnimation(loadAnimation);
			intent = new Intent(Main.this, AddItem.class);
			startActivity(intent);
			overridePendingTransition(R.anim.activity_new,
					R.anim.activity_finish);
			finish();
			break;
		case R.id.t_set:
			intent = new Intent(Main.this, SetActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.activity_new,
					R.anim.activity_finish);
			finish();
			break;
		default:
			break;
		}
	}
	/**��ȡ����**/
	public void getDB() {
		cursor = dbReader.query("note", null, null, null, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			myAdpter = new MyAdapter(this, cursor, null);
			lv.setAdapter(myAdpter);
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getDB();
	}

	@Override
	/**ʵ���б�����Ӧ����**/
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		//cursor.moveToPosition(position);
		Intent i = new Intent(Main.this, SelectAct.class);
		//i.putExtra("position", cursor.getInt(cursor.getColumnIndex("_id")));
		i.putExtra("position", position);
		startActivity(i);
		overridePendingTransition(R.anim.activity_new,
				R.anim.activity_finish);
		finish();
	}
	protected void onDestroy() {
		super.onDestroy();
		// Ӧ�õ����һ��Activity�ر�ʱӦ�ͷ���Դ
		dboh.close();
		dbReader.close();
		cursor.close();
	}
}
