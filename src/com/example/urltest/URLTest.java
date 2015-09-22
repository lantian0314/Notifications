package com.example.urltest;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import com.example.notifications.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class URLTest extends Activity {

	private ImageView urlImageView;
	private Button get;
	private Button post;
	private EditText text;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.urltest);

		urlImageView = (ImageView) findViewById(R.id.urlImag);

		try {
			URL url = new URL("");
			InputStream is = url.openStream();
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			urlImageView.setImageBitmap(bitmap);
			is.close();
			is = url.openStream();
			OutputStream os = openFileOutput("asd.png", MODE_WORLD_READABLE);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				os.write(buffer, 0, len);
			}
			is.close();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		get=(Button) findViewById(R.id.get);
		post=(Button) findViewById(R.id.post);
		text=(EditText) findViewById(R.id.text);
		
		get.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String response=GetPostUtil.sendGet("http://www.baidu.com", null);
				text.setText(response+"成功");
			}
		});
		
		post.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String response=GetPostUtil.sendPost("http://www.hao123.com", null);
				text.setText(response+"成功");
				
			}
		});
	}
}
