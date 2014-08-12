/**
 * 
 */
package com.example.eyecontacts.widget;

import com.example.eyescontacts.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

public class ButtonPlus extends Button {
	private static final String TAG = "ButtonPlus";

	public ButtonPlus(Context context) {
		super(context);
	}

	public ButtonPlus(Context context, AttributeSet attrs) {
		super(context, attrs);
		setCustomFont(context, attrs);
	}

	public ButtonPlus(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setCustomFont(context, attrs);
	}

	private void setCustomFont(Context ctx, AttributeSet attrs) {
		TypedArray txtPlusTypedArray = ctx.obtainStyledAttributes(attrs,
				R.styleable.TextViewPlus);
		String textFont = txtPlusTypedArray
				.getString(R.styleable.TextViewPlus_textFont);

		TypedArray defaultTypedArray = ctx.obtainStyledAttributes(attrs,
				new int[] { android.R.attr.textStyle });
		int style = defaultTypedArray.getInt(0, 0);
		setCustomFont(ctx, textFont, style);
		txtPlusTypedArray.recycle();
	}

	public boolean setCustomFont(Context ctx, String asset, int style) {
		Typeface tf = null;
		try {
			tf = Typeface.createFromAsset(ctx.getAssets(), "fonts/" + asset);
		} catch (Exception e) {
			Log.e(TAG, "Could not get typeface: " + e.getMessage());
			return false;
		}

		setTypeface(tf, style);
		return true;
	}

}
