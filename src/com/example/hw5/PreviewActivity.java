package com.example.hw5;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

public class PreviewActivity extends Activity {

	private App app;
	boolean isInFav = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);
		Intent intent = getIntent();
		if (intent.getExtras() != null) {
			app = (App) intent.getExtras().get(MainActivity.APP_KEY);
			ParseQuery<ParseObject> isInFavQuery = ParseQuery.getQuery(FavoritesHelper.FAVS_CLASSNAME);
			isInFavQuery.whereEqualTo(App.APP_ID, app.getId());
			isInFavQuery.findInBackground(new FindCallback<ParseObject>() {

				@Override
				public void done(List<ParseObject> arg0, ParseException arg1) {
					if (arg1 == null) {
						if (arg0.size() != 0) {
							// in favs
							FavoritesHelper.setFavIcon(PreviewActivity.this);
							isInFav = true;
						} else {
							isInFav = false;
						}
					} else {
						Log.e(MainActivity.LOGGING_KEY, "Error while fetching is in favs");
					}
				}
			});
			((TextView) findViewById(R.id.textViewPrTitle)).setText(app.getTitle());
			if (app.getAppPhotoLarge() != null) {
				ImageView iv = (ImageView) findViewById(R.id.imageViewThumbnail);
				Picasso.with(this).load(app.getAppPhotoLarge()).into(iv);
			}
		}
	}

	public void imgClicked(View view) {
		try {
//			Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(app.getAppUrl()));
			Intent myIntent = new Intent(this, WebViewActivity.class);
			myIntent.putExtra(WebViewActivity.URL_KEY, app.getAppUrl());
			startActivity(myIntent);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(this, "No application can handle this request." + " Please install a webbrowser",
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}

	public void favsClicked(View view) {
		if (!isInFav) {
			FavoritesHelper.addAppToFavourites(this, app);
		}else{
			FavoritesHelper.removeAppInFavs(this, app);
		}
	}

	public void shareClicked(View view) {
		ParseQuery<ParseUser> fetchAllUsers = ParseUser.getQuery();
		fetchAllUsers.selectKeys(Arrays.asList("firstName", "lastName"));
		fetchAllUsers.findInBackground(new FindCallback<ParseUser>() {
			
			@Override
			public void done(final List<ParseUser> arg0, ParseException arg1) {
				if(arg1 == null){
					AlertDialog.Builder builder = new AlertDialog.Builder(PreviewActivity.this);
					builder.setView(PreviewActivity.this.getLayoutInflater().inflate(R.layout.user_list_dialog, null));
					builder.setTitle("Users");
					builder.setAdapter(new UserDisplayAdapter(PreviewActivity.this, android.R.layout.simple_list_item_1, arg0), new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							ShareHelper.shareAppWithUser(PreviewActivity.this, app, arg0.get(which));
							dialog.dismiss();
						}
					});
					builder.create().show();
				}else{
					Log.e(MainActivity.LOGGING_KEY, "Fetching users resulted in error", arg1);
				}
				
			}
		});
		//ShareHelper.shareAppWithUser(this, app, user);
	}
}
