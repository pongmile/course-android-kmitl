package kmitl.lab03.pongmile.simplemydot.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import kmitl.lab03.pongmile.simplemydot.model.Dot;


public class Dotview extends View {

    private Paint paint;
    private ArrayList<Dot> dots;
    private OnTouchListener listener;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.dots != null) {
            for (Dot dot : dots) {
                paint.setColor(dot.getColor());
                canvas.drawCircle(dot.getCenterX(), dot.getCenterY(), dot.getRadius(), paint);
            }
        }
    }

    public Dotview(Context context) {
        super(context);
        paint = new Paint();
        dots = new ArrayList<>();
    }

    public Dotview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        dots = new ArrayList<>();
    }

    public Dotview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        dots = new ArrayList<>();
    }

    public Dotview(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        dots = new ArrayList<>();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        listener.onTouch(this, event);

        return super.onTouchEvent(event);
    }

    public void addDot(Dot dot) {
        dots.add(dot);
    }

    public void clear() {
        dots.clear();
    }

    public void setOnTouchListener(OnTouchListener listener) {
        this.listener = listener;
    }

    public void undo(){
        if(!dots.isEmpty()) {
            dots.remove(dots.remove(dots.size() - 1));
        }
    }

    public int findDot(int x, int y){
        for (int i = 0; i < dots.size(); i++){
            int centerX = dots.get(i).getCenterX();
            int centerY = dots.get(i).getCenterY();
            double distance = Math.sqrt(Math.pow(centerX - x, 2)) + Math.sqrt(Math.pow(centerY - y, 2));
            if (distance <= dots.get(i).getRadius()){
                return i;
            }
        }
        return -1;
    }

    public void removeDot(int i){
        dots.remove(i);
    }

}
