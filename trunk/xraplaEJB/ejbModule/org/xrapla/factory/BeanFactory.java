package org.xrapla.factory;

import javax.naming.InitialContext;

import org.xrapla.sessionbean.AppointmentProviderLocal;
import org.xrapla.sessionbean.UserProvider;
import org.xrapla.sessionbean.UserProviderLocal;

/**
 * Session Bean implementation class BeanFactory
 */

public class BeanFactory {

    /**
     * Default constructor. 
     */
    private BeanFactory() {

    }

    public static UserProviderLocal getUserProvider() {
    	UserProviderLocal bean = null;
    	
        try {
            InitialContext ctx = new InitialContext();
            bean = (UserProviderLocal) ctx.lookup("java:global/xrapla/xraplaEJB/UserProvider!org.xrapla.sessionbean.UserProviderLocal");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return bean;
    }
    
    public static AppointmentProviderLocal getAppointmentProvider() {
    	AppointmentProviderLocal bean = null;
    	
    	try {
    		InitialContext ctx = new InitialContext();
    		bean = (AppointmentProviderLocal) ctx.lookup("java:global/xrapla/xraplaEJB/AppointmentProvider!org.xrapla.sessionbean.AppointmentProviderLocal");
    	}
    	catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	
    	return bean;
    }
}
