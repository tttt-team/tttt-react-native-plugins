package org.tttt.rn.aliyun.livepush;

import com.alivc.live.pusher.AlivcLivePusher;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import androidx.annotation.NonNull;

public class AliyunLivepushViewManager extends SimpleViewManager<AliyunLivepushSurfaceView> {

    public static final String REACT_CLASS = "TTTTAliyunLivepushPreview";
    private ReactApplicationContext reactContext;
    private AlivcLivePusher alivcLivePusher;

    AliyunLivepushViewManager(ReactApplicationContext context, AlivcLivePusher alivcLivePusher) {
        this.reactContext = context;
        this.alivcLivePusher = alivcLivePusher;
    }

    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @NonNull
    @Override
    protected AliyunLivepushSurfaceView createViewInstance(@NonNull ThemedReactContext reactContext) {
        AliyunLivepushSurfaceView view = new AliyunLivepushSurfaceView(reactContext, alivcLivePusher);
        return view;
    }

    @ReactProp(name = "pushConfig")
    public void setPushConfig(AliyunLivepushSurfaceView view, ReadableMap config) {
        view.setConfig(config.toHashMap());
    }
}
