package com.example.apidemowjy;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

public class EarthquakeActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.earthquake_test);

		updateFromPreferences2();

		// Use the Search Manager to find the SearchableInfo related to this
		// Activity.
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchableInfo searchableInfo = searchManager
				.getSearchableInfo(getComponentName());

		// Bind the Activity's SearchableInfo to the Search View
		SearchView searchView = (SearchView) findViewById(R.id.searchView);
		searchView.setSearchableInfo(searchableInfo);
	}

	static final private int MENU_PREFERENCES = Menu.FIRST + 1;
	static final private int MENU_UPDATE = Menu.FIRST + 2;
	private static final int SHOW_PREFERENCES = 1;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		menu.add(0, MENU_PREFERENCES, Menu.NONE, R.string.menu_preferences);

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case (MENU_PREFERENCES): {
			// Intent i = new Intent(this, Preferences2Activity.class);
			// startActivityForResult(i, SHOW_PREFERENCES);
			Class c = Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB ? PreferencesActivity.class
					: FragmentPreferencesActivity.class;
			Intent i = new Intent(this, c);

			startActivityForResult(i, SHOW_PREFERENCES);
			return true;
		}
		}
		return false;
	}

	public int minimumMagnitude = 0;
	public boolean autoUpdateChecked = false;
	public int updateFreq = 0;

	private void updateFromPreferences() {
		Context context = getApplicationContext();
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);

		int minMagIndex = prefs.getInt(PreferencesActivity.PREF_MIN_MAG_INDEX,
				0);
		if (minMagIndex < 0)
			minMagIndex = 0;

		int freqIndex = prefs.getInt(
				PreferencesActivity.PREF_UPDATE_FREQ_INDEX, 0);
		if (freqIndex < 0)
			freqIndex = 0;

		autoUpdateChecked = prefs.getBoolean(
				PreferencesActivity.PREF_AUTO_UPDATE, false);

		Resources r = getResources();
		// Get the option values from the arrays.
		String[] minMagValues = r.getStringArray(R.array.magnitude);
		String[] freqValues = r.getStringArray(R.array.update_freq_values);

		// Convert the values to ints.
		minimumMagnitude = Integer.valueOf(minMagValues[minMagIndex]);
		updateFreq = Integer.valueOf(freqValues[freqIndex]);
	}

	private void updateFromPreferences2() {
		Context context = getApplicationContext();
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);

		minimumMagnitude = Integer.parseInt(prefs.getString(
				Preferences2Activity.PREF_MIN_MAG, "3"));
		updateFreq = Integer.parseInt(prefs.getString(
				Preferences2Activity.PREF_UPDATE_FREQ, "60"));

		autoUpdateChecked = prefs.getBoolean(
				Preferences2Activity.PREF_AUTO_UPDATE, false);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == SHOW_PREFERENCES) {
			// if (resultCode == Activity.RESULT_OK) {
			updateFromPreferences2();
			FragmentManager fm = getFragmentManager();
			final EarthquakeListFragment earthquakeList = (EarthquakeListFragment) fm
					.findFragmentById(R.id.EarthquakeListFragment);
			earthquakeList.refreshEarthquakes();
			/*
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					earthquakeList.refreshEarthquakes();
				}

			});
			t.start();
			*/
			// }
		}
	}
}
