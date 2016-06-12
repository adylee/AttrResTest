package com.example.administrator.attrrestest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

//import java.util.logging.Handler;

/**
 * Created by Administrator on 2016/6/12.
 */
public class AlphaImageView extends ImageView {
    private int alphaDelta = 0;
    private int curAlpha = 0;
    private final int SPEED = 300;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            if(msg.what == 0x123)
            {
                curAlpha += alphaDelta;
                if(curAlpha>255) curAlpha = 255;
                AlphaImageView.this.setAlpha(curAlpha);
            }
        }

    };
    public AlphaImageView(Context context,AttributeSet attrs)
    {
        super(context,attrs);
        TypedArray typedArray =  context.obtainStyledAttributes(attrs,R.styleable.AlphaImageView);
       // TypedArray typedArray = context.obtainStyleAttributes(attrs,R.styleable.AlphaImageView);
        int duration =  typedArray.getInt(R.styleable.AlphaImageView_duration,0);
        alphaDelta = 255 * SPEED / duration;
    }
    @Override
    protected void onDraw(Canvas canvas)
    {
        this.setAlpha(curAlpha);
        super.onDraw(canvas);
        final Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                Message msg = new Message();
                msg.what = 0x123;
                if(curAlpha >= 255)
                {
                    timer.cancel();
                }
                else{
                    handler.sendMessage(msg);
                }
            }
        },0,SPEED);
    }

}
