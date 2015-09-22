package com.example.urltest;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import com.example.notifications.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MultiThreadDown extends Activity{

	private EditText download_edit;
	private Button download_btn;
	private ProgressBar download_pb;
	private DownUtil downUtil;
	private int mDownStatus;
	private String targetFile;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.urltest);
		download_edit=(EditText) findViewById(R.id.download_edit);
		download_btn=(Button) findViewById(R.id.download_btn);
		download_pb=(ProgressBar) findViewById(R.id.download_pb);
		targetFile=Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"123.jpg";
		//����Handler����
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				
			}
		};
		
		download_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//��ʼ��DownUtil
				downUtil=new DownUtil(download_edit.getText().toString(), targetFile, 4);
				//��ʼ����
				downUtil.download();
				//����ÿ����Ȼ����ɽ���
				final Timer timer=new Timer();
				timer.schedule(new TimerTask() {	
					@Override
					public void run() {
						//�����ɱ���
						double completeRate=downUtil.getCompleteRate();
						mDownStatus=(int) (completeRate*100);
						//���Ϳ���Ϣ
						handler.sendEmptyMessage(0x123);
						//������ȫ��ȡ������
						if (mDownStatus>=100) {
							timer.cancel();
						}
					}
				},0,100);
			}
		});
		
	}
	
}
