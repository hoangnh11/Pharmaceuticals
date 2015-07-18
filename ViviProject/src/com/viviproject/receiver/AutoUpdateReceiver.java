package com.viviproject.receiver;

import com.viviproject.service.TrackingLocationService;
import com.viviproject.ultilities.Logger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoUpdateReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		Logger.error("AutoUpdateReceiver #onReceive");
		Intent serviceIntent = new Intent(context, TrackingLocationService.class);
		context.startService(serviceIntent);
	}

}
