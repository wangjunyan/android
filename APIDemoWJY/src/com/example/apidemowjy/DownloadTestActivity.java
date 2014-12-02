package com.example.apidemowjy;

import java.util.Arrays;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DownloadTestActivity extends Activity {
	private long myDownloadReference;
	private DownloadChangeObserver downloadObserver;

	private void regNotificationClick() {
		IntentFilter filter = new IntentFilter(
				DownloadManager.ACTION_NOTIFICATION_CLICKED);

		BroadcastReceiver receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String extraID = DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS;
				long[] references = intent.getLongArrayExtra(extraID);
				for (long reference : references)
					if (reference == myDownloadReference) {
						// Do something with downloading file.
						Log.d(MyApplication.TAG, "click the downloading file");
					}
			}
		};

		registerReceiver(receiver, filter);
	}

	private void regDownloadComplete() {
		final DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

		IntentFilter filter = new IntentFilter(
				DownloadManager.ACTION_DOWNLOAD_COMPLETE);

		BroadcastReceiver receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				long reference = intent.getLongExtra(
						DownloadManager.EXTRA_DOWNLOAD_ID, -1);
				if (myDownloadReference == reference) {
					Query myDownloadQuery = new Query();
					myDownloadQuery.setFilterById(reference);

					Cursor myDownload = downloadManager.query(myDownloadQuery);
					if (myDownload.moveToFirst()) {
						int fileNameIdx = myDownload
								.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
						int fileUriIdx = myDownload
								.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);

						String fileName = myDownload.getString(fileNameIdx);
						String fileUri = myDownload.getString(fileUriIdx);

						// TODO Do something with the file.
						Log.d(MyApplication.TAG, fileName + " : " + fileUri);
					}
					myDownload.close();

				}
			}
		};

		registerReceiver(receiver, filter);
	}

	private void startDownload() {
		String serviceString = Context.DOWNLOAD_SERVICE;
		DownloadManager downloadManager;
		downloadManager = (DownloadManager) getSystemService(serviceString);
		
		downloadObserver = new DownloadChangeObserver();
		getContentResolver().registerContentObserver(Uri.parse("content://downloads/my_downloads"), true, downloadObserver);

		Uri uri = Uri
				.parse("http://test-d.1qianbao.com:1080/youqian/app/1qb_88888.apk");
		DownloadManager.Request request = new Request(uri);
		long reference = downloadManager.enqueue(request);

		//
		myDownloadReference = reference;
		Log.d(MyApplication.TAG, "Download Reference: " + reference);
		
		
	}

	private void checkPausedDownload() {
		// Obtain the Download Manager Service.
		String serviceString = Context.DOWNLOAD_SERVICE;
		DownloadManager downloadManager;
		downloadManager = (DownloadManager) getSystemService(serviceString);

		// Create a query for paused downloads.
		Query pausedDownloadQuery = new Query();
		pausedDownloadQuery.setFilterByStatus(DownloadManager.STATUS_PAUSED);

		// Query the Download Manager for paused downloads.
		Cursor pausedDownloads = downloadManager.query(pausedDownloadQuery);

		// Find the column indexes for the data we require.
		int reasonIdx = pausedDownloads
				.getColumnIndex(DownloadManager.COLUMN_REASON);
		int titleIdx = pausedDownloads
				.getColumnIndex(DownloadManager.COLUMN_TITLE);
		int fileSizeIdx = pausedDownloads
				.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
		int bytesDLIdx = pausedDownloads
				.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);

		// Iterate over the result Cursor.
		while (pausedDownloads.moveToNext()) {
			// Extract the data we require from the Cursor.
			String title = pausedDownloads.getString(titleIdx);
			int fileSize = pausedDownloads.getInt(fileSizeIdx);
			int bytesDL = pausedDownloads.getInt(bytesDLIdx);

			// Translate the pause reason to friendly text.
			int reason = pausedDownloads.getInt(reasonIdx);
			String reasonString = "Unknown";
			switch (reason) {
			case DownloadManager.PAUSED_QUEUED_FOR_WIFI:
				reasonString = "Waiting for WiFi";
				break;
			case DownloadManager.PAUSED_WAITING_FOR_NETWORK:
				reasonString = "Waiting for connectivity";
				break;
			case DownloadManager.PAUSED_WAITING_TO_RETRY:
				reasonString = "Waiting to retry";
				break;
			default:
				break;
			}

			// Construct a status summary
			StringBuilder sb = new StringBuilder();
			sb.append(title).append("\n");
			sb.append(reasonString).append("\n");
			sb.append("Downloaded ").append(bytesDL).append(" / ")
					.append(fileSize);

			// Display the status
			Log.d("DOWNLOAD", sb.toString());
		}

		// Close the result Cursor.
		pausedDownloads.close();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.download_test);

		Button buttonOpen = (Button) findViewById(R.id.button1);
		buttonOpen.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				regNotificationClick();
				regDownloadComplete();
				startDownload();
			}
		});

		Button buttonDownload = (Button) findViewById(R.id.button2);
		buttonDownload.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				checkPausedDownload();
			}
		});
	}

	class DownloadChangeObserver extends ContentObserver {

		public DownloadChangeObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			int[] bytesAndStatus = new int[] { -1, -1, 0 };
			DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
			DownloadManager.Query query = new DownloadManager.Query()
					.setFilterById(myDownloadReference);
			Cursor c = null;
			try {
				c = downloadManager.query(query);
				if (c != null && c.moveToFirst()) {
					bytesAndStatus[0] = c
							.getInt(c
									.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
					bytesAndStatus[1] = c
							.getInt(c
									.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
					bytesAndStatus[2] = c.getInt(c
							.getColumnIndex(DownloadManager.COLUMN_STATUS));
				}
			} finally {
				if (c != null) {
					c.close();
				}
			}

			Log.d(MyApplication.TAG, Arrays.toString(bytesAndStatus));
		}

	}
}
