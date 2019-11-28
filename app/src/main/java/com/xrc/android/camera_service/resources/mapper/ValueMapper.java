package com.xrc.android.camera_service.resources.mapper;

import java.util.HashMap;
import java.util.Map;

public class ValueMapper<T, V> {
    private final Map<T, V> representationValueMap = new HashMap<>();
    private final Map<V, T> dtoValueMap = new HashMap<>();

    protected ValueMapper(Map<T, V[]> map) {
        for (Map.Entry<T, V[]> entry : map.entrySet()) {
            T representationValue = entry.getKey();
            V[] dtoValues = entry.getValue();
            if (dtoValues.length == 0)
                continue;

            representationValueMap.put(representationValue, dtoValues[0]);
            for (V dtoValue : dtoValues) {
                dtoValueMap.put(dtoValue, representationValue);
            }

        }
    }

    public V getDto(T representationValue) {
        return representationValueMap.get(representationValue);
    }

    public T getRepresentationValue(V dtoValue) {
        return dtoValueMap.get(dtoValue);
    }
}
