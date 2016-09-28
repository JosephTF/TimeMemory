package activity;

import com.joseph.timememory.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class About extends Activity implements OnClickListener {
	private ImageButton back_btn;

	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		back_btn = (ImageButton) findViewById(R.id.back2);
		back_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				Intent intent = new Intent(About.this, SetActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.activity_new,
						R.anim.activity_finish);
			}
		});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
