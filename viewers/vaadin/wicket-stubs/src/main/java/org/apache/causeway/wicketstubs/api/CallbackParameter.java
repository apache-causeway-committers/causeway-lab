package org.apache.causeway.wicketstubs.api;

public class CallbackParameter {

    public static CallbackParameter context(String name) {
        return new CallbackParameter(name, null, (String) null);
    }

    public static CallbackParameter resolved(
            String name,
            String code) {
        return new CallbackParameter(null, name, code);
    }

    private CallbackParameter(
            String functionParameterName,
            String ajaxParameterName,
            String ajaxParameterCode) {
    }

}
