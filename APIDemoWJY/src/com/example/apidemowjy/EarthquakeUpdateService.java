package com.example.apidemowjy;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Location;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

public class EarthquakeUpdateService extends Service {

	public static String TAG = "EARTHQUAKE_UPDATE_SERVICE";

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	private void addNewQuake(Quake _quake) {
		/*
		 * // Add the new quake to our list of earthquakes. EarthquakeActivity
		 * earthquakeActivity = (EarthquakeActivity)getActivity(); if
		 * (_quake.getMagnitude() > earthquakeActivity.minimumMagnitude) {
		 * earthquakes.add(_quake); }
		 * 
		 * // Notify the array adapter of a change. aa.notifyDataSetChanged();
		 */
		ContentResolver cr = getContentResolver();
		// Construct a where clause to make sure we don't already have this
		// earthquake in the provider.
		String w = EarthquakeProvider.KEY_DATE + " = "
				+ _quake.getDate().getTime();

		// If the earthquake is new, insert it into the provider.
		Cursor query = cr.query(EarthquakeProvider.CONTENT_URI, null, w, null,
				null);
		if (query.getCount() == 0) {
			ContentValues values = new ContentValues();

			values.put(EarthquakeProvider.KEY_DATE, _quake.getDate().getTime());
			values.put(EarthquakeProvider.KEY_DETAILS, _quake.getDetails());
			values.put(EarthquakeProvider.KEY_SUMMARY, _quake.toString());

			double lat = _quake.getLocation().getLatitude();
			double lng = _quake.getLocation().getLongitude();
			values.put(EarthquakeProvider.KEY_LOCATION_LAT, lat);
			values.put(EarthquakeProvider.KEY_LOCATION_LNG, lng);
			values.put(EarthquakeProvider.KEY_LINK, _quake.getLink());
			values.put(EarthquakeProvider.KEY_MAGNITUDE, _quake.getMagnitude());

			cr.insert(EarthquakeProvider.CONTENT_URI, values);
		}
		query.close();
	}

	public void refreshEarthquakes() {
		// Get the XML
		URL url;
		try {
			String quakeFeed = getString(R.string.quake_feed);
			url = new URL(quakeFeed);

			URLConnection connection;
			connection = url.openConnection();

			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			int responseCode = httpConnection.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream in = httpConnection.getInputStream();

				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();

				// Parse the earthquake feed.
				Document dom = db.parse(in);
				Element docEle = dom.getDocumentElement();

				// Clear the old earthquakes
				// earthquakes.clear();

				// Get a list of each earthquake entry.
				NodeList nl = docEle.getElementsByTagName("entry");
				if (nl != null && nl.getLength() > 0) {
					for (int i = 0; i < nl.getLength(); i++) {
						Element entry = (Element) nl.item(i);
						Element title = (Element) entry.getElementsByTagName(
								"title").item(0);
						Element g = (Element) entry.getElementsByTagName(
								"georss:point").item(0);
						Element when = (Element) entry.getElementsByTagName(
								"updated").item(0);
						Element link = (Element) entry.getElementsByTagName(
								"link").item(0);

						if (g == null) {
							continue;
						}

						String details = title.getFirstChild().getNodeValue();
						String hostname = "http://earthquake.usgs.gov";
						String linkString = hostname
								+ link.getAttribute("href");

						String point = g.getFirstChild().getNodeValue();
						String dt = when.getFirstChild().getNodeValue();
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd'T'hh:mm:ss'Z'");
						Date qdate = new GregorianCalendar(0, 0, 0).getTime();
						try {
							qdate = sdf.parse(dt);
						} catch (ParseException e) {
							Log.d(MyApplication.TAG, "Date parsing exception.",
									e);
						}

						String[] location = point.split(" ");
						Location l = new Location("dummyGPS");
						l.setLatitude(Double.parseDouble(location[0]));
						l.setLongitude(Double.parseDouble(location[1]));

						String magnitudeString = details.split(" ")[1];
						int end = magnitudeString.length() - 1;
						double magnitude = Double.parseDouble(magnitudeString
								.substring(0, end));

						details = details.split(",")[1].trim();

						final Quake quake = new Quake(qdate, details, l,
								magnitude, linkString);
						addNewQuake(quake);
					}
				}
			}
		} catch (MalformedURLException e) {
			Log.d(MyApplication.TAG, "MalformedURLException", e);
		} catch (IOException e) {
			Log.d(MyApplication.TAG, "IOException", e);
		} catch (ParserConfigurationException e) {
			Log.d(MyApplication.TAG, "Parser Configuration Exception", e);
		} catch (SAXException e) {
			Log.d(MyApplication.TAG, "SAX Exception", e);
		} finally {
			stopSelf();
		}
	}

	//private Timer updateTimer;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Retrieve the shared preferences
		Log.d(MyApplication.TAG, "earthquake service onStartCommand");
		Context context = getApplicationContext();
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);

		int updateFreq = Integer.parseInt(prefs.getString(
				Preferences2Activity.PREF_UPDATE_FREQ, "60"));
		boolean autoUpdateChecked = prefs.getBoolean(
				Preferences2Activity.PREF_AUTO_UPDATE, false);

		Log.d(MyApplication.TAG, "autoUpdateChecked=" + autoUpdateChecked
				+ " updateFreq=" + updateFreq);
		//updateTimer.cancel();
		if (autoUpdateChecked) {
			// updateTimer = new Timer("earthquakeUpdates");
			// doRefresh = new MyTimerTask();
			// updateTimer.scheduleAtFixedRate(doRefresh, 0,
			// updateFreq * 60 * 1000);
			int alarmType = AlarmManager.ELAPSED_REALTIME_WAKEUP;
			long timeToRefresh = SystemClock.elapsedRealtime() + updateFreq
					* 60 * 1000;
			alarmManager.setInexactRepeating(alarmType, timeToRefresh,
					updateFreq * 60 * 1000, alarmIntent);
		} else {
			alarmManager.cancel(alarmIntent);

		}
		Thread t = new Thread(new Runnable() {
			public void run() {
				refreshEarthquakes();
			}
		});
		t.start();

		// return Service.START_STICKY;
		return Service.START_NOT_STICKY;
	};

	private MyTimerTask doRefresh;

	// private TimerTask doRefresh = new TimerTask() {
	// public void run() {
	// Log.d(MyApplication.TAG, "refreshEarthquake starting ... ");
	// refreshEarthquakes();
	// }
	// };

	private AlarmManager alarmManager;
	private PendingIntent alarmIntent;

	@Override
	public void onCreate() {
		Log.d(MyApplication.TAG, "earthquake service onCreate");
		// updateTimer = new Timer("earthquakeUpdates");
		alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		String ALARM_ACTION = EarthquakeAlarmReceiver.ACTION_REFRESH_EARTHQUAKE_ALARM;
		Intent intentToFire = new Intent(ALARM_ACTION);
		alarmIntent = PendingIntent.getBroadcast(this, 0, intentToFire, 0);
	}

	private class MyTimerTask extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Log.d(MyApplication.TAG, "refreshEarthquake starting ... ");
			refreshEarthquakes();
		}

	}
}
