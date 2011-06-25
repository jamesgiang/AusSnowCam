package com.jamesgiang.aussnowcam;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FragmentViewer extends Fragment {
	private View mContentView;
	private static WebView webview;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.viewer_fragment, null);
        return mContentView;
    }
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
		Log.d("test","1");
		super.onActivityCreated(savedInstanceState);
		webview = (WebView) mContentView.findViewById(R.id.webview);
		webview.setWebViewClient(new MyWebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.setWebChromeClient(new WebChromeClient() {
	    	public void onProgressChanged(WebView view, int progress) {
	    		getActivity().setTitle(getString(R.string.loading));
	    		getActivity().setProgress(progress * 100); 
	    		if(progress == 100) {
	    			getActivity().setTitle(getString(R.string.app_name));
	    		}
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
	public void loadCam(String url) {
		webview.loadUrl(url);
	}
	public void refresh() {
		webview.reload();
	}
}