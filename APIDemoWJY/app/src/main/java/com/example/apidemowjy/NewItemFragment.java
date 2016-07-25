package com.example.apidemowjy;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class NewItemFragment extends Fragment {
	private OnNewItemAddedListener onNewItemAddedListener;

	public interface OnNewItemAddedListener {
		public void onNewItemAdded(String newItem);
	}

	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);
		Log.d(MyApplication.TAG, "fragment - onAttach");
		try {
			onNewItemAddedListener = (OnNewItemAddedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnNewItemAddedListener");
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(MyApplication.TAG, "fragment - onCreate");
		// Initialize the Fragment.
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(MyApplication.TAG, "fragment - onCreateView");
		View view = inflater.inflate(R.layout.new_item_fragment, container,
				false);

		final EditText myEditText = (EditText) view
				.findViewById(R.id.myEditText);

		myEditText.setOnKeyListener(new View.OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN)
					if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
							|| (keyCode == KeyEvent.KEYCODE_ENTER)) {
						String newItem = myEditText.getText().toString();
						onNewItemAddedListener.onNewItemAdded(newItem);
						myEditText.setText("");
						return true;
					}
				return false;
			}
		});

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(MyApplication.TAG, "fragment - onActivityCreated");
		// Complete the Fragment initialization Ð particularly anything
		// that requires the parent Activity to be initialized or the
		// Fragment's view to be fully inflated.
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.d(MyApplication.TAG, "fragment - onStart");
		// Apply any required UI change now that the Fragment is visible.
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(MyApplication.TAG, "fragment - onResume");
		// Resume any paused UI updates, threads, or processes required
		// by the Fragment but suspended when it became inactive.
	}

	@Override
	public void onPause() {
		// Suspend UI updates, threads, or CPU intensive processes
		// that don't need to be updated when the Activity isn't
		// the active foreground activity.
		// Persist all edits or state changes
		// as after this call the process is likely to be killed.
		super.onPause();
		Log.d(MyApplication.TAG, "fragment - onPause");
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		// Save UI state changes to the savedInstanceState.
		// This bundle will be passed to onCreate, onCreateView, and
		// onCreateView if the parent Activity is killed and restarted.
		super.onSaveInstanceState(savedInstanceState);
		Log.d(MyApplication.TAG, "fragment - onSaveInstanceState");
	}

	@Override
	public void onStop() {
		// Suspend remaining UI updates, threads, or processing
		// that aren't required when the Fragment isn't visible.
		super.onStop();
		Log.d(MyApplication.TAG, "fragment - onStop");
	}

	@Override
	public void onDestroyView() {
		// Clean up resources related to the View.
		super.onDestroyView();
		Log.d(MyApplication.TAG, "fragment - onDestroyView");
	}

	@Override
	public void onDestroy() {
		// Clean up any resources including ending threads,
		// closing database connections etc.
		super.onDestroy();
		Log.d(MyApplication.TAG, "fragment - onDestroy");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		Log.d(MyApplication.TAG, "fragment - onDetach");
	}

}
