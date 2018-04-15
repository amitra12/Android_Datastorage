package com.androidstorageapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Application level class to initialize and maintain various application life
 * cycle specific details
 */
public class ApplicationEx extends android.app.Application {
	/** Shared Preference to store Preference Data */
	public static SharedPreferences sharedPreference;
	/** Application Context */
	public static Context context;

	/**
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		sharedPreference = context.getSharedPreferences(context.getResources()
				.getString(R.string.book_details), context.MODE_WORLD_READABLE);
	}

}
