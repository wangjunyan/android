package com.example.apidemowjy;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class EarthquakeListFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<Cursor> {
	// ArrayAdapter<Quake> aa;
	// ArrayList<Quake> earthquakes = new ArrayList<Quake>();

	SimpleCursorAdapter adapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// int layoutID = android.R.layout.simple_list_item_1;
		// aa = new ArrayAdapter<Quake>(getActivity(), layoutID, earthquakes);
		// setListAdapter(aa);

		// Create a new Adapter and bind it to the List View
		adapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_list_item_1, null,
				new String[] { EarthquakeProvider.KEY_SUMMARY },
				new int[] { android.R.id.text1 }, 0);
		setListAdapter(adapter);

		getLoaderManager().initLoader(0, null, this);
/*
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				refreshEarthquakes();
			}

		});
		t.start();
*/
		refreshEarthquakes();
	}

	public void refreshEarthquakes(){
		getLoaderManager().restartLoader(0, null,
				EarthquakeListFragment.this);
		getActivity().startService(new Intent(getActivity(), 
                EarthquakeUpdateService.class));
	}

/*
	private Handler handler = new Handler();

	public void refreshEarthquakes() {
		handler.post(new Runnable() {
			public void run() {
				getLoaderManager().restartLoader(0, null,
						EarthquakeListFragment.this);
			}
		});

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
						// // Process a newly found earthquake
						// handler.post(new Runnable() {
						//
						// @Override
						// public void run() {
						// // TODO Auto-generated method stub
						// addNewQuake(quake);
						// }
						//
						// });

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
		}
	}
*/
	
	private void addNewQuake(Quake _quake) {
		/*
		 * // Add the new quake to our list of earthquakes. EarthquakeActivity
		 * earthquakeActivity = (EarthquakeActivity)getActivity(); if
		 * (_quake.getMagnitude() > earthquakeActivity.minimumMagnitude) {
		 * earthquakes.add(_quake); }
		 * 
		 * // Notify the array adapter of a change. aa.notifyDataSetChanged();
		 */
		ContentResolver cr = getActivity().getContentResolver();
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

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// TODO Auto-generated method stub
		String[] projection = new String[] { EarthquakeProvider.KEY_ID,
				EarthquakeProvider.KEY_SUMMARY };

		EarthquakeActivity earthquakeActivity = (EarthquakeActivity) getActivity();
		String where = EarthquakeProvider.KEY_MAGNITUDE + " > "
				+ earthquakeActivity.minimumMagnitude;

		CursorLoader loader = new CursorLoader(getActivity(),
				EarthquakeProvider.CONTENT_URI, projection, where, null, null);

		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// TODO Auto-generated method stub
		adapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub
		adapter.swapCursor(null);
	}
}
