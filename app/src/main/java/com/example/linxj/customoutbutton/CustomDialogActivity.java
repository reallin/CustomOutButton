package com.example.linxj.customoutbutton;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
public class CustomDialogActivity extends Activity {

	private List<String> dataList;
	private ListView listView;
	private Button cancelButton;
	private String[] dataSource;
	private int flag;
	private static final float RATIO = 5/10f;
	private String templeContent = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.setContentView(R.layout.global_popwin_main);
		dataList = new ArrayList<String>();
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.width = LayoutParams.FILL_PARENT;
		lp.gravity = Gravity.BOTTOM;
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		getWindow().setAttributes(lp);
		initViews();
	}
	
	private void initViews(){
		Bundle bundle = getIntent().getExtras();
		flag = bundle.getInt("flag");
		initData(flag);
		cancelButton = (Button) findViewById(R.id.cancel_but);
		listView = (ListView) findViewById(R.id.select);
		listView.setAdapter(new SessionBaseAdapter());
		listView.setOnItemClickListener(new OnItemClickListenerImpl());
		setPopWindowSize(flag);
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putInt("flag", flag);
				bundle.putString("content", "");
				intent.putExtras(bundle);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}
	
	private void initData(int flag){
		dataList = new ArrayList<String>();
		switch (flag) {
			case 0:
				Bundle bundle = getIntent().getExtras();
				dataSource = bundle.getStringArray("project_year");
			case 1:
				dataSource = getResources().getStringArray(R.array.startYear);
			break;
		}
		for (int i = 0; i < dataSource.length; i++) {
			dataList.add(dataSource[i]);
		}
	}
	
	private void setPopWindowSize(int flag){
		switch (flag) {
			case 0:
			case 1:
				float screenHeight = this.getResources().getDisplayMetrics().heightPixels;
				LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) listView.getLayoutParams();
				layoutParams.height = (int) (screenHeight * RATIO);
				listView.setLayoutParams(layoutParams);
			break;
		}
	}
	
	final class ViewHolder {
		public ImageView image;
        public TextView itemName;
        public RelativeLayout layout;
    }
	
	class SessionBaseAdapter extends BaseAdapter {
		
		public SessionBaseAdapter() {}
		
		@Override
		public int getCount() {
			return dataList.size();
		}

		@Override
		public Object getItem(int postion) {
			return postion;
		}

		@Override
		public long getItemId(int postion) {
			return postion;
		}

		@Override
		public View getView(final int postion, View convertView, ViewGroup parent) {
			final ViewHolder holder = new ViewHolder();
			convertView = LayoutInflater.from(CustomDialogActivity.this).inflate(R.layout.global_popwin_listitem, null);
            holder.layout = (RelativeLayout) convertView.findViewById(R.id.type1_layout_1);
            holder.image = (ImageView) convertView.findViewById(R.id.icon);
            holder.itemName = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
            holder.itemName.setText(dataList.get(postion));
            holder.layout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {
					holder.itemName.setTextColor(Color.parseColor("#F56A55"));
					holder.image.setVisibility(View.VISIBLE);
					templeContent = dataList.get(postion);
					startIntent();
				}
			});
			return convertView;
		}
	}
	
	private void startIntent() {

		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putInt("flag", flag);
		bundle.putString("content", templeContent);
		intent.putExtras(bundle);	
		setResult(RESULT_OK, intent);
		this.finish();
		}

	public class OnItemClickListenerImpl implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int postion, long id) {
			ViewHolder holder = (ViewHolder) view.getTag();
			holder.image.setVisibility(View.VISIBLE);
			holder.itemName.setTextColor(Color.parseColor("#F56A55"));
			templeContent = dataList.get(postion);
			startIntent();
		}
	}
	@Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && isOutOfBounds(CustomDialogActivity.this, event)) {
        	startIntent();
            return true;
        }  
        return super.onTouchEvent(event);
    }  
  
    private boolean isOutOfBounds(Activity context, MotionEvent event) {
        final int x = (int) event.getX();
        final int y = (int) event.getY();
        final int slop = ViewConfiguration.get(context).getScaledWindowTouchSlop();
        final View decorView = context.getWindow().getDecorView();
        return (x < -slop) || (y < -slop)|| (x > (decorView.getWidth() + slop))|| (y > (decorView.getHeight() + slop));
    }
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		startIntent();
	}
}
