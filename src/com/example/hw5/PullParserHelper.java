/**
 * PullParserHelper.java
 * A Yang
 * Ajay Vijayakumaran Nair
 * Nachiket Doke
 * 
 */
package com.example.hw5;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class PullParserHelper {
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

	public List<App> getAppList(InputStream inStream) throws XmlPullParserException, IOException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser parser = factory.newPullParser();
		parser.setInput(inStream, "UTF-8");
		int eventType = parser.getEventType();
		List<App> appList = new ArrayList<App>();
		App currentApp = null;
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_TAG:
				String startTagName = parser.getName();
				if (startTagName.equals("entry")) {
					currentApp = new App();
				} else if (currentApp != null && startTagName.equals("id")) {
					String idString = parser.getAttributeValue(null, "id");
					currentApp.setId(Long.parseLong(idString));
				} else if (currentApp != null && startTagName.equals("title")) {
					currentApp.setTitle(parser.nextText());
				} else if (currentApp != null && startTagName.equals("link")) {
					currentApp.setAppUrl(parser.getAttributeValue(null, "href"));
				} else if (currentApp != null && startTagName.equals("artist")) {
					currentApp.setDevName(parser.nextText());
				} else if (currentApp != null && startTagName.equals("price")) {
					String stringPrice = parser.getAttributeValue(null, "amount");
					currentApp.setPrice(Double.parseDouble(stringPrice));
				} else if (currentApp != null && startTagName.equals("releaseDate")) {
					String stringReleaseDate = parser.nextText();
					Date date = null;
					try {
						date = dateFormatter.parse(stringReleaseDate);
					} catch (ParseException e) {
						Log.w(LoginActivity.LOGGING_KEY, e.getMessage(), e);
					}
					currentApp.setReleaseDate(date);
				} else if (currentApp != null && startTagName.equals("image")) {
					if (parser.getAttributeValue(null, "height").equals("100")) {
						currentApp.setAppPhotoLarge(parser.nextText());
					} else if (parser.getAttributeValue(null, "height").equals("53")) {
						currentApp.setAppPhotoSmall(parser.nextText());
					}
				}
				break;
			case XmlPullParser.END_TAG:
				if (parser.getName().equals("entry")) {
					// appList.add(parser.getAttributeValue(null,
					// "url_m").trim());
					appList.add(currentApp);
					currentApp = null;
				}
				break;
			default:
				break;
			}
			eventType = parser.next();
		}
		return appList;
	}
}
