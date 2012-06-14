package com.ntu.rose;

import java.util.Locale;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.util.DisplayMetrics;

public class UserPreference extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.userpeference);
		
		/** Display and refresh the current language. */
		final Resources res = getApplicationContext().getResources();
		final DisplayMetrics dm = res.getDisplayMetrics();
		final Configuration conf = new Configuration();
		String key_language = (String) getResources().getText(R.string.key_language);
		ListPreference language = (ListPreference) findPreference(key_language);
		language.setSummary(language.getValue());
		language.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				preference.setSummary((CharSequence) newValue);
				// switch language
				conf.locale = new Locale(((String)newValue).toLowerCase());
				res.updateConfiguration(conf, dm);
				// refresh current list
				startActivity(getIntent()); 
				finish();
				return true;
			}
		});
		
		/** Display and refresh the current cache size. */
		String key_cache = (String) getResources().getText(R.string.key_cachesize);
		EditTextPreference cache = (EditTextPreference) findPreference(key_cache);
		cache.setSummary(cache.getText());
		cache.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				preference.setSummary(newValue + " MB");
				return true;
			}
		});
	}
}
