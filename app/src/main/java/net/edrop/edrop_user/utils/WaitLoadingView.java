package net.edrop.edrop_user.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import net.edrop.edrop_user.R;

public class WaitLoadingView extends View{

    private int radius;
    private int width;
    private int time;
    private int speed = 8;
    private int maxMove;

    private int leftColor;
    private int centerColor;
    private int rightColor;

    private Paint paint;
    private boolean isover = false;

    private static final int CHANGEING =1;

    public WaitLoadingView(Context context) {
        this(context, null);
    }

    public WaitLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaitLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        time = 0;

        paint = new Paint();
        leftColor = getResources().getColor(R.color.red);
        centerColor = getResources().getColor(R.color.gray);
        rightColor = getResources().getColor(R.color.blue);
        paint.setColor(leftColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        radius = getDefaultSize(0, heightMeasureSpec) / 2; //圆半径
        width = getDefaultSize(0, widthMeasureSpec); //控件宽

        maxMove = width - radius * 2; //圆最大运行距离
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(leftColor);
        canvas.drawCircle(radius + time, radius, radius, paint);
        paint.setColor(centerColor);
        canvas.drawCircle(width / 2, radius, radius, paint);
        paint.setColor(rightColor);
        canvas.drawCircle(width - radius - time, radius, radius, paint);
    }

    public void start(){
        handler.sendEmptyMessageDelayed(CHANGEING, 50);
    }

    public static int getDefaultSize(int size, int masureSpec){
        int result = size;
        int specMode = MeasureSpec.getMode(masureSpec);
        int specSize = MeasureSpec.getSize(masureSpec);

        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case CHANGEING:
                    if(time == maxMove){
                        time = 0;
                        isover = false;
                        int color = leftColor;
                        leftColor = rightColor;
                        rightColor = color;
                        invalidate();
                    }
                    time = time + speed;
                    if(time > maxMove){
                        time = maxMove;
                    }
                    if(time >= maxMove / 2 && !isover){
                        int color = centerColor;
                        centerColor = rightColor;
                        rightColor = color;
                        isover = true;
                    }
                    invalidate();
                    handler.sendEmptyMessageDelayed(CHANGEING, 30);
                    break;
            }
        }
    };

}
