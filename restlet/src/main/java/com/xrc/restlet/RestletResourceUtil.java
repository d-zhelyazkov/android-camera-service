package com.xrc.restlet;

import org.restlet.resource.Resource;

public class RestletResourceUtil {

    private final Resource restletResource;

    public RestletResourceUtil(Resource restletResource) {
        this.restletResource = restletResource;
    }

    public Object getRequestAttribute(String attribute) {
        return restletResource.getRequest().getAttributes().get(attribute);
    }

    public String getRequestAttributeStr(String attribute) {
        Object attributeValue = getRequestAttribute(attribute);
        return (attributeValue != null) ? attributeValue.toString() : "";
    }
}
