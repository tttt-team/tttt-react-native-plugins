package org.tttt.rn.rong.im;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

public class RongImConversationListViewManager extends SimpleViewManager<RongImConversationListFragmentLayout> {
    private static final String REACT_CLASS = "TTTTRongImConversationListView";

    private static final String COMMAND_CREATE_FRAGMENT = "createFragment";

    private static final String COMMAND_REMOVE_FRAGMENT = "removeFragment";

    private ReactContext context;

    private RongImConversationListFragmentLayout layout;

    private RongImConversationListFragment fragment;

    private FragmentActivity activity;

    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @NonNull
    @Override
    protected RongImConversationListFragmentLayout createViewInstance(@NonNull ThemedReactContext reactContext) {
        RongImConversationListFragmentLayout layout = new RongImConversationListFragmentLayout(reactContext);
        this.context = reactContext;
        this.layout = layout;
        return layout;
    }

    @Nullable
    @Override
    public Map<String, Object> getExportedViewConstants() {
        return MapBuilder.of("Commands", (Object) MapBuilder.of("createFragment", COMMAND_CREATE_FRAGMENT,"removeFragment",COMMAND_REMOVE_FRAGMENT));
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
            RongImConversationListFragmentLayout view,
            String command,
            @Nullable ReadableArray args) {
        Log.i("receiveCommand{}", command);
        switch (command) {
            case COMMAND_CREATE_FRAGMENT:
                Log.i("commandId{}", command);
                this.createFragment(args);
                break;
            case COMMAND_REMOVE_FRAGMENT:
                Log.i("commandId{}", command);
                this.removeFragment(args);
                break;
        }
    }

    private void createFragment(ReadableArray args) {
        fragment = new RongImConversationListFragment(context);
        Uri uri = Uri.parse("rong://" + this.context.getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")
                .build();
        fragment.setUri(uri);
        activity = (FragmentActivity) this.context.getCurrentActivity();
        if (activity != null) {
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(this.layout.getId(), fragment, "ConversationListFragment")
                    .commit();
        }
    }

    private void removeFragment(ReadableArray args) {
        if (activity != null) {
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .remove(this.fragment)
                    .commit();
        }
    }

}
