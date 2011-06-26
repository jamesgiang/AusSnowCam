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
import android.app.AlertDialog;
import android.content.Context;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Gravity;
import android.widget.TextView;

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
}
