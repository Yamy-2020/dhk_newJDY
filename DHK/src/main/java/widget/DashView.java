package widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.kym.ui.util.DIsplayUtils;

/**
 * Created by zachary on 2018/1/22.
 */

public class DashView extends ImageView {

    private int dp1;
    private Paint mPaint;

    public DashView(Context context) {
        this(context, null);
    }

    public DashView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dp1 = DIsplayUtils.dp2px(context, 2);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setColor(0xFFF5F5F5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int count = height / dp1 / 2 + 1;
        int circleDotX = width / 2;
        int startHeight = dp1 * 2;
        for (int i = 0; i < count; i++) {
            canvas.drawCircle(circleDotX, startHeight + dp1 * 3 * i, dp1, mPaint);
        }
    }
}