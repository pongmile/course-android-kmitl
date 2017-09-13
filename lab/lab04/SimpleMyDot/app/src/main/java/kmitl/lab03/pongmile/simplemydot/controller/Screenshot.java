package kmitl.lab03.pongmile.simplemydot.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;


public class Screenshot {
    public static Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }
    public static File store(Bitmap bm, String fileName, File savePath) {
        File dir = new File(savePath.getAbsolutePath());
        if (!dir.exists())
            dir.mkdirs();
        File file = new File(savePath.getAbsolutePath(), fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public static File getMainDirectoryName(Context context) {
        File mainDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "SimpleMyDot");

        if (!mainDir.exists()) {
            if (mainDir.mkdir())
                Toast.makeText(context, "Main Directory Created : " + mainDir, Toast.LENGTH_SHORT).show();
            Log.e("Create Directory", "Main Directory Created : " + mainDir);
        }
        return mainDir;
    }

}
