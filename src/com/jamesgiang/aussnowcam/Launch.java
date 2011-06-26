/**
 * AusSnowCam: View snowcams from Australia's snow resorts
 * File: Launch.java
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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Launch extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(android.os.Build.VERSION.SDK_INT == 11 || android.os.Build.VERSION.SDK_INT == 12  ) {
			Intent i = new Intent();
			i.setClassName("com.jamesgiang.aussnowcam", "com.jamesgiang.aussnowcam.MainTablet");
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