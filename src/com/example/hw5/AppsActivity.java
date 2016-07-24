/**
 * Ajay Vijayakumaran Nair
 * Ayang
 * Nachiket Doke
 */
package com.example.hw5;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import com.parse.ParseUser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AppsActivity extends Activity {

	private String baseUrl = "https://itunes.apple.com/us/rss/topgrossingapplications/limit=100/xml";
	private ProgressDialog progressDialogue;
	protected DisplayType currentDisplay;
	private List<App> apps = new ArrayList<App>();
	ListView listView;
	ArrayAdapter<App> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apps);
		adapter = new AppDisplayAdapter(AppsActivity.this, R.layout.app_list_item_layout, apps);
		adapter.notifyDataSetChanged();
		listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(adapter);
		this.currentDisplay = DisplayType.APPS;
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				App story = apps.get(position);
				Intent intent = new Intent(AppsActivity.this, PreviewActivity.class);
				intent.putExtra(MainActivity.APP_KEY, story);
				startActivity(intent);
				Log.d(MainActivity.LOGGING_KEY, story.toString());
			}
		});
		// apps = FavoritesHelper.getAppsInFavorites(this);
		// adapter.clear();
		// adapter.addAll(apps);
		//
		new FetchAppDetailsTask().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.apps_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.clearShared:
			ShareHelper.clearSharedApps(this);
			if (this.currentDisplay == DisplayType.SHARED) {
				updateAppsDisplayWithEmpty();
			}
			break;
		case R.id.clearFavs:
			FavoritesHelper.clearFavs(this);
			if (this.currentDisplay == DisplayType.FAVS) {
				updateAppsDisplayWithEmpty();
			}
			break;
		case R.id.viewAll:
			if (currentDisplay != DisplayType.APPS) {
				this.currentDisplay = DisplayType.APPS;
				new FetchAppDetailsTask().execute();
			}
			break;
		case R.id.viewFav:
			this.currentDisplay = DisplayType.FAVS;
			FavoritesHelper.displayAppsInFavorites(this);
			break;
		case R.id.viewShared:
			this.currentDisplay = DisplayType.SHARED;
			ShareHelper.displaySharedApps(this);
			break;
		case R.id.logout:
			ParseUser.logOut();
			finish();
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private class FetchAppDetailsTask extends AsyncTask<Void, Void, List<App>> {

		@Override
		protected void onPreExecute() {
			if (!Common.ifNwDisconnectedDisplayMsg(AppsActivity.this, this)) {
				return;
			}
			progressDialogue = new ProgressDialog(AppsActivity.this);
			progressDialogue.setTitle("Loading Apps");
			progressDialogue.setCancelable(false);
			progressDialogue.show();
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(final List<App> result) {
			progressDialogue.dismiss();
			updateAppsDisplayWith(result);
			super.onPostExecute(result);
		}

		@Override
		protected List<App> doInBackground(Void... params) {
			List<App> appList = null;
			try {
				URL url = new URL(baseUrl);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.connect();
				int statusCode = connection.getResponseCode();
				if (statusCode == HttpURLConnection.HTTP_OK) {
					InputStream inStream = connection.getInputStream();
					Log.d(MainActivity.LOGGING_KEY, "Using Pull");
					appList = new PullParserHelper().getAppList(inStream);
					return appList;
				} else {
					Log.d(MainActivity.LOGGING_KEY, "Http status is not OK");
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}

	protected void updateAppsDisplayWith(List<App> apps) {
		this.apps.clear();
		adapter.addAll(apps);
		adapter.setNotifyOnChange(true);
	}

	protected void updateAppsDisplayWithEmpty() {
		this.apps.clear();
		adapter.addAll(apps);
		adapter.setNotifyOnChange(true);
	}

}

enum DisplayType {
	APPS, FAVS, SHARED;
}