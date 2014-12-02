package com.example.article;

import android.net.Uri;

public class Articles {
	public static final String ID = "_id";
	public static final String TITLE = "_title";
	public static final String ABSTRACT = "_abstract";
	public static final String URL = "_url";
	
	public static final String DEFAULT_SORT_ORDER = "_id asc";
	
	public static final String METHOD_GET_ITEM_COUNT = "METHOD_GET_ITEM_COUNT";
	public static final String KEY_ITEM_COUNT = "KEY_ITEM_COUNT";
	
	public static final String AUTHORITY = "com.example.providers.articles";
	
	public static final int ITEM = 1;
	public static final int ITEM_ID = 2;
	public static final int ITEM_POS = 3;
	
	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.example.article";
	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.example.article";
	
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/item");
	public static final Uri CONTENT_POS_URI = Uri.parse("content://" + AUTHORITY + "/pos");
	
}
