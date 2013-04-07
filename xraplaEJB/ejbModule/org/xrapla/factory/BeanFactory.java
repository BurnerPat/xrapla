package org.xrapla.factory;

import javax.naming.InitialContext;

import org.xrapla.sessionbean.AppointmentProviderLocal;
import org.xrapla.sessionbean.RoomProviderLocal;
import org.xrapla.sessionbean.RoomValidatorLocal;
import org.xrapla.sessionbean.UserProviderLocal;

public class BeanFactory {

	private BeanFactory() {

	}

	public static UserProviderLocal getUserProvider() {
		UserProviderLocal bean = null;

		try {
			InitialContext ctx = new InitialContext();
			bean = (UserProviderLocal) ctx
					.lookup("java:global/xrapla/xraplaEJB/UserProvider!org.xrapla.sessionbean.UserProviderLocal");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return bean;
	}

	public static AppointmentProviderLocal getAppointmentProvider() {
		AppointmentProviderLocal bean = null;

		try {
			InitialContext ctx = new InitialContext();
			bean = (AppointmentProviderLocal) ctx
					.lookup("java:global/xrapla/xraplaEJB/AppointmentProvider!org.xrapla.sessionbean.AppointmentProviderLocal");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return bean;
	}

	public static RoomProviderLocal getRoomProvider() {
		RoomProviderLocal bean = null;

		try {
			InitialContext ctx = new InitialContext();
			bean = (RoomProviderLocal) ctx
					.lookup("java:global/xrapla/xraplaEJB/RoomProvider!org.xrapla.sessionbean.RoomProviderLocal");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return bean;
	}

	public static RoomValidatorLocal getRoomValidator() {
		RoomValidatorLocal bean = null;

		try {
			InitialContext ctx = new InitialContext();
			bean = (RoomValidatorLocal) ctx
					.lookup("java:global/xrapla/xraplaEJB/RoomValidator!org.xrapla.sessionbean.RoomValidatorLocal");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return bean;
	}
}
