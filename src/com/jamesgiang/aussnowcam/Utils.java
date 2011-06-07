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
