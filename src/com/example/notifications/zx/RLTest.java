package com.example.notifications.zx;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

public class RLTest extends Activity {

	private static final int ID_button1 = 1;
	private static final int ID_button2 = 2;
	private static final int ID_button3 = 3;
	private static final int ID_button4 = 4;
	private RelativeLayout relativeLayout;
	private Button button1;
	private Button button2;
	private Button button3;
	private Button button4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		relativeLayout = new RelativeLayout(this);
		button1 = new Button(this);
		button1.setText("button1");
		button1.setId(ID_button1);
		RelativeLayout.LayoutParams LP1 = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		// btn1 λ�ڸ� View �Ķ������ڸ� View ��ˮƽ����
		LP1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		LP1.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
		relativeLayout.addView(button1, LP1);

		button2 = new Button(this);
		button2.setText("button2");
		button2.setId(ID_button2);
		RelativeLayout.LayoutParams LP2 = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		// btn2 λ�� btn1 ���·�������ߺ� btn1 ����߶���
		LP2.addRule(RelativeLayout.BELOW, ID_button1);
		LP2.addRule(RelativeLayout.ALIGN_LEFT, ID_button1);
		relativeLayout.addView(button2, LP2);
		
		button3 = new Button(this);
		button3.setText("button3");
		button3.setId(ID_button3);
		RelativeLayout.LayoutParams LP3 = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		 // btn3 λ�� btn1 ���·���btn2 ���ҷ������ұߺ� btn1 ���ұ߶��루Ҫ���䣩   
		LP3.addRule(RelativeLayout.BELOW, ID_button1);
		LP3.addRule(RelativeLayout.RIGHT_OF, ID_button2);
		LP3.addRule(RelativeLayout.ALIGN_RIGHT, ID_button1);
		relativeLayout.addView(button3, LP3);
		
		button4 = new Button(this);
		button4.setText("button4");
		button4.setId(ID_button4);
		RelativeLayout.LayoutParams LP4 = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		// btn4 λ�� btn2 ���·����ڸ� Veiw ��ˮƽ���� 
		LP4.addRule(RelativeLayout.BELOW, ID_button2);
		LP4.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
		relativeLayout.addView(button4, LP4);
		
		setContentView(relativeLayout);
	}
}
