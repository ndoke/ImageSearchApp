/**
 * Ajay Vijayakumaran Nair
 * Ayang
 * Nachiket Doke
 */
package com.example.hw5;

import static com.example.hw5.App.APP_DEV_NAME;
import static com.example.hw5.App.APP_ID;
import static com.example.hw5.App.APP_PHOTO_LARGE;
import static com.example.hw5.App.APP_PHOTO_SMALL;
import static com.example.hw5.App.APP_PRICE;
import static com.example.hw5.App.APP_TITLE;
import static com.example.hw5.App.APP_URL;

import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.widget.ImageView;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class FavoritesHelper {
	public static final String FAVS_CLASSNAME = "Favorites";

	public static void addAppToFavourites(final PreviewActivity activity, App app) {
		ParseObject appParseObj = new ParseObject(FAVS_CLASSNAME);
		appParseObj.put(APP_TITLE, app.getTitle());
		appParseObj.put(APP_URL, app.getAppUrl());
		appParseObj.put(APP_PHOTO_LARGE, app.getAppPhotoLarge());
		appParseObj.put(APP_PHOTO_SMALL, app.getAppPhotoSmall());
		appParseObj.put(APP_DEV_NAME, app.getDevName());
		appParseObj.put(APP_PRICE, app.getPrice());
		appParseObj.put(APP_ID, app.getId());
		appParseObj.saveInBackground(new SaveCallback() {

			@Override
			public void done(ParseException arg0) {
				if (arg0 == null) {
					// Toast.makeText(activity, "App saved in favs",
					// Toast.LENGTH_LONG).show();
					setFavIcon(activity);
					activity.isInFav = true;
				} else {
					Log.e(MainActivity.LOGGING_KEY, "Error while saving in Favs", arg0);
				}
			}
		});
	}

	public static void displayAppsInFavorites(final AppsActivity activity) {
		ParseQuery<ParseObject> favApps = ParseQuery.getQuery(FAVS_CLASSNAME);
		favApps.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				if(arg1 == null){
						List<App> apps = App.convertToApps(arg0);
						activity.updateAppsDisplayWith(apps);
				}else{
					Log.e(MainActivity.LOGGING_KEY, "problem while retrieving fav apps for clearing", arg1);
				}
				
			}
		});
	}

	public static void removeAppInFavs(final PreviewActivity activity, App app) {
		ParseQuery<ParseObject> isInFavQuery = ParseQuery.getQuery(FavoritesHelper.FAVS_CLASSNAME);
		isInFavQuery.whereEqualTo(APP_ID, app.getId());
		isInFavQuery.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				if (arg1 == null) {
					if (arg0.size() != 0) {
						// in favs
						arg0.get(0).deleteInBackground(new DeleteCallback() {

							@Override
							public void done(ParseException arg0) {
								FavoritesHelper.unSetFavIcon(activity);
								activity.isInFav = false;
							}
						});
					}
				} else {
					Log.e(MainActivity.LOGGING_KEY, "Error while fetching is in favs");
				}
			}
		});
		
	}

	public static void clearFavs(Activity activity) {
		ParseQuery<ParseObject> favApps = ParseQuery.getQuery(FAVS_CLASSNAME);
		favApps.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				if(arg1 == null){
					try {
						ParseObject.deleteAll(arg0);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
				}else{
					Log.e(MainActivity.LOGGING_KEY, "problem while retrieving fav apps for clearing", arg1);
				}
				
			}
		});
	}

	public static void setFavIcon(Activity activity) {
		((ImageView) activity.findViewById(R.id.imageViewFavInd)).setImageDrawable(activity.getResources().getDrawable(
				R.drawable.rating_important));
	}

	public static void unSetFavIcon(Activity activity) {
		((ImageView) activity.findViewById(R.id.imageViewFavInd)).setImageDrawable(activity.getResources().getDrawable(
				R.drawable.rating_not_important));
	}
}
