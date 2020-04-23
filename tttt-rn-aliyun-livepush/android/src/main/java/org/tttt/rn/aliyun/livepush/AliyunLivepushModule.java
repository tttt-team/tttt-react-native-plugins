package org.tttt.rn.aliyun.livepush;

import android.util.Log;

import com.alivc.live.pusher.AlivcLivePushStats;
import com.alivc.live.pusher.AlivcLivePusher;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AliyunLivepushModule extends ReactContextBaseJavaModule {

    private static final String REACT_CLASS = "TTTTAliyunLivepushModule";
    private static final String LOG_TAG = "AliyunLivepushModule";
    private final AlivcLivePusher alivcLivePusher;

    private boolean isPushing = false;

    AliyunLivepushModule(ReactApplicationContext context, AlivcLivePusher alivcLivePusher) {
        super(context);
        this.alivcLivePusher = alivcLivePusher;
    }

    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        return super.getConstants();
    }

    @ReactMethod
    public void startPush(String pushUrl, Callback errorCallback, Callback successCallback) {
        if (isPushing) {
            return;
        }
        try {
            Log.i(LOG_TAG, "start push...");
            this.alivcLivePusher.startPush(pushUrl);
            this.isPushing = true;
            Log.i(LOG_TAG, "started push...");
            successCallback.invoke();
        } catch (IllegalArgumentException e) {
            Log.e(LOG_TAG, "start push error. ", e);
            e.printStackTrace();
            errorCallback.invoke(e.getMessage());
        } catch (IllegalStateException e) {
            Log.e(LOG_TAG, "start push error. ", e);
            e.printStackTrace();
            errorCallback.invoke(e.getMessage());
        }
    }

    @ReactMethod
    public void stopPush(Callback errorCallback, Callback successCallback) {
        if (!isPushing) {
            return;
        }
        try {
            Log.i(LOG_TAG, "stop push...");
            // hack 我是真tnnd无奈这个api, 只能这样确保推流状态不是ERROR, 才能正常关闭 0 0!
            // 用户等不及就关闭app重开了 ^_^
            while (this.alivcLivePusher.getCurrentStatus() == AlivcLivePushStats.ERROR) {
                Log.w(LOG_TAG, "start error. restarting...");
                this.alivcLivePusher.restartPush();
            }
            this.alivcLivePusher.stopPush();
            this.isPushing = false;
            Log.i(LOG_TAG, "stopped push...");
            successCallback.invoke();
        } catch (IllegalArgumentException e) {
            Log.e(LOG_TAG, "stop push error. ", e);
            e.printStackTrace();
            errorCallback.invoke(e.getMessage());
        } catch (IllegalStateException e) {
            Log.e(LOG_TAG, "stop push error. ", e);
            e.printStackTrace();
            errorCallback.invoke(e.getMessage());
        }
    }
}
