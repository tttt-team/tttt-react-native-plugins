package org.tttt.rn.rong.im;

import android.net.Uri;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.fragment.IHistoryDataResultCallback;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Conversation.ConversationType;
import io.rong.imkit.widget.adapter.ConversationListAdapter;
import io.rong.imlib.RongIMClient.ErrorCode;
import io.rong.imlib.RongIMClient.ResultCallback;
import io.rong.imlib.model.Message;
import io.rong.push.core.PushProtocalStack;


public class RongImConversationListFragment extends ConversationListFragment {

    private static final String LOG_TAG = "RongImConversationL.F";

    private ReactContext context;

    RongImConversationListFragment(ReactContext context) {
        this.context = context;
    }

//    @Override
//    protected void initFragment(Uri uri) {
//        super.initFragment(uri);
//        Log.w(LOG_TAG, "hack: 无视连接状态，强制调用getConversationList, 测试完删了");
//        // todo 测试完连接服务后删了这个重写方法
//        ConversationType[] defConversationType = new ConversationType[]{ConversationType.PRIVATE, ConversationType.GROUP, ConversationType.DISCUSSION, ConversationType.SYSTEM, ConversationType.CUSTOMER_SERVICE, ConversationType.CHATROOM, ConversationType.PUBLIC_SERVICE, ConversationType.APP_PUBLIC_SERVICE, ConversationType.ENCRYPTED};
//
//        try {
////            Method[] methods = this.getClass().getSuperclass().getDeclaredMethods();
////            for (Method m : methods) {
////                System.out.println(m.getName());
////            }
//            Method method = this.getClass().getSuperclass().getDeclaredMethod(
//                    "getConversationList",
//                    ConversationType[].class,
//                    boolean.class);
//            method.setAccessible(true);
//            method.invoke(this, defConversationType, false);
//        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
//            e.printStackTrace();
//        }
//    }

    // todo 测试完连接服务后删了这个重写方法
//    @Override
//    public void getConversationList(ConversationType[] conversationTypes, final IHistoryDataResultCallback<List<Conversation>> callback, boolean isLoadMore) {
//        long lTimestamp = isLoadMore ? this.getParentTimestamp() : 0L;
//        RongIMClient.getInstance().getConversationListByPage(new ResultCallback<List<Conversation>>() {
//            public void onSuccess(List<Conversation> conversations) {
//
//                if (RongImConversationListFragment.this.getActivity() != null && !RongImConversationListFragment.this.getActivity().isFinishing()) {
//                    if (callback != null) {
//                        List<Conversation> resultConversations = new ArrayList<>();
//                        // hack fixme 假数据为了展示一条会话item
////                        conversations = RongImConversationListFragment.this.fakeConversations();
//
//                        if (conversations != null) {
//                            RongImConversationListFragment.this.setParentTimestamp(conversations.get(conversations.size() - 1).getSentTime());
//                            Iterator var3 = conversations.iterator();
//
//                            while(var3.hasNext()) {
//                                Conversation conversation = (Conversation)var3.next();
//                                System.out.println("--------"+conversation.getConversationTitle());
//                                System.out.println("--------"+conversation.getLatestMessage());
//                                System.out.println("--------"+conversation.getTargetId());
//                                if (!RongImConversationListFragment.this.shouldFilterConversation(conversation.getConversationType(), conversation.getTargetId())) {
//                                    resultConversations.add(conversation);
//                                }
//                            }
//                        }
//
//                        callback.onResult(resultConversations);
//                    }
//
//                }
//            }
//
//            public void onError(ErrorCode e) {
//                if (callback != null) {
//                    callback.onError();
//                }
//
//            }
//        }, lTimestamp, this.getParentPageSize(), conversationTypes);
//    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(LOG_TAG, "conversation item click");
        ListView mList = getParentList();
        ConversationListAdapter mAdapter = getParentAdapter();
        Long previousClickTimestamp = (Long)view.getTag(R.id.rc_debounceClick_last_timestamp);
        long currentTimestamp = SystemClock.uptimeMillis();
        view.setTag(R.id.rc_debounceClick_last_timestamp, currentTimestamp);
        if (previousClickTimestamp == null || Math.abs(currentTimestamp - previousClickTimestamp) > 1000L) {
            int realPosition = position - mList.getHeaderViewsCount();
            if (realPosition >= 0 && realPosition < mAdapter.getCount()) {
                UIConversation uiConversation = mAdapter.getItem(realPosition);
                ConversationType conversationType = uiConversation.getConversationType();
                if (this.getGatherState(conversationType)) {
                    throw new UnsupportedOperationException("sub conversation list not support yet!!!");
                    // \RongIM.getInstance().startSubConversationList(this.getActivity(), conversationType);
                } else {
                    if (RongContext.getInstance().getConversationListBehaviorListener() != null && RongContext.getInstance().getConversationListBehaviorListener().onConversationClick(this.getActivity(), view, uiConversation)) {
                        return;
                    }
                    uiConversation.setUnReadMessageCount(0);
                    // note 不使用融云sdk逻辑去跳转activity
//                    RongIM.getInstance().startConversation(this.getActivity(), conversationType, uiConversation.getConversationTargetId(), uiConversation.getUIConversationTitle());

                    // note 获取父容器, 需要把事件绑定到父容器上
                    RongImConversationListFragmentLayout v= (RongImConversationListFragmentLayout) getView().getParent();
                    // note 发送事件，触发js端端onItemClick方法
                    WritableMap event = Arguments.createMap();
                    event.putString("conversationType", conversationType.getName());
                    event.putString("targetId", uiConversation.getConversationTargetId());
                    event.putString("title", uiConversation.getUIConversationTitle());
                    this.context.getJSModule(RCTEventEmitter.class).receiveEvent(
                            v.getId(),
                            "onItemClick",
                            event
                    );
                }
            }
        }
    }

    // todo 测试完删除
//    private List<Conversation> fakeConversations() {
//        List<Conversation> conversations = new ArrayList<>(3);
//        Conversation c;
//        for (int i = 1; i <= 10; i++) {
//            c = new Conversation();
//            c.setTargetId("c" + i);
//            c.setConversationType(ConversationType.PRIVATE);
//            conversations.add(c);
//        }
//        return conversations;
//    }

    private long getParentTimestamp() {
        return (long) this.getParentFieldValue("timestamp");
    }

    private void setParentTimestamp(long timestamp) {
        this.setParentFieldValue("timestamp", timestamp);
    }

    private int getParentPageSize() {
        return (int) this.getParentFieldValue("pageSize");
    }

    private ListView getParentList() {
        return (ListView) this.getParentFieldValue("mList");
    }

    private ConversationListAdapter getParentAdapter() {
        return (ConversationListAdapter) this.getParentFieldValue("mAdapter");
    }

    private Object getParentFieldValue(String fieldName) {
        Class c = this.getClass().getSuperclass();
        if (c == null) {
            return null;
        }
        try {
            Field field = c.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setParentFieldValue(String fieldName, Object fieldValue) {
        Class c = this.getClass().getSuperclass();
        if (c == null) {
            return;
        }
        try {
            Field field = c.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(this, fieldValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
