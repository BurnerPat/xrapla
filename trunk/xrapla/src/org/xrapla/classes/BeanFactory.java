package org.xrapla.classes;

import javax.naming.InitialContext;

public class BeanFactory {
    public static UserProviderInterface getUserProvider()
    {
    	UserProviderInterface bean = null;
        try
        {
            InitialContext ctx = new InitialContext();
            bean = (UserProviderInterface) ctx.lookup("java:global/EnterpriseApplication/EnterpriseApplication-ejb/SessionBean!controller.UserProviderInterface");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return bean;
    }
}
