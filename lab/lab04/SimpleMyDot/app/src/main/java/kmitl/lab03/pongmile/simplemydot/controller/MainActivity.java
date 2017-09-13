package kmitl.lab03.pongmile.simplemydot.controller;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.io.ByteArrayOutputStream;
import java.util.Random;

import kmitl.lab03.pongmile.simplemydot.DotParcelable;
import kmitl.lab03.pongmile.simplemydot.DotSerializable;
import kmitl.lab03.pongmile.simplemydot.R;
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

                intent.putExtra("dot ", dotParcelable);
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

    public void onUndo(View view){
        dotview.undo();
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

    private boolean requestExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    100);
            return false;
        } else {
            return true;
        }
    }

    private Bitmap screenShot() {
        View root = this.dotview.getRootView();
        Bitmap screenShot = Bitmap.createBitmap(root.getWidth(), root.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(screenShot);
        root.draw(canvas);
        return screenShot;
    }

    public Uri imageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void shareScreen(Uri uri) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/*");
        startActivity(Intent.createChooser(intent, "Share screenshot"));
    }

    public void Sharescreen(View view){
        if(requestExternalStoragePermission()) {
            Bitmap screenshot = screenShot();
            Uri uri = imageUri(getApplicationContext(), screenshot);
            shareScreen(uri);
        }
    }


}