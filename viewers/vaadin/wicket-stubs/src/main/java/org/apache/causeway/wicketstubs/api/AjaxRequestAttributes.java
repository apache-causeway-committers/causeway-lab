package org.apache.causeway.wicketstubs.api;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class AjaxRequestAttributes {
    public static final String XML_DATA_TYPE = "xml";
    private boolean multipart = false;
    private Method method;
    private Duration requestTimeout;
    private boolean preventDefault;
    private EventPropagation eventPropagation;
    private String[] eventNames;
    private String formId;
    private String submittingComponentName;
    private boolean wicketAjaxResponse;
    private String dataType;
    private List<IAjaxCallListener> ajaxCallListeners;
    private Map<String, Object> extraParameters;
    private List<CharSequence> dynamicExtraParameters;
    private AjaxChannel channel;
    private boolean async;
    private ThrottlingSettings throttlingSettings;
    private CharSequence childSelector;
    private boolean serializeRecursively;

    public AjaxRequestAttributes() {
        this.method = AjaxRequestAttributes.Method.GET;
        this.preventDefault = false;
        this.eventPropagation = AjaxRequestAttributes.EventPropagation.BUBBLE;
        this.eventNames = new String[0];
        this.wicketAjaxResponse = true;
        this.dataType = "xml";
        this.async = true;
    }

    public CharSequence getChildSelector() {
        return this.childSelector;
    }

    public void setChildSelector(CharSequence childSelector) {
        this.childSelector = childSelector;
    }

    public boolean isMultipart() {
        return this.multipart;
    }

    public AjaxRequestAttributes setMultipart(boolean multipart) {
        this.multipart = multipart;
        return this;
    }

    public Method getMethod() {
        return this.method;
    }

    public AjaxRequestAttributes setMethod(Method method) {
        this.method = (Method)Args.notNull(method, "method");
        return this;
    }

    public Duration getRequestTimeout() {
        return this.requestTimeout;
    }

    public AjaxRequestAttributes setRequestTimeout(Duration requestTimeout) {
        this.requestTimeout = requestTimeout;
        return this;
    }

    public List<IAjaxCallListener> getAjaxCallListeners() {
        if (this.ajaxCallListeners == null) {
            this.ajaxCallListeners = new ArrayList();
        }

        return this.ajaxCallListeners;
    }

    public Map<String, Object> getExtraParameters() {
        if (this.extraParameters == null) {
            this.extraParameters = new LinkedHashMap();
        }

        return this.extraParameters;
    }

    public List<CharSequence> getDynamicExtraParameters() {
        if (this.dynamicExtraParameters == null) {
            this.dynamicExtraParameters = new ArrayList();
        }

        return this.dynamicExtraParameters;
    }

    public boolean isPreventDefault() {
        return this.preventDefault;
    }

    public AjaxRequestAttributes setPreventDefault(boolean preventDefault) {
        this.preventDefault = preventDefault;
        return this;
    }

    public EventPropagation getEventPropagation() {
        return this.eventPropagation;
    }

    public AjaxRequestAttributes setEventPropagation(EventPropagation eventPropagation) {
        this.eventPropagation = (EventPropagation)Args.notNull(eventPropagation, "eventPropagation");
        return this;
    }

    public AjaxRequestAttributes setAsynchronous(boolean async) {
        this.async = async;
        return this;
    }

    public boolean isAsynchronous() {
        return this.async;
    }

    public AjaxChannel getChannel() {
        return this.channel;
    }

    public AjaxRequestAttributes setChannel(AjaxChannel channel) {
        this.channel = channel;
        return this;
    }

    public String[] getEventNames() {
        return this.eventNames;
    }

    public AjaxRequestAttributes setEventNames(String... eventNames) {
        Args.notNull(eventNames, "eventNames");
        this.eventNames = eventNames;
        return this;
    }

    public String getFormId() {
        return this.formId;
    }

    public AjaxRequestAttributes setFormId(String formId) {
        this.formId = formId;
        return this;
    }

    public String getSubmittingComponentName() {
        return this.submittingComponentName;
    }

    public AjaxRequestAttributes setSubmittingComponentName(String submittingComponentName) {
        this.submittingComponentName = submittingComponentName;
        return this;
    }

    public boolean isWicketAjaxResponse() {
        return this.wicketAjaxResponse;
    }

    public AjaxRequestAttributes setWicketAjaxResponse(boolean wicketAjaxResponse) {
        this.wicketAjaxResponse = wicketAjaxResponse;
        return this;
    }

    public String getDataType() {
        return this.dataType;
    }

    public AjaxRequestAttributes setDataType(String dataType) {
        this.dataType = Args.notEmpty(dataType, "dataType");
        return this;
    }

    public ThrottlingSettings getThrottlingSettings() {
        return this.throttlingSettings;
    }

    public AjaxRequestAttributes setThrottlingSettings(ThrottlingSettings throttlingSettings) {
        this.throttlingSettings = throttlingSettings;
        return this;
    }

    public boolean isSerializeRecursively() {
        return this.serializeRecursively;
    }

    public AjaxRequestAttributes setSerializeRecursively(boolean serializeRecursively) {
        this.serializeRecursively = serializeRecursively;
        return this;
    }

    public static enum Method {
        GET,
        POST;

        private Method() {
        }

        public String toString() {
            return this.name();
        }
    }

    public static enum EventPropagation {
        STOP,
        STOP_IMMEDIATE,
        BUBBLE;

        private EventPropagation() {
        }
    }
}
