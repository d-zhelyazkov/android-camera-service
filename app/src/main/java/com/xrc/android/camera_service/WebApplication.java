package com.xrc.android.camera_service;

import com.xrc.android.camera_service.resource.ImageResource;
import com.xrc.android.camera_service.resource.SettingsResource;
import com.xrc.android.camera_service.resource.SettingResource;
import com.xrc.android.camera_service.resource.SettingValuesResource;
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
        apiRouter.attach(SettingValuesResource.PATH, SettingValuesResource.class);

        return apiRouter;
    }

}
