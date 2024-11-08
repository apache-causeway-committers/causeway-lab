package org.apache.causeway.wicketstubs.api;

import com.github.openjson.JSONException;

public class WicketRuntimeException extends RuntimeException {
    public WicketRuntimeException(String s) {
    }

    public WicketRuntimeException(JSONException e) {
    }

    public WicketRuntimeException(String s, Exception x) {
    }
}
