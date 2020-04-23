package org.tttt.rn.aliyun.player;

import android.util.Log;

import com.aliyun.player.AliPlayer;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import javax.annotation.Nonnull;

public class AliyunPlayerModule extends ReactContextBaseJavaModule implements SubscribeModule {

    private static final String LOG_TAG = "AliyunPlayerModule";
    private static final String REACT_CLASS = "TTTTAliyunPlayerModule";
    private final ReactApplicationContext reactContext;

    private AliPlayer player;

    AliyunPlayerModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    @Nonnull
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    public void subscribe(AliPlayer mAliyunVodPlayer) {
        this.player = mAliyunVodPlayer;
    }

    @ReactMethod
    public void start(String stringArgument, int numberArgument, Callback callback) {
        Log.i(LOG_TAG, "start play");
        this.player.start();
    }
}
