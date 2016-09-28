package activity;

/**
 * author:王越
 * 导航页
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
	// 引导图片资源
	private static final int[] pics = new int[] { R.drawable.g1, R.drawable.g2,
			R.drawable.g3, R.drawable.g4 };

	// 底部小店图片
	private ImageView[] dots;

	// 记录当前选中位置
	private int currentIndex;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewpager);

		views = new ArrayList<View>();

		// 初始化引导图片列表
		for (int i = 0; i < pics.length; i++) {
			ImageView iv = new ImageView(this);
			iv.setImageResource(pics[i]);
			iv.setScaleType(ScaleType.FIT_XY);
			views.add(iv);
		}
		vp = (ViewPager) findViewById(R.id.viewpager);
		// 初始化Adapter
		vpAdapter = new ViewPagerAdapter(views);
		vp.setAdapter(vpAdapter);
		// 绑定回调
		vp.setOnPageChangeListener(this);
		// 初始化底部小点
		initDots();
		btn = (Button) findViewById(R.id.btn);
		alpha=new AlphaAnimation(0.0f, 1.0f);
		alpha.setDuration(2000);
		alpha2=new AlphaAnimation(1.0f, 0.0f);
		alpha2.setDuration(1000);
	}

	/*
	 * 初始化引导点
	 */
	private void initDots() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

		dots = new ImageView[pics.length];

		// 循环取得小点图片
		for (int i = 0; i < pics.length; i++) {
			dots[i] = (ImageView) ll.getChildAt(i);
			dots[i].setEnabled(true);// 都设为灰色
			dots[i].setOnClickListener(this);
			dots[i].setTag(i);// 设置位置tag，方便取出与当前位置对应
		}

		currentIndex = 0;
		dots[currentIndex].setEnabled(false);// 设置为白色，即选中状态
	}

	/*
	 * 设置当前的引导页
	 */
	private void setCurView(int position) {
		if (position < 0 || position >= pics.length) {
			return;
		}

		vp.setCurrentItem(position);
	}

	/*
	 * 这只当前引导小点的选中
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

	// 当滑动状态改变时调用
	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	// 当前页面被滑动时调用
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	// 新的页面被选中时调用
	@Override
	public void onPageSelected(int position) {
		// 设置底部小点选中状态
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

	// 点击启航按钮
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