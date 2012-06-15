package com.ntu.rose;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

public class Search extends Activity {

	TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		
		tv = (TextView) findViewById(R.id.textView1);

		Intent intent = getIntent();
		if(Intent.ACTION_SEARCH.equals(intent.getAction())){
			String query = intent.getStringExtra(SearchManager.QUERY);
			performSearch(query);
		}
	}

	private void performSearch(String query) {
		tv.setText(query);
		// FIXME put into database
		// return a adapter (this class should extends ListActivity)
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
}
