package com.jamesgiang.aussnowcam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Launch extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(android.os.Build.VERSION.SDK_INT == 11 || android.os.Build.VERSION.SDK_INT == 12  ) {
			Intent i = new Intent();
			i.setClassName("com.jamesgiang.aussnowcam", "com.jamesgiang.aussnowcam.MainHoneycomb");
			startActivity(i);
			finish();
		} else {
			Intent i = new Intent();
			i.setClassName("com.jamesgiang.aussnowcam", "com.jamesgiang.aussnowcam.MainPhone");
			startActivity(i);
			finish();
		}
	}
}