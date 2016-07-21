package com.ajinkyabadve.floatingmenulib.viewgroup;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ajinkyabadve.floatingmenulib.R;

/**
 * Created by Ajinkya on 21/07/2016.
 */
public class FabView extends LinearLayout {
    TextView mFabLabel;
    String mLabel = null;
    FloatingActionButton mFloatingActionButton;
    private LinearLayout mMainLayout;
    private FabClickListener mFabClickListener;
//    private LabelClickListener mLabelClickListener;

    public interface FabClickListener {
        void onFabClick(View view);
    }

    interface LabelClickListener {
        void onLabelClick(View view);
    }

    public FabClickListener getFabClickListener() {
        return mFabClickListener;
    }

    public void setFabClickListener(FabClickListener fabClickListener) {
        this.mFabClickListener = fabClickListener;
    }

//    public LabelClickListener getLabelClickListener() {
//        return mLabelClickListener;
//    }

//    public void setLabelClickListener(LabelClickListener labelClickListener) {
//        this.mLabelClickListener = labelClickListener;
//    }

    public FabView(Context context) {
        super(context);
        initializeViews(context, null);
    }

    public FabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);

    }

    public FabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context, attrs);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FabView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeViews(context, attrs);

    }

    private void initializeViews(Context context, AttributeSet attrs) {
        getCustomAttributes(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMainLayout = (LinearLayout) inflater.inflate(R.layout.fab_view, this);
//        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56, getResources().getDisplayMetrics());
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, height);
//        int bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());
//        params.bottomMargin = bottomMargin;
////        params.gravity = Gravity.END;
//        mMainLayout.setLayoutParams(params);
//        mMainLayout.setBackgroundColor(getResources().getColor(R.color.black_semi_transparent));
    }

    private void getCustomAttributes(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.FabView, 0, 0);
        mLabel = a.getString(R.styleable.FabView_fab_label);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mFabLabel = (TextView) this.findViewById(R.id.fab_label);
        if (null != mLabel) {
            mFabLabel.setText(mLabel);
        }
        mFabLabel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (mLabelClickListener != null) {
//                    mLabelClickListener.onLabelClick(view);
//                }
                if (mFabClickListener != null) {
                    mFabClickListener.onFabClick(view);
                }
            }
        });
        mFloatingActionButton = (FloatingActionButton) this.findViewById(R.id.fab_button);
        mFloatingActionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFabClickListener != null) {
                    mFabClickListener.onFabClick(view);
                }
            }
        });

    }
}
