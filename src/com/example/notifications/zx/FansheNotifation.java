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
		// ����֪ͨ��չ�ֵ�������Ϣ
		int icon = android.R.drawable.ic_lock_idle_alarm;
		CharSequence tickerText = "�ҵ�֪ͨ������";
		long when = System.currentTimeMillis();
		// Notification noti=new Notification();
		String path = Environment.getExternalStorageDirectory().toString()
				+ "/" + "123.png";
		// noti.largeIcon=convertToBitmap(path,20,20);
		// noti.when=when;
		Notification notification = new Notification(icon, tickerText, when);
		notification.largeIcon = convertToBitmap(path, 20, 20);
		Bitmap bitmap = convertToBitmap(path, 40, 40);

		// ��������֪ͨ��ʱҪչ�ֵ�������Ϣ
		Context context = getApplicationContext();
		CharSequence contentTitle = "�ҵ�֪ͨ����չ������";
		CharSequence contentText = "�ҵ�֪ͨ��չ����ϸ����";
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
		// ��mNotificationManager��notify����֪ͨ�û����ɱ�������Ϣ֪ͨ
		mNotificationManager.notify(1, notification);

	}

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
		opts.inSampleSize = (int) scale;
		WeakReference<Bitmap> weak = new WeakReference<Bitmap>(
				BitmapFactory.decodeFile(path, opts));
		return Bitmap.createScaledBitmap(weak.get(), w, h, true);
	}

}
