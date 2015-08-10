package com.example.notifications.tools;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BaseTools {
	/**
	 * ��ȡ��ǰӦ�ð汾��
	 * @param context
	 * @return version
	 * @throws Exception
	 */
	public static String getAppVersion(Context context) throws Exception {
		// ��ȡpackagemanager��ʵ��
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()���㵱ǰ��İ�����0�����ǻ�ȡ�汾��Ϣ
		PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
		String versionName = packInfo.versionName;
		return versionName;
	}
	
	/**
	 * ��ȡ��ǰϵͳSDK�汾��
	 */
	public static int getSystemVersion(){
		/*��ȡ��ǰϵͳ��android�汾��*/
		int version= android.os.Build.VERSION.SDK_INT;
		return version;
	}
	
	/**
	 * ����·����ͼƬת��ΪBitmap
	 * @param path
	 * @param w
	 * @param 
	 * @return
	 */
	public static Bitmap convertToBitmap(String path, int w, int h) {
	    BitmapFactory.Options opts = new BitmapFactory.Options();
	// ����Ϊtureֻ��ȡͼƬ��С
	  opts.inJustDecodeBounds = true;
	   opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
	   // ����Ϊ��
	  BitmapFactory.decodeFile(path, opts);
	 int width = opts.outWidth;
	  int height = opts.outHeight;
	  float scaleWidth = 0.f, scaleHeight = 0.f;
	  if (width > w || height > h) {
	     // ����
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
