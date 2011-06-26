/**
 * AusSnowCam: View snowcams from Australia's snow resorts
 * File: MainTablet.java
 *
 * @author James Giang
 *
 * Copyright 2011 James Giang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.jamesgiang.aussnowcam;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import com.jamesgiang.aussnowcam.Fragments.CamList;
import com.jamesgiang.aussnowcam.Fragments.CamViewer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.OnNavigationListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

public class MainTablet extends Activity {
	private GoogleAnalyticsTracker tracker;
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	    tracker.stop();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		tracker.trackPageView("/Main");
		tracker.setCustomVar(1, "Device", "Tablet");
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);
        setContentView(R.layout.main_tablet);
        tracker = GoogleAnalyticsTracker.getInstance();
        tracker.start("UA-23871335-1", 20, this);
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
    	SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.resort_options, android.R.layout.simple_spinner_dropdown_item);
        OnNavigationListener mOnNavigationListener = new OnNavigationListener() {
        	@Override
        	public boolean onNavigationItemSelected(int position, long itemId) {
        		CamList fragment = (CamList) getFragmentManager().findFragmentById(R.id.camlist);
				fragment.refreshList(position);
				String[] resorts = getResources().getStringArray(R.array.resort_options);
				tracker.trackEvent("Load Resorts", resorts[position], "", 0);
        	    return true;
        	}
    	};
    	actionBar.setListNavigationCallbacks(mSpinnerAdapter, mOnNavigationListener);
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tablet_menu, menu);
        return true;
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
	        case R.id.menu1:
	        	tracker.trackEvent("Menu", "Reload", "", 0);
	        	CamViewer fragment = (CamViewer) getFragmentManager().findFragmentById(R.id.camviewer);
	        	fragment.refresh();
	        	return true;
	        case R.id.menu2:
	        	tracker.trackEvent("Menu", "About", "", 0);
	        	Utils.About(this);
	        	return true;
    	}
        return false;
    }
}