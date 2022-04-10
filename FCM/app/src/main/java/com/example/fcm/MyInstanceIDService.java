package com.example.fcm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyInstanceIDService extends Service {
    public MyInstanceIDService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}