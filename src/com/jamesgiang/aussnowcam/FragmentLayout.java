/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jamesgiang.aussnowcam;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.OnNavigationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

/**
 * Demonstration of using fragments to implement different activity layouts.
 * This sample provides a different layout (and activity flow) when run in
 * landscape.
 */
public class FragmentLayout extends Activity {
	int resort;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);
        setContentView(R.layout.fragment_layout);
        if (savedInstanceState != null) {
            resort = savedInstanceState.getInt("resortChoice", 1);
        }
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
    	SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.action_list, android.R.layout.simple_spinner_dropdown_item);
        OnNavigationListener mOnNavigationListener = new OnNavigationListener() {
        	@Override
        	public boolean onNavigationItemSelected(int position, long itemId) {
        		FragmentList fragment = (FragmentList) getFragmentManager().findFragmentById(R.id.cam);
				if(position==0) {
					resort = 1;
					fragment.refreshList(1);
				} else if(position==1) {
					resort = 2;
					fragment.refreshList(2);
				} else if(position==2) {
					resort = 3;
					fragment.refreshList(3);
				} else if(position==3) {
					resort = 4;
					fragment.refreshList(4);
				} else if(position==4) {
					resort = 5;
					fragment.refreshList(5);
				} else if(position==5) {
					resort = 6;
					fragment.refreshList(6);
				} else if(position==6) {
					resort = 7;
					fragment.refreshList(7);
				} else if(position==7) {
					resort = 8;
					fragment.refreshList(8);
				} else if(position==8) {
					resort = 9;
					fragment.refreshList(9);
				}
        	    return true;
        	}
    	};
    	actionBar.setListNavigationCallbacks(mSpinnerAdapter, mOnNavigationListener);
    }    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("resortChoice", resort);
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_honeycomb, menu);
        return true;
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
	        case R.id.menu1:
	        	//tracker.trackEvent("Menu", "Reload", "", 0);
	        	FragmentViewer fragment = (FragmentViewer) getFragmentManager().findFragmentById(R.id.viewer);
	        	fragment.refresh();
	        	return true;
	        case R.id.menu2:
	        	//tracker.trackEvent("Menu", "About", "", 0);
	        	Utils.About(this);
	        	return true;
    	}
        return false;
    }
}