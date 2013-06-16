package com.github.propra13.gruppe43;


//Interface für Objekte, die auf KeyInput reagieren können
public interface KeyInterface {
	abstract void pressed(String key);
	abstract void released(String key);

}
