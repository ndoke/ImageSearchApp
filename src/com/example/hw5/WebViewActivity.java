/**
 * Ajay Vijayakumaran Nair 
 * A yang 
 * Nachiket Doke
 * 
 */
package com.example.hw5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends Activity {

	public static final String URL_KEY = "url";
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);
		Intent intent = getIntent();
		if (intent.getExtras() != null) {
			String url = intent.getExtras().getString(URL_KEY);
			webView = (WebView) findViewById(R.id.webView1);
			webView.setWebViewClient(new WebViewClient() {

				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					view.loadUrl(url);
					return false;
				}

			});
			webView.loadUrl(url);
		} else {
			Log.w(LoginActivity.LOGGING_KEY, "extras are null. this is not expected.");
		}
	}
}
