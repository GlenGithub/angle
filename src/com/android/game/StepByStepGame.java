package com.android.game;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.android.angle.AngleActivity;
import com.android.angle.FPSCounter;

//PASO 1:
//Lo 1� que debemos hacer es crear la Activity de nuestro juego deriv�ndola de AngleActivity.
//Con esto conseguiremos que el motor haga por si solo muchas de las cosas que necesitaremos.
/** 
* @author Ivan Pajuelo
*/
public class StepByStepGame extends AngleActivity
{
	//PASO 2:
	//En lugar de trabajar directamente sobre la Activity, crearemos dos interfaces de usuario 
	//deriv�ndolas de AngleUI. Una para el juego y otra para el men�
	//De esta forma veremos como cambiar instant�neamente de una a otra.
	GameUI mTheGame;
	MenuUI mTheMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		//Esta linea es opcional. A�ade un objeto FPSCounter directamente a la View principal
		//As� podremos ver el rendimiento de nuestro juego a trav�s de LogCat
		mGLSurfaceView.addObject(new FPSCounter());

		//PASO 3:
		//Importante. No olvidemos instanciar los objetos (esto deber�a ser obvio)
		mTheGame=new GameUI(this);
		mTheMenu=new MenuUI(this);

		//PASO 4:
		//Establecemos la interface de usuario activa. En este caso el men�.
		setUI(mTheMenu);
		
		//PASO 5:
		//Todo el motor corre sobre una View principal que crea AngleActivity (mGLSurfaceView)
		//A�n as�, en lugar de usar esta View directamente, la insertaremos dentro de un 
		//FrameLayout por si queremos a�adir Views de las API.
		FrameLayout mainLayout=new FrameLayout(this);
		mainLayout.addView(mGLSurfaceView);
		setContentView(mainLayout);
		
		//(continua en MenuUI)
	}

}
