package kmitl.lab03.pongmile.simplemydot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

import kmitl.lab03.pongmile.simplemydot.model.Dot;
import kmitl.lab03.pongmile.simplemydot.view.Dotview;

public class MainActivity extends AppCompatActivity implements Dot.OnDotChangedListener{

    private Dotview dotview;
    private Dot dot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dotview = (Dotview) findViewById(R.id.dotView);

        dot = new Dot(this, 0, 0, 30);
    }

    public void onRandomdot(View view) {
        Random random = new Random();
        int centerX = random.nextInt(this.dotview.getWidth());
        int centerY = random.nextInt(this.dotview.getHeight());
        this.dot.setCenterX(centerX);
        this.dot.setCenterY(centerY);

    }

    @Override
    public void onDotChanged(Dot dot) {

        dotview.setDot(dot);
        dotview.invalidate();

    }
}
