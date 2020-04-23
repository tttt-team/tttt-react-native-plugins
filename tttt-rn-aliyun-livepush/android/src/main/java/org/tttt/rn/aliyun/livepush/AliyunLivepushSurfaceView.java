package org.tttt.rn.aliyun.livepush;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.alivc.live.pusher.AlivcLivePushConfig;
import com.alivc.live.pusher.AlivcLivePushStats;
import com.alivc.live.pusher.AlivcLivePusher;
import com.alivc.live.pusher.AlivcPreviewDisplayMode;
import com.alivc.live.pusher.AlivcPreviewOrientationEnum;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

public class AliyunLivepushSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String LOG_TAG = "AliyunLivepushView";

    private SurfaceStatus mSurfaceStatus = SurfaceStatus.UNINITED;
    private AlivcLivePusher mAlivcLivePusher;
    private Map<String, Object> config = new HashMap<>(0);

    public AliyunLivepushSurfaceView(@NonNull Context context,
                                     @NonNull AlivcLivePusher alivcLivePusher) {
        super(context);
        this.mAlivcLivePusher = alivcLivePusher;
        this.initView();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i(LOG_TAG, "preview created");
        if(this.mSurfaceStatus == SurfaceStatus.UNINITED
                || this.mSurfaceStatus == SurfaceStatus.DESTROYED) {
            if (this.mSurfaceStatus == SurfaceStatus.UNINITED) {
                this.mSurfaceStatus = SurfaceStatus.CREATED;
            } else {
                if (this.mAlivcLivePusher.getCurrentStatus() == AlivcLivePushStats.IDLE) {
                    Log.w(LOG_TAG, "pusher idle, rebuild it. ");
                    this.mAlivcLivePusher = new AlivcLivePusher();
                }
                this.mSurfaceStatus = SurfaceStatus.RECREATED;
            }
            this.initPusher();
            try {
                this.mAlivcLivePusher.startPreview(this);
            } catch (IllegalArgumentException | IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i(LOG_TAG, "preview changed");
        this.mSurfaceStatus = SurfaceStatus.CHANGED;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i(LOG_TAG, "preview destroyed");
        this.mSurfaceStatus = SurfaceStatus.DESTROYED;
        this.mAlivcLivePusher.stopPreview();
//        this.mAlivcLivePusher.destroy();
    }

    private void initView() {
        //注册回调方法
        super.getHolder().addCallback(this);
        //设置一些参数方便后面绘图
        setFocusable(true);
        setKeepScreenOn(true);
        setFocusableInTouchMode(true);
    }

    private void initPusher() {
        AlivcLivePushConfig config = new AlivcLivePushConfig();
        config.setPreviewDisplayMode(AlivcPreviewDisplayMode.ALIVC_LIVE_PUSHER_PREVIEW_ASPECT_FILL);
        config.setPreviewOrientation(AlivcPreviewOrientationEnum.ORIENTATION_PORTRAIT);
        try {
            this.mAlivcLivePusher.init(getContext().getApplicationContext(), config);
            Log.e("test", this.mAlivcLivePusher.getCurrentStatus().name());
        } catch (IllegalArgumentException | IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public void setConfig(Map<String, Object> config) {
        this.config = config;
    }
}
