package com.xrc.restlet;

import org.restlet.resource.Resource;

import java.util.function.Function;

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

    public <T> T getRequestAttribute(String attribute, Function<Object, T> converter) {
        Object requestAttribute = getRequestAttribute(attribute);
        return converter.apply(requestAttribute);
    }
}
