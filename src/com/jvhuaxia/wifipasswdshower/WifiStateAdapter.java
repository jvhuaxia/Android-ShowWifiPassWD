package com.jvhuaxia.wifipasswdshower;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WifiStateAdapter extends BaseAdapter {
    MainActivity mainActivity = null;
    List<WIFIState> items = null;

    public WifiStateAdapter(MainActivity mainActivity, List<WIFIState> items) {
	this.items = items;
	this.mainActivity = mainActivity;
    }

    @Override
    public int getCount() {
	return items.size();
    }

    @Override
    public Object getItem(int arg0) {
	return items.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
	return 0;
    }

    @Override
    public View getView(int arg0, View view, ViewGroup arg2) {
	WIFIState wifiState = items.get(arg0);
	if (view == null)
	    view = LayoutInflater.from(mainActivity).inflate(R.layout.wifistate_item, null);
	((TextView) view.findViewById(R.id.textViewWifiName)).setText(wifiState.getWifiName());
	((TextView) view.findViewById(R.id.textViewWifiPassWord)).setText(wifiState.getWifiPassword());
	return view;
    }

}
