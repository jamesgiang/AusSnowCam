package com.jamesgiang.aussnowcam;
import com.loopj.android.http.*;

public class BOMClient {
	private static final String BASE_URL = "http://reg.bom.gov.au/fwo/";
	private static AsyncHttpClient client = new AsyncHttpClient();
	public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.get(getAbsoluteUrl(url), params, responseHandler);
	}
	private static String getAbsoluteUrl(String relativeUrl) {
		return BASE_URL + relativeUrl;
	}
}
