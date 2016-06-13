package com.tomer.alwayson.Services;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.tomer.alwayson.Prefs;
import com.tomer.alwayson.R;
import com.tomer.alwayson.WidgetProvider;

/**
 * Created by tomer AKA rosenpin on 6/13/16.
 */
public class WidgetUpdater extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Started widget updater service");

        Prefs prefs = new Prefs(getApplicationContext());
        prefs.apply();

        Context context = this;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        ComponentName thisWidget = new ComponentName(context, WidgetProvider.class);
        if (!prefs.enabled) {
            remoteViews.setTextColor(R.id.toggle, context.getResources().getColor(android.R.color.holo_red_light));
            remoteViews.setTextViewText(R.id.toggle, context.getString(R.string.off));
        } else {
            remoteViews.setTextColor(R.id.toggle, context.getResources().getColor(android.R.color.holo_green_light));
            remoteViews.setTextViewText(R.id.toggle, context.getString(R.string.on));
        }
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);

        stopSelf();
    }
}
