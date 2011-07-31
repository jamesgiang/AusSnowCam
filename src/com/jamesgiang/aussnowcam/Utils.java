/**
 * AusSnowCam: View snowcams from Australia's snow resorts
 * File: Utils.java
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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.json.*;

import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Utils {
	
	public static void About(Context c) {
    	AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(c);
    	dialogBuilder.setTitle(R.string.app_name);
    	dialogBuilder.setIcon(R.drawable.icon);
    	TextView textView = new TextView(c);
    	SpannableString s = new SpannableString(c.getString(R.string.about_info));
    	Linkify.addLinks(s, Linkify.WEB_URLS);
	  	textView.setText(s);
	  	textView.setGravity(Gravity.CENTER);
	  	textView.setMovementMethod(LinkMovementMethod.getInstance());
    	dialogBuilder.setView(textView);
    	dialogBuilder.show();
	}
	
    public static void WriteSettings(Context context, String data, String file) throws IOException {
		FileOutputStream fOut = null; 
		OutputStreamWriter osw = null;
		fOut = context.openFileOutput(file,Context.MODE_PRIVATE);       
		osw = new OutputStreamWriter(fOut); 
		osw.write(data);
		osw.close(); 
		fOut.close(); 
    }
    
    public static String ReadSettings(Context context, String file) throws IOException { 
    	FileInputStream fIn = null; 
		InputStreamReader isr = null;
		String data = null;
		fIn = context.openFileInput(file);       
		isr = new InputStreamReader(fIn); 
		char[] inputBuffer = new char[fIn.available()]; 
		isr.read(inputBuffer); 
		data = new String(inputBuffer);
		isr.close();
		fIn.close(); 
		return data; 
    }
	
	public static boolean CheckSetting(Context context, String file) {
		String[] filenames = context.fileList();
		for (String name : filenames) {
			if (name.equals(file)) {
				return true;
			}
		}
		return false;
	}
	
	public static String getEndPoint(int i) {
		String result = null;
		switch(i) {
		case 0:
			result = "IDV60801/IDV60801.94894.json";
			break;
		case 1:
			result = "IDV60801/IDV60801.94906.json";
			break;
		case 2:
			result = "IDV60801/IDV60801.94903.json";
			break;
		case 3:
			result = "IDV60801/IDV60801.95901.json";
			break;
		case 4:
			result = "IDN60801/IDN60801.94915.json";
			break;
		case 5:
			result = "IDN60801/IDN60801.95908.json";
			break;
		case 6:
			result = null;
			break;
		case 7:
			result = "IDN60801/IDN60801.95912.json";
			break;
		case 8:
			result = null;
			break;
		}
		return result; 
	}
	
	public static void toastWeather(final Context c, int i) {
		String end_point = getEndPoint(i);
		if(end_point != null) {
			BOMClient.get(end_point, null, new JsonHttpResponseHandler() {
	            @Override
	            public void onSuccess(JSONObject response) {
					try {
						String resultString = null;
						JSONObject result = (JSONObject) response.getJSONObject("observations").getJSONArray("data").get(0);
						JSONObject header = (JSONObject) response.getJSONObject("observations").getJSONArray("header").get(0);
						resultString = "Weather Observations for " + header.getString("name") + "\n\n";
						if(result.isNull("air_temp")) {
							resultString = "Temperature: - \n";
						} else {
							resultString += "Temperature: " + result.getString("air_temp") + "\n";
						}
						if(result.isNull("wind_dir")) {
							resultString += "Wind Direction: - \n";
						} else {
							resultString += "Wind Direction: " + result.getString("wind_dir") + "\n";
						}
						if(result.isNull("wind_spd_kmh")) {
							resultString += "Wind Speed (km/h): - \n";
						} else {
							resultString += "Wind Speed (km/h): " + result.getString("wind_spd_kmh") + "\n";
						}
						if(result.isNull("gust_kmh")) {
							resultString += "Gust Speed (km/h): - \n";
						} else {
							resultString += "Gust Speed (km/h): " + result.getString("gust_kmh") + "\n";
						}
						if(result.isNull("rain_trace")) {
							resultString += "Rain since 9am (mm): - \n\n";
						} else {
							resultString += "Rain since 9am (mm): " + result.getString("rain_trace") + "\n\n";
						}
						resultString += header.getString("refresh_message");
						Toast.makeText(c, resultString, Toast.LENGTH_LONG).show();
					} catch (JSONException e) {
						Toast.makeText(c, "Weather data not available", Toast.LENGTH_LONG).show();
					}
	            }
	        });
		} else {
			Toast.makeText(c, "Weather data not available", Toast.LENGTH_LONG).show();
		}
    }
	
	public static void loadWeather(final Activity a, int i) {
		final TextView title;
		final TextView data1;
		final TextView data2;
		final TextView data3;
		final TextView data4;
		final TextView data5;
		final TextView data6;
		title = (TextView) a.findViewById(R.id.weatherTitle);
		data1 = (TextView) a.findViewById(R.id.data1);
		data2 = (TextView) a.findViewById(R.id.data2);
		data3 = (TextView) a.findViewById(R.id.data3);
		data4 = (TextView) a.findViewById(R.id.data4);
		data5 = (TextView) a.findViewById(R.id.data5);
		data6 = (TextView) a.findViewById(R.id.data6);
		String end_point = getEndPoint(i);
		if(end_point != null) {
			BOMClient.get(end_point, null, new JsonHttpResponseHandler() {
	            @Override
	            public void onSuccess(JSONObject response) {
					try {
						JSONObject result = (JSONObject) response.getJSONObject("observations").getJSONArray("data").get(0);
						JSONObject header = (JSONObject) response.getJSONObject("observations").getJSONArray("header").get(0);
						data1.setVisibility(View.VISIBLE);
						data2.setVisibility(View.VISIBLE);
						data3.setVisibility(View.VISIBLE);
						data4.setVisibility(View.VISIBLE);
						data5.setVisibility(View.VISIBLE);
						data6.setVisibility(View.VISIBLE);
						title.setText("Weather Observations for " + header.getString("name"));
						if(result.isNull("air_temp")) {
							data1.setText("Temperature: - ");
						} else {
							data1.setText("Temperature: " + result.getString("air_temp"));
						}
						if(result.isNull("wind_dir")) {
							data2.setText("Wind Direction: - ");
						} else {
							data2.setText("Wind Direction: " + result.getString("wind_dir"));
						}
						if(result.isNull("wind_spd_kmh")) {
							data3.setText("Wind Speed (km/h): - ");
						} else {
							data3.setText("Wind Speed (km/h): " + result.getString("wind_spd_kmh"));
						}
						if(result.isNull("gust_kmh")) {
							data4.setText("Gust Speed (km/h): - ");
						} else {
							data4.setText("Gust Speed (km/h): " + result.getString("gust_kmh"));
						}
						if(result.isNull("rain_trace")) {
							data5.setText("Rain since 9am (mm): - ");
						} else {
							data5.setText("Rain since 9am (mm): " + result.getString("rain_trace"));
						}
						data6.setText(header.getString("refresh_message"));
					} catch (JSONException e) {
						title.setText("Weather data not available");
						data1.setVisibility(View.GONE);
						data2.setVisibility(View.GONE);
						data3.setVisibility(View.GONE);
						data4.setVisibility(View.GONE);
						data5.setVisibility(View.GONE);
						data6.setVisibility(View.GONE);
					}
	            }
	        });
		} else {
			title.setText("Weather data not available");
			data1.setVisibility(View.GONE);
			data2.setVisibility(View.GONE);
			data3.setVisibility(View.GONE);
			data4.setVisibility(View.GONE);
			data5.setVisibility(View.GONE);
			data6.setVisibility(View.GONE);
		}
    }
}
