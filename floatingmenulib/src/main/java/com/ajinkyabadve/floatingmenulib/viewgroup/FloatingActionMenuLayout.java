package com.ajinkyabadve.floatingmenulib.viewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
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
    public boolean isMenuShown = false;
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
        mainFloatingActionButton = new FloatingActionButton(getContext());
        LayoutParams p = new LayoutParams((int) Util.convertDptoPixcel(getContext(), 56), (int) Util.convertDptoPixcel(getContext(), 56));
        p.gravity = Gravity.BOTTOM | Gravity.END;
        p.rightMargin = (int) Util.convertDptoPixcel(getContext(), 6);
        mainFloatingActionButton.setLayoutParams(p);
        float fabElevation = (int) Util.convertDptoPixcel(getContext(), 6);
        mainFloatingActionButton.setCompatElevation(fabElevation);
        mainFloatingActionButton.setUseCompatPadding(true);
        mainFloatingActionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mFabMenuClickListener.onFabClick(view, !isMenuShown);
                if (!isMenuShown) {
                    showFabMenu();
                } else {
                    hideFabMenu();
                }


            }


        });
//        this.addView(mainFloatingActionButton);


    }

    public void hideFabMenu() {
        isMenuShown = false;
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
        isMenuShown = true;
        mMainLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.black_semi_transparent));
        int size = views.size();
        for (int i = 0; i < size; i++) {
            View view = views.get(i);
            view.setVisibility(VISIBLE);
            int dpToMovie = 0;
            if (i == 0) {
                dpToMovie = 88;
            } else {
                dpToMovie = ((i + 1) * (88));
            }
            float fab1spec = Util.convertDptoPixcel(getContext(), dpToMovie);
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
    protected void onFinishInflate() {
        this.addView(mainFloatingActionButton);
        super.onFinishInflate();
    }


    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        if (child instanceof FabView) {
            views.add(child);
        } else {

        }
    }


    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        final int id = child.getId();
        if (child instanceof FabView) {
            ((FabView) child).setClipChildren(true);
            child.setMinimumHeight((int) Util.convertDptoPixcel(getContext(), 56));
            child.setVisibility(INVISIBLE);
            super.addView(child, index, params);
        } else {
            super.addView(child, index, params);
        }
    }


    @Override
    protected Parcelable onSaveInstanceState() {
        //begin boilerplate code that allows parent classes to save state
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        //end
        ss.isMenuShown = this.isMenuShown;
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        //begin boilerplate code so parent classes can restore state
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        //end
        this.isMenuShown = ss.isMenuShown;
        if (isMenuShown) {
            showFabMenu();
        } else {
            hideFabMenu();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        views = null;
    }

    static class SavedState extends BaseSavedState {
        boolean isMenuShown;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.isMenuShown = in.readByte() != 0;

        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeByte((byte) (isMenuShown ? 1 : 0));
        }

        //required field that makes Parcelables from a Parcel
        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }
}
