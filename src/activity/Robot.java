package activity;

import httpdata.HttpData;
import httpdata.HttpGetDataListener;
import httpdata.ListData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import adapter.TextAdapter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.joseph.timememory.R;

public class Robot extends Activity implements HttpGetDataListener,
		OnClickListener {
	private HttpData httpData;
	private List<ListData> lists;
	private ListView lv;
	private EditText sendtext;
	private Button send_btn;
	private String content_str;
	private TextAdapter adapter;
	private String[] welcome_array;
	private double currentTime, oldTime = 0;
	private ImageButton back_btn;
	private TextView title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.robot);
		initView();
	}

	private void initView() {
		back_btn = (ImageButton) findViewById(R.id.back2);
		back_btn.setOnClickListener(this);
		lv = (ListView) findViewById(R.id.lv);
		sendtext = (EditText) findViewById(R.id.sendText);
		send_btn = (Button) findViewById(R.id.send_btn);
		lists = new ArrayList<ListData>();
		send_btn.setOnClickListener(this);
		adapter = new TextAdapter(lists, this);
		lv.setAdapter(adapter);
		ListData listData;
		listData = new ListData(getRandomWelcomeTips(), ListData.RECEIVER,
				getTime());
		lists.add(listData);
		title = (TextView) findViewById(R.id.settitlename);
		title.setText("您正与小T聊天中...");
		Typeface typeFace = Typeface.createFromAsset(getAssets(),
				"fonts/FZLTHJW.TTF");
		title.setTypeface(typeFace);
	}

	private String getRandomWelcomeTips() {
		String welcome_tip = null;
		welcome_array = this.getResources()
				.getStringArray(R.array.welcome_tips);
		int index = (int) (Math.random() * (welcome_array.length - 1));
		welcome_tip = welcome_array[index];
		return welcome_tip;
	}

	@Override
	public void getDataUrl(String data) {
		parseText(data);
	}

	public void parseText(String str) {
		try {
			JSONObject jb = new JSONObject(str);
			ListData listData;
			listData = new ListData(jb.getString("text"), ListData.RECEIVER,
					getTime());
			lists.add(listData);
			adapter.notifyDataSetChanged();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.send_btn:
			getTime();
			content_str = sendtext.getText().toString();
			sendtext.setText("");
			String dropk = content_str.replace(" ", "");
			String droph = dropk.replace("\n", "");
			ListData listData;
			listData = new ListData(content_str, ListData.SEND, getTime());
			lists.add(listData);
			adapter.notifyDataSetChanged();
			httpData = (HttpData) new HttpData(
					"http://www.tuling123.com/openapi/api?key=f7ad10d975a6cef34e0d5142c0e63204&info="
							+ droph, this).execute();
			break;
		case R.id.back2:
			Intent intent = new Intent(Robot.this, SetActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.activity_new,
					R.anim.activity_finish);
			finish();
		default:
			break;
		}
	}

	private String getTime() {
		currentTime = System.currentTimeMillis();
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		Date curDate = new Date();
		String str = format.format(curDate);
		if (currentTime - oldTime >= 500) {
			oldTime = currentTime;
			return str;
		} else {
			return "";
		}
	}
}
