package com.example.minkin.playertest;

import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;

/*
 *创建人:yanggl
 *创建时间:2018-6-11  9:52
 *类描述:
 *备注:
 */
public class MusicPlayer implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {

    private static MusicPlayer musicPlayer;
    private MediaPlayer mediaPlayer;

    private MusicPlayer(){
        mediaPlayer=new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);//设置音量
        mediaPlayer.setOnBufferingUpdateListener(this);//监听事件，网络流媒体的缓冲监听
        mediaPlayer.setOnPreparedListener(this);//设置准备完毕监听器
        mediaPlayer.setOnCompletionListener(this);//设置播放完毕监听事件
    }

    public static synchronized MusicPlayer getInstance(){
        if(musicPlayer == null){
            musicPlayer=new MusicPlayer();
        }
        return musicPlayer;
    }

    public void startPlay(final String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mediaPlayer.reset();//重置 MediaPlayer 对象
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepare();//缓冲
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //网络流媒体的缓冲监听
    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    //播放完毕回调方法
    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.stop();
    }

    //准备完毕后会回调该接口的方法
    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }
}
