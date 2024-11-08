package org.apache.causeway.wicketstubs.api;

public class CallbackParameter {
    private String functionParameterName;
    private String ajaxParameterName;
    private String ajaxParameterCode;

    public static CallbackParameter context(String name) {
        return new CallbackParameter(name, (String) null, (String) null);
    }

    public static CallbackParameter explicit(String name) {
        return new CallbackParameter(name, name, name);
    }

    public static CallbackParameter resolved(String name, String code) {
        return new CallbackParameter((String) null, name, code);
    }

    public static CallbackParameter converted(String name, String code) {
        return new CallbackParameter(name, name, code);
    }

    private CallbackParameter(String functionParameterName, String ajaxParameterName, String ajaxParameterCode) {
        this.functionParameterName = functionParameterName;
        this.ajaxParameterName = ajaxParameterName;
        this.ajaxParameterCode = ajaxParameterCode;
    }

    public String getFunctionParameterName() {
        return this.functionParameterName;
    }

    public String getAjaxParameterName() {
        return this.ajaxParameterName;
    }

    public String getAjaxParameterCode() {
        return this.ajaxParameterCode;
    }
}
