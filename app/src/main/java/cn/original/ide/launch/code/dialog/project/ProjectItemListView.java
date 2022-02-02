package cn.original.ide.launch.code.dialog.project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class ProjectItemListView extends ListView {
    public boolean isOverScroll = false;
    private float downY;
    private float moveY;
    private CoordinatorLayout bottomCoordinator;

    public ProjectItemListView(Context context) {
        super(context);
        init();
    }

    public ProjectItemListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProjectItemListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressWarnings("all")
    private void init() {
        // todo
    }

    public void setCoordinatorDisallow() {
        if (bottomCoordinator == null)
            return;
        bottomCoordinator.requestDisallowInterceptTouchEvent(true);
    }

    /**
     * 绑定需要被拦截 intercept 的 CoordinatorLayout
     *
     * @param contentView View
     */
    public void bindBottomSheetDialog(View contentView) {
        // try throw illegal
        try {
            FrameLayout parentOne = (FrameLayout) contentView.getParent();
            this.bottomCoordinator = (CoordinatorLayout) parentOne.getParent();
            setOnTouchListener(
                    new OnTouchListener() {
                        @SuppressLint("ClickableViewAccessibility")
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (bottomCoordinator == null)
                                return false;
                            int firstVisiblePos = getFirstVisiblePosition();
                            switch (event.getAction()) {
                                case MotionEvent.ACTION_DOWN:
                                    downY = event.getRawY();
                                    bottomCoordinator.requestDisallowInterceptTouchEvent(true);
                                    break;
                                case MotionEvent.ACTION_MOVE:
                                    moveY = event.getRawY();
                                    if ((moveY - downY) > 10) {
                                        // coordinator.requestDisallowInterceptTouchEvent(true);
                                        if (firstVisiblePos == 0 && isOverScroll) {
                                            bottomCoordinator.requestDisallowInterceptTouchEvent(false);
                                            break;
                                        }
                                    }
                                    bottomCoordinator.requestDisallowInterceptTouchEvent(true);
                                    break;
                                case MotionEvent.ACTION_UP:
                                    break;
                            }
                            return false;
                        }
                    }
            );
        } catch (Exception e) {
            // maybe 可能是强转异常
            // todo
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (bottomCoordinator == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        int size = (int) ((float) (getResources().getDisplayMetrics().heightPixels * 0.618));
        @SuppressLint("WrongConstant") int newHeightSpec = MeasureSpec.makeMeasureSpec(
                size, Integer.MIN_VALUE // mode
        );
        super.onMeasure(widthMeasureSpec, newHeightSpec);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        isOverScroll = clampedY;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

    }

}
