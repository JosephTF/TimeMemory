package adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.AbsoluteLayout;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.joseph.timememory.R;

public class MyAdapter extends BaseAdapter {
	private Context context;
	private Time time;
	private Cursor cursor;
	private RelativeLayout layout;
	private int width, screenwidth, daycountwidth, contenttvwidth, whitewidth;
	private TextView contenttv,timetv;
	private String content;

	// private KJBitmap kjBitmap ;

	public MyAdapter(Context context, Cursor cursor, Time time) {
		this.context = context;
		this.cursor = cursor;
		this.time = time;
	}

	@Override
	public int getCount() {
		return cursor.getCount();
	}

	@Override
	public Object getItem(int position) {
		return cursor.getPosition();
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// 声明界面各个控件
		LayoutInflater inflater = LayoutInflater.from(context);
		layout = (RelativeLayout) inflater.inflate(R.layout.cell, null);
		contenttv = (TextView) layout.findViewById(R.id.list_content);
		TextPaint paint = contenttv.getPaint();
		timetv = (TextView) layout.findViewById(R.id.list_time);
		TextPaint paint2 = timetv.getPaint();
		ImageView listbg = (ImageView) layout.findViewById(R.id.list_bg);
		// 声明游标，获取数据
		cursor.moveToPosition(position);
		content = cursor.getString(cursor.getColumnIndex("title"));
		contenttv.setText(content);
		int id = cursor.getInt(cursor.getColumnIndex("_id"));
		int color = cursor.getInt(cursor.getColumnIndex("color"));
		String begintime = cursor.getString(cursor.getColumnIndex("date"));
		// 输入日期的格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		// 换算纪念日日期
		Date date1 = null;
		try {
			date1 = sdf.parse(begintime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 获取现在日期
		Date date = new Date(System.currentTimeMillis());
		String endtime = sdf.format(date);
		Date date2 = null;
		try {
			date2 = sdf.parse(endtime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 计算日期差
		GregorianCalendar cal1 = new GregorianCalendar();
		GregorianCalendar cal2 = new GregorianCalendar();
		cal1.setTime(date1);
		cal2.setTime(date2);
		int dayCount = (int) ((cal2.getTimeInMillis() - cal1.getTimeInMillis()) / (1000 * 3600 * 24));// 从间隔毫秒变成间隔天数
		// 计算imageview的宽度（单位均为px）,screenwidth为屏幕宽度，marginlength为边距宽度和，initwidth为十天内固定宽度，width为动态宽度
		/* 根据手机的分辨率把DP转成PX像素 */
		final float scale = context.getResources().getDisplayMetrics().density;
		int marginlength = (int) (12 * scale + 0.5f);
		int initwidth = (int) (60 * scale + 0.5f);
		screenwidth = parent.getWidth();
		if (dayCount < 10) {
			width = initwidth;
		} else if (dayCount < 365) {
			float widthd = Float.parseFloat(Integer.toString(screenwidth
					- marginlength - initwidth)) / 356;
			width = (int) ((dayCount - 9) * widthd + initwidth);
		} else {
			width = screenwidth - marginlength;
		}
		timetv.setText(Integer.toString(dayCount));
		// 根据不同宽度的imageview安排contenttv的布局情况
		/** 计算右边空白的宽度whitewidth、文字所占宽度contenttvwidth **/
		whitewidth = screenwidth - marginlength - width;
		contenttvwidth = (int) paint.measureText(content);
		daycountwidth = (int) paint2.measureText(Integer.toString(dayCount));
		if (contenttvwidth < whitewidth) {
			contenttv.setTextColor(Color.GRAY);
			contenttv.setPadding(width + 10, 0, 0, 0);
		} else {
			if (whitewidth < width){
				contenttv.setTextColor(Color.WHITE);
				contenttv.setWidth(width-daycountwidth+60);
				contenttv.setPadding(daycountwidth + 40, 0, 0, 0);
			} else {
				contenttv.setTextColor(Color.GRAY);
				contenttv.setPadding(width + 10, 0, 0, 0);
			}

		}
		// 为控件赋参数
		int height = (int) (66 * scale + 0.5f);
		LayoutParams para = listbg.getLayoutParams();
		para.width = width;
		para.height = height;
		listbg.setLayoutParams(para);
		listbg.setBackgroundColor(color);
		return layout;
	}
}
