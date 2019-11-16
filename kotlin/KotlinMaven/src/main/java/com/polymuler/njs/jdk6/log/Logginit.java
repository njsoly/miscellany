package com.polymuler.njs.jdk6.log;

import njsoly.What;
import org.apache.log4j.Logger;

public interface Logginit {

	Logger getLogger();

	Logger getLogger(Class<?> whatClass);
	
	
} // end interface Logginit
