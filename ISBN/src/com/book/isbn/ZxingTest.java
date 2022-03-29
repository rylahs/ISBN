package com.book.isbn;

import com.book.isbn.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ZxingTest extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan);
        Intent intent = getIntent(); // 값을 받아온다.
        
        String titledata = intent.getStringExtra("titlesrc");
        String imagedata = intent.getStringExtra("imagesrc");
        String authordata = intent.getStringExtra("authorsrc");
        String pricedata = intent.getStringExtra("pricesrc");
        String publisherdata = intent.getStringExtra("publishersrc");
        
        TextView scanResultText1 = (TextView) findViewById(R.id.edittitle);
        TextView scanResultText2 = (TextView) findViewById(R.id.editimage);
        TextView scanResultText = (TextView) findViewById(R.id.editprice);
        TextView scanResultText3 = (TextView) findViewById(R.id.editauthor);
        TextView scanResultText4 = (TextView) findViewById(R.id.editpublisher);
        
        scanResultText1.setText(titledata);
        scanResultText2.setText(imagedata);
        scanResultText.setText(authordata);
        scanResultText3.setText(pricedata);
        scanResultText4.setText(publisherdata);
        
        
        
        
    }
}