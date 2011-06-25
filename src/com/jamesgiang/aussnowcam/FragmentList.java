package com.jamesgiang.aussnowcam;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmentList extends ListFragment {
    int mCurCheckPosition = 0;

    @Override
    public void onResume() {
    	super.onResume();
    	showDetails(mCurCheckPosition);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showDetails(position);
    }

    void showDetails(int index) {
        mCurCheckPosition = index;
        getListView().setItemChecked(index, true);
        FragmentViewer fragment = (FragmentViewer) getFragmentManager().findFragmentById(R.id.viewer);
        switch(getActivity().getActionBar().getSelectedNavigationIndex()) {
        case 0:
        	switch(index) {
	            case 0:
	            	fragment.loadCam("http://www.mtbuller.com.au/snowcam/img_2.jpg");
	            	//tracker.trackEvent(getString(R.string.mtbuller), "Cam - " + cams[pos], "", 0);
	            	break;
	            case 1:
	            	fragment.loadCam("http://www.mtbuller.com.au/snowcam/img_3.jpg");
//	            	tracker.trackEvent(getString(R.string.mtbuller), "Cam - " + cams[pos], "", 0);
	            	break;
	            case 2:
	            	fragment.loadCam("http://www.mtbuller.com.au/snowcam/img_5.jpg");
//	            	tracker.trackEvent(getString(R.string.mtbuller), "Cam - " + cams[pos], "", 0);
	            	break;
	            case 3:
	            	fragment.loadCam("http://www.mtbuller.com.au/snowcam/img_6.jpg");
//	            	tracker.trackEvent(getString(R.string.mtbuller), "Cam - " + cams[pos], "", 0);
	            	break;
	            case 4:
	            	fragment.loadCam("http://www.mtbuller.com.au/snowcam/img_7.jpg");
//	            	tracker.trackEvent(getString(R.string.mtbuller), "Cam - " + cams[pos], "", 0);
	            	break;
	            case 5:
	            	fragment.loadCam("http://www.mtbuller.com.au/snowcam/img_1.jpg");
//	            	tracker.trackEvent(getString(R.string.mtbuller), "Cam - " + cams[pos], "", 0);
	            	break;
        	}
        	break;
        case 1:
        	break;
        }
    }
    
    public void refreshList(int resort) {
    	switch(resort) {
    	case 1:
    		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.mtbuller)));
    		break;
    	case 2:
    		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.mthotham)));
    		break;
    	case 3:
    		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.fallscreek)));
    		break;
    	case 4:
    		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.bawbaw)));
    		break;
    	case 5:
    		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.perisher)));
    		break;
    	case 6:
    		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.thredbo)));
    		break;
    	case 7:
    		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.selwyn)));
    		break;
    	case 8:
    		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.charlotte)));
    		break;
    	case 9:
    		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.lakemountain)));
    		break;
    	default:
    		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.mtbuller)));
    		break;
    	}
    }
}