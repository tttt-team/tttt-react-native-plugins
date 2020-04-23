package org.tttt.rn.aliyun.player;

import android.content.Context;

import com.aliyun.player.AliPlayer;
import com.aliyun.player.AliPlayerFactory;

public class AliyunPlayerWrapper {

    private AliPlayer mAliyunVodPlayer;

    private SubscribeModule subscribeModule;

    public void setSubscribe(SubscribeModule module) {
        this.subscribeModule = module;
    }

    public void initPlayer(Context context) {
        if (this.mAliyunVodPlayer == null) {
            this.mAliyunVodPlayer = AliPlayerFactory.createAliPlayer(context.getApplicationContext());
            this.subscribeModule.subscribe(this.mAliyunVodPlayer);
        }
    }

    public AliPlayer player(Context context) {
        this.initPlayer(context);
        return this.mAliyunVodPlayer;
    }
}
