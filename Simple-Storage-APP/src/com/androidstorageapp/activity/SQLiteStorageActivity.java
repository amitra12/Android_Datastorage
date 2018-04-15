package com.androidstorageapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.androidstorageapp.R;
import com.androidstorageapp.data.DatabaseHandler;
import com.androidstorageapp.utils.AndroidStorage;

/**
 * SQLite storage activity for storing data in SQLite database
 * 
 * @author DEVEN
 * 
 */
public class SQLiteStorageActivity extends Activity {
	private EditText messageEditText;
	private Button saveButton;
	private Button cancelButton;
	private DatabaseHandler db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sqlite_storage);
		getActionBar().setTitle(R.string.sqlite_storage_view);

		messageEditText = (EditText) findViewById(R.id.message);
		saveButton = (Button) findViewById(R.id.btn_save);
		cancelButton = (Button) findViewById(R.id.btn_cancel);

		saveButton.setOnClickListener(onClickListener);
		cancelButton.setOnClickListener(onClickListener);

	}

	/**
	 * On Click Listener
	 */
	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			int id = view.getId();
			switch (id) {
			/**
			 * save button clicked
			 */
			case R.id.btn_save:
				saveMessageToSQLite();
				break;
			/**
			 * cancel button clicked
			 */
			case R.id.btn_cancel:
				finish();
				break;

			default:
				break;
			}
		}
	};

	/**
	 * Function to save data in SQLite database
	 */
	public void saveMessageToSQLite() {
		String message = messageEditText.getText().toString();
		int count = 0;
		db = new DatabaseHandler(getApplicationContext());
		db.closeDB();
		db.openInternalDB();
		db.addMessage(message);
		count = db.getCursorCount();
		db.close();

		Intent returnIntent = new Intent();
		String itemStr = "SQLite " + count + ", "
				+ AndroidStorage.getCurrentDateTime();
		returnIntent.putExtra("result", itemStr);
		setResult(RESULT_OK, returnIntent);
		finish();

	}

}
