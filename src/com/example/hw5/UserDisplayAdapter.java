package com.example.hw5;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseUser;

public class UserDisplayAdapter extends ArrayAdapter<ParseUser> {

	private List<ParseUser> objects;
	private Context context;
	private int resource;
	public UserDisplayAdapter(Context context, int resource, List<ParseUser> objects) {
		super(context, resource, objects);
		this.context = context;
		this.objects = objects;
		this.resource = resource;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(resource, parent, false);
		}
		String name = (String)objects.get(position).get("firstName") +  " " + (String)objects.get(position).get("lastName");
		((TextView) convertView.findViewById(android.R.id.text1)).setText(name);
		return convertView;
	}

}
