/**
 * AusSnowCam: View snowcams from Australia's snow resorts
 * File: Main.java
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.ads.*;

public class Main extends Activity {
	private static final int MENU1 = Menu.FIRST;
	private static final int MENU2 = Menu.FIRST + 1;
	private static final int MENU3 = Menu.FIRST + 2;
	private WebView webview;
	private Spinner spnCamSelect;
	private String app_title;
	
	
	@Override
	public void onConfigurationChanged (Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app_title = getString(R.string.app_name);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        getWindow().setFeatureInt( Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);
        setContentView(R.layout.main);
        webview = (WebView) findViewById(R.id.webview);
        spnCamSelect = (Spinner) findViewById(R.id.spnCamSelect);
        
        AdView adView = new AdView(this, AdSize.BANNER, "a14dee1ce46976e");
        LinearLayout layout = (LinearLayout)findViewById(R.id.header);
        layout.addView(adView);
        adView.loadAd(new AdRequest());
        
        
        if(Utils.CheckSetting(this, "selected_resort")) {
        	try {
				if(Utils.ReadSettings(Main.this, "selected_resort").equalsIgnoreCase("1")) {
					ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.mtbuller, android.R.layout.simple_spinner_item);
			        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			        spnCamSelect.setAdapter(adapter);
			        app_title = app_title + " - " + getString(R.string.mtbuller);
				} else if(Utils.ReadSettings(Main.this, "selected_resort").equalsIgnoreCase("2")){
					ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.mthotham, android.R.layout.simple_spinner_item);
			        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			        spnCamSelect.setAdapter(adapter);
			        app_title = app_title + " - " + getString(R.string.mthotham);
				} else if(Utils.ReadSettings(Main.this, "selected_resort").equalsIgnoreCase("3")){
					ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.fallscreek, android.R.layout.simple_spinner_item);
			        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			        spnCamSelect.setAdapter(adapter);
			        app_title = app_title + " - " + getString(R.string.fallscreek);
				} else if(Utils.ReadSettings(Main.this, "selected_resort").equalsIgnoreCase("4")){
					ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.bawbaw, android.R.layout.simple_spinner_item);
			        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			        spnCamSelect.setAdapter(adapter);
			        app_title = app_title + " - " + getString(R.string.bawbaw);
				} else if(Utils.ReadSettings(Main.this, "selected_resort").equalsIgnoreCase("5")){
					ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.perisher, android.R.layout.simple_spinner_item);
			        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			        spnCamSelect.setAdapter(adapter);
			        app_title = app_title + " - " + getString(R.string.perisher);
				} else if(Utils.ReadSettings(Main.this, "selected_resort").equalsIgnoreCase("6")){
					ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.thredbo, android.R.layout.simple_spinner_item);
			        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			        spnCamSelect.setAdapter(adapter);
			        app_title = app_title + " - " + getString(R.string.thredbo);
				}
			} catch (IOException e) {
				Toast.makeText(getApplicationContext(), "Please select a resort first", Toast.LENGTH_SHORT).show();
			}
        } else {
        	try {
				Utils.WriteSettings(Main.this, "1", "selected_resort");
				ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.mtbuller, android.R.layout.simple_spinner_item);
		        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		        spnCamSelect.setAdapter(adapter);
		        app_title = app_title + " - " + getString(R.string.mtbuller);
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
	    		Main.this.setTitle(getString(R.string.loading));
	    		Main.this.setProgress(progress * 100); 
	    		if(progress == 100) {
	    			Main.this.setTitle(app_title);
	    		}
	    	}
        });
        
        spnCamSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            	int resort;
				try {
					resort = Integer.parseInt(Utils.ReadSettings(Main.this, "selected_resort"));
				} catch (NumberFormatException e) {
					resort = 0;
				} catch (IOException e) {
					resort = 0;
				}
				Log.d("test", Integer.toString(resort));
				switch(resort){
					case 0:
						webview.loadData("<html><body><b>" + getString(R.string.noresort) + "</b></body></html>", "text/html", "utf-8");
	                	break;
					case 1:
						switch(pos) {
			                case 0:
			                	webview.loadUrl("http://www.mtbuller.com.au/snowcam/img_2.jpg");
			                	break;
			                case 1:
			                	 webview.loadUrl("http://www.mtbuller.com.au/snowcam/img_3.jpg");
			                	break;
			                case 2:
			                	 webview.loadUrl("http://www.mtbuller.com.au/snowcam/img_5.jpg");
			                	break;
			                case 3:
			                	 webview.loadUrl("http://www.mtbuller.com.au/snowcam/img_6.jpg");
			                	break;
			                case 4:
			                	 webview.loadUrl("http://www.mtbuller.com.au/snowcam/img_7.jpg");
			                	break;
			                default:
			                	webview.loadData("<html><body><b>" + getString(R.string.nocam) + "</b></body></html>", "text/html", "utf-8");
						}
						break;
					case 2:
						switch(pos) {
			                case 0:
			                	webview.loadUrl("http://hotham.ski.com.au/cams/hotham-snakegully1.jpg");
			                	break;
			                case 1:
			                	 webview.loadUrl("http://hotham.ski.com.au/cams/hotham-bigd1.jpg");
			                	break;
			                case 2:
			                	 webview.loadUrl("http://hotham.ski.com.au/cams/hotham-central1.jpg");
			                	break;
			                case 3:
			                	 webview.loadUrl("http://hotham.ski.com.au/cams/hotham-heavenly1.jpg");
			                	break;
			                case 4:
			                	 webview.loadUrl("http://hotham.ski.com.au/cams/dinnerplain1.jpg");
			                	break;
			                default:
			                	webview.loadData("<html><body><b>" + getString(R.string.nocam) + "</b></body></html>", "text/html", "utf-8");
						}
						break;
					case 3:
						switch(pos) {
			                case 0:
			                	webview.loadUrl("http://fallscreek.ski.com.au/cams/fallscreek-drovers1.jpg");
			                	break;
			                case 1:
			                	 webview.loadUrl("http://fallscreek.ski.com.au/cams/fallscreek-drovers8.jpg");
			                	break;
			                case 2:
			                	 webview.loadUrl("http://fallscreek.ski.com.au/cams/fallscreek-towers2.jpg");
			                	break;
			                case 3:
			                	 webview.loadUrl("http://fallscreek.ski.com.au/cams/fallscreek-dickey1.jpg");
			                	break;
			                case 4:
			                	 webview.loadUrl("http://fallscreek.ski.com.au/cams/fallscreek-express4.jpg");
			                	break;
			                case 5:
			                	 webview.loadUrl("http://fallscreek.ski.com.au/cams/fallscreek-nissen6.jpg");
			                	break;
			                case 6:
			                	 webview.loadUrl("http://fallscreek.ski.com.au/cams/fallscreek-nissen1.jpg");
			                	break;
			                default:
			                	webview.loadData("<html><body><b>" + getString(R.string.nocam) + "</b></body></html>", "text/html", "utf-8");
						}
						break;
					case 4:
						switch(pos) {
			                case 0:
			                	webview.loadUrl("http://www.mountbawbaw.com.au/images/snowcams/camera3.jpg");
			                	break;
			                case 1:
			                	 webview.loadUrl("http://www.mountbawbaw.com.au/images/snowcams/camera2.jpg");
			                	break;
			                case 2:
			                	 webview.loadUrl("http://www.mountbawbaw.com.au/images/snowcams/camera1.jpg");
			                	break;
			                case 3:
			                	 webview.loadUrl("http://www.mountbawbaw.com.au/images/snowcams/camera4.jpg");
			                	break;
			                case 4:
			                	 webview.loadUrl("http://www.mountbawbaw.com.au/images/snowcams/camera5.jpg");
			                	break;
			                case 5:
			                	webview.loadUrl("http://www.mountbawbaw.com.au/images/snowcams/camera6.jpg");
			                	break;
			                default:
			                	webview.loadData("<html><body><b>" + getString(R.string.nocam) + "</b></body></html>", "text/html", "utf-8");
						}
						break;
					case 5:
						switch(pos) {
			                case 0:
			                	webview.loadUrl("http://perisher.com.au/images/snowcams/Xv8.jpg");
			                	break;
			                case 1:
			                	 webview.loadUrl("http://perisher.com.au/images/snowcams/Xfront.jpg");
			                	break;
			                case 2:
			                	 webview.loadUrl("http://perisher.com.au/images/snowcams/xnorthp.jpg");
			                	break;
			                case 3:
			                	 webview.loadUrl("http://perisher.com.au/images/snowcams/Xmtp.jpg");
			                	break;
			                case 4:
			                	 webview.loadUrl("http://perisher.com.au/images/snowcams/Xbluecow.jpg");
			                	break;
			                case 5:
			                	webview.loadUrl("http://perisher.com.au/images/snowcams/Xsmiggin.jpg");
			                	break;
			                case 6:
			                	webview.loadUrl("http://perisher.com.au/images/snowcams/Xridge.jpg");
			                	break;
			                case 7:
			                	webview.loadUrl("http://perisher.com.au/images/snowcams/Xcowt.jpg");
			                	break;
			                case 8:
			                	webview.loadUrl("http://perisher.com.au/images/snowcams/Xtube.jpg");
			                	break;
			                default:
			                	webview.loadData("<html><body><b>" + getString(R.string.nocam) + "</b></body></html>", "text/html", "utf-8");
						}
						break;
					case 6:
						switch(pos) {
			                case 0:
			                	webview.loadUrl("http://www.thredbo.com.au/liveimages/Basin01.jpg");
			                	break;
			                case 1:
			                	 webview.loadUrl("http://www.thredbo.com.au/liveimages/EaglesNest01.jpg");
			                	break;
			                case 2:
			                	 webview.loadUrl("http://www.thredbo.com.au/liveimages/AlpineWay01.jpg");
			                	break;
			                case 3:
			                	 webview.loadUrl("http://www.thredbo.com.au/liveimages/KosciuszkoExpress01.jpg");
			                	break;
			                case 4:
			                	 webview.loadUrl("http://www.thredbo.com.au/liveimages/AlpineWay02.jpg");
			                	break;
			                case 5:
			                	webview.loadUrl("http://www.thredbo.com.au/liveimages/Cruiser01.jpg");
			                	break;
			                case 6:
			                	webview.loadUrl("http://www.thredbo.com.au/liveimages/FridayFlat01.jpg");
			                	break;
			                default:
			                	webview.loadData("<html><body><b>" + getString(R.string.nocam) + "</b></body></html>", "text/html", "utf-8");
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
	        	Dialog selectView = new AlertDialog.Builder(Main.this)
				.setTitle(R.string.changeresort)
				.setItems(R.array.resort_options, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						app_title = getString(R.string.app_name);
						if(which==0) {
							try {
								Utils.WriteSettings(Main.this, "1", "selected_resort");
								ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(Main.this, R.array.mtbuller, android.R.layout.simple_spinner_item);
						        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						        spnCamSelect.setAdapter(adapter);
						        app_title = app_title + " - " + getString(R.string.mtbuller);
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else if(which==1) {
							try {
								Utils.WriteSettings(Main.this, "2", "selected_resort");
								ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(Main.this, R.array.mthotham, android.R.layout.simple_spinner_item);
						        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						        spnCamSelect.setAdapter(adapter);
						        app_title = app_title + " - " + getString(R.string.mthotham);
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else if(which==2) {
							try {
								Utils.WriteSettings(Main.this, "3", "selected_resort");
								ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(Main.this, R.array.fallscreek, android.R.layout.simple_spinner_item);
						        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						        spnCamSelect.setAdapter(adapter);
						        app_title = app_title + " - " + getString(R.string.fallscreek);
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else if(which==3) {
							try {
								Utils.WriteSettings(Main.this, "4", "selected_resort");
								ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(Main.this, R.array.bawbaw, android.R.layout.simple_spinner_item);
						        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						        spnCamSelect.setAdapter(adapter);
						        app_title = app_title + " - " + getString(R.string.bawbaw);
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else if(which==4) {
							try {
								Utils.WriteSettings(Main.this, "5", "selected_resort");
								ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(Main.this, R.array.perisher, android.R.layout.simple_spinner_item);
						        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						        spnCamSelect.setAdapter(adapter);
						        app_title = app_title + " - " + getString(R.string.perisher);
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else if(which==5) {
							try {
								Utils.WriteSettings(Main.this, "6", "selected_resort");
								ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(Main.this, R.array.thredbo, android.R.layout.simple_spinner_item);
						        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						        spnCamSelect.setAdapter(adapter);
						        app_title = app_title + " - " + getString(R.string.thredbo);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						Main.this.setTitle(app_title);
					}
				})
				.setNegativeButton(R.string.close, null)
				.create();
	        	selectView.show();
	        	return true;
	        case MENU2:
	        	webview.reload();
	        	return true;
	        case MENU3:
	        	Utils.About(this);
	        	return true;
    	}
        return false;
    }
}