package activity;

/**
 * author:��Խ
 * ��ӭҳ
 */
import com.joseph.timememory.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.TextView;

public class Welcome extends Activity {
	private boolean isFirstIn;
	Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		getVersionName();
		newRun();
	}
	/**�����ӳ��߳�**/
	private void newRun() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// ��ȡSharedPreferences����Ҫ������
				SharedPreferences preferences = getSharedPreferences(
						"isFirstUse", MODE_WORLD_READABLE);
				isFirstIn = preferences.getBoolean("isFirstUse", true);
				/**
				 * ����û����ǵ�һ��ʹ����ֱ�ӵ�ת����ʾ����,�����ת����������
				 */
				if (isFirstIn) {
					startActivity(new Intent(Welcome.this, GuideViewPager.class));
				} else {
					startActivity(new Intent(Welcome.this, Main.class));
				}
				Welcome.this.finish();
			}
		}, 2500);
	}

	/*
	 * ��ȡ�汾�Ų���ʾ
	 */
	private void getVersionName() {
		PackageManager pm = getPackageManager();
		try {
			PackageInfo pi = pm.getPackageInfo(getPackageName().toString(), 0);
			TextView versionNumber = (TextView) findViewById(R.id.versionNumber);
			versionNumber.setText("�汾�ţ�" + pi.versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}
}
