/**
 * AusSnowCam: View snowcams from Australia's snow resorts
 * File: CamList.java
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

package com.jamesgiang.aussnowcam.Fragments;

import com.jamesgiang.aussnowcam.R;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CamList extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    	showCam(position);
    }

    void showCam(int index) {
    	getListView().setItemChecked(index, true);
        String[] links;
        CamViewer fragment = (CamViewer) getFragmentManager().findFragmentById(R.id.camviewer);
        switch(getActivity().getActionBar().getSelectedNavigationIndex()) {
	        case 0:
	        	links = getResources().getStringArray(R.array.mtbuller_links);
	        	fragment.loadCam(links[index]);
	        	break;
	        case 1:
	        	links = getResources().getStringArray(R.array.mthotham_links);
	        	fragment.loadCam(links[index]);
	        	break;
	        case 2:
	        	links = getResources().getStringArray(R.array.fallscreek_links);
	        	fragment.loadCam(links[index]);
	        	break;
	        case 3:
	        	links = getResources().getStringArray(R.array.bawbaw_links);
	        	fragment.loadCam(links[index]);
	        	break;
	        case 4:
	        	links = getResources().getStringArray(R.array.perisher_links);
	        	fragment.loadCam(links[index]);
	        	break;
	        case 5:
	        	links = getResources().getStringArray(R.array.thredbo_links);
	        	fragment.loadCam(links[index]);
	        	break;
	        case 6:
	        	links = getResources().getStringArray(R.array.selwyn_links);
	        	fragment.loadCam(links[index]);
	        	break;
	        case 7:
	        	links = getResources().getStringArray(R.array.charlotte_links);
	        	fragment.loadCam(links[index]);
	        	break;
	        case 8:
	        	links = getResources().getStringArray(R.array.lakemountain_links);
	        	fragment.loadCam(links[index]);
	        	break;
        }
    }
    public void refreshList(int resort) {
    	switch(resort) {
    	case 0:
    		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.mtbuller)));
    		break;
    	case 1:
    		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.mthotham)));
    		break;
    	case 2:
    		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.fallscreek)));
    		break;
    	case 3:
    		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.bawbaw)));
    		break;
    	case 4:
    		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.perisher)));
    		break;
    	case 5:
    		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.thredbo)));
    		break;
    	case 6:
    		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.selwyn)));
    		break;
    	case 7:
    		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.charlotte)));
    		break;
    	case 8:
    		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.lakemountain)));
    		break;
    	}
    	showCam(0);
    }
}