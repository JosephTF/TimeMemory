package activity;

import com.joseph.timememory.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

public class PicSelect extends Activity implements OnClickListener{
	private ImageLoader loader;
	private ImageView backbtn, iv_img1, iv_img2,iv_img3,iv_img4,iv_img5,iv_img6,iv_img7,iv_img8,iv_img9,iv_img10,iv_img11,iv_img12,iv_img13,iv_img14,iv_img15,iv_img16;
	private ImageLoadingListener ILL;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.picselect);
		loader = ImageLoader.getInstance();
		backbtn = (ImageView) findViewById(R.id.back3);
		backbtn.setOnClickListener(this);
		iv_img1 = (ImageView) findViewById(R.id.iv_img1);
		iv_img1.setOnClickListener(this);
		iv_img2 = (ImageView) findViewById(R.id.iv_img2);
		iv_img2.setOnClickListener(this);
		iv_img3 = (ImageView) findViewById(R.id.iv_img3);
		iv_img3.setOnClickListener(this);
		iv_img4 = (ImageView) findViewById(R.id.iv_img4);
		iv_img4.setOnClickListener(this);
		iv_img5 = (ImageView) findViewById(R.id.iv_img5);
		iv_img5.setOnClickListener(this);
		iv_img6 = (ImageView) findViewById(R.id.iv_img6);
		iv_img6.setOnClickListener(this);
		iv_img7 = (ImageView) findViewById(R.id.iv_img7);
		iv_img7.setOnClickListener(this);
		iv_img8 = (ImageView) findViewById(R.id.iv_img8);
		iv_img8.setOnClickListener(this);
		iv_img9 = (ImageView) findViewById(R.id.iv_img9);
		iv_img9.setOnClickListener(this);
		iv_img10 = (ImageView) findViewById(R.id.iv_img10);
		iv_img10.setOnClickListener(this);
		iv_img11 = (ImageView) findViewById(R.id.iv_img11);
		iv_img11.setOnClickListener(this);
		iv_img12 = (ImageView) findViewById(R.id.iv_img12);
		iv_img12.setOnClickListener(this);
		iv_img13 = (ImageView) findViewById(R.id.iv_img13);
		iv_img13.setOnClickListener(this);
		iv_img14 = (ImageView) findViewById(R.id.iv_img14);
		iv_img14.setOnClickListener(this);
		iv_img15 = (ImageView) findViewById(R.id.iv_img15);
		iv_img15.setOnClickListener(this);
		iv_img16 = (ImageView) findViewById(R.id.iv_img16);
		iv_img16.setOnClickListener(this);
		intent = new Intent(PicSelect.this, AddItem.class);
		ILL = new ImageLoadingListener() {

			@Override
			public void onLoadingStarted(String arg0, View arg1) {
				Log.i("info", "onLoadingStarted");
			}

			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
				Log.i("info", "onLoadingFailed");
			}

			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				Log.i("info", "onLoadingComplete");
			}

			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
				Log.i("info", "onLoadingCancelled");
			}
		};
		loader.displayImage(
				"http://b.zol-img.com.cn/sjbizhi/images/9/800x1280/1456989039621.jpg",
				iv_img1, ILL);
		loader.displayImage(
				"http://b.zol-img.com.cn/sjbizhi/images/9/800x1280/1456989115355.jpg",
				iv_img2, ILL);
		loader.displayImage(
				"http://b.zol-img.com.cn/sjbizhi/images/4/800x1280/136808677655.jpg",
				iv_img3, ILL);
		loader.displayImage(
				"http://b.zol-img.com.cn/sjbizhi/images/8/800x1280/1430732272542.jpg",
				iv_img4, ILL);
		loader.displayImage(
				"http://b.zol-img.com.cn/sjbizhi/images/8/800x1280/1430732258402.jpg",
				iv_img5, ILL);
		loader.displayImage(
				"http://b.zol-img.com.cn/sjbizhi/images/8/800x1280/1430732340216.jpg",
				iv_img6, ILL);
		loader.displayImage(
				"http://b.zol-img.com.cn/sjbizhi/images/7/800x1280/1413181943780.jpg",
				iv_img7, ILL);
		loader.displayImage(
				"http://b.zol-img.com.cn/sjbizhi/images/9/800x1280/1449241688502.jpg",
				iv_img8, ILL);
		loader.displayImage(
				"http://b.zol-img.com.cn/sjbizhi/images/8/1080x1920/1446122150952.jpg",
				iv_img9, ILL);
		loader.displayImage(
				"http://b.zol-img.com.cn/sjbizhi/images/8/800x1280/142252064765.jpg",
				iv_img10, ILL);
		loader.displayImage(
				"http://b.zol-img.com.cn/sjbizhi/images/8/800x1280/1422520694926.jpg",
				iv_img11, ILL);
		loader.displayImage(
				"http://b.zol-img.com.cn/sjbizhi/images/9/800x1280/1456296636657.jpg",
				iv_img12, ILL);
		loader.displayImage(
				"http://b.zol-img.com.cn/sjbizhi/images/6/800x1280/1394618362431.jpg",
				iv_img13, ILL);
		loader.displayImage(
				"http://b.zol-img.com.cn/sjbizhi/images/8/800x1280/1445497304601.jpg",
				iv_img14, ILL);
		loader.displayImage(
				"http://b.zol-img.com.cn/sjbizhi/images/7/800x1280/1411544481792.jpg",
				iv_img15, ILL);
		loader.displayImage(
				"http://b.zol-img.com.cn/sjbizhi/images/5/800x1280/1374055259686.jpg",
				iv_img16, ILL);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back3:
			finish();
			break;
		case R.id.iv_img1:
			intent = new Intent(PicSelect.this, AddItem.class);
			intent.putExtra("picpath",
					"http://b.zol-img.com.cn/sjbizhi/images/9/800x1280/1456989039621.jpg");
			intent();
		case R.id.iv_img2:
			intent = new Intent(PicSelect.this, AddItem.class);
			intent.putExtra("picpath",
					"http://b.zol-img.com.cn/sjbizhi/images/9/800x1280/1456989115355.jpg");
			intent();
		case R.id.iv_img3:
			intent = new Intent(PicSelect.this, AddItem.class);
			intent.putExtra("picpath",
					"http://b.zol-img.com.cn/sjbizhi/images/4/800x1280/136808677655.jpg");
			intent();
		case R.id.iv_img4:
			intent = new Intent(PicSelect.this, AddItem.class);
			intent.putExtra("picpath",
					"http://b.zol-img.com.cn/sjbizhi/images/8/800x1280/1430732272542.jpg");
			intent();
		case R.id.iv_img5:
			intent = new Intent(PicSelect.this, AddItem.class);
			intent.putExtra("picpath",
					"http://b.zol-img.com.cn/sjbizhi/images/8/800x1280/1430732258402.jpg");
			intent();
		case R.id.iv_img6:
			intent = new Intent(PicSelect.this, AddItem.class);
			intent.putExtra("picpath",
					"http://b.zol-img.com.cn/sjbizhi/images/8/800x1280/1430732340216.jpg");
			intent();
		case R.id.iv_img7:
			intent = new Intent(PicSelect.this, AddItem.class);
			intent.putExtra("picpath",
					"http://b.zol-img.com.cn/sjbizhi/images/7/800x1280/1413181943780.jpg");
			intent();
		case R.id.iv_img8:
			intent = new Intent(PicSelect.this, AddItem.class);
			intent.putExtra("picpath",
					"http://b.zol-img.com.cn/sjbizhi/images/9/800x1280/1449241688502.jpg");
			intent();
		case R.id.iv_img9:
			intent = new Intent(PicSelect.this, AddItem.class);
			intent.putExtra("picpath",
					"http://b.zol-img.com.cn/sjbizhi/images/8/1080x1920/1446122150952.jpg");
			intent();
		case R.id.iv_img10:
			intent = new Intent(PicSelect.this, AddItem.class);
			intent.putExtra("picpath",
					"http://b.zol-img.com.cn/sjbizhi/images/8/800x1280/142252064765.jpg");
			intent();
		case R.id.iv_img11:
			intent = new Intent(PicSelect.this, AddItem.class);
			intent.putExtra("picpath",
					"http://b.zol-img.com.cn/sjbizhi/images/8/800x1280/1422520694926.jpg");
			intent();
		case R.id.iv_img12:
			intent = new Intent(PicSelect.this, AddItem.class);
			intent.putExtra("picpath",
					"http://b.zol-img.com.cn/sjbizhi/images/9/800x1280/1456296636657.jpg");
			intent();
		case R.id.iv_img13:
			// String
			// a="http://b.zol-img.com.cn/sjbizhi/images/9/800x1280/1461658850616.jpg";
			intent = new Intent(PicSelect.this, AddItem.class);
			intent.putExtra("picpath",
					"http://b.zol-img.com.cn/sjbizhi/images/6/800x1280/1394618362431.jpg");
			intent();
		case R.id.iv_img14:
			intent = new Intent(PicSelect.this, AddItem.class);
			intent.putExtra("picpath",
					"http://b.zol-img.com.cn/sjbizhi/images/8/800x1280/1445497304601.jpg");
			intent();
		case R.id.iv_img15:
			intent = new Intent(PicSelect.this, AddItem.class);
			intent.putExtra("picpath",
					"http://b.zol-img.com.cn/sjbizhi/images/7/800x1280/1411544481792.jpg");
			intent();
		case R.id.iv_img16:
			intent = new Intent(PicSelect.this, AddItem.class);
			intent.putExtra("picpath",
					"http://b.zol-img.com.cn/sjbizhi/images/5/800x1280/1374055259686.jpg");
			intent();
		default:
			break;
		}
	}

	public void intent() {
		setResult(4, intent);
		finish();
	}
}
