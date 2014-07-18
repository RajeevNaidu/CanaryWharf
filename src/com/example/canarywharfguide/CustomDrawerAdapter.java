package com.example.canarywharfguide;

import java.util.ArrayList;
import java.util.List;
 
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class CustomDrawerAdapter extends ArrayAdapter<DataItem> {

	Context context;
	List<DataItem> DataItemList;
	int layoutResID;

	public CustomDrawerAdapter(Context context, int layoutResourceID,
			List<DataItem> listItems) {
		super(context, layoutResourceID, listItems);
		this.context = context;
		this.DataItemList = listItems;
		this.layoutResID = layoutResourceID;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

	
		DataItemHolder drawerHolder;
		View view = convertView;

		if (view == null)
		{
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			view = inflater.inflate(layoutResID, parent, false);
			drawerHolder = new DataItemHolder(view);
			drawerHolder.subitem=(TextView)view.findViewById(R.id.tv_item);
			drawerHolder.title = (TextView) view.findViewById(R.id.tv_header);
			drawerHolder.icon=(ImageView)view.findViewById(R.id.icon);
			drawerHolder.header_layout=(LinearLayout)view.findViewById(R.id.header_layout);
			drawerHolder.item_layout=(LinearLayout)view.findViewById(R.id.item_layout);
            view.setTag(drawerHolder);

		} 
		else 
		{
			drawerHolder = (DataItemHolder) view.getTag();

		}
		
		DataItem dItem = (DataItem) this.DataItemList.get(position);

		
		if (dItem.getTilte() != null) {
			drawerHolder.header_layout.setVisibility(LinearLayout.VISIBLE);
			drawerHolder.item_layout.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.title.setText(dItem.getTilte());
		    Log.d("Getview","Passed4");
		}
		else {
			
			drawerHolder.header_layout.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.item_layout.setVisibility(LinearLayout.VISIBLE);
			drawerHolder.subitem.setText(dItem.getItem());
            //drawerHolder.icon.setImageDrawable(R.drawable.ic_launcher);//view.getResources().getDrawable(dItem.getIcon()));
       
			Log.d("Getview","Passed5");
		}
		return view;
	}

	private static class DataItemHolder {
		TextView title,subitem;
		ImageView icon;
		LinearLayout header_layout,item_layout;
		public DataItemHolder(View v)
		{
			title=(TextView)v.findViewById(R.id.tv_header);
			subitem=(TextView)v.findViewById(R.id.tv_item);
			icon=(ImageView)v.findViewById(R.id.icon);
			header_layout=(LinearLayout)v.findViewById(R.id.header_layout);
			item_layout=(LinearLayout)v.findViewById(R.id.item_layout);
		}
		
	}
}