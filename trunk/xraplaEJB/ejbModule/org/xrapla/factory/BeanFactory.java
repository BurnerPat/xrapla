package org.xrapla.factory;

import javax.naming.InitialContext;

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
        // TODO Auto-generated constructor stub
    }

    public static UserProviderLocal getUserProvider()
    {
    	UserProviderLocal bean = null;
        try
        {
            InitialContext ctx = new InitialContext();
            System.out.println("-------------->>>>> Name: " + UserProvider.class.getName());
            bean = (UserProviderLocal) ctx.lookup("java:global/xrapla/xraplaEJB/UserProvider!org.xrapla.sessionbean.UserProviderLocal");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return bean;
    }
}
