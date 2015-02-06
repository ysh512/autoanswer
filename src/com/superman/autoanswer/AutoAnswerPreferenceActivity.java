/*
 * AutoAnswer
 * Copyright (C) 2010 EverySoft
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.superman.autoanswer;

import com.wandoujia.ads.sdk.AdListener;
import com.wandoujia.ads.sdk.Ads;
import com.wandoujia.ads.sdk.InterstitialAd;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class AutoAnswerPreferenceActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	private AutoAnswerNotifier mNotifier;
	private static final String TAG_INTERSTITIAL_WIDGET = "a09174193f4923ebde856742c6aca959";
	private static final String TAG_LIST = "314e37cefefa8a0facb2e77ba84694bf";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try{
			Ads.init(this, "100021801", "e2289f43306d5d714806bd813a1dc463");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
//		Ads.showAppWall(this, TAG_LIST);
		mNotifier = new AutoAnswerNotifier(this);
		mNotifier.updateNotification();
		SharedPreferences sharedPreferences = getPreferenceManager().getSharedPreferences();
		sharedPreferences.registerOnSharedPreferenceChangeListener(this);
		addPreferencesFromResource(R.xml.preferences);
	}
	
	

	@Override
	protected void onDestroy() {
		final InterstitialAd interstitialAd = new InterstitialAd(this, TAG_INTERSTITIAL_WIDGET);
	    interstitialAd.setAdListener(new AdListener() {
	      @Override
	      public void onAdReady() {
	        interstitialAd.show();
	      }

	      @Override
	      public void onLoadFailure() {

	      }

	      @Override
	      public void onAdPresent() {

	      }

	      @Override
	      public void onAdDismiss() {

	      }
	    });
		
		interstitialAd.load();
		getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
		
		
		
		super.onDestroy();
	}
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (key.equals("enabled")) {
			mNotifier.updateNotification();
		}
	}
}