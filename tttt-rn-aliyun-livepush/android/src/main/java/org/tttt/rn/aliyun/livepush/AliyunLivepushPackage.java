package org.tttt.rn.aliyun.livepush;

import java.util.Arrays;
import java.util.List;

import com.alivc.live.pusher.AlivcLivePusher;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import androidx.annotation.NonNull;

public class AliyunLivepushPackage implements ReactPackage {

    private AlivcLivePusher alivcLivePusher = new AlivcLivePusher();

    @NonNull
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        return Arrays.<NativeModule>asList(new AliyunLivepushModule(reactContext, alivcLivePusher));
    }

    @NonNull
    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Arrays.<ViewManager>asList(new AliyunLivepushViewManager(reactContext, alivcLivePusher));
    }
}
