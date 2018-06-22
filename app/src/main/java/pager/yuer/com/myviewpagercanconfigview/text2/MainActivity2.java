package pager.yuer.com.myviewpagercanconfigview.text2;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import pager.yuer.com.myviewpagercanconfigview.R;


public class MainActivity2 extends Activity {
	GridView gv_main;

	int[] icons = new int[]{R.drawable.icon, R.drawable.icon,R.drawable.icon,
			R.drawable.icon,R.drawable.icon,R.drawable.icon,
			R.drawable.icon,R.drawable.icon,R.drawable.icon};
	String[] titles = new String[]{"会员登记","统计分析","消费收银","积分兑换","会员级别","会员信息","会员列表","积分扣除","消费项目"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main2);
		gv_main=(GridView) findViewById(R.id.gv_main);
		gv_main.setAdapter(new MainItemAdapter(titles, icons, this));
	}
	class MainItemAdapter extends BaseAdapter{
		LayoutInflater mLayoutInflater = null;
		private List<Picture> pictures;
		public MainItemAdapter(String[] titles, int[] icons, Context context)
		{
			super();
			pictures = new ArrayList<Picture>();
			mLayoutInflater = LayoutInflater.from(context);
			for (int i = 0; i < icons.length; i++)
			{
				Picture picture = new Picture(titles[i], icons[i]);
				pictures.add(picture);
			}
		}

		@Override
		public int getCount() {
			if (null != pictures)
			{
				return pictures.size();
			} else
			{
				return 0;
			}
		}



		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder mHolder;
			if(convertView == null){
				mHolder = new ViewHolder();
				convertView = mLayoutInflater.inflate(R.layout.main_items, null);
				mHolder.tv_item = (TextView) convertView.findViewById(R.id.tv_item);
				mHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
				convertView.setTag(mHolder);
			}else{
				mHolder = (ViewHolder) convertView.getTag();
			}
			mHolder.tv_item.setText(pictures.get(position).title);
			mHolder.iv_icon.setImageResource(pictures.get(position).icon);

			return convertView;
		}

		class ViewHolder{
			ImageView iv_icon;
			TextView tv_item;
		}
		@Override
		public Object getItem(int position) {
			return pictures.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}




		class Picture{
			public String title;
			public int icon;
			public Picture(String title, int icon) {
				super();
				this.title = title;
				this.icon = icon;
			}


		}
	}


}
