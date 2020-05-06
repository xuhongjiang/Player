package com.example.myapplication;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.pockettv.player.media.IjkPlayerView;

public class MainActivity extends AppCompatActivity {

    public static final String IP_CAMERA_ADDRESS = "rtsp://admin:Ts4009694288@192.168.0.164:554/h264/ch1/main/av_stream";

    private IjkPlayerView ijkPlayerView;
    private ImageView snapIv;
    private Button snapBtn;
    private boolean inSnap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ijkPlayerView = findViewById(R.id.ijkPlayerView);
        snapIv = findViewById(R.id.snap);
        snapBtn = findViewById(R.id.btnSnap);
        snapBtn.setOnClickListener(view -> {
            Bitmap bitmap = ijkPlayerView.snapshotBitmap();
            if (bitmap != null) {
                Log.e("MainActivity", "bitmap ");
                snapIv.setImageBitmap(bitmap);
            } else {
                Log.e("MainActivity", "bitmap null");
            }
        });
        initIjkPlayView();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        IjkPlayerView.onResume();
//      有些源的地址不是一直有效 需要每次重加加载一遍
        if (!ijkPlayerView.isEnableDropScreen() && !ijkPlayerView.isManualPause()) {
            playUrl();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //父类的 已经unbind了 所以在之前调用
        ijkPlayerView.onDestroy();

    }

    public void initIjkPlayView() {
        //设置软解
        ijkPlayerView.init()
                .enableOrientation()
                .setTitle("播放标题")
                .setStartAndStopClickListener(new IjkPlayerView.StartAndStopClickListener() {
                    @Override
                    public void onStart() {
                        playUrl();
                    }

                    @Override
                    public void onStop() {
                        //停止播放 就需要 停止录制
                        if (ijkPlayerView.isRecording()) {
//                            stopRecord();
                        }

                        ijkPlayerView.stop();
                    }
                })
                .setOnCompletionListener(iMediaPlayer -> {
                    //如果点击的是 现在正在直播的  则意为 退出回放 传值0
                    ijkPlayerView.reset();
                    //todo do on complete
                    playUrl();
                })
                .setOnErrorListener((iMediaPlayer, i, i1) -> {
                    if (!ijkPlayerView.isEnableDropScreen()) {
                        //todo  doOnError
                        playUrl();
                    }
                    return true;
                });
    }

    private void playUrl() {
        if (ijkPlayerView != null) {
            ijkPlayerView.setVideoPath(Uri.parse(IP_CAMERA_ADDRESS));
            ijkPlayerView.start();
        }
    }
}
