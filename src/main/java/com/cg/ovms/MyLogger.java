package com.cg.ovms;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.time.LocalDateTime;

public class MyLogger {
    private static final Logger logger = LogManager.getLogger(MyLogger.class);
    public static String info(String string) {
    	String message = LocalDateTime.now().toString()+" :: INFO :: "+string;
        logger.info(message);
      //  logger.warn(message);
        return message;
    }
}



