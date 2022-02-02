package oms.io;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import oms.ability.ModAbility;

public class Image extends ModAbility {


    public void save(String path, Bitmap bitmap) {
        try {
            new File(path).getFile().getParentFile().mkdirs();
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap bitmap(Drawable drawable) {
        Bitmap bitmap = null;
        int h = drawable.getIntrinsicHeight();
        int w = drawable.getIntrinsicWidth();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        bitmap = Bitmap.createBitmap(w, h, config);
        //注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    public void save(String path, Drawable drawable) {
        try {
            Bitmap bitmap = bitmap(drawable);
            new File(path).getFile().getParentFile().mkdirs();
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Drawable drawable(Bitmap bitmap) {
        return new BitmapDrawable(getContext().getResources(), bitmap);
    }

    public Drawable drawable(int t) {
        if (Build.VERSION.SDK_INT < 22) {
            return getContext().getResources().getDrawable(t);
        } else {
            return getContext().getDrawable(t);
        }
    }


}
