package com.example.practice_6_mirea_task_2;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class BannerService extends Service {

    String inputIntentStr = null;
    WindowManager windowManager;
    View rootView;
    TextView textView;

    public BannerService() {}

    @Override
    public void onCreate() {
        super.onCreate();

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        rootView = (ConstraintLayout) LayoutInflater.from(this).inflate(R.layout.banner_service, null);
        textView = rootView.findViewById(R.id.banner_text_view);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        inputIntentStr = intent.getStringExtra("value");

        final WindowManager.LayoutParams params = new
                WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        String text;
        if (inputIntentStr.length() > 0) {
            textView.setText("");
            text = "Saved value is '" + inputIntentStr + "'";
            textView.setText(text);
        } else {
            text = "Please input the good name";
            textView.setText(text);
        }

        Intent bannerIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, bannerIntent, PendingIntent.FLAG_IMMUTABLE);

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRootViewClick();
            }
        });

        windowManager.addView(rootView, params);
        params.gravity = Gravity.BOTTOM | Gravity.CENTER;
        windowManager.updateViewLayout(rootView, params);

        return super.onStartCommand(intent, flags, startId);
    }

    public void onRootViewClick() {
        Intent dialogIntent = new Intent(this, MainActivity.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(dialogIntent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}