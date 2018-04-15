package com.androidstorageapp.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidstorageapp.R;

/**
 * 
 * @author DEVEN List Adapter to handle String list
 */
public class SavedItemListAdapter extends BaseAdapter {
	private List<String> StringList = new ArrayList<String>();
	private Context context;

	/**
	 * 
	 * @param StringList
	 * @param applicationContext
	 */
	public SavedItemListAdapter(List<String> StringList,
			Context applicationContext) {
		this.StringList = StringList;
		this.context = applicationContext;
	}

	/**
	 * Update the String list
	 * 
	 * @param StringList
	 */
	public void setStringList(List<String> StringList) {
		this.StringList = StringList;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return StringList.size();
	}

	@Override
	public Object getItem(int position) {
		return StringList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * get view method that handles iterations of list
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ContactsViewHolder contactsViewHolder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.saved_item_list_row, null);
			contactsViewHolder = new ContactsViewHolder();
			contactsViewHolder.name = (TextView) convertView
					.findViewById(R.id.item);
		} else {
			contactsViewHolder = (ContactsViewHolder) convertView.getTag();
		}
		String item = StringList.get(position);

		contactsViewHolder.name.setText(item);

		convertView.setTag(R.id.id_name, "");
		convertView.setTag(contactsViewHolder);
		return convertView;
	}

	private class ContactsViewHolder {
		TextView name;
	}
}
