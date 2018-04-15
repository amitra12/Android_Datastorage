package com.androidstorageapp.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.androidstorageapp.R;
import com.androidstorageapp.adapter.SavedItemListAdapter;
import com.androidstorageapp.data.DatabaseHandler;
import com.androidstorageapp.utils.Constants;

/**
 * 
 * @author DEVEN Home Activity that displays Preference & SQlite storage
 *         options.
 * 
 */
public class HomeActivity extends Activity {
	private Button preferenceButton;
	private Button sqliteButton;
	private Button closeButton;
	private ListView savedItemListView;
	private SavedItemListAdapter adapter;
	private List<String> itemList = new ArrayList<String>();
	private DatabaseHandler db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		getActionBar().setTitle(R.string.app_title);

		/**
		 * Retrieve previously stored data in list
		 */
		db = new DatabaseHandler(getApplicationContext());
		db.openInternalDB();
		itemList = db.getPastDataList();
		db.closeDB();

		savedItemListView = (ListView) findViewById(R.id.storage_list_view);
		preferenceButton = (Button) findViewById(R.id.btn_preference);
		sqliteButton = (Button) findViewById(R.id.btn_sqlite);
		closeButton = (Button) findViewById(R.id.btn_close);

		preferenceButton.setOnClickListener(onClickListener);
		sqliteButton.setOnClickListener(onClickListener);
		closeButton.setOnClickListener(onClickListener);

		adapter = new SavedItemListAdapter(itemList, getApplicationContext());
		savedItemListView.setAdapter(adapter);

	}

	/**
	 * On Click Listener
	 */
	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			int id = view.getId();
			Intent intent = null;
			switch (id) {
			/**
			 * Navigate user to Preference Storage Activity
			 */
			case R.id.btn_preference:
				intent = new Intent(HomeActivity.this,
						PreferenceStorageActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent,
						Constants.RequestCodes.PREFERENCE_REQUEST_CODE);
				break;
			/**
			 * Navigate user to SQLite Storage Activity
			 */
			case R.id.btn_sqlite:
				intent = new Intent(HomeActivity.this,
						SQLiteStorageActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent,
						Constants.RequestCodes.SQLITE_REQUEST_CODE);
				break;
			/**
			 * Close the app.
			 */
			case R.id.btn_close:
				finish();
				break;

			default:
				break;
			}
		}
	};

	/**
	 * Function to update past data list once the data is saved in either
	 * preference activity or sqlite activity
	 * 
	 * @param data
	 */
	public void updateLastDataList(Intent data) {
		String result = data.getStringExtra("result");
		db.openInternalDB();
		db.addPastData(result);
		itemList = db.getPastDataList();
		db.closeDB();
		adapter.setStringList(itemList);
	}

	/**
	 * Callback to update information when either Preference Activity or SQLite
	 * Activity is finished. (i.e. when user clicks on save button)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		/**
		 * Request code for Preference Storage Activity
		 */
		if (requestCode == Constants.RequestCodes.PREFERENCE_REQUEST_CODE) {

			if (resultCode == RESULT_OK) {
				updateLastDataList(data);
			}
		}/**
		 * Request code for SQLite Storage Activity
		 */
		else if (requestCode == Constants.RequestCodes.SQLITE_REQUEST_CODE) {

			if (resultCode == RESULT_OK) {
				updateLastDataList(data);
			}
		}

	}

}
