package com.github.propra13.gruppe43;


//Interface f�r Objekte, die auf KeyInput reagieren k�nnen
public interface KeyInterface {
	abstract void pressed(String key);
	abstract void released(String key);

}
