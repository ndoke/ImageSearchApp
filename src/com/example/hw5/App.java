/**
 * Ajay Vijayakumaran Nair
 * Ayang
 * Nachiket Doke
 */
package com.example.hw5;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.parse.ParseObject;

public class App implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String title;
	private String appUrl;
	private String devName;
	private double price;
	private String appPhotoSmall;
	private String appPhotoLarge;
	private Date releaseDate;
	public static final String APP_TITLE = "title";
	public static final String APP_URL = "url";
	public static final String APP_PHOTO_LARGE = "photoLarge";
	public static final String APP_PHOTO_SMALL = "photoSmall";
	public static final String APP_DEV_NAME = "devName";
	public static final String APP_PRICE = "price";
	public static final String APP_ID = "id";
	public static final String APP_RELEASE_DATE="releaseDate";

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public String getDevName() {
		return devName;
	}

	public void setDevName(String devName) {
		this.devName = devName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getAppPhotoSmall() {
		return appPhotoSmall;
	}

	public void setAppPhotoSmall(String appPhotoSmall) {
		this.appPhotoSmall = appPhotoSmall;
	}

	public String getAppPhotoLarge() {
		return appPhotoLarge;
	}

	public void setAppPhotoLarge(String appPhotoLarge) {
		this.appPhotoLarge = appPhotoLarge;
	}

	public static App convertToApp(ParseObject po){
		App app = new App();
		app.setAppPhotoLarge(po.getString(APP_PHOTO_LARGE));
		app.setAppPhotoSmall(po.getString(APP_PHOTO_SMALL));
		app.setAppUrl(po.getString(APP_URL));
		app.setDevName(po.getString(APP_DEV_NAME));
		app.setId(po.getLong(APP_ID));
		app.setPrice(po.getDouble(APP_PRICE));
		app.setTitle(po.getString(APP_TITLE));
		app.setReleaseDate(po.getDate(APP_RELEASE_DATE));
		return app;
	}
	public static List<App> convertToApps(List<ParseObject> pos){
		List<App> apps = new ArrayList<App>();
		for(ParseObject po : pos){
			apps.add(convertToApp(po));
		}
		return apps;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
}
