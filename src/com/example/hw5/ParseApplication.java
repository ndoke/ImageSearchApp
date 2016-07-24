/**
 * Ajay Vijayakumaran Nair
 * Ayang
 * Nachiket Doke
 */
package com.example.hw5;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;

public class ParseApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Initialize Crash Reporting.
    //ParseCrashReporting.enable(this);

    // Enable Local Datastore.
    //Parse.enableLocalDatastore(this);

    // Add your initialization code here
    Parse.initialize(this, "jWiWq9bHli4TQOdRBX5MKf3cm5ZLbrhDFtPyTsSZ", "ivGFZEAkfxlBn5cjr6S0jpIbw1qxAqKNPuy3PU4c");


    //ParseUser.enableAutomaticUser();
    ParseACL defaultACL = new ParseACL();
    // Optionally enable public read access.
    // defaultACL.setPublicReadAccess(true);
    //ParseUser.getCurrentUser().saveInBackground();
    ParseACL.setDefaultACL(defaultACL, true);
  }
}
