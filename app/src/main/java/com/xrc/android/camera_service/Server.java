package com.xrc.android.camera_service;

import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.engine.Engine;
import org.restlet.engine.connector.ConnectorHelper;
import org.restlet.ext.nio.HttpServerHelper;

import java.util.Collections;
import java.util.List;

class Server {

    private static final Protocol PROTOCOL = Protocol.HTTP;

    private static final int PORT = 9001;

    private final Component restletComponent = new Component();
    void init() {
        restletComponent.getServers().add(PROTOCOL, PORT);
        restletComponent.getDefaultHost().attach(WebApplication.PATH, new WebApplication());

        Engine engine = Engine.getInstance();
        ConnectorHelper<org.restlet.Server> httpServerHelper = new HttpServerHelper(null);
        List<ConnectorHelper<org.restlet.Server>> registeredServers =
                Collections.singletonList(httpServerHelper);
        engine.setRegisteredServers(registeredServers);

    }

    void start() throws Exception {
        restletComponent.start();
    }

    void stop() throws Exception {
        restletComponent.stop();
    }
}
