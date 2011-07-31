/**
 * AusSnowCam: View snowcams from Australia's snow resorts
 * File: MainPhone.java
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.ads.*;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

public class MainPhone extends Activity {
	private WebView webview;
	private Spinner spnCamSelect;
	private String app_title;
	private GoogleAnalyticsTracker tracker;
	private String[] resorts;
	private String[] links;
	
	@Override
	public void onConfigurationChanged (Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	    tracker.stop();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		tracker.trackPageView("/Main");
		tracker.setCustomVar(1, "Device", "Phone");
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tracker = GoogleAnalyticsTracker.getInstance();
        tracker.start("UA-23871335-1", 20, this);
        resorts = getResources().getStringArray(R.array.resort_options);
        app_title = getString(R.string.app_name);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        getWindow().setFeatureInt( Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);
        setContentView(R.layout.main_phone);
        webview = (WebView) findViewById(R.id.webview);
        spnCamSelect = (Spinner) findViewById(R.id.spnCamSelect);
        
        Map<String, Object> extras = new HashMap<String, Object>();
        extras.put("color_bg", "D8EBF7");
        extras.put("color_text", "4C586A");
        AdRequest adRequest = new AdRequest();
        adRequest.setExtras(extras);
        AdView adView = (AdView) findViewById(R.id.adView);
        adView.loadAd(adRequest);
        
        if(Utils.CheckSetting(this, "selected_resort")) {
        	try {
        		app_title = app_title + " - " + resorts[Integer.parseInt(Utils.ReadSettings(MainPhone.this, "selected_resort"))];
        		switch(Integer.parseInt(Utils.ReadSettings(MainPhone.this, "selected_resort"))) {
	        		case 0:
	        			load_mtbuller();
	        			break;
	        		case 1:
	        			load_mthotham();
	        			break;
	        		case 2:
	        			load_fallscreek();
	        			break;
	        		case 3:
	        			load_bawbaw();
	        			break;
	        		case 4:
	        			load_perisher();
	        			break;
	        		case 5:
	        			load_thredbo();
	        			break;
	        		case 6:
	        			load_selwyn();
	        			break;
	        		case 7:
	        			load_charlotte();
	        			break;
	        		case 8:
	        			load_lakemountain();
	        			break;
        		}
			} catch (IOException e) {
				Toast.makeText(getApplicationContext(), "Please select a resort first", Toast.LENGTH_SHORT).show();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
        } else {
        	try {
				Utils.WriteSettings(MainPhone.this, "0", "selected_resort");
				app_title = app_title + " - " + resorts[Integer.parseInt(Utils.ReadSettings(MainPhone.this, "selected_resort"))];
				load_mtbuller();
			} catch (IOException e) {
				Toast.makeText(getApplicationContext(), "Please select a resort first", Toast.LENGTH_SHORT).show();
			}
        }
        this.setTitle(app_title);
        webview.setWebViewClient(new MyWebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.setWebChromeClient(new WebChromeClient() {
	    	public void onProgressChanged(WebView view, int progress) {
	    		MainPhone.this.setTitle(getString(R.string.loading));
	    		MainPhone.this.setProgress(progress * 100); 
	    		if(progress == 100) {
	    			MainPhone.this.setTitle(app_title);
	    		}
	    	}
        });
        spnCamSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            	webview.loadUrl(links[pos]);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            	webview.loadData("<html><body><b>" + getString(R.string.noresort) + "</b></body></html>", "text/html", "utf-8");
            }
        });   
    }
    
	private class MyWebViewClient extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url);
	        return true;
	    }
	}
	
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.phone_menu, menu);
        return true;
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
	        case R.id.menu1:
	        	tracker.trackEvent("Menu", "Change Resort", "", 0);
	        	Dialog selectView = new AlertDialog.Builder(MainPhone.this)
				.setTitle(R.string.changeresort)
				.setItems(R.array.resort_options, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						tracker.trackEvent("Load Resorts", resorts[which], "", 0);
						app_title = getString(R.string.app_name) + " - " + resorts[which];
						try {
							switch(which) {
							case 0:
								Utils.WriteSettings(MainPhone.this, "0", "selected_resort");
								load_mtbuller();
								break;
							case 1:
								Utils.WriteSettings(MainPhone.this, "1", "selected_resort");
								load_mthotham();
								break;
							case 2:
								Utils.WriteSettings(MainPhone.this, "2", "selected_resort");
								load_fallscreek();
								break;
							case 3:
								Utils.WriteSettings(MainPhone.this, "3", "selected_resort");
								load_bawbaw();
								break;
							case 4:
								Utils.WriteSettings(MainPhone.this, "4", "selected_resort");
								load_perisher();
								break;
							case 5:
								Utils.WriteSettings(MainPhone.this, "5", "selected_resort");
								load_thredbo();
								break;
							case 6:
								Utils.WriteSettings(MainPhone.this, "6", "selected_resort");
								load_selwyn();
								break;
							case 7:
								Utils.WriteSettings(MainPhone.this, "7", "selected_resort");
								load_charlotte();
								break;
							case 8:
								Utils.WriteSettings(MainPhone.this, "8", "selected_resort");
								load_lakemountain();
								break;
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
						MainPhone.this.setTitle(app_title);
					}
				})
				.setNegativeButton(R.string.close, null)
				.create();
	        	selectView.show();
	        	
	        	return true;
	        case R.id.menu2:
	        	tracker.trackEvent("Menu", "Reload", "", 0);
	        	webview.reload();
	        	return true;
	        case R.id.menu3:
	        	tracker.trackEvent("Menu", "About", "", 0);
	        	Utils.About(this);
	        	return true;
	        case R.id.menu4:
	        	tracker.trackEvent("Menu", "Weather", "", 0);
				try {
					Utils.toastWeather(getApplicationContext(), Integer.parseInt(Utils.ReadSettings(MainPhone.this, "selected_resort")));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        	return true;
    	}
        return false;
    }
    
    private void load_mtbuller() {
    	links = getResources().getStringArray(R.array.mtbuller_links);
    	ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(MainPhone.this, R.array.mtbuller, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCamSelect.setAdapter(adapter);
    }
    
    private void load_mthotham() {
    	links = getResources().getStringArray(R.array.mthotham_links);
    	ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.mthotham, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCamSelect.setAdapter(adapter);
    }
    
    private void load_fallscreek() {
    	links = getResources().getStringArray(R.array.fallscreek_links);
    	ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.fallscreek, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCamSelect.setAdapter(adapter);
    }
    
    private void load_bawbaw() {
    	links = getResources().getStringArray(R.array.bawbaw_links);
    	ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.bawbaw, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCamSelect.setAdapter(adapter);
    }
    
    private void load_perisher(){
    	links = getResources().getStringArray(R.array.perisher_links);
    	ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.perisher, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCamSelect.setAdapter(adapter);
    }
    
    private void load_thredbo() {
    	links = getResources().getStringArray(R.array.thredbo_links);
    	ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.thredbo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCamSelect.setAdapter(adapter);
    }
    
    private void load_selwyn() {
    	links = getResources().getStringArray(R.array.selwyn_links);
    	ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.selwyn, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCamSelect.setAdapter(adapter);
    }
    
    private void load_charlotte() {
    	links = getResources().getStringArray(R.array.charlotte_links);
    	ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.charlotte, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCamSelect.setAdapter(adapter);
    }
    
    private void load_lakemountain() {
    	links = getResources().getStringArray(R.array.lakemountain_links);
    	ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.lakemountain, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCamSelect.setAdapter(adapter);
    }
}