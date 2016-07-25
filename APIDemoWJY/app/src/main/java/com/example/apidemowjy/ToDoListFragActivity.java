package com.example.apidemowjy;

import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

public class ToDoListFragActivity extends Activity implements
		NewItemFragment.OnNewItemAddedListener,
		LoaderManager.LoaderCallbacks<Cursor> {

	// private ArrayAdapter<String> aa;
	// private ArrayList<String> todoItems;
	private ToDoItemAdapter aa;
	private ArrayList<ToDoItem> todoItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);

		FragmentManager fm = getFragmentManager();
		ToDoListFragment todoListFragment = (ToDoListFragment) fm
				.findFragmentById(R.id.TodoListFragment);

		todoItems = new ArrayList<ToDoItem>();
		int resID = R.layout.todolist_item;
		// aa = new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_1,
		// todoItems);
		aa = new ToDoItemAdapter(this, resID, todoItems);
		todoListFragment.setListAdapter(aa);

		getLoaderManager().initLoader(0, null, this);

	}

	@Override
	protected void onResume() {
		super.onResume();
		getLoaderManager().restartLoader(0, null, this);
	}

	public void onNewItemAdded(String newItem) {
		// ToDoItem newTodoItem = new ToDoItem(newItem);
		// todoItems.add(0, newTodoItem);
		// aa.notifyDataSetChanged();
		ContentResolver cr = getContentResolver();

		ContentValues values = new ContentValues();
		values.put(ToDoContentProvider.KEY_TASK, newItem);

		cr.insert(ToDoContentProvider.CONTENT_URI, values);
		getLoaderManager().restartLoader(0, null, this);

	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// TODO Auto-generated method stub
		CursorLoader loader = new CursorLoader(this,
				ToDoContentProvider.CONTENT_URI, null, null, null, null);

		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// TODO Auto-generated method stub
		int keyTaskIndex = cursor
				.getColumnIndexOrThrow(ToDoContentProvider.KEY_TASK);

		todoItems.clear();
		while (cursor.moveToNext()) {
			Log.d(MyApplication.TAG, cursor.getString(keyTaskIndex));
			ToDoItem newItem = new ToDoItem(cursor.getString(keyTaskIndex));
			todoItems.add(newItem);
		}
		aa.notifyDataSetChanged();
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub

	}
}
