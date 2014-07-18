package com.example.canarywharfguide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.MapFragment;



public class mapFragment extends MapFragment
{
MapFragment map_frag;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view;
		view=inflater.inflate(R.layout.map_fragment, container, false);
		
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	

}
