package widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.kym.ui.R;
import com.kym.ui.util.DIsplayUtils;

/**
 * 最大高度的ScrollView
 * Created by zachary on 2018/1/31.
 */

public class MaxHeightScrollView extends ScrollView {

    private float height;

    public MaxHeightScrollView(Context context) {
        this(context, null);
    }

    public MaxHeightScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaxHeightScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightScrollView);
        height = ta.getDimension(R.styleable.MaxHeightScrollView_max_height, DIsplayUtils.getScreenHeight(context) / 2);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) height, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
