package org.tttt.rn.rong.im;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableArray;

import org.tttt.rn.rong.im.conversation.RongImConversationViewManager;

import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imkit.utils.StringUtils;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.TextMessage;
import io.rong.push.RongPushClient;

public class RongImModule extends ReactContextBaseJavaModule{

    private static final String REACT_CLASS = "TTTTRongImModule";
    private final ReactApplicationContext reactContext;

    private ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
            super.onActivityResult(activity, requestCode, resultCode, data);
            FragmentActivity fragmentActivity = (FragmentActivity) activity;
            Fragment fragment = fragmentActivity.getSupportFragmentManager().findFragmentByTag(
                    RongImConversationViewManager.CONVERSATION_FRAGMENT_TAG);
            fragment.onActivityResult(requestCode & 0xffff, resultCode, data);
        }
    };

    RongImModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.reactContext.addActivityEventListener(this.mActivityEventListener);
        // 初始化, 仅一次
        RongIM.init(reactContext);
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @ReactMethod
    public void connectServer(String token) {
        System.out.println("----------------------------------------" + token);
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                Log.e("123", "--onTokenIncorrect");
            }

            @Override
            public void onSuccess(String userid) {
                Log.e("123", "--onSuccess" + userid);

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e("123", "--onError" + errorCode);
            }
        });
    }
    @ReactMethod
    public void getUserInfo(ReadableArray readableArray){
        for (int i = 0; i< readableArray.size(); i++) {
            Map<String, Object> map = readableArray.getMap(i).toHashMap();
            RongIM.getInstance().refreshUserInfoCache(
                    new UserInfo(
                            null != map.get("targetId") ? map.get("targetId").toString() : "",
                            null != map.get("title") ? map.get("title").toString() : "" ,
                            Uri.parse(map.get("img").toString())
                    )
            );
        }

    }
    @ReactMethod
    public void setMessage(){
        RongIM.getInstance().setMessageInterceptor(new RongIM.MessageInterceptor() {
            @Override
            public boolean intercept(Message message) {
                System.out.println("+++++++++++"+message);
                return false;
            }
        });
    }
}
