package com.example.apidemowjy;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MyOtherActivity extends Activity {
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.selector_layout);

		Intent intent = getIntent();
		String action = intent.getAction();
		Uri data = intent.getData();
	}

	@Override
	public void onStart() {
		super.onStart();

		final ListView listView = (ListView) findViewById(R.id.listView1);

		Button okButton = (Button) findViewById(R.id.ok_button);
		okButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				long selected_id = listView.getSelectedItemId();

				//Intent result = new Intent(Intent.ACTION_PICK, Uri.parse(Long.toString(selected_id)));
				Uri selectedHorse = Uri.parse("content://horses/" + 5);
				Intent result = new Intent(Intent.ACTION_PICK, selectedHorse);
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
