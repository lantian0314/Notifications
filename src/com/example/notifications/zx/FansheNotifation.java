package com.example.notifications.zx;

import java.lang.ref.WeakReference;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;

public class FansheNotifation extends Activity {
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		// 定义通知栏展现的内容信息
		int icon = android.R.drawable.ic_lock_idle_alarm;
		CharSequence tickerText = "我的通知栏标题";
		long when = System.currentTimeMillis();
		// Notification noti=new Notification();
		String path = Environment.getExternalStorageDirectory().toString()
				+ "/" + "123.png";
		// noti.largeIcon=convertToBitmap(path,20,20);
		// noti.when=when;
		Notification notification = new Notification(icon, tickerText, when);
		notification.largeIcon = convertToBitmap(path, 20, 20);
		Bitmap bitmap = convertToBitmap(path, 40, 40);

		// 定义下拉通知栏时要展现的内容信息
		Context context = getApplicationContext();
		CharSequence contentTitle = "我的通知栏标展开标题";
		CharSequence contentText = "我的通知栏展开详细内容";
		Intent notificationIntent = new Intent(context, Kong.class);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		notification.setLatestEventInfo(context, contentTitle, contentText,
				contentIntent);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		try {
			Class<?> localClass = Class.forName("com.android.internal.R$id");
			int iconID = localClass.getField("icon").getInt(localClass);
			if (bitmap != null && notification.contentView != null) {
				notification.contentView.setImageViewBitmap(iconID, bitmap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// noti.setLatestEventInfo(context, contentTitle, contentText,
		// contentIntent);
		// 用mNotificationManager的notify方法通知用户生成标题栏消息通知
		mNotificationManager.notify(1, notification);

	}

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
		opts.inSampleSize = (int) scale;
		WeakReference<Bitmap> weak = new WeakReference<Bitmap>(
				BitmapFactory.decodeFile(path, opts));
		return Bitmap.createScaledBitmap(weak.get(), w, h, true);
	}

}
