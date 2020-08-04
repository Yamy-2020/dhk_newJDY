package com.kym.ui.activity.sun_util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.dueeeke.videoplayer.player.VideoView;

public class RotateVideoView extends VideoView {
    public RotateVideoView(@NonNull Context context) {
        super(context);
    }

    public RotateVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RotateVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void startFullScreen() {
        super.startFullScreen();
        mOrientationHelper.disable();
    }


    @Override
    public void stopFullScreen() {
        super.stopFullScreen();
        mOrientationHelper.disable();
    }
}
