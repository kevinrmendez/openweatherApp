package com.example.kevin.asynctasktest;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

import java.net.URI;

/**
 * Created by Kevin Mendez on 2/9/2018.
 */

public class Observer extends ContentObserver {
    public Observer(Handler handler) {
        super(handler);
    }
    @Override
    public void onChange(boolean selfChange) {
        this.onChange(selfChange, null);
    }
    @Override
    public void onChange(boolean selfChange, Uri uri) {
        // do s.th.
        // depending on the handler you might be on the UI
        // thread, so be cautious!
    }



}
