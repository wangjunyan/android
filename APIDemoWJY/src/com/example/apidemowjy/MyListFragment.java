package com.example.apidemowjy;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyListFragment extends Fragment {
	public MyListFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Create, or inflate the Fragment's UI, and return it.
		// If this Fragment has no UI then return null.
		return inflater.inflate(R.layout.list_fragment, container, false);
	}
}
