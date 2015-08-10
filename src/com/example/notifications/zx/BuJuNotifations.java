package com.example.notifications.zx;


import com.example.notifications.R;
import com.example.notifications.tools.BaseTools;


import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

public class BuJuNotifations extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//��ȡ״̬֪ͨ���Ĺ���
		NotificationManager mNotificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		//ʵ����֪ͨ��������
		NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(this);
		//���趨RemoteViews
		RemoteViews view_custom = new RemoteViews(getPackageName(), R.layout.view_custom);
		//���ö�ӦIMAGEVIEW��ID����ԴͼƬ
		//view_custom.setImageViewResource(R.id.custom_icon, R.drawable.icon);
		String path=Environment.getExternalStorageDirectory().toString()+"/"+"123.png";    
		Bitmap bitmap=BaseTools.convertToBitmap(path,40,40);
		view_custom.setImageViewBitmap(R.id.custom_icon, bitmap);
		view_custom.setTextViewText(R.id.tv_custom_title, "���Ա���");
		view_custom.setTextViewText(R.id.tv_custom_content, "��������");
		//mBuilder.setContentTitle("���Ա���");
		//mBuilder.setContentText("��������");
		mBuilder.setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL)); //����֪ͨ�������ͼ  
	//  .setNumber(number) //����֪ͨ���ϵ�����  
	    mBuilder.setTicker("����֪ͨ����"); //֪ͨ�״γ�����֪ͨ��������������Ч����  
	   mBuilder .setWhen(System.currentTimeMillis());//֪ͨ������ʱ�䣬����֪ͨ��Ϣ����ʾ��һ����ϵͳ��ȡ����ʱ��  
	    mBuilder.setPriority(Notification.PRIORITY_DEFAULT); //���ø�֪ͨ���ȼ�  
	//  .setAutoCancel(true)//���������־���û��������Ϳ�����֪ͨ���Զ�ȡ��    
	    mBuilder.setOngoing(false);//ture��������Ϊһ�����ڽ��е�֪ͨ������ͨ����������ʾһ����̨����,�û���������(�粥������)����ĳ�ַ�ʽ���ڵȴ�,���ռ���豸(��һ���ļ�����,ͬ������,������������)  
	   mBuilder .setDefaults(Notification.DEFAULT_VIBRATE);//��֪ͨ������������ƺ���Ч������򵥡���һ�µķ�ʽ��ʹ�õ�ǰ���û�Ĭ�����ã�ʹ��defaults���ԣ��������  
	    //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND ������� // requires VIBRATE permission  
	    mBuilder.setSmallIcon(R.drawable.ic_launcher);//����֪ͨСICON  
	    Notification notify = mBuilder.build();
		notify.contentView = view_custom;
	    mNotificationManager.notify(1, notify);
	}

	public PendingIntent getDefalutIntent(int flags){  
	    PendingIntent pendingIntent= PendingIntent.getActivity(this, 1, new Intent(), flags);  
	    return pendingIntent;  
	}  

}
