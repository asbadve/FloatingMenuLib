package com.ajinkyabadve.floatingmenulib.viewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.ajinkyabadve.floatingmenulib.R;
import com.ajinkyabadve.floatingmenulib.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ajinkya on 15/07/2016.
 */
public class FloatingActionMenuLayout extends FrameLayout {
    private static final String TAG = FloatingActionMenuLayout.class.getSimpleName();
    private FloatingActionButton mainFloatingActionButton;
    private View mMainLayout;
    public static boolean FabFlag = false;
    private int countSubMenu;
    List<View> views = new ArrayList<>();
    private FabMenuClickListener mFabMenuClickListener;

    public FabMenuClickListener getFabMenuClickListener() {
        return mFabMenuClickListener;
    }

    public void setFabMenuClickListener(FabMenuClickListener fabMenuClickListener) {
        this.mFabMenuClickListener = fabMenuClickListener;
    }

    public interface FabMenuClickListener {
        void onFabClick(View view, boolean isMenuExpand);

    }

    public FloatingActionMenuLayout(Context context) {
        super(context);
    }

    public FloatingActionMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            initViews(context, attrs);
    }


    public FloatingActionMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode())
            initViews(context, attrs);
    }


    private void initViews(Context context, AttributeSet attrs) {
        getCustomAttributes(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMainLayout = inflate(getContext(), R.layout.floating_menu, this);
        mainFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab_main_button);
        mainFloatingActionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mFabMenuClickListener.onFabClick(view, !FabFlag);
                if (!FabFlag) {
                    showFabMenu();
                } else {
                    hideFabMenu();
                }


            }


        });

    }

    public void hideFabMenu() {
        FabFlag = false;
        mMainLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));

        int size = views.size();
        for (int i = 0; i < size; i++) {
            View view = views.get(i);
            view.setVisibility(INVISIBLE);
            view.animate().translationY(0)
                    .setInterpolator(new DecelerateInterpolator(2));

        }

    }


    public void showFabMenu() {
        FabFlag = true;
        mMainLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.black_semi_transparent));
        int size = views.size();
        for (int i = 0; i < size; i++) {
            View view = views.get(i);
            view.setVisibility(VISIBLE);
            int dpToMovie = 0;
            if (i == 0) {
                dpToMovie = 80;
            } else {
                dpToMovie = ((i + 1) * 80);
            }
            int fab1spec = Util.convertDptoPixcel(getContext(), dpToMovie);
            view.animate().translationY(-fab1spec)
                    .setInterpolator(new DecelerateInterpolator(2));
        }
    }

    private void getCustomAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.FloatingActionMenuLayout, 0, 0);
        countSubMenu = a.getInt(R.styleable.FloatingActionMenuLayout_count_submenu, 0);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        if (child instanceof FabView) {
            views.add(child);
        }
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        final int id = child.getId();
        if (child instanceof FabView) {
            ((FabView) child).setClipChildren(true);
            child.setMinimumHeight(Util.convertDptoPixcel(getContext(), 56));
            child.setVisibility(INVISIBLE);
            super.addView(child, index, params);
        } else {
            super.addView(child, index, params);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        views = null;
    }
}
