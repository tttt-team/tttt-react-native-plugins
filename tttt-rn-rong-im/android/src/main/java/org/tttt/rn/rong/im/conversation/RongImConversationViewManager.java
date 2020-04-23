package org.tttt.rn.rong.im.conversation;

import android.net.Uri;
import android.util.Log;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

import org.tttt.rn.rong.im.RongImConversationListFragmentLayout;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

public class RongImConversationViewManager extends SimpleViewManager<RongImConversationFragmentLayout> {

    public static final String CONVERSATION_FRAGMENT_TAG = "ConversationFragment";

    private static final String REACT_CLASS = "TTTTRongImConversationView";
    private static final String COMMAND_CREATE_FRAGMENT = "createFragment";

    private ReactContext context;
    private RongImConversationFragmentLayout layout;

    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @NonNull
    @Override
    protected RongImConversationFragmentLayout createViewInstance(@NonNull ThemedReactContext reactContext) {
        RongImConversationFragmentLayout layout = new RongImConversationFragmentLayout(reactContext);
        this.context = reactContext;
        this.layout = layout;
        return layout;
    }

    @Nullable
    @Override
    public Map<String, Object> getExportedViewConstants() {
        return MapBuilder.of("Commands", (Object) MapBuilder.of("createFragment", COMMAND_CREATE_FRAGMENT));
    }

    @Nullable
    @Override
    public Map getExportedCustomBubblingEventTypeConstants() {
        return MapBuilder.builder()
                .put("onItemClick",
                        MapBuilder.of("phasedRegistrationNames",
                                MapBuilder.of("bubbled", "onItemClick")))
                .build();
    }

    @Override
    public void receiveCommand(
            RongImConversationFragmentLayout view,
            String command,
            @Nullable ReadableArray args) {
        Log.e("receiveCommand", "************");
        switch (command) {
            case COMMAND_CREATE_FRAGMENT:
                Log.e("commandId", "*************");
                createFragment(args);
                break;
        }
    }

    private void createFragment(ReadableArray args) {
        String conversationTypeName = args.getString(0);
        String targetId = args.getString(1);
        String title = args.getString(2);
        RongImConversationFragment fragment = new RongImConversationFragment();
        Uri uri = Uri.parse("rong://" + this.context.getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(conversationTypeName.toLowerCase())
                .appendQueryParameter("targetId", targetId)
                .appendQueryParameter("title", title)
                .build();
        fragment.setUri(uri);
        FragmentActivity activity = (FragmentActivity) this.context.getCurrentActivity();
        if (activity != null) {
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(this.layout.getId(), fragment, CONVERSATION_FRAGMENT_TAG)
                    .commit();
        }
    }
}
