package com.example.hw5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
	public static final String LOGGING_KEY = "mt";
	public static final String HISTORY_CHECK_KEY = "option.check";
	public static final String APP_KEY = "app.key";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void appBtnClicked(View view) {
		Intent intent = new Intent(this, AppsActivity.class);
		intent.putExtra(HISTORY_CHECK_KEY, false);
		startActivity(intent);
	}

	public void historyBtnClicked(View view) {
		Intent intent = new Intent(this, AppsActivity.class);
		intent.putExtra(HISTORY_CHECK_KEY, true);
		startActivity(intent);
	}
}
