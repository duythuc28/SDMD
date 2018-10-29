package au.edu.swin.sdmd.movieratingsjava.View;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

public class RatingView extends android.support.v7.widget.AppCompatImageView {

    private Bitmap bitmap;
    private Context mContext;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // TODO:
    }

    public RatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        bitmap = Bitmap.createBitmap(192, 192, Bitmap.Config.ARGB_8888);
    }

    public void setImageByName(String movieName, String movieRating) {
        if (AsyncDrawable.cancelPotentialWork(movieName, this)) {
            final BitmapWorkerTask task = new BitmapWorkerTask(this);
            final AsyncDrawable asyncDrawable =
                    new AsyncDrawable(mContext.getResources(), bitmap, task);
            this.setImageDrawable(asyncDrawable);
            task.execute(movieName, movieRating);
        }
    }

    /** Creates a unique movie icon based on name and rating */
    private Bitmap getMovieIcon(String movieName, String movieRating) {
        int bgColor = getColor(movieName);
        bitmap.eraseColor(bgColor); // fill bitmap with the color
        Canvas c = new Canvas(bitmap);
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(getTextColor(bgColor));
        p.setTextSize(96.0f);
        c.drawText(movieRating, 32, 96, p);
        return bitmap;
    }

    /** Construct a color from a movie name */
    private int getColor(String name) {
        String hex = toHexString(name);
        String red = "#"+hex.substring(0,2);
        String green = "#"+hex.substring(2,4);
        String blue = "#"+hex.substring(4,6);
        String alpha = "#"+hex.substring(6,8);
        int color = Color.argb(Integer.decode(alpha), Integer.decode(red),
                Integer.decode(green), Integer.decode(blue));
        return color;
    }

    /** Given a movie name -- generate a hex value from its hashcode */
    private String toHexString(String name) {
        int hc = name.hashCode();
        String hex = Integer.toHexString(hc);
        if (hex.length() < 8)
        {
            hex = hex+hex+hex;
            hex = hex.substring(0,8); // use default color value
        }
        return hex;
    }

    /** Crude optimization to obtain a contrasting color -- does not work well yet */
    private int getTextColor(int bg) {

        int r = Color.red(bg);
        int g = Color.green(bg);
        int b = Color.blue(bg);
        String hex = Integer.toHexString(r)+Integer.toHexString(g);
        hex += Integer.toHexString(b);

        int cDec = Integer.decode("#"+hex);
        if (cDec > 0xFFFFFF/2)  // go dark for lighter shades
            return Color.rgb(0, 0, 0);
        else
        {
            r = (r+128)%256;
            g = (g+128)%256;
            b = (b+128)%256;
            return Color.rgb(r,g,b);
        }
    }


    class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private String movieName;
        private String movieRating;

        public BitmapWorkerTask(ImageView imageView) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(String... params) {
            movieName = params[0];
            movieRating = params[1];
            return getMovieIcon(params[0], params[1]);
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {

            if (isCancelled()) {
                bitmap = null;
            }

            if (bitmap != null) {
                final ImageView imageView;
                imageView = imageViewReference.get();
                final BitmapWorkerTask bitmapWorkerTask =
                        AsyncDrawable.getBitmapWorkerTask(imageView);
                if (this == bitmapWorkerTask) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }

    }

    static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

        private AsyncDrawable(Resources res, Bitmap bitmap,
                             BitmapWorkerTask bitmapWorkerTask) {
            super(res, bitmap);
            bitmapWorkerTaskReference =
                    new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }

        public static boolean cancelPotentialWork(String movieName, ImageView imageView) {
            final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

            if (bitmapWorkerTask != null) {
                final String bitmapData = bitmapWorkerTask.movieName;
                if (!bitmapData.equals(movieName) ) {
                    // Cancel previous task
                    bitmapWorkerTask.cancel(true);
                } else {
                    // The same work is already in progress
                    return false;
                }
            }
            // No task associated with the ImageView, or an existing task was cancelled
            return true;
        }

        public static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
            if (imageView != null) {
                final Drawable drawable = imageView.getDrawable();
                if (drawable instanceof AsyncDrawable) {
                    final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                    return asyncDrawable.getBitmapWorkerTask();
                }
            }
            return null;
        }
    }

}
