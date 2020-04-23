package org.tttt.rn.aliyun.player;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

import com.aliyun.player.AliPlayer;
import com.aliyun.player.AliPlayerFactory;
import com.aliyun.player.IPlayer;
import com.aliyun.player.bean.ErrorInfo;
import com.aliyun.player.bean.InfoBean;
import com.aliyun.player.nativeclass.TrackInfo;
import com.aliyun.player.source.UrlSource;

import androidx.annotation.NonNull;

public class AliyunPlayerViewLayout extends RelativeLayout {

    private static final String LOG_TAG = "AliyunPlayerViewLayout";

    private SurfaceView mSurfaceView;

    //播放器
    private AliPlayer mAliyunVodPlayer;

    public AliyunPlayerViewLayout(@NonNull Context context, AliPlayer player) {
        super(context);
        this.mAliyunVodPlayer = player;
    }

//    @Override
//    public void requestLayout() {
//        super.requestLayout();
//        post(measureAndLayout);
//    }
//
//    private final Runnable measureAndLayout = new Runnable() {
//        @Override
//        public void run() {
//            measure(
//                    MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
//                    MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
//            layout(getLeft(), getTop(), getRight(), getBottom());
//        }
//    };

    public void initVideoView(UrlSource urlSource) {
        //初始化播放用的surfaceView
        initSurfaceView();
        //初始化播放器
        initAliVcPlayer(urlSource);
//        //初始化封面
//        initCoverView();
//        //初始化手势view
//        initGestureView();
//        //初始化控制栏
//        initControlView();
//        //初始化清晰度view
//        initQualityView();
//        //初始化缩略图
//        initThumbnailView();
//        //初始化倍速view
//        initSpeedView();
//        //初始化指引view
//        initGuideView();
//        //初始化提示view
//        initTipsView();
//        //初始化网络监听器
//        initNetWatchdog();
//        //初始化屏幕方向监听
//        initOrientationWatchdog();
//        //初始化手势对话框控制
//        initGestureDialogManager();
//        //默认为蓝色主题
//        setTheme(Theme.Blue);
//        //先隐藏手势和控制栏，防止在没有prepare的时候做操作。
//        hideGestureAndControlViews();
    }

    private void initSurfaceView() {
        this.mSurfaceView = new SurfaceView(getContext().getApplicationContext());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        //添加到布局中
        super.addView(this.mSurfaceView, params);

        SurfaceHolder holder = this.mSurfaceView.getHolder();
        //增加surfaceView的监听
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                Log.d(LOG_TAG, "surfaceCreated = surfaceHolder = " + surfaceHolder);
                if (AliyunPlayerViewLayout.this.mAliyunVodPlayer != null) {
                    AliyunPlayerViewLayout.this.mAliyunVodPlayer.setDisplay(surfaceHolder);
                    //防止黑屏
                    AliyunPlayerViewLayout.this.mAliyunVodPlayer.redraw();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width,
                                       int height) {
                Log.d(LOG_TAG, "surfaceChanged surfaceHolder = " + surfaceHolder
                        + ", width = " + width + ", height = " + height);
                if (mAliyunVodPlayer != null) {
                    mAliyunVodPlayer.redraw();
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
//                VcPlayerLog.d(TAG, " surfaceDestroyed = surfaceHolder = " + surfaceHolder);
                Log.d(LOG_TAG, "surfaceDestroyed = surfaceHolder = " + surfaceHolder);
                if (mAliyunVodPlayer != null) {
                    mAliyunVodPlayer.setDisplay(null);
                }
            }
        });
    }

    private void initAliVcPlayer(UrlSource urlSource) {
        mAliyunVodPlayer = AliPlayerFactory.createAliPlayer(super.getContext().getApplicationContext());
        mAliyunVodPlayer.enableLog(false);

        // 视频分辨率变化回调
        this.mAliyunVodPlayer.setOnVideoSizeChangedListener(new IPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(int i, int i1) {

            }
        });
        // 字幕变化
        this.mAliyunVodPlayer.setOnSubtitleDisplayListener(new IPlayer.OnSubtitleDisplayListener() {
            @Override
            public void onSubtitleShow(long l, String s) {

            }

            @Override
            public void onSubtitleHide(long l) {

            }
        });
        // 截图
        this.mAliyunVodPlayer.setOnSnapShotListener(new IPlayer.OnSnapShotListener() {
            @Override
            public void onSnapShot(Bitmap bitmap, int i, int i1) {

            }
        });
        ///////////////////////////////////////////////////////////////////////////////////////////
        //设置准备回调
        mAliyunVodPlayer.setOnPreparedListener(new IPlayer.OnPreparedListener() {
            @Override
            public void onPrepared() {
                Log.e(LOG_TAG, "onPrepared");
            }
        });
        //播放器出错监听
        mAliyunVodPlayer.setOnErrorListener(new IPlayer.OnErrorListener() {
            @Override
            public void onError(ErrorInfo errorInfo) {
                Log.e(LOG_TAG, "onError: " + errorInfo);
                Log.e(LOG_TAG, "onError: " + errorInfo.getCode());
                Log.e(LOG_TAG, "onError: " + errorInfo.getMsg());
                Log.e(LOG_TAG, "onError: " + errorInfo.getExtra());
            }
        });
        //播放器加载回调
        mAliyunVodPlayer.setOnLoadingStatusListener(new IPlayer.OnLoadingStatusListener() {
            @Override
            public void onLoadingBegin() {
                Log.e(LOG_TAG, "onLoadingBegin");
            }

            @Override
            public void onLoadingProgress(int i, float v) {
                Log.e(LOG_TAG, "onLoadingProgress: " + "i = " + i + ", v = " + v);
            }

            @Override
            public void onLoadingEnd() {
                Log.e(LOG_TAG, "onLoadingEnd");
            }
        });
        //播放器状态
        mAliyunVodPlayer.setOnStateChangedListener(new IPlayer.OnStateChangedListener() {
            @Override
            public void onStateChanged(int i) {
                Log.e(LOG_TAG, "onStateChanged: " + i);
            }
        });
        //播放结束
        mAliyunVodPlayer.setOnCompletionListener(new IPlayer.OnCompletionListener() {
            @Override
            public void onCompletion() {
                Log.e(LOG_TAG, "onCompletion");
            }
        });
        //播放信息监听
        mAliyunVodPlayer.setOnInfoListener(new IPlayer.OnInfoListener() {
            @Override
            public void onInfo(InfoBean infoBean) {
                Log.e(LOG_TAG, "onInfo: " + infoBean);
                Log.e(LOG_TAG, "onInfo: " + infoBean.getCode());
                Log.e(LOG_TAG, "onInfo: " + infoBean.getExtraValue());
                Log.e(LOG_TAG, "onInfo: " + infoBean.getExtraMsg());
            }
        });
        //第一帧显示
        mAliyunVodPlayer.setOnRenderingStartListener(new IPlayer.OnRenderingStartListener() {
            @Override
            public void onRenderingStart() {
                Log.e(LOG_TAG, "onRenderingStart");
            }
        });
        //trackChange监听
        mAliyunVodPlayer.setOnTrackChangedListener(new IPlayer.OnTrackChangedListener() {
            @Override
            public void onChangedSuccess(TrackInfo trackInfo) {
                Log.e(LOG_TAG, "onChangedSuccess: " + trackInfo);
            }

            @Override
            public void onChangedFail(TrackInfo trackInfo, ErrorInfo errorInfo) {
                Log.e(LOG_TAG, "onChangedFail: " + "trackInfo = " + trackInfo + ", errorInfo: " + errorInfo);
            }
        });
        //seek结束事件
        mAliyunVodPlayer.setOnSeekCompleteListener(new IPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete() {
                Log.e(LOG_TAG, "onSeekComplete");
            }
        });
        mAliyunVodPlayer.setOnSeiDataListener(new IPlayer.OnSeiDataListener() {
            @Override
            public void onSeiData(int i, byte[] bytes) {
                Log.e(LOG_TAG, "onSeiData");
            }
        });
        this.mAliyunVodPlayer.setAutoPlay(true);
        this.mAliyunVodPlayer.setDataSource(urlSource);
        this.mAliyunVodPlayer.prepare();
    }
}
