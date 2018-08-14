package com.server.util;

import java.util.HashMap;
import java.util.Map;

import com.server.model.Login;


public class SessionUtil {
	public static Map<String, Login> sessions = new HashMap<String, Login>();//store all normal users sessions
}
