package kmitl.lab03.pongmile.simplemydot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sccond);

        Button btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView tvValueX = (TextView) findViewById(R.id.tvValueX);
        TextView tvDot = (TextView) findViewById(R.id.tvDot);

        int x = getIntent().getIntExtra("xValue", 0);
        DotSerializable dotSerializable = (DotSerializable) getIntent().getSerializableExtra("dotSerializable");

        tvValueX.setText(String.valueOf(x));

        tvDot.setText("centerX : " + dotSerializable.getCenterX() +
                    "\n centerY : " + dotSerializable.getCenterY() +
                    "\n Radius : " + dotSerializable.getRadius());
        tvDot.setTextColor(dotSerializable.getColor());

        DotParcelable dotParcelable = (DotParcelable) getIntent().getParcelableExtra("dotParcelable");
        tvDot.setText("CenterX : " + dotParcelable.getCenterX() +
                "\n CenterY : " + dotParcelable.getCenterY() +
                "\n Radius : " + dotParcelable.getRadius());
    }
}
