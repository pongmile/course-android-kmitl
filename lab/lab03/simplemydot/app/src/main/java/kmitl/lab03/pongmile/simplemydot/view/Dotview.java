package kmitl.lab03.pongmile.simplemydot.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import kmitl.lab03.pongmile.simplemydot.model.Dot;


public class Dotview extends View {

    private Paint paint;
    private Dot dot;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.RED);
        if(this.dot != null){
            canvas.drawCircle(this.dot.getCenterX(), this.dot.getCenterY(), 30, paint);
        }
    }

    public Dotview(Context context) {
        super(context);
        paint = new Paint();
    }

    public Dotview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    public Dotview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
    }

    public Dotview(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        paint = new Paint();
    }

    public void setDot(Dot dot) {
    }
}
