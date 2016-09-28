package activity;

import java.util.ArrayList;
import java.util.List;

import com.joseph.timememory.R;

import colorpick.ColorItem;
import adapter.ColorAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;

/**
 * @author 王越 颜色拾取页
 */
public class ColorPicker extends Activity {
	private static final String TAG = "Jo";
	private ListView mListView;
	private ColorAdapter mAdapter;
	private List<ColorItem> mList;
	private int colorValue, colorResoureValue;
	private TypedArray colorValues;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.colorpicker);
		setupViews();
		initData();
	}

	private void setupViews() {
		mListView = (ListView) findViewById(R.id.list);

	}

	private void initData() {
		mList = new ArrayList<ColorItem>();

		String[] colorName = getResources().getStringArray(R.array.color_names);
		int size = colorName.length;

		colorValues = getResources().obtainTypedArray(R.array.color_values);
		for (int i = 0; i < size; i++) {
			colorResoureValue = colorValues.getResourceId(i, 0);
			colorValue = getResources().getColor(colorResoureValue);
			ColorItem item = new ColorItem(colorName[i], colorValue);
			mList.add(item);
		}
		mAdapter = new ColorAdapter(this, mList);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				colorResoureValue = colorValues.getResourceId(position, 0);
				colorValue = getResources().getColor(colorResoureValue);
				//回传数据
				Intent intent = new Intent();
				intent.putExtra("color", colorValue);
				setResult(2,intent);
				finish();
			}
		});

	}
	
}
