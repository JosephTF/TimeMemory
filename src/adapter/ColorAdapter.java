package adapter;

import java.util.List;

import colorpick.ColorItem;

import com.joseph.timememory.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ColorAdapter extends BaseAdapter{

	private Context mContext;
	private LayoutInflater mInflater;
	
	private List<ColorItem> mList;
	
	public ColorAdapter(Context context, List<ColorItem> list){
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
		mList = list;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder;
		if (convertView == null){
			convertView = mInflater.inflate(R.layout.color_adapter_layout, null);
			viewHolder = new ViewHolder();
			viewHolder.ll_color = (LinearLayout) convertView.findViewById(R.id.ll_color);
			viewHolder.tv_color = (TextView) convertView.findViewById(R.id.tv_color);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		ColorItem item = mList.get(position);
		String text = item.name;
		int color = item.color;
		
		viewHolder.ll_color.setBackgroundColor(color);
		viewHolder.tv_color.setText(text);
		
		
		return convertView;
		
	}

	
	static class  ViewHolder{
		public LinearLayout ll_color;
		public TextView tv_color;
	}
}
