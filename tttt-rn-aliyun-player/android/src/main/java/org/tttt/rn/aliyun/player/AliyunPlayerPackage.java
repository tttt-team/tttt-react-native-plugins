package org.tttt.rn.aliyun.player;

import java.util.Arrays;
import java.util.List;

import com.aliyun.player.AliPlayer;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

public class AliyunPlayerPackage implements ReactPackage {

    private AliyunPlayerWrapper wrapper;

    public AliyunPlayerPackage() {
        this.wrapper = new AliyunPlayerWrapper();
    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        AliyunPlayerModule module = new AliyunPlayerModule(reactContext);
        this.wrapper.setSubscribe(module);
        this.wrapper.initPlayer(reactContext);
        return Arrays.<NativeModule>asList(module);
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Arrays.<ViewManager>asList(new AliyunPlayerViewLayoutManager(this.wrapper));
    }
}
