package kmitl.lab03.pongmile.simplemydot.fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import kmitl.lab03.pongmile.simplemydot.R;
import kmitl.lab03.pongmile.simplemydot.controller.MainActivity;
import kmitl.lab03.pongmile.simplemydot.model.Dot;
import kmitl.lab03.pongmile.simplemydot.view.Dotview;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements Dot.OnDotChangedListener, Dotview.OnTouchListener, View.OnClickListener {


    private Dotview dotview;
    private OnDotSelectListener selectListener;

    MainActivity main = new MainActivity();

    public MainFragment() {

        // Required empty public constructor
    }

    public void setSelectListener(OnDotSelectListener selectListener) {
        this.selectListener = selectListener;
    }

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectListener = (OnDotSelectListener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initSetup(rootView);
        return rootView;

    }

    private void initSetup(View rootView) {
        dotview = rootView.findViewById(R.id.dotView);
        Button randomDotButton = rootView.findViewById(R.id.randomDot);
        Button clearDotButton = rootView.findViewById(R.id.clearDot);
        Button undoButton = rootView.findViewById(R.id.Undo);
        Button shareButton = rootView.findViewById(R.id.share);

        dotview.setListener(this);
        randomDotButton.setOnClickListener(this);
        clearDotButton.setOnClickListener(this);
        undoButton.setOnClickListener(this);
        shareButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(R.id.randomDot == view.getId()){
            onRandomDot();
        }
        if(R.id.clearDot == view.getId()){
            onClearDots();
        }
        if(R.id.Undo == view.getId()){
            onUndo();
        }
        if(R.id.share == view.getId()){
            Sharescreen();
        }
    }


    public void onRandomDot() {
        Random random = new Random();
        int centerX = random.nextInt(dotview.getWidth());
        int centerY = random.nextInt(dotview.getHeight());
        new Dot(centerX, centerY, 30, randomColor(), (Dot.OnDotChangedListener) this);
    }

    public void onClearDots() {
        dotview.clear();
        dotview.invalidate();
    }

    public void onUndo(){
        dotview.undo();
        dotview.invalidate();
    }


    public void onDotChanged(Dot dot) {
        dotview.addDot(dot);
        dotview.invalidate();
    }


    private int randomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public boolean onTouch(View v, MotionEvent event) {
        new Dot((int) event.getX(), (int) event.getY(), 80, randomColor(), (Dot.OnDotChangedListener) this);
        return false;
    }

    private void shareScreen(Uri uri) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/*");
        startActivity(Intent.createChooser(intent, "Share screenshot"));
    }

    private boolean requestExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    100);
            return false;
        } else {
            return true;
        }
    }


    public Uri imageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public void Sharescreen(){

        if(requestExternalStoragePermission()) {
            Bitmap captureScreen = main.captureScreen();
            Uri uri = imageUri(getContext(), captureScreen);
            shareScreen(uri);
        }
    }




    public interface OnDotSelectListener {
        void onDotSelect(Dot dot);
    }

}
