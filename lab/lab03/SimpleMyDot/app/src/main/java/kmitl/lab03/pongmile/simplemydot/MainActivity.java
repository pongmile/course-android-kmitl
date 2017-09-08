package kmitl.lab03.pongmile.simplemydot;

import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.Random;

import kmitl.lab03.pongmile.simplemydot.model.Dot;
import kmitl.lab03.pongmile.simplemydot.view.Dotview;

public class MainActivity extends AppCompatActivity implements Dot.OnDotChangedListener, Dotview.OnTouchListener{

    private Dotview dotview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnOpenActivity = (Button) findViewById(R.id.btnOpenActivity);

        final DotSerializable dotSerializable = new DotSerializable();
        dotSerializable.setCenterX(150);
        dotSerializable.setCenterY(150);
        dotSerializable.setColor(Color.RED);
        dotSerializable.setRadius(30);


        final DotParcelable dotParcelable = new DotParcelable(150, 150, 0);

        btnOpenActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("xValue", 30);
                intent.putExtra("dotSerializable", dotSerializable);

                intent.putExtra("dotParcelable", dotParcelable);
                startActivity(intent);
            }
        });

        dotview = (Dotview) findViewById(R.id.dotView);
        dotview.setOnTouchListener((View.OnTouchListener) this);
    }

    public void onRandomDot(View view) {
        Random random = new Random();
        int centerX = random.nextInt(dotview.getWidth());
        int centerY = random.nextInt(dotview.getHeight());
        new Dot(centerX, centerY, 30, randomColor(), this);
    }

    public void onClearDots(View view) {
        dotview.clear();
        dotview.invalidate();
    }

    @Override
    public void onDotChanged(Dot dot) {

        dotview.addDot(dot);
        dotview.invalidate();
    }

    private int randomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        new Dot((int) event.getX(), (int) event.getY(), 80, randomColor(), this);
        return false;
    }
}