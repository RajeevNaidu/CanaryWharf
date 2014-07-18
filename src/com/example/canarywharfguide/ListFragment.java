package com.example.canarywharfguide;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ListFragment extends Fragment
{
   public ListFragment()
   {
	   
   }

@Override
public View getView() {
	// TODO Auto-generated method stub
	return super.getView();
}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	View view=container;
	ListView details_list=(ListView)view.findViewById(R.id.listView1);
	
	return view;
}
   
}
