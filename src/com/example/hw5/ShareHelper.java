package com.example.hw5;

import static com.example.hw5.App.APP_DEV_NAME;
import static com.example.hw5.App.APP_ID;
import static com.example.hw5.App.APP_PHOTO_LARGE;
import static com.example.hw5.App.APP_PHOTO_SMALL;
import static com.example.hw5.App.APP_PRICE;
import static com.example.hw5.App.APP_TITLE;
import static com.example.hw5.App.APP_URL;

import java.util.List;

import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ShareHelper {
	public static final String SHARED_CLASSNAME = "SharedApps";

	public static void shareAppWithUser(final PreviewActivity activity, App app, ParseUser user) {
		ParseObject appParseObj = new ParseObject(SHARED_CLASSNAME);
		appParseObj.put(APP_TITLE, app.getTitle());
		appParseObj.put(APP_URL, app.getAppUrl());
		appParseObj.put(APP_PHOTO_LARGE, app.getAppPhotoLarge());
		appParseObj.put(APP_PHOTO_SMALL, app.getAppPhotoSmall());
		appParseObj.put(APP_DEV_NAME, app.getDevName());
		appParseObj.put(APP_PRICE, app.getPrice());
		appParseObj.put(APP_ID, app.getId());
		appParseObj.setACL(new ParseACL(user));
		appParseObj.saveInBackground(new SaveCallback() {

			@Override
			public void done(ParseException arg0) {
				if (arg0 == null) {
					Toast.makeText(activity, "App saved in shared list", Toast.LENGTH_LONG).show();
				} else {
					Log.e(MainActivity.LOGGING_KEY, "Error while saving in Shared", arg0);
				}
			}
		});
	}
	public static void displaySharedApps(final AppsActivity activity){
		ParseQuery<ParseObject> allSharedApps = ParseQuery.getQuery(SHARED_CLASSNAME);
		allSharedApps.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				if(arg1 == null){
					List<App> apps = App.convertToApps(arg0);
					activity.updateAppsDisplayWith(apps);
				}else{
					Log.e(MainActivity.LOGGING_KEY, "problem while retrieving all shared for display", arg1);
				}
				
			}
		});
	}
	public static void clearSharedApps(AppsActivity activity){
		ParseQuery<ParseObject> allSharedApps = ParseQuery.getQuery(SHARED_CLASSNAME);
		allSharedApps.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				if(arg1 == null){
					try {
						ParseObject.deleteAll(arg0);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else{
					Log.e(MainActivity.LOGGING_KEY, "problem while retrieving all shared for clearing", arg1);
				}
				
			}
		});
	}
}
