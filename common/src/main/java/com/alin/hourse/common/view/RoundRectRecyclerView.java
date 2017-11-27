package com.alin.hourse.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Path.FillType;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.alin.hourse.common.R;


/**
 * Created by 003 on 2017-05-24.
 */
public class RoundRectRecyclerView extends RecyclerView {
    private Paint paint;

    private Path path;

    private RectF rect;

    private float cornerRadius;

    public RoundRectRecyclerView(Context context)
    {
        this(context,null);
    }

    public RoundRectRecyclerView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        initialize(context,attrs);
    }

    private void initialize(Context context,AttributeSet attrs)
    {
        if(attrs != null)
        {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundRectRecyclerView);

            try
            {
                this.cornerRadius = ta.getDimension(R.styleable.RoundRectRecyclerView_corner_radius, 0);
            }
            catch(Throwable t)
            {}
            finally
            {
                ta.recycle();
            }
        }

        this.rect = new RectF();
        this.path = new Path();
        this.paint = new Paint();
        path.setFillType(FillType.INVERSE_WINDING);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        this.setWillNotDraw(false);
        this.setLayerType(LAYER_TYPE_HARDWARE, null);
    }

    private void resetPath()
    {
        rect.set(0, 0, this.getWidth(), this.getHeight());
        path.reset();
        path.addRoundRect(rect, cornerRadius, cornerRadius, Direction.CW);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        resetPath();
    }

    @Override
    public void draw(Canvas canvas)
    {
        int saveLayerCount = 0;
        int width = this.getWidth();
        int height = this.getHeight();

        if(cornerRadius > 0 && width > 0 && height > 0)
        {
            saveLayerCount = canvas.saveLayerAlpha(0, 0, width, height, 255, Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
        }

        super.draw(canvas);

        if(saveLayerCount != 0)
        {
            canvas.drawPath(path, paint);
            canvas.restoreToCount(saveLayerCount);
        }
    }

    public float getCornerRadius()
    {
        return this.cornerRadius;
    }

    public void setCornerRadius(float cornerRadius)
    {
        this.cornerRadius = cornerRadius;
        resetPath();
        this.invalidate();
    }
}