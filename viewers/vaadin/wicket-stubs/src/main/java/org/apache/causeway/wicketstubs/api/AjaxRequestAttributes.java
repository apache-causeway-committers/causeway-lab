package org.apache.causeway.wicketstubs.api;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public final class AjaxRequestAttributes {
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
    }

    public Method getMethod() {
        return this.method;
    }

    public AjaxRequestAttributes setMethod(Method method) {
        this.method = (Method)Args.notNull(method, "method");
        return this;
    }

    public boolean isPreventDefault() {
        return this.preventDefault;
    }

    public AjaxRequestAttributes setPreventDefault(boolean preventDefault) {
        this.preventDefault = preventDefault;
        return this;
    }

    public AjaxChannel getChannel() {
        return this.channel;
    }

    public AjaxRequestAttributes setChannel(AjaxChannel channel) {
        return null;
    }

    public enum Method {
        GET,
        POST;

        public String toString() {
            return this.name();
        }
    }

    public enum EventPropagation {
        STOP,
        STOP_IMMEDIATE,
        BUBBLE;

        EventPropagation() {
        }
    }
}
