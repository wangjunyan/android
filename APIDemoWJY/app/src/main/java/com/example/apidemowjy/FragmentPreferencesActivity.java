package com.example.apidemowjy;

import java.util.List;

import android.preference.PreferenceActivity;

public class FragmentPreferencesActivity extends PreferenceActivity {
	@Override
	public void onBuildHeaders(List<Header> target) {
		loadHeadersFromResource(R.xml.preference_headers, target);
	}
}
