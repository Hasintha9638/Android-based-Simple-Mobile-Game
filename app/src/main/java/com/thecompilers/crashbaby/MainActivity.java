package com.thecompilers.crashbaby;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {
    private FrameLayout gameFrame;
    private int frameHeight,framewidth,initialframewidth;
    private LinearLayout startLayout;

    private ImageView box,bomb,orange,banana,waterlemon;
    private Drawable imageBox;

    private int boxSize;

    private float boxX,boxY;
    private float bombX,bombY;
    private float orangeX,orangeY;
    private float waterlemonX,waterlemonY;
    private float bananaX,bananaY;

     private TextView scoreLbl,highScoreLabel,todayscorelbl;
     private int score,highScore,timeCount;

     public musicfile musicfiles;

    private Timer timer;
    private Handler handler=new Handler();

    private boolean start_flag=false;
    private boolean action_flag=false;
    private boolean banana_flag=false;
    private boolean lemon_flag=false;

    private Button btnmusic;

    private  boolean btnMusic=false;

    private SharedPreferences sharedPreferences;

    int i=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameFrame=findViewById(R.id.gameFrame);
        startLayout=findViewById(R.id.startLayout);

        todayscorelbl=findViewById(R.id.todayscore);

        bomb=findViewById(R.id.bombImage);
        waterlemon=findViewById(R.id.waterlemonImage);
        orange=findViewById(R.id.orangeImage);
        box=findViewById(R.id.boxImage);
        banana=findViewById(R.id.bananaImage);

        scoreLbl=findViewById(R.id.scoreLbl);
        highScoreLabel=findViewById(R.id.HighScoreLbl);

        imageBox=getResources().getDrawable(R.drawable.basket);

        sharedPreferences=getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        highScore=sharedPreferences.getInt("HIGH_SCORE",0);
        highScoreLabel.setText("High Score : "+highScore);

        pausebutton=findViewById(R.id.btnPause);

        musicfiles=new musicfile(this);

        btnmusic=findViewById(R.id.btnMusic);
        View v=new View(this);
        musicon(v);


    }

    public void onStart(View v){
        start_flag=true;
        startLayout.setVisibility(View.INVISIBLE);
        if(frameHeight==0){
            frameHeight=gameFrame.getHeight();
            framewidth=gameFrame.getWidth();
            initialframewidth=framewidth;

            boxSize=box.getHeight();
            boxX=box.getX();
            boxY=box.getY();

        }
        framewidth=initialframewidth;
        box.setX(0.0f);
        bomb.setY(3000.0f);
        waterlemon.setY(3000.0f);
        banana.setY(3000.0f);
        orange.setY(3000.0f);

        bombY=bomb.getY();
        orangeY=orange.getY();
        waterlemonY=waterlemon.getY();
        bananaY=banana.getY();

        box.setVisibility(View.VISIBLE);
        banana.setVisibility(View.VISIBLE);
        waterlemon.setVisibility(View.VISIBLE);
        orange.setVisibility(View.VISIBLE);
        bomb.setVisibility(View.VISIBLE);
        pausebutton.setVisibility(View.VISIBLE);

        timeCount=0;
        score=0;

        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(start_flag){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            }
        },0,15);


    }



    public void changePos(){
     timeCount+=20;
        //orange
        orangeY+=10;
        float orangecenterX=orangeX+orange.getWidth()/2;
        float orangecenterY=orangeY+orange.getHeight()/2;

        if(hitCheck(orangecenterX,orangecenterY)){
            orangeY=frameHeight+100;
            score+=10;
            musicfiles.playHitOrange();
        }
        if (orangeY>frameHeight){
            orangeY=-100;
            orangeX=(float)Math.floor(Math.random()*(framewidth-orange.getWidth()));
        }
        orange.setX(orangeX);
        orange.setY(orangeY);

        //banana
        if(! banana_flag && timeCount%10000==0){
            banana_flag=true;
            bananaY=-20;
            bananaX=(float)Math.floor(Math.random()*(framewidth-banana.getWidth()));
        }
        if(banana_flag){
            bananaY+=20;
            float bananacenterX=bananaX+banana.getWidth()/2;
            float bananacenterY=bananaY+banana.getHeight()/2;

            if( hitCheck(bananacenterX,bananacenterY)){
               bananaY=frameHeight+20;
               score+=20;
                musicfiles.playHitbanana();

            }
            if(bananaY>frameHeight){
                banana_flag=false;
                banana.setX(bananaX);
                banana.setY(bananaY);
            }

            if(bananaY>frameHeight) banana_flag=false;
            banana.setX(bananaX);
            banana.setY(bananaY);


        }

        //lemon

        if(! lemon_flag && score>=100*i){
            lemon_flag=true;
            i+=2;
            waterlemonY=-20;
            waterlemonX=(float)Math.floor(Math.random()*(framewidth-waterlemon.getWidth()));
        }
        if(lemon_flag){
            waterlemonY+=25;
            float waterlemoncenterX=waterlemonX+waterlemon.getWidth()/2;
            float waterlemoncenterY=waterlemonY+waterlemon.getHeight()/2;

            if( hitCheck(waterlemoncenterX,waterlemoncenterY)){
                waterlemonY=frameHeight+100;
                score+=30;
                musicfiles.playHitlemon();
                if(initialframewidth>framewidth*110/100){
                    framewidth=framewidth*110/100;
                    changeFrameWidth(framewidth);
                }


            }
            if(waterlemonY>frameHeight){
                lemon_flag=false;
                waterlemon.setX(waterlemonX);
                waterlemon.setY(waterlemonY);
            }

            if(waterlemonY>frameHeight) lemon_flag=false;
            waterlemon.setX(waterlemonX);
            waterlemon.setY(waterlemonY);


        }


        //bomb

            bombY+=18;
            float bombcenterX=bombX+bomb.getWidth()/2;
            float bombcenterY=bombY+bomb.getHeight()/2;

            if( hitCheck(bombcenterX,bombcenterY)){
                bombY=frameHeight+100;
                //change frame
                framewidth=framewidth*90/100;
                changeFrameWidth(framewidth);
                musicfiles.playHitbomb();
                //game over
                if(framewidth<=boxSize*1.5){
                        gameOver();
                }


            }
            if(bombY>frameHeight){
               bombY=-100;
               bombX=(float)Math.floor(Math.random()*(framewidth-bomb.getWidth()));
            }

             bomb.setX(bombX);
             bomb.setY(bombY);




        //move box
        if(action_flag){
            //touching
            boxX+=14;
            box.setImageDrawable(imageBox);
        }else{
            //releasing
            boxX -=14;
            box.setImageDrawable(imageBox);
        }

        //check box position
        if(boxX<0){
            boxX=0;
            box.setImageDrawable(imageBox);
        }
        if(framewidth-boxSize<boxX){
            boxX=framewidth-boxSize;
            box.setImageDrawable(imageBox);
        }
        box.setX(boxX);
        scoreLbl.setText("Score : "+score);
    }


    public boolean hitCheck(float x,float y){
        if(boxX<=x && x<=boxX +boxSize && boxY<=y && y<=frameHeight){
            return true;
        }
        return false;
    }

    public void changeFrameWidth(int framewidth){
        ViewGroup.LayoutParams params=gameFrame.getLayoutParams();
        params.width=framewidth;
        gameFrame.setLayoutParams(params);
    }



    public void gameOver(){
        timer.cancel();
        timer=null;
         start_flag=false;
        action_flag=false;
        banana_flag=false;
        lemon_flag=false;
        //wait 1 second to sleep
         try{
             TimeUnit.SECONDS.sleep(1);
         }catch (InterruptedException e){
             e.printStackTrace();
         }
         changeFrameWidth(initialframewidth);
         startLayout.setVisibility(View.VISIBLE);

        box.setVisibility(View.INVISIBLE);
        banana.setVisibility(View.INVISIBLE);
        waterlemon.setVisibility(View.INVISIBLE);
        orange.setVisibility(View.INVISIBLE);
        bomb.setVisibility(View.INVISIBLE);
        pausebutton.setVisibility(View.INVISIBLE);

        todayscorelbl.setText("Your score : "+score);
        //update high score
        if(score>highScore){
            highScore=score;
            highScoreLabel.setText("High Score : "+highScore);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putInt("HIGH_SCORE",highScore);
            editor.commit();
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {


        if(start_flag){
           if(event.getAction()==MotionEvent.ACTION_DOWN){
                action_flag=true;
           }else if(event.getAction()==MotionEvent.ACTION_UP){
               action_flag=false;
           }
       }
       return true;
    }


    public void onQuit(View v){
        musicoff(v);
        finish();
        System.exit(0);
        int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);

    }

    @Override
    protected void onPause() {
        super.onPause();
        View v=new View(this);
        pausebt(v);
        musicoff(v);
    }

    public Button pausebutton;
    public boolean pause_flag=false;

    public  void pausebt(View v){

        if(pause_flag==false){
            pause_flag=true;
            timer.cancel();
            timer=null;
            musicoff(v);
            pausebutton.setText("Start");
        }else{
            musicon(v);
            pause_flag=false;
            pausebutton.setText("Pause");
            timer=new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(start_flag){
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                changePos();
                            }
                        });
                    }
                }
            },0,15);
        }

    }

    public void musicon(View v){


        if(btnMusic==false){
            Intent i=new Intent(this,backgroundmusic.class);
            startService(i);
            btnmusic.setText("Music OFF");
            btnMusic=true;
        }else{
            Intent i=new Intent(this,backgroundmusic.class);
            stopService(i);
            btnmusic.setText("Music ON");
            btnMusic=false;
        }

    }



    public void musicoff(View v){
        Intent i=new Intent(this,backgroundmusic.class);
        stopService(i);
        btnmusic.setText("Music ON");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        View v=new View(this);
        musicoff(v);
    }
}
