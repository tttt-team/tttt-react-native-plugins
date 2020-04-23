package org.tttt.rn.aliyun.player;

import android.util.Log;

import com.aliyun.player.source.UrlSource;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AliyunPlayerViewLayoutManager extends SimpleViewManager<AliyunPlayerViewLayout> {
    private static final String LOG_TAG = "AliyunPlayerV.L.M.";
    private static final String REACT_CLASS = "TTTTAliyunPlayerView";

    private static final String COMMAND_CREATE_VIEW = "createView";

    private ReactContext context;

    private AliyunPlayerViewLayout layout;

    private final AliyunPlayerWrapper playerWrapper;

    AliyunPlayerViewLayoutManager(AliyunPlayerWrapper playerWrapper) {
        this.playerWrapper = playerWrapper;
    }

    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @NonNull
    @Override
    protected AliyunPlayerViewLayout createViewInstance(@NonNull ThemedReactContext reactContext) {
        AliyunPlayerViewLayout layout =
                new AliyunPlayerViewLayout(reactContext, this.playerWrapper.player(reactContext));
        this.context = reactContext;
        this.layout = layout;
        return layout;
    }

    @Nullable
    @Override
    public Map<String, Object> getExportedViewConstants() {
        return MapBuilder.of("Commands",
                (Object) MapBuilder.of("createView", COMMAND_CREATE_VIEW));
    }

    @Override
    public void receiveCommand(
            AliyunPlayerViewLayout view,
            String command,
            @Nullable ReadableArray args) {
        Log.i(LOG_TAG, "receive command: " + command);
        switch (command) {
            case COMMAND_CREATE_VIEW:
                Log.i(LOG_TAG, "handle command: " + command);
                this.createView(args);
                break;
        }
    }

    private void createView(ReadableArray args) {
        String uri = args.getString(0);
        String title = args.getString(1);
        UrlSource url = new UrlSource();
        url.setUri(uri);
        url.setTitle(title);
        this.layout.initVideoView(url);
    }
}
