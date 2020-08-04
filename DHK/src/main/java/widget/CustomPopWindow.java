package widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.StyleRes;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * 自定义PopupWindow
 * Created by zachary on 2018/1/2.
 */

public class CustomPopWindow implements PopupWindow.OnDismissListener {
    private Context mContext;
    private PopupWindow mPopupWindow;

    private boolean mFocusable = true;
    private boolean mOutsideTouchable = true;
    private PopupWindow.OnDismissListener dismissListener;
    private View mContentView;
    private int mAnimationStyle = -1;
    private boolean mTranslucent = true;//半透明
    private int mWidth = 0;
    private int mHeight = 0;

    private boolean showing = false;
    private boolean cancelable = true;

    private CustomPopWindow(Context context) {
        mContext = context;
    }

    private CustomPopWindow create() {
        if (mWidth == 0 || mHeight == 0) {
            mPopupWindow = new PopupWindow(mContentView, ViewGroup.LayoutParams.MATCH_PARENT, 480);
        } else {
            mPopupWindow = new PopupWindow(mContentView, mWidth, mHeight);
        }
        mPopupWindow.setFocusable(mFocusable);
        mPopupWindow.setOutsideTouchable(mOutsideTouchable);
        if (mAnimationStyle != -1) {
            mPopupWindow.setAnimationStyle(mAnimationStyle);
        }
        if (cancelable) {
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        if (dismissListener != null) {
            mPopupWindow.setOnDismissListener(dismissListener);
        }
        mPopupWindow.setOnDismissListener(this);
        return this;

    }

    private void setBackground(float alpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
                .getAttributes();
        lp.alpha = alpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    public void showAsDropDown(View view) {
        if (mPopupWindow != null) {
            if (mTranslucent) {
                setBackground(0.5F);
            }
            mPopupWindow.showAsDropDown(view);
            showing = true;
        }
    }

    public void showAsDropDown(View view, int xOff, int yOff) {
        if (mPopupWindow != null) {
            if (mTranslucent) {
                setBackground(0.5F);
            }
            mPopupWindow.showAsDropDown(view, xOff, yOff);
            showing = true;
        }
    }

    public void showAtLocation(View parent, int gravity, int xOff, int yOff) {
        if (mPopupWindow != null) {
            if (mTranslucent) {
                setBackground(0.5F);
            }
            mPopupWindow.showAtLocation(parent, gravity, xOff, yOff);
            showing = true;
        }
    }

    public void showAtLocation(View parent, int gravity) {
        if (mPopupWindow != null) {
            if (mTranslucent) {
                setBackground(0.5F);
            }
            mPopupWindow.showAtLocation(parent, gravity, 0, 0);
            showing = true;
        }
    }

    public void dismiss() {
        if (mPopupWindow != null) {
            if (dismissListener != null) {
                dismissListener.onDismiss();
            }
            if (mTranslucent) {
                setBackground(1F);
            }
            mPopupWindow.dismiss();
            showing = false;
        }
    }

    public boolean isShowing() {
        return showing;
    }

    @Override
    public void onDismiss() {
        dismiss();
    }

    public static class Builder {

        private CustomPopWindow mCustomPopWindow = null;

        public Builder(Activity context) {
            mCustomPopWindow = new CustomPopWindow(context);
        }

        public Builder setFocusable(boolean focusable) {
            mCustomPopWindow.mFocusable = focusable;
            return this;
        }

        public Builder setOutsideFocusable(boolean outsideFocusable) {
            mCustomPopWindow.mOutsideTouchable = outsideFocusable;
            return this;
        }

        public Builder setOnDismissListener(PopupWindow.OnDismissListener listener) {
            mCustomPopWindow.dismissListener = listener;
            return this;
        }

        public Builder setContentView(View view) {
            mCustomPopWindow.mContentView = view;
            return this;
        }

        public Builder setAnimationStyle(@StyleRes int animationStyle) {
            mCustomPopWindow.mAnimationStyle = animationStyle;
            return this;
        }

        public Builder setTranslucent(boolean mTranslucent) {
            mCustomPopWindow.mTranslucent = mTranslucent;
            return this;
        }

        public Builder size(int width, int height) {
            mCustomPopWindow.mWidth = width;
            mCustomPopWindow.mHeight = height;
            return this;
        }

        public Builder setCancelable(boolean b) {
            mCustomPopWindow.cancelable = b;
            return this;
        }

        public CustomPopWindow build() {
            return mCustomPopWindow.create();
        }
    }
}
