package com.zyw.horrarndoo.sdk.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyw.horrarndoo.sdk.R;


/**
 * 历史数据查询界面Tips自定义View
 * @author zyw
 * @creation 2016-12-27
 */
public class HistoryTipView extends LinearLayout {
	private TextView tv_tips1;
	private TextView tv_tips2;
	private ImageView iv_tips;
	private TypedArray ta;

	public HistoryTipView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		ta = context.obtainStyledAttributes(attrs, R.styleable.HistoryTipsView);
		init(context);
		ta.recycle();
	}

	public HistoryTipView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		//init(context);
	}

	public HistoryTipView(Context context) {
		this(context, null);
		//init(context);
	}

	public void init(Context context){
		View.inflate(context, R.layout.sub_history_tips, this);

		tv_tips1 = (TextView) findViewById(R.id.tv_tip1);
		tv_tips2 = (TextView) findViewById(R.id.tv_tip2);
		iv_tips = (ImageView) findViewById(R.id.iv_tip);

		setTipOneText(ta.getString(R.styleable.HistoryTipsView_tip_one_text));
		setTipTwoText(ta.getString(R.styleable.HistoryTipsView_tip_two_text));
		setTextColor(ta.getColor(R.styleable.HistoryTipsView_android_textColor, Color.WHITE));
		setTextSize(ta.getDimension(R.styleable.HistoryTipsView_android_textSize, 12));
		setBackground(ta.getResourceId(R.styleable.HistoryTipsView_android_src, 0));
	}

	/**
	 * 设置控件Tip1文字
	 * @param str
	 */
	public void setTipOneText(String str){
		tv_tips1.setText(str);
	}

	/**
	 * 设置控件Tip2s文字
	 * @param str
	 */
	public void setTipTwoText(String str){
		tv_tips2.setText(str);
	}

	/**
	 * 设置控件Tip1 2文字颜色
	 * @param color
	 */
	public void setTextColor(int color){
		tv_tips1.setTextColor(color);
		tv_tips2.setTextColor(color);
	}

	/**
	 * 设置根据view的选中状态更新text字号
	 */
	public void setTextSize(float textSize) {
		tv_tips1.getPaint().setTextSize(textSize);
		tv_tips2.getPaint().setTextSize(textSize);
	}

	public void setBackground(int resId){
		iv_tips.setImageResource(resId);
	}
}

