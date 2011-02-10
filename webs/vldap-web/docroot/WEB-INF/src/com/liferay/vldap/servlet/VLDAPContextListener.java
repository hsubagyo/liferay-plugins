/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.vldap.servlet;

import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.BasePortalLifecycle;
import com.liferay.vldap.server.VLDAPServer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public class VLDAPContextListener
	extends BasePortalLifecycle implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		portalDestroy();

		ServletContextPool.remove(_servletContextName);
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext();

		_servletContextName = servletContext.getServletContextName();

		ServletContextPool.put(_servletContextName, servletContext);

		registerPortalLifecycle();
	}

	protected void doPortalDestroy() throws Exception {
		_vldapServer.destroy();
	}

	protected void doPortalInit() throws Exception {
		_vldapServer = new VLDAPServer();

		_vldapServer.init(_servletContextName);
	}

	private String _servletContextName;
	private VLDAPServer _vldapServer;

}