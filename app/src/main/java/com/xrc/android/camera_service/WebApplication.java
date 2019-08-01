package com.xrc.android.camera_service;

import com.xrc.android.camera_service.resources.ImageResource;
import com.xrc.android.camera_service.resources.SettingsResource;
import com.xrc.android.camera_service.resources.SettingResource;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

class WebApplication extends Application {

    static final String PATH = "/camera";

    private static final String DISPLAY_NAME = "Camera Web Service";

    WebApplication() {
        setName(DISPLAY_NAME);
    }

    @Override
    public Restlet createInboundRoot() {

        Router apiRouter = new Router(getContext());
        apiRouter.attach(ImageResource.PATH, ImageResource.class);
        apiRouter.attach(SettingsResource.PATH, SettingsResource.class);
        apiRouter.attach(SettingResource.PATH, SettingResource.class);

        return apiRouter;
    }

}
