package com.example.apidemowjy;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class NostromoController extends Activity {

	public static Uri CONTENT_URI = Uri.parse("content://moonbases");

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.selector_layout);
		final ListView listView = (ListView) findViewById(R.id.listView1);

		Button okButton = (Button) findViewById(R.id.ok_button);
		okButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				long selected_id = listView.getSelectedItemId();
				selected_id = 4;
				Intent result = new Intent(Intent.ACTION_PICK, ContentUris
						.withAppendedId(CONTENT_URI, selected_id));
				setResult(RESULT_OK, result);
				finish();
			}
		});

		Button cancelButton = (Button) findViewById(R.id.cancel_button);
		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
	}
}
