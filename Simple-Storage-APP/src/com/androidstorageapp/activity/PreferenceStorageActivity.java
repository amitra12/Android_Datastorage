package com.androidstorageapp.activity;

import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.androidstorageapp.ApplicationEx;
import com.androidstorageapp.R;
import com.androidstorageapp.utils.AndroidStorage;

/**
 * Preference storage activity for storing data in shared preference
 * 
 * @author DEVEN
 * 
 */
public class PreferenceStorageActivity extends Activity {
	private EditText bookNameEditText;
	private EditText bookAuthorEditText;
	private EditText bookDescriptionEditText;
	private Button saveButton;
	private Button cancelButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preference_storage);
		getActionBar().setTitle(R.string.preference_storage_view);

		bookNameEditText = (EditText) findViewById(R.id.book_name_value);
		bookAuthorEditText = (EditText) findViewById(R.id.book_author_value);
		bookDescriptionEditText = (EditText) findViewById(R.id.book_description_value);

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
			 * Save Button Clicked
			 */
			case R.id.btn_save:
				savePreference();
				break;
			/**
			 * Cancel Button Clicked
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
	 * Function to save data in Shared Preferences.
	 */
	public void savePreference() {
		String bookName = bookNameEditText.getText().toString();
		String authorName = bookAuthorEditText.getText().toString();
		String description = bookDescriptionEditText.getText().toString();
		int count = 0;

		Set<String> strSet = new HashSet<String>();
		strSet.add(bookName);
		strSet.add(authorName);
		strSet.add(description);

		SharedPreferences.Editor prefEditor = ApplicationEx.sharedPreference
				.edit();
		prefEditor.putStringSet(bookName, strSet).commit();
		if (ApplicationEx.sharedPreference != null) {
			count = ApplicationEx.sharedPreference.getInt(getResources()
					.getString(R.string.count), 0);
		}
		count = count + 1;
		prefEditor.putInt(getResources().getString(R.string.count), count)
				.commit();

		Intent returnIntent = new Intent();
		String itemStr = "Save Preference " + count + ", "
				+ AndroidStorage.getCurrentDateTime();
		returnIntent.putExtra("result", itemStr);
		setResult(RESULT_OK, returnIntent);
		finish();

	}
}
