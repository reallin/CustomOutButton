package com.example.linxj.customoutbutton;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by lvjia on 2015/10/30.
 */
public class customButton extends FrameLayout {
private TextView leftText;
    private TextView rightText;
    private RelativeLayout rel;
    private OutClickListener mlistener = null;
    private int flag;

    public customButton(final Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.outbutton, this);
        leftText = (TextView)super.findViewById(R.id.text1);
        rightText = (TextView)super.findViewById(R.id.edit1);
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.OutButton);
        leftText.setText(ta.getString(R.styleable.OutButton_leftText));
        rightText.setText(ta.getString(R.styleable.OutButton_rightText));
        leftText.setTextSize(ta.getDimension(R.styleable.OutButton_leftSize, 10));
        rightText.setTextSize(ta.getDimension(R.styleable.OutButton_rightSize, 10));
        //rel.setBackground(ta.getDrawable(R.styleable.OutButton_btnbackground));
        rightText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mlistener !=null)
              mlistener.popListener();
                /*int a = mlistener.getFlag();
                Intent i = new Intent(context,CustomDialogActivity.class);
                context.ststartActivityForResult(i,1);*/
            }
        });
        ta.recycle();
    }
    public void setOutClickListener(OutClickListener listener){
        this.mlistener = listener;
    }
    public interface OutClickListener{
        void popListener();
        //int getFlag();
    }
}
