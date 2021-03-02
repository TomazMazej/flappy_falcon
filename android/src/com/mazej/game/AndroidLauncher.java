package com.mazej.game;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.utils.Timer;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.mazej.game.TheFalcon;
import com.mazej.game.dodatki.AdService;
import com.mazej.game.faze.Faza;
import com.mazej.game.faze.FazaIgranja;
import com.mazej.game.faze.GameStateManager;
import com.mazej.game.faze.KoncnaFaza;

public class AndroidLauncher extends AndroidApplication implements AdService {

	private static final String TAG ="AndroidLauncher";
	protected AdView adView;

	private static final String AD_UNIT_ID_INTERSTITIAL = "ca-app-pub-3940256099942544/1033173712"; //ca-app-pub-3940256099942544/1033173712 ca-app-pub-4585950872793310/6238037572
	private InterstitialAd interstitialAd;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		RelativeLayout layout= new RelativeLayout(this);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		View gameView = initializeForView(new TheFalcon(this), config);
		layout.addView(gameView);

		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(AD_UNIT_ID_INTERSTITIAL);
		interstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {}

			@Override
			public void onAdClosed() {
				loadIntersitialAd();
			}
		});

		loadIntersitialAd();

		// BANNER ADD
		/*adView=new AdView(this);

		adView.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				Log.i(TAG, "Ad Loaded...");
			}
		});
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId("ca-app-pub-4585950872793310/6261242865"); // ca-app-pub-3940256099942544/6300978111

		AdRequest.Builder builder= new AdRequest.Builder();
		builder.addTestDevice("");
		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
		);

		layout.addView(adView, adParams);
		adView.loadAd(builder.build());*/

		setContentView(layout);
	}

	private void loadIntersitialAd(){
		AdRequest interstitialRequest = new AdRequest.Builder().build();
		interstitialAd.loadAd(interstitialRequest);
	}

	@Override
	public void showInterstitial() {
		runOnUiThread(new Runnable() {
			public void run() {
				if (interstitialAd.isLoaded())
					interstitialAd.show();
				else
					loadIntersitialAd();
			}
		});
	}

	@Override
	public boolean isInterstitialLoaded() {
		return interstitialAd.isLoaded();
	}

	@Override
	protected void onPause(){
		super.onPause();
		FazaIgranja.pause = true;
	}

	@Override
	protected void onResume() {
		super.onResume();

		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				FazaIgranja.pause = false;
			}
		}, -1);
	}
}