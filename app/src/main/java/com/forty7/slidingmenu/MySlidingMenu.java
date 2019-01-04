package com.forty7.slidingmenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * 2019.1.4 Forty'7
 * xiaowangboke@vip.qq.com
 */
public class MySlidingMenu extends HorizontalScrollView {

    private static final float radio = 0.4f;//菜单占屏幕宽度比
    private final int mScreenWidth;
    private final int mMenuWidth;

    private boolean once = true;
    private boolean isOpen;
    private MySlidingMenu mySlidingMenu;

    public MySlidingMenu(final Context context, AttributeSet attrs) {
        super(context, attrs);
        mScreenWidth = Utils.getScreenWidth(context);
        mMenuWidth = (int) (mScreenWidth * radio);
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        setHorizontalScrollBarEnabled(false);
    }

    /**
     * 关闭菜单
     */

    public void closeMenu() {
        this.smoothScrollTo(0, 0);
        isOpen = false;
    }

    /**
     * 菜单是否打开
     */
    public boolean isOpen() {
        return isOpen;
    }


    /**
     * 当打开菜单时记录此 view ，方便下次关闭
     */
    private void onOpenMenu() {
        if(callMessage != null){
            callMessage.holdOpenMenu(this);
            isOpen = true;
        }
    }

    /**
     * 当触摸此 item 时，关闭上一次打开的 item
     */
    private void closeOpenMenu() {
        if (!isOpen) {
            if(callMessage != null){
                callMessage.closeOpenMenu();
            }
        }
    }

    /**
     * 获取正在滑动的 item
     */
    public MySlidingMenu getScrollingMenu() {
        return mySlidingMenu;
    }

    /**
     * 设置ScrollingMenu
     */
    public void setScrollingMenus(MySlidingMenu scrollingMenu) {
        mySlidingMenu = scrollingMenu;
    }

    /**
     * 设置本 item 为正在滑动 item
     */
    public void setScrollingMenu(MySlidingMenu scrollingMenu) {
        if(callMessage != null){
            callMessage.setScrollingMenu(scrollingMenu);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (once) {
            LinearLayout wrapper = (LinearLayout) getChildAt(0);
            wrapper.getChildAt(0).getLayoutParams().width = mScreenWidth;
            wrapper.getChildAt(1).getLayoutParams().width = mMenuWidth;
            once = false;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (getScrollingMenu() != null && getScrollingMenu() != this) {
            return false;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downTime = System.currentTimeMillis();
                closeOpenMenu();
                setScrollingMenu(this);
                break;
            case MotionEvent.ACTION_UP:
                setScrollingMenu(null);
                int scrollX = getScrollX();
                if (System.currentTimeMillis() - downTime <= 100 && scrollX == 0) {
                    if (mCustomOnClickListener != null) {
                        mCustomOnClickListener.onClick();
                    }
                    return false;
                }
                if (Math.abs(scrollX) > mMenuWidth / 2) {
                    this.smoothScrollTo(mMenuWidth, 0);
                    onOpenMenu();
                } else {
                    this.smoothScrollTo(0, 0);
                }
                return false;
        }
        return super.onTouchEvent(ev);
    }
    long downTime = 0;


    interface CustomOnClickListener {
        void onClick();
    }

    private CustomOnClickListener mCustomOnClickListener;

    void setCustomOnClickListener(CustomOnClickListener listener) {
        this.mCustomOnClickListener = listener;
    }

    /**
     * 用于通信(解耦)
     */
    interface CallMessage{
        void holdOpenMenu(MySlidingMenu slidingMenu);
        void closeOpenMenu();
        void setScrollingMenu(MySlidingMenu scrollingMenu);
    }

    public void setCallMessageListener(CallMessage callMessage){
        this.callMessage = callMessage;
    }

    private CallMessage callMessage;

}
