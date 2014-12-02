package com.example.apidemowjy;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AsyncTaskTest extends Activity {

	private Button btnDownload;
	private ProgressDialog prgDialog;
	public static final int progress_bar_type = 0;
	private static String file_url = "http://test-d.1qianbao.com:1080/youqian/app/1qb_88888.apk";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.asynctask_test);
		btnDownload = (Button) findViewById(R.id.btnProgressBar);
		btnDownload.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				btnDownload.setEnabled(false);
				Toast.makeText(getApplicationContext(), "Go Go Go...",
						Toast.LENGTH_LONG).show();
				new DownloadMusicfromInternet().execute(file_url);

			}
		});
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case progress_bar_type:
			prgDialog = new ProgressDialog(this);
			prgDialog.setMessage("Downloading file. Please wait...");
			prgDialog.setIndeterminate(false);
			prgDialog.setMax(100);
			prgDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			prgDialog.setCancelable(false);
			prgDialog.show();
			return prgDialog;
		default:
			return null;
		}
	}

	class DownloadMusicfromInternet extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showDialog(progress_bar_type);
		}

		// Download Music File from Internet
		@Override
		protected String doInBackground(String... f_url) {
			int count;
			try {
				URL url = new URL(f_url[0]);
				URLConnection conection = url.openConnection();
				conection.connect();
				// Get Music file length
				int lenghtOfFile = conection.getContentLength();
				// input stream to read file - with 8k buffer
				InputStream input = new BufferedInputStream(url.openStream(),
						10 * 1024);
				// Output stream to write file in SD card
				OutputStream output = new FileOutputStream(Environment
						.getExternalStorageDirectory().getPath()
						+ "/test.apk");
				byte data[] = new byte[1024];
				long total = 0;
				while ((count = input.read(data)) != -1) {
					total += count;
					publishProgress("" + (int) ((total * 100) / lenghtOfFile));
					output.write(data, 0, count);
				}
				output.flush();
				output.close();
				input.close();
			} catch (Exception e) {
				Log.e(MyApplication.TAG, e.getMessage());
			}
			return null;
		}

		protected void onProgressUpdate(String... progress) {
			prgDialog.setProgress(Integer.parseInt(progress[0]));
		}

		@Override
		protected void onPostExecute(String file_url) {
			dismissDialog(progress_bar_type);
			Toast.makeText(getApplicationContext(), "Download complete!",
					Toast.LENGTH_LONG).show();
		}
	}

}
