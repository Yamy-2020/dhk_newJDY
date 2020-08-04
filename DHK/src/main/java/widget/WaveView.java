package widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.kym.ui.R;
import com.kym.ui.util.DIsplayUtils;

/**
 * Created by zachary on 2018/1/22.
 */

public class WaveView extends ImageView {

    private Path path = new Path();
    private Paint mPaint;
    private int dp10;
    private final int dp5;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WaveView);
        int mWaveColor = ta.getColor(R.styleable.WaveView_wave_background, 0xFFFF4144);
        ta.recycle();

        dp10 = DIsplayUtils.dp2px(context, 10);
        dp5 = DIsplayUtils.dp2px(context, 5);
        mPaint = new Paint();
        mPaint.setColor(mWaveColor);
        mPaint.setAntiAlias(true);

        mPaint.setStyle(Paint.Style.FILL);
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int count = width / dp5 / 2 + 1;
        path.reset();
        path.moveTo(dp10, 0);
        path.lineTo(width - dp10, 0);
        path.quadTo(width, 0, width, dp10);
        path.lineTo(width, height);
        path.lineTo(0, height);
        path.lineTo(0, dp10);
        path.quadTo(0, 0, dp10, 0);
        canvas.clipPath(path);
        super.onDraw(canvas);

        int currentY = dp10 * 3 / 2;
        int stepSize = dp5 * 2;
        int controlPointY = currentY - dp5  * 3 / 2;
        path.reset();
        path.moveTo(0, 0);
        path.lineTo(width, 0);
        path.lineTo(width, currentY);
        for (int i = 0; i < count; i++) {
            if (i == count - 1) {
                path.quadTo(width - dp5 - stepSize * i, controlPointY, 0, currentY);
            } else {
                path.quadTo(width - dp5 - stepSize * i, controlPointY, width - stepSize * (i + 1), currentY);
            }
        }
        canvas.drawPath(path, mPaint);
    }
}