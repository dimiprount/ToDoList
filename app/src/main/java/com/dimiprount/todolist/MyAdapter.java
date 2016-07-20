package com.dimiprount.todolist;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

	Context ctxt;
	ArrayList<Notepad> mydata;
	
	public MyAdapter(Context context, ArrayList<Notepad> data) {

		this.ctxt = context;
		this.mydata = data;
	}
	
	@Override
	public int getCount() {
		return mydata.size();
	}

	@Override
	public Notepad getItem(int pos) {
		return mydata.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh;
		if (convertView == null) {
			LayoutInflater li = (LayoutInflater) ctxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(R.layout.list_items, parent, false);	// How it will look like
			vh = new ViewHolder(convertView);
			convertView.setTag(vh);
		     
		} else {
			vh = (ViewHolder) convertView.getTag();
		}		
		Notepad notes = getItem(position);
		vh.showcell.setText(notes.getNote());
		notifyDataSetChanged();
		return convertView;	
	}

	class ViewHolder {
		TextView showcell;
		public ViewHolder(View v) {
			// TODO Auto-generated constructor stub
			showcell = (TextView) v.findViewById(R.id.tvlistitems);
		}
	}

	public void remove(Notepad selectedItem) {
		// TODO Auto-generated method stub
		mydata.remove(selectedItem);
		notifyDataSetChanged();
		
		}
	}
