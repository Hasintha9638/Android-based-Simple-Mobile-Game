package com.thecompilers.crashbaby;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class musicfile {

    private AudioAttributes audioAttributes;
    final int SOUND_POOL_MAX=4;

    private static SoundPool soundPool;
    private static int hitorange;
    private static int hitbanana;
    private static int hitbomb;
    private static int hitlemon;

    public  musicfile(Context context){

        //soundpool is deprecatedin API level 21
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            audioAttributes=new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            soundPool=new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(SOUND_POOL_MAX)
                    .build();
        }else{

            soundPool=new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC,0);
        }

            hitbanana=soundPool.load(context,R.raw.orang,1);
            hitorange=soundPool.load(context,R.raw.orang,1);
            hitlemon=soundPool.load(context,R.raw.melon,1);
            hitbomb=soundPool.load(context,R.raw.over,1);
    }

    public void playHitOrange(){
        soundPool.play(hitorange,1.0f,1.0f,1,0,1.0f);
    }
    public void playHitbanana(){
        soundPool.play(hitbanana,1.0f,1.0f,1,0,1.0f);
    }
    public void playHitbomb(){
        soundPool.play(hitbomb,1.0f,1.0f,1,0,1.0f);
    }
    public void playHitlemon(){
        soundPool.play(hitlemon,1.0f,1.0f,1,0,1.0f);
    }

}
