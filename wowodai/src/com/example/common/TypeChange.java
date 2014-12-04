package com.example.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.Editable;

/**
 * 类型之间的转换
 * 
 * @author vxxv
 * 
 */
public class TypeChange {

	/**
	 * byte[] → Bitmap
	 * 
	 * @param b
	 * @return
	 */
	public Bitmap Bytes2Bimap(byte[] b) {
		if (b != null && b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {
			return null;
		}
	}

	/**
	 * Bitmap → byte[]
	 * 
	 * @param bm
	 * @return
	 */
	public byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (bm == null) {
			return null;
		}
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * Bitmap缩放
	 * 
	 * @param bitmap
	 * @param width
	 * @param height
	 * @return
	 */
	public Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
		if (bitmap == null) {
			return null;
		}
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / w);
		float scaleHeight = ((float) height / h);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		return newbmp;
	}

	/**
	 * 把输入流转成字符串
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public String readInputStream(InputStream is) throws IOException {
		String result = null;
		if (is == null)
			return null;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		int len = 0;
		byte[] buf = new byte[1024];
		while ((len = is.read(buf)) != -1) {
			bout.write(buf, 0, len);
		}
		result = new String(bout.toByteArray());
		is.close();
		bout.close();
		return URLDecoder.decode(result, "utf-8");
	}

	/**
	 * Editable类型数据转成String,如果Editable数据为空返回"",而不是NULL
	 * 
	 * @param editable
	 * @return
	 */

	public String editable2String(Editable editable) {
		String result = "";
		if (editable != null) {
			result = editable.toString();
		}
		return result;
	}

	/**
	 * CharSequence类型数据转成String,如果CharSequence数据为空返回"",而不是NULL
	 * 
	 * @param charSequence
	 * @return
	 */
	public String charSequence(CharSequence charSequence) {
		String result = "";
		if (charSequence != null) {
			result = charSequence.toString();
		}
		return result;
	}

	/**
	 * Object类型数据转成String,如果Object数据为空返回"",而不是NULL
	 * 
	 * @param object
	 * @return
	 */

	public String object2String(Object object) {
		String result = "";
		if (object != null) {
			result = object.toString();
		}
		return result;
	}

	/**
	 * Object类型数据转成int,如果Object数据为空返回"0",而不是NULL
	 * 
	 * @param object
	 * @return
	 */

	public int object2Integer(Object object) {
		int result = 0;
		if (object != null) {
			result = (int) Float.parseFloat(object.toString());
		}
		return result;
	}

	/**
	 * 获取圆角位图的方法
	 * 
	 * @param bitmap
	 *            需要转化成圆角的位图
	 * @param pixels
	 *            圆角的度数，数值越大，圆角越大
	 * @return 处理后的圆角位图
	 */
	public Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		if (bitmap == null) {
			return null;
		}
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

}
