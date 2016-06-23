package com.ratingfund.app.util;

import java.io.Closeable;
import java.io.IOException;

public class IOCloseUtil {
	public static void close(Closeable ... io){
		for(Closeable temp : io){
			if(null!=temp){
				try {
					temp.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
	}
}
