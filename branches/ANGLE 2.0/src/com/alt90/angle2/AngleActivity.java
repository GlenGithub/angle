package com.alt90.angle2;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class AngleActivity extends Activity
{
	private static final int sPointerSize = 10;
	public static AngleActivity uInstance = null;
	public static boolean[] iKeys = new boolean[KeyEvent.MAX_KEYCODE];
	public static Pointer[] iPointer = new Pointer[sPointerSize];
	public static Fling[] iFling = new Fling[sPointerSize];
	protected GLSurfaceView lGLSurfaceView;

	public class Pointer
	{
		public AngleVector fPosition_uu = new AngleVector();
		public boolean isDown;
		public boolean newData;

	}

	public class Fling
	{
		public long lBegin;
		public AngleVector lOrigin_uu = new AngleVector();
		public AngleVector fDelta_uu = new AngleVector();
		public float fTime;
		public boolean newData;

	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		for (int p = 0; p < sPointerSize; p++)
		{
			iPointer[p] = new Pointer();
			iFling[p] = new Fling();
		}
		uInstance = this;
		lGLSurfaceView = new GLSurfaceView(this);
		lGLSurfaceView
				.setRenderer(new AngleRenderer(getResources().getDimension(R.dimen.Width), getResources().getDimension(R.dimen.Height)));
		setContentView(lGLSurfaceView);
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		lGLSurfaceView.onPause();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		lGLSurfaceView.onResume();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		iKeys[keyCode] = true;
		return true;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		iKeys[keyCode] = false;
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		for (int p = 0; p < event.getPointerCount(); p++)
		{
			int pid = event.getPointerId(p);
			iPointer[pid].fPosition_uu.fX = event.getX(pid);
			iPointer[pid].fPosition_uu.fY = event.getY(pid);
			iPointer[pid].fPosition_uu.set(AngleRenderer.coordsScreenToUser(iPointer[pid].fPosition_uu));
			switch (event.getAction())
			{
				case MotionEvent.ACTION_DOWN:
					iPointer[pid].isDown = true;
					iPointer[pid].newData = true;
					if (iFling[pid].lBegin==0)
					{
						iFling[pid].lBegin=android.os.SystemClock.uptimeMillis();
						iFling[pid].lOrigin_uu.set(iPointer[pid].fPosition_uu);
					}
					break;
				case MotionEvent.ACTION_UP:
					iPointer[pid].isDown = false;
					iPointer[pid].newData = true;
					if (iFling[pid].lBegin!=0)
					{
						iFling[pid].fTime=(android.os.SystemClock.uptimeMillis()-iFling[pid].lBegin)/1000.f;
						iFling[pid].lBegin=0;
						iFling[pid].fDelta_uu.set(iPointer[pid].fPosition_uu);
						iFling[pid].fDelta_uu.sub(iFling[pid].lOrigin_uu);
						iFling[pid].newData = true;
					}
					break;
			}
		}
		// --- Prevent flooding ---
		try
		{
			Thread.sleep(16);
		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		// ------------------------
		return true;
	}

	@Override
	public boolean onTrackballEvent(MotionEvent event)
	{
		return super.onTrackballEvent(event);
	}

}
