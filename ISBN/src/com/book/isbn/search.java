package com.book.isbn;

import com.book.isbn.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class search extends Activity {

	private Button bt1;  // ���ڵ�� �Ľ��ϱ� ��ư
	private String info;
	
	/** Called when the activity is first created. */
	@TargetApi(Build.VERSION_CODES.DONUT) @Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    // TODO Auto-generated method stub
	    bt1=(Button)findViewById(R.id.button1);
	    
	    bt1.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				IntentIntegrator.initiateScan(search.this); // ���ڵ� ��ĵ ���� 
			}
			
	    });
	    		
	}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // ���ڵ� ���� ���� ��� ó���ϴ� �޼ҵ�
    	//super.onActivityResult(requestCode, resultCode, data);
    	if(RESULT_OK == resultCode){
    		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
    	    if (scanResult != null) { // ����� �ִٸ�
    	      // handle scan result
    	    	TextView scanResultText = (TextView) findViewById(R.id.edt1); 
    	    	String text = scanResult.getContents();
    	    	scanResultText.setText(text);
    	    	// ���ڵ� ��ĵ ����� edt1�� �޾ƿ�
    	    	Intent intent = new Intent(search.this, NaverBook.class);// �Ѱ��� Intent ���� , �Ľ��� ���� NaverBook.class�� �̵�
    	    	Bundle data1 = new Bundle(); // ���鵵 ����
    	    	data1.putString("key", text.toString()); // key��� Ű����� IBSN���� �Ѱ���
    	    	intent.putExtras(data1); // Intent�� �� �Ѱ���
    	    	startActivity(intent); // NaverShopping ����
    	    	
    	    }
    	}    		
    }

}
