package com.android.game;

import android.graphics.Typeface;
import android.view.MotionEvent;

import com.android.angle.AngleActivity;
import com.android.angle.AngleFont;
import com.android.angle.AngleObject;
import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleString;
import com.android.angle.AngleUI;

public class MenuUI extends AngleUI
{
	//PASO 6:
	//Como ya hemos dicho, el motor usa una View principal. En esta View hemos a�adido una UI (MenuUI)
	//y ahora en esta UI a�adiremos m�s cosas.
	//Haremos lo mismo con la UI de juego. La gracia de las UIs, es que s�lo hay una activa,
	//de esta manera, cuando establecemos una, lo que hacemos es dejar activa s�lo una de las ramas del �rbol
	//tanto para su dibujado como para su l�gica.
	//Ahora bien. En lugar de a�adir diretamente todos nuestros objetos a las UIs, es m�s conveniente usar
	//ramas intermedias.
	//Para eso usaremos AngleObjects como grupos de objetos.
	private AngleObject ogMenuTexts;
	
	//PASO 7:
	//Vamos a crear 2 textos para el men�. Para ello, necesitaremos una fuente 
	private AngleString strPlay;
	private AngleString strHiScore;
	private AngleString strExit;

	private int mHiScore;

	public MenuUI(AngleActivity activity)
	{
		super(activity);
		ogMenuTexts = new AngleObject();

		//PASO 8:
		//Ya que estamos, a�adiremos tambi�n una imagen de fondo
		//Esta no es la forma m�s clara, pero si es la m�s r�pida de a�adir un objeto (en este caso un sprite)
		addObject(new AngleSprite(160, 240, new AngleSpriteLayout(mActivity.mGLSurfaceView, 320, 480, com.android.tutorial.R.drawable.bg_menu)));
		//Por partes:
		//AddObject a�ade un objeto. en este caso a this y adem�s devuelve el objeto a�adido (m�s adelante veremos para que usarlo)
		//El objeto que estamos a�adiendo es un AngleSprite y usamos su constructor (X, Y, AngleSpriteLayout)
		//El AngleSpriteLayout lo creamos tambi�n inline. Usando su 2� constructo m�s sencillo (como especificamos las dimensiones, no podemos usar el m�s simple)
		//En cualquier caso, siempre hay que pasarle la instancia de la view principal y el Drawable con la imagen
		
		//PASO 9:
		//Por encima del fondo, a�adimos un grupo de objetos, donde pondremos el men� en si.
		//En el caso actual, no seria necesario, pero si m�s adelante queremos a�adir otros objetos sobre el fondo y bajo los textos (sin tener que usar las Z) para conseguir algunos efectos visuales,
		//ya lo tenemos.
		addObject(ogMenuTexts);

		//PASO 10:
		//Creamos una fuente para nuestros textos. En este caso, la crearemos usando un ttf.
		//Altura=25, 222 caracteres, sin borde ni espacio entre caracteres, de color cyan y con el canal alfa al 100% 
		AngleFont fntCafe=new AngleFont(mActivity.mGLSurfaceView, 25, Typeface.createFromAsset(mActivity.getAssets(),"cafe.ttf"), 222, 0, 0, 30, 200, 255, 255);
		
		//PASO 11:
		//Creamos y a�adimos los 3 textos del men� a ogMenuTexts
		//Ahora veremos la utilidad de que addObject devuelva el objeto a�adido para hacerlo todo inline
		//Podriamos a�adirlos antes del paso 9. No importa el orden en el que se construya el �rbol
		//En el constructor del String, tenemos: la fuente que usa, la posici�n en pantalla de la linea de esctritura 
		//(en este caso, al usar una alineaci�n al centro, la posici�n en X, marca el centro) y la alineaci�n horizontal
		strPlay = (AngleString) ogMenuTexts.addObject(new AngleString(fntCafe, "Play", 160, 180, AngleString.aCenter));
		strHiScore = (AngleString) ogMenuTexts.addObject(new AngleString(fntCafe, "Hi Score", 160, 210, AngleString.aCenter));
		strExit = (AngleString) ogMenuTexts.addObject(new AngleString(fntCafe, "Exit", 160, 390, AngleString.aCenter));
		
		//La estructura por ahora nos queda as�:
		//mGLSurfaceView
		// >mTheGame
		// >mTheMenu
		//   >sprBackground
		//   >ogMenuTexts
		//     >strPlay
		//     >strExit
	}

	//PASO 12:
	//Sobrecargamos el m�todo onTouchEvent para responder cuando uno de los textos sea pulsado 
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			float eX = event.getX();
			float eY = event.getY();

			//PASO 13:
			//El m�todo test de AngleString, comprueba si una posici�n determinada, est� dentro de las dimensiones del texto 
			if (strPlay.test(eX, eY))
				//Si es as�, en este caso, cambiamos la UI por la del juego
				((StepByStepGame) mActivity).setUI(((StepByStepGame) mActivity).mTheGame);
			else if (strExit.test(eX, eY))
				mActivity.finish();

			return true;
		}
		return false;
	}

	//PASO 14:
	//Sobrecargamos el callback onActivate para actualizar el HiScore 
	@Override
	public void onActivate()
	{
		if (((StepByStepGame) mActivity).mTheGame.mScore>mHiScore)
				mHiScore=((StepByStepGame) mActivity).mTheGame.mScore;
		strHiScore.set("Hi Score: "+mHiScore);
		super.onActivate();
	}
	
	//(continua em GameUI)
}
