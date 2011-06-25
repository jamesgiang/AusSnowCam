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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
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

public class MainHoneycomb extends Activity {
	private static final int MENU1 = Menu.FIRST;
	private static final int MENU2 = Menu.FIRST + 1;
	private static final int MENU3 = Menu.FIRST + 2;
	private WebView webview;
	private Spinner spnCamSelect;
	private String app_title;
	private GoogleAnalyticsTracker tracker;
	private String[] cams;
	
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
		tracker.trackPageView("/MainPhone");
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tracker = GoogleAnalyticsTracker.getInstance();
        tracker.start("UA-23871335-1", 20, this);
        
        app_title = getString(R.string.app_name);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        getWindow().setFeatureInt( Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);
        setContentView(R.layout.main);
        webview = (WebView) findViewById(R.id.webview);
        spnCamSelect = (Spinner) findViewById(R.id.spnCamSelect);
        AdView adView = (AdView) findViewById(R.id.adView);
        adView.loadAd(new AdRequest());
        if(Utils.CheckSetting(this, "selected_resort")) {
        	try {
				if(Utils.ReadSettings(MainHoneycomb.this, "selected_resort").equalsIgnoreCase("1")) {
					load_mtbuller();
				} else if(Utils.ReadSettings(MainHoneycomb.this, "selected_resort").equalsIgnoreCase("2")){
					load_mthotham();
				} else if(Utils.ReadSettings(MainHoneycomb.this, "selected_resort").equalsIgnoreCase("3")){
					load_fallscreek();
				} else if(Utils.ReadSettings(MainHoneycomb.this, "selected_resort").equalsIgnoreCase("4")){
					load_bawbaw();
				} else if(Utils.ReadSettings(MainHoneycomb.this, "selected_resort").equalsIgnoreCase("5")){
					load_perisher();
				} else if(Utils.ReadSettings(MainHoneycomb.this, "selected_resort").equalsIgnoreCase("6")){
					load_thredbo();
				} else if(Utils.ReadSettings(MainHoneycomb.this, "selected_resort").equalsIgnoreCase("7")){
					load_selwyn();
				} else if(Utils.ReadSettings(MainHoneycomb.this, "selected_resort").equalsIgnoreCase("8")){
					load_charlotte();
				} else if(Utils.ReadSettings(MainHoneycomb.this, "selected_resort").equalsIgnoreCase("9")){
					load_lakemountain();
				}
			} catch (IOException e) {
				Toast.makeText(getApplicationContext(), "Please select a resort first", Toast.LENGTH_SHORT).show();
			}
        } else {
        	try {
				Utils.WriteSettings(MainHoneycomb.this, "1", "selected_resort");
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
	    		MainHoneycomb.this.setTitle(getString(R.string.loading));
	    		MainHoneycomb.this.setProgress(progress * 100); 
	    		if(progress == 100) {
	    			MainHoneycomb.this.setTitle(app_title);
	    		}
	    	}
        });
        spnCamSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            	int resort;
				try {
					resort = Integer.parseInt(Utils.ReadSettings(MainHoneycomb.this, "selected_resort"));
				} catch (NumberFormatException e) {
					resort = 0;
				} catch (IOException e) {
					resort = 0;
				}
				switch(resort){
					case 0:
						webview.loadData("<html><body><b>" + getString(R.string.noresort) + "</b></body></html>", "text/html", "utf-8");
	                	break;
					case 1:
						switch(pos) {
			                case 0:
			                	webview.loadUrl("http://www.mtbuller.com.au/snowcam/img_2.jpg");
			                	tracker.trackEvent(getString(R.string.mtbuller), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 1:
			                	webview.loadUrl("http://www.mtbuller.com.au/snowcam/img_3.jpg");
			                	tracker.trackEvent(getString(R.string.mtbuller), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 2:
			                	webview.loadUrl("http://www.mtbuller.com.au/snowcam/img_5.jpg");
			                	tracker.trackEvent(getString(R.string.mtbuller), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 3:
			                	webview.loadUrl("http://www.mtbuller.com.au/snowcam/img_6.jpg");
			                	tracker.trackEvent(getString(R.string.mtbuller), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 4:
			                	webview.loadUrl("http://www.mtbuller.com.au/snowcam/img_7.jpg");
			                	tracker.trackEvent(getString(R.string.mtbuller), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 5:
			                	webview.loadUrl("http://www.mtbuller.com.au/snowcam/img_1.jpg");
			                	tracker.trackEvent(getString(R.string.mtbuller), "Cam - " + cams[pos], "", 0);
			                	break;
			                default:
			                	webview.loadData("<html><body><b>" + getString(R.string.nocam) + "</b></body></html>", "text/html", "utf-8");
			                	tracker.trackEvent(getString(R.string.mtbuller), "Cam - Error", "", 0);
						}
						break;
					case 2:
						switch(pos) {
			                case 0:
			                	webview.loadUrl("http://hotham.ski.com.au/cams/hotham-snakegully1.jpg");
			                	tracker.trackEvent(getString(R.string.mthotham), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 1:
			                	webview.loadUrl("http://hotham.ski.com.au/cams/hotham-bigd1.jpg");
			                	tracker.trackEvent(getString(R.string.mthotham), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 2:
			                	 webview.loadUrl("http://hotham.ski.com.au/cams/hotham-central1.jpg");
			                	 tracker.trackEvent(getString(R.string.mthotham), "Cam - " + cams[pos], "", 0);
			                	 break;
			                case 3:
			                	 webview.loadUrl("http://hotham.ski.com.au/cams/hotham-heavenly1.jpg");
			                	 tracker.trackEvent(getString(R.string.mthotham), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 4:
			                	 webview.loadUrl("http://hotham.ski.com.au/cams/dinnerplain1.jpg");
			                	 tracker.trackEvent(getString(R.string.mthotham), "Cam - " + cams[pos], "", 0);
			                	break;
			                default:
			                	webview.loadData("<html><body><b>" + getString(R.string.nocam) + "</b></body></html>", "text/html", "utf-8");
			                	tracker.trackEvent(getString(R.string.mthotham), "Cam - Error", "", 0);
						}
						break;
					case 3:
						switch(pos) {
			                case 0:
			                	webview.loadUrl("http://fallscreek.ski.com.au/cams/fallscreek-drovers1.jpg");
			                	tracker.trackEvent(getString(R.string.fallscreek), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 1:
			                	 webview.loadUrl("http://fallscreek.ski.com.au/cams/fallscreek-drovers8.jpg");
			                	 tracker.trackEvent(getString(R.string.fallscreek), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 2:
			                	 webview.loadUrl("http://fallscreek.ski.com.au/cams/fallscreek-towers2.jpg");
			                	 tracker.trackEvent(getString(R.string.fallscreek), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 3:
			                	 webview.loadUrl("http://fallscreek.ski.com.au/cams/fallscreek-dickey1.jpg");
			                	 tracker.trackEvent(getString(R.string.fallscreek), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 4:
			                	 webview.loadUrl("http://fallscreek.ski.com.au/cams/fallscreek-express4.jpg");
			                	 tracker.trackEvent(getString(R.string.fallscreek), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 5:
			                	 webview.loadUrl("http://fallscreek.ski.com.au/cams/fallscreek-nissen6.jpg");
			                	 tracker.trackEvent(getString(R.string.fallscreek), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 6:
			                	 webview.loadUrl("http://fallscreek.ski.com.au/cams/fallscreek-nissen1.jpg");
			                	 tracker.trackEvent(getString(R.string.fallscreek), "Cam - " + cams[pos], "", 0);
			                	break;
			                default:
			                	webview.loadData("<html><body><b>" + getString(R.string.nocam) + "</b></body></html>", "text/html", "utf-8");
			                	tracker.trackEvent(getString(R.string.fallscreek), "Cam - Error", "", 0);
						}
						break;
					case 4:
						switch(pos) {
			                case 0:
			                	webview.loadUrl("http://www.mountbawbaw.com.au/images/snowcams/camera3.jpg");
			                	tracker.trackEvent(getString(R.string.bawbaw), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 1:
			                	webview.loadUrl("http://www.mountbawbaw.com.au/images/snowcams/camera2.jpg");
			                	tracker.trackEvent(getString(R.string.bawbaw), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 2:
			                	webview.loadUrl("http://www.mountbawbaw.com.au/images/snowcams/camera1.jpg");
			                	tracker.trackEvent(getString(R.string.bawbaw), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 3:
			                	webview.loadUrl("http://www.mountbawbaw.com.au/images/snowcams/camera4.jpg");
			                	tracker.trackEvent(getString(R.string.bawbaw), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 4:
			                	webview.loadUrl("http://www.mountbawbaw.com.au/images/snowcams/camera5.jpg");
			                	tracker.trackEvent(getString(R.string.bawbaw), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 5:
			                	webview.loadUrl("http://www.mountbawbaw.com.au/images/snowcams/camera6.jpg");
			                	tracker.trackEvent(getString(R.string.bawbaw), "Cam - " + cams[pos], "", 0);
			                	break;
			                default:
			                	webview.loadData("<html><body><b>" + getString(R.string.nocam) + "</b></body></html>", "text/html", "utf-8");
			                	tracker.trackEvent(getString(R.string.bawbaw), "Cam - Error", "", 0);
						}
						break;
					case 5:
						switch(pos) {
			                case 0:
			                	webview.loadUrl("http://perisher.com.au/images/snowcams/Xv8.jpg");
			                	tracker.trackEvent(getString(R.string.perisher), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 1:
			                	webview.loadUrl("http://perisher.com.au/images/snowcams/Xfront.jpg");
			                	tracker.trackEvent(getString(R.string.perisher), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 2:
			                	webview.loadUrl("http://perisher.com.au/images/snowcams/xnorthp.jpg");
			                	tracker.trackEvent(getString(R.string.perisher), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 3:
			                	webview.loadUrl("http://perisher.com.au/images/snowcams/Xmtp.jpg");
			                	tracker.trackEvent(getString(R.string.perisher), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 4:
			                	webview.loadUrl("http://perisher.com.au/images/snowcams/Xbluecow.jpg");
			                	tracker.trackEvent(getString(R.string.perisher), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 5:
			                	webview.loadUrl("http://perisher.com.au/images/snowcams/Xsmiggin.jpg");
			                	tracker.trackEvent(getString(R.string.perisher), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 6:
			                	webview.loadUrl("http://perisher.com.au/images/snowcams/Xridge.jpg");
			                	tracker.trackEvent(getString(R.string.perisher), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 7:
			                	webview.loadUrl("http://perisher.com.au/images/snowcams/Xcowt.jpg");
			                	tracker.trackEvent(getString(R.string.perisher), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 8:
			                	webview.loadUrl("http://perisher.com.au/images/snowcams/Xtube.jpg");
			                	tracker.trackEvent(getString(R.string.perisher), "Cam - " + cams[pos], "", 0);
			                	break;
			                default:
			                	webview.loadData("<html><body><b>" + getString(R.string.nocam) + "</b></body></html>", "text/html", "utf-8");
			                	tracker.trackEvent(getString(R.string.perisher), "Cam - Error", "", 0);
						}
						break;
					case 6:
						switch(pos) {
			                case 0:
			                	webview.loadUrl("http://www.thredbo.com.au/liveimages/Basin01.jpg");
			                	tracker.trackEvent(getString(R.string.thredbo), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 1:
			                	webview.loadUrl("http://www.thredbo.com.au/liveimages/EaglesNest01.jpg");
			                	tracker.trackEvent(getString(R.string.thredbo), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 2:
			                	webview.loadUrl("http://www.thredbo.com.au/liveimages/AlpineWay01.jpg");
			                	tracker.trackEvent(getString(R.string.thredbo), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 3:
			                	webview.loadUrl("http://www.thredbo.com.au/liveimages/KosciuszkoExpress01.jpg");
			                	tracker.trackEvent(getString(R.string.thredbo), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 4:
			                	webview.loadUrl("http://www.thredbo.com.au/liveimages/AlpineWay02.jpg");
			                	tracker.trackEvent(getString(R.string.thredbo), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 5:
			                	webview.loadUrl("http://www.thredbo.com.au/liveimages/Cruiser01.jpg");
			                	tracker.trackEvent(getString(R.string.thredbo), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 6:
			                	webview.loadUrl("http://www.thredbo.com.au/liveimages/FridayFlat01.jpg");
			                	tracker.trackEvent(getString(R.string.thredbo), "Cam - " + cams[pos], "", 0);
			                	break;
			                default:
			                	webview.loadData("<html><body><b>" + getString(R.string.nocam) + "</b></body></html>", "text/html", "utf-8");
						}
						break;
					case 7:
						switch(pos) {
			                case 0:
			                	webview.loadUrl("http://selwynsnowcams.com/view/images/camera_01_latest/c01_left_000M.jpg");
			                	tracker.trackEvent(getString(R.string.selwyn), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 1:
			                	webview.loadUrl("http://selwynsnowcams.com/view/images/camera_01_latest/c01_right_000M.jpg");
			                	tracker.trackEvent(getString(R.string.selwyn), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 2:
			                	webview.loadUrl("http://selwynsnowcams.com/view/images/camera_02_latest/c02_left_000M.jpg");
			                	tracker.trackEvent(getString(R.string.selwyn), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 3:
			                	webview.loadUrl("http://selwynsnowcams.com/view/images/camera_02_latest/c02_right_000M.jpg");
			                	tracker.trackEvent(getString(R.string.selwyn), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 4:
			                	webview.loadUrl("http://selwynsnowcams.com/view/images/camera_03_latest/c03_000M.jpg");
			                	tracker.trackEvent(getString(R.string.selwyn), "Cam - " + cams[pos], "", 0);
			                	break;
			                default:
			                	webview.loadData("<html><body><b>" + getString(R.string.nocam) + "</b></body></html>", "text/html", "utf-8");
			                	tracker.trackEvent(getString(R.string.selwyn), "Cam - Error", "", 0);
						}
						break;
					case 8:
						switch(pos) {
			                case 0:
			                	webview.loadUrl("http://webcam.charlottepass.com.au/charlotte1_900.jpg");
			                	tracker.trackEvent(getString(R.string.charlotte), "Cam - " + cams[pos], "", 0);
			                	break;
			                case 1:
			                	webview.loadUrl("http://webcam.charlottepass.com.au/charlotte2_900.jpg");
			                	tracker.trackEvent(getString(R.string.charlotte), "Cam - " + cams[pos], "", 0);
			                	break;
			                default:
			                	webview.loadData("<html><body><b>" + getString(R.string.nocam) + "</b></body></html>", "text/html", "utf-8");
			                	tracker.trackEvent(getString(R.string.charlotte), "Cam - Error", "", 0);
						}
						break;
					case 9:
						switch(pos) {
							case 0:
								webview.loadUrl("http://www.murrindindicomputers.com.au/lakemountain/trun.jpg");
								tracker.trackEvent(getString(R.string.lakemountain), "Cam - " + cams[pos], "", 0);
								break;
							case 1:
								webview.loadUrl("http://www.murrindindicomputers.com.au/lakemountain/village.jpg");
								tracker.trackEvent(getString(R.string.lakemountain), "Cam - " + cams[pos], "", 0);
								break;
							case 2:
								webview.loadUrl("http://lakemountainresort.com.au/~webcam/1.jpg");
								tracker.trackEvent(getString(R.string.lakemountain), "Cam - " + cams[pos], "", 0);
								break;
							case 3:
								webview.loadUrl("http://lakemountainresort.com.au/~webcam/2.jpg");
								tracker.trackEvent(getString(R.string.lakemountain), "Cam - " + cams[pos], "", 0);
								break;
							case 4:
								webview.loadUrl("http://lakemountainresort.com.au/~webcam/3.jpg");
								tracker.trackEvent(getString(R.string.lakemountain), "Cam - " + cams[pos], "", 0);
								break;
							case 5:
								webview.loadUrl("http://lakemountainresort.com.au/~webcam/4.jpg");
								tracker.trackEvent(getString(R.string.lakemountain), "Cam - " + cams[pos], "", 0);
								break;
							case 6:
								webview.loadUrl("http://lakemountainresort.com.au/~webcam/5.jpg");
								tracker.trackEvent(getString(R.string.lakemountain), "Cam - " + cams[pos], "", 0);
								break;
							case 7:
								webview.loadUrl("http://lakemountainresort.com.au/~webcam/6.jpg");
								tracker.trackEvent(getString(R.string.lakemountain), "Cam - " + cams[pos], "", 0);
								break;
							case 8:
								webview.loadUrl("http://lakemountainresort.com.au/~webcam/7.jpg");
								tracker.trackEvent(getString(R.string.lakemountain), "Cam - " + cams[pos], "", 0);
								break;
							default:
								webview.loadData("<html><body><b>" + getString(R.string.nocam) + "</b></body></html>", "text/html", "utf-8");
			                	tracker.trackEvent(getString(R.string.lakemountain), "Cam - Error", "", 0);
						}
						break;
	                default:
	                	webview.loadData("<html><body><b>" + getString(R.string.noresort) + "</b></body></html>", "text/html", "utf-8");
            	}
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
    	menu.add(0, MENU1, 0, R.string.menu1);
    	menu.add(0, MENU2, 0, R.string.menu2);
        menu.add(0, MENU3, 0, R.string.menu3);
        return true;
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
	        case MENU1:
	        	tracker.trackEvent("Menu", "Change Resort", "", 0);
	        	Dialog selectView = new AlertDialog.Builder(MainHoneycomb.this)
				.setTitle(R.string.changeresort)
				.setItems(R.array.resort_options, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						app_title = getString(R.string.app_name);
						if(which==0) {
							try {
								Utils.WriteSettings(MainHoneycomb.this, "1", "selected_resort");
								load_mtbuller();
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else if(which==1) {
							try {
								Utils.WriteSettings(MainHoneycomb.this, "2", "selected_resort");
								load_mthotham();
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else if(which==2) {
							try {
								Utils.WriteSettings(MainHoneycomb.this, "3", "selected_resort");
								load_fallscreek();
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else if(which==3) {
							try {
								Utils.WriteSettings(MainHoneycomb.this, "4", "selected_resort");
								load_bawbaw();
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else if(which==4) {
							try {
								Utils.WriteSettings(MainHoneycomb.this, "5", "selected_resort");
								load_perisher();
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else if(which==5) {
							try {
								Utils.WriteSettings(MainHoneycomb.this, "6", "selected_resort");
								load_thredbo();
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else if(which==6) {
							try {
								Utils.WriteSettings(MainHoneycomb.this, "7", "selected_resort");
								load_selwyn();
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else if(which==7) {
							try {
								Utils.WriteSettings(MainHoneycomb.this, "8", "selected_resort");
								load_charlotte();
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else if(which==8) {
							try {
								Utils.WriteSettings(MainHoneycomb.this, "9", "selected_resort");
								load_lakemountain();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						MainHoneycomb.this.setTitle(app_title);
					}
				})
				.setNegativeButton(R.string.close, null)
				.create();
	        	selectView.show();
	        	
	        	return true;
	        case MENU2:
	        	tracker.trackEvent("Menu", "Reload", "", 0);
	        	webview.reload();
	        	return true;
	        case MENU3:
	        	tracker.trackEvent("Menu", "About", "", 0);
	        	Utils.About(this);
	        	return true;
    	}
        return false;
    }
    
    private void load_mtbuller() {
    	ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(MainHoneycomb.this, R.array.mtbuller, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCamSelect.setAdapter(adapter);
        app_title = app_title + " - " + getString(R.string.mtbuller);
        cams = getResources().getStringArray(R.array.mtbuller);
        tracker.trackEvent(getString(R.string.mtbuller), "Load resort", "", 0);
    }
    
    private void load_mthotham() {
    	ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.mthotham, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCamSelect.setAdapter(adapter);
        app_title = app_title + " - " + getString(R.string.mthotham);
        cams = getResources().getStringArray(R.array.mthotham);
        tracker.trackEvent(getString(R.string.mthotham), "Load resort", "", 0);
    }
    
    private void load_fallscreek() {
    	ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.fallscreek, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCamSelect.setAdapter(adapter);
        app_title = app_title + " - " + getString(R.string.fallscreek);
        cams = getResources().getStringArray(R.array.fallscreek);
        tracker.trackEvent(getString(R.string.fallscreek), "Load resort", "", 0);
    }
    
    private void load_bawbaw() {
    	ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.bawbaw, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCamSelect.setAdapter(adapter);
        app_title = app_title + " - " + getString(R.string.bawbaw);
        cams = getResources().getStringArray(R.array.bawbaw);
        tracker.trackEvent(getString(R.string.bawbaw), "Load resort", "", 0);
    }
    
    private void load_perisher(){
    	ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.perisher, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCamSelect.setAdapter(adapter);
        app_title = app_title + " - " + getString(R.string.perisher);
        cams = getResources().getStringArray(R.array.perisher);
        tracker.trackEvent(getString(R.string.perisher), "Load resort", "", 0);
    }
    
    private void load_thredbo() {
    	ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.thredbo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCamSelect.setAdapter(adapter);
        app_title = app_title + " - " + getString(R.string.thredbo);
        cams = getResources().getStringArray(R.array.thredbo);
        tracker.trackEvent(getString(R.string.thredbo), "Load resort", "", 0);
    }
    
    private void load_selwyn() {
    	ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.selwyn, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCamSelect.setAdapter(adapter);
        app_title = app_title + " - " + getString(R.string.selwyn);
        cams = getResources().getStringArray(R.array.selwyn);
        tracker.trackEvent(getString(R.string.selwyn), "Load resort", "", 0);
    }
    
    private void load_charlotte() {
    	ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.charlotte, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCamSelect.setAdapter(adapter);
        app_title = app_title + " - " + getString(R.string.charlotte);
        cams = getResources().getStringArray(R.array.charlotte);
        tracker.trackEvent(getString(R.string.charlotte), "Load resort", "", 0);
    }
    
    private void load_lakemountain() {
    	ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.lakemountain, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCamSelect.setAdapter(adapter);
        app_title = app_title + " - " + getString(R.string.lakemountain);
        cams = getResources().getStringArray(R.array.lakemountain);
        tracker.trackEvent(getString(R.string.lakemountain), "Load resort", "", 0);
    }
}