package activity;

/**
 * author:王越
 * 欢迎页
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
	/**创建延迟线程**/
	private void newRun() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// 读取SharedPreferences中需要的数据
				SharedPreferences preferences = getSharedPreferences(
						"isFirstUse", MODE_WORLD_READABLE);
				isFirstIn = preferences.getBoolean("isFirstUse", true);
				/**
				 * 如果用户不是第一次使用则直接调转到显示界面,否则调转到引导界面
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
	 * 获取版本号并显示
	 */
	private void getVersionName() {
		PackageManager pm = getPackageManager();
		try {
			PackageInfo pi = pm.getPackageInfo(getPackageName().toString(), 0);
			TextView versionNumber = (TextView) findViewById(R.id.versionNumber);
			versionNumber.setText("版本号：" + pi.versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}
}
