package org.xrapla.factory;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.InitialContext;

import org.xrapla.sessionbean.UserProvider;
import org.xrapla.sessionbean.UserProviderLocal;

/**
 * Session Bean implementation class BeanFactory
 */
@Stateless
@LocalBean
public class BeanFactory implements BeanFactoryLocal {

    /**
     * Default constructor. 
     */
    public BeanFactory() {
        // TODO Auto-generated constructor stub
    }

    public UserProviderLocal getUserProvider()
    {
    	UserProviderLocal bean = null;
        try
        {
            InitialContext ctx = new InitialContext();
            System.out.println("-------------->>>>> Name: " + UserProvider.class.getName());
            bean = (UserProviderLocal) ctx.lookup("java:global/xrapla/org.xrapla.classes.UserProvider");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return bean;
    }
}
