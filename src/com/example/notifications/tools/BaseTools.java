package com.example.notifications.tools;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BaseTools {
	/**
	 * 获取当前应用版本号
	 * @param context
	 * @return version
	 * @throws Exception
	 */
	public static String getAppVersion(Context context) throws Exception {
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
		String versionName = packInfo.versionName;
		return versionName;
	}
	
	/**
	 * 获取当前系统SDK版本号
	 */
	public static int getSystemVersion(){
		/*获取当前系统的android版本号*/
		int version= android.os.Build.VERSION.SDK_INT;
		return version;
	}
	
	/**
	 * 根据路径将图片转换为Bitmap
	 * @param path
	 * @param w
	 * @param 
	 * @return
	 */
	public static Bitmap convertToBitmap(String path, int w, int h) {
	    BitmapFactory.Options opts = new BitmapFactory.Options();
	// 设置为ture只获取图片大小
	  opts.inJustDecodeBounds = true;
	   opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
	   // 返回为空
	  BitmapFactory.decodeFile(path, opts);
	 int width = opts.outWidth;
	  int height = opts.outHeight;
	  float scaleWidth = 0.f, scaleHeight = 0.f;
	  if (width > w || height > h) {
	     // 缩放
	     scaleWidth = ((float) width) / w;
	     scaleHeight = ((float) height) / h;
	 }
	opts.inJustDecodeBounds = false;
	  float scale = Math.max(scaleWidth, scaleHeight);
	   opts.inSampleSize = (int)scale;
	   WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
	  return Bitmap.createScaledBitmap(weak.get(), w, h, true);
	}
}
