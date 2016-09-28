package activity;

/**
 * author:��Խ
 * ����ҳ
 */
import java.util.ArrayList;
import java.util.List;

import adapter.ViewPagerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.joseph.timememory.R;

public class GuideViewPager extends Activity implements OnClickListener,
		OnPageChangeListener {

	private ViewPager vp;
	private ViewPagerAdapter vpAdapter;
	private List<View> views;
	private Button btn;
	private Animation alpha,alpha2;
	// ����ͼƬ��Դ
	private static final int[] pics = new int[] { R.drawable.g1, R.drawable.g2,
			R.drawable.g3, R.drawable.g4 };

	// �ײ�С��ͼƬ
	private ImageView[] dots;

	// ��¼��ǰѡ��λ��
	private int currentIndex;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewpager);

		views = new ArrayList<View>();

		// ��ʼ������ͼƬ�б�
		for (int i = 0; i < pics.length; i++) {
			ImageView iv = new ImageView(this);
			iv.setImageResource(pics[i]);
			iv.setScaleType(ScaleType.FIT_XY);
			views.add(iv);
		}
		vp = (ViewPager) findViewById(R.id.viewpager);
		// ��ʼ��Adapter
		vpAdapter = new ViewPagerAdapter(views);
		vp.setAdapter(vpAdapter);
		// �󶨻ص�
		vp.setOnPageChangeListener(this);
		// ��ʼ���ײ�С��
		initDots();
		btn = (Button) findViewById(R.id.btn);
		alpha=new AlphaAnimation(0.0f, 1.0f);
		alpha.setDuration(2000);
		alpha2=new AlphaAnimation(1.0f, 0.0f);
		alpha2.setDuration(1000);
	}

	/*
	 * ��ʼ��������
	 */
	private void initDots() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

		dots = new ImageView[pics.length];

		// ѭ��ȡ��С��ͼƬ
		for (int i = 0; i < pics.length; i++) {
			dots[i] = (ImageView) ll.getChildAt(i);
			dots[i].setEnabled(true);// ����Ϊ��ɫ
			dots[i].setOnClickListener(this);
			dots[i].setTag(i);// ����λ��tag������ȡ���뵱ǰλ�ö�Ӧ
		}

		currentIndex = 0;
		dots[currentIndex].setEnabled(false);// ����Ϊ��ɫ����ѡ��״̬
	}

	/*
	 * ���õ�ǰ������ҳ
	 */
	private void setCurView(int position) {
		if (position < 0 || position >= pics.length) {
			return;
		}

		vp.setCurrentItem(position);
	}

	/*
	 * ��ֻ��ǰ����С���ѡ��
	 */
	private void setCurDot(int position) {
		if (position < 0 || position > pics.length - 1
				|| currentIndex == position) {
			return;
		}

		dots[position].setEnabled(false);
		dots[currentIndex].setEnabled(true);

		currentIndex = position;
	}

	// ������״̬�ı�ʱ����
	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	// ��ǰҳ�汻����ʱ����
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	// �µ�ҳ�汻ѡ��ʱ����
	@Override
	public void onPageSelected(int position) {
		// ���õײ�С��ѡ��״̬
		setCurDot(position);
		if (position == (pics.length - 1)) {
			btn.setVisibility(0);
			btn.startAnimation(alpha);
		}else{
			if(btn.getVisibility()!=8){
				btn.startAnimation(alpha2);
				btn.setVisibility(8);
			}
		}
	}

	// ���������ť
	public void start(View view) {
		Intent intent = new Intent(GuideViewPager.this, Main.class);
		startActivity(intent);
		GuideViewPager.this.finish();
	}

	@Override
	public void onClick(View v) {
		int position = (Integer) v.getTag();
		setCurView(position);
		setCurDot(position);
	}
}