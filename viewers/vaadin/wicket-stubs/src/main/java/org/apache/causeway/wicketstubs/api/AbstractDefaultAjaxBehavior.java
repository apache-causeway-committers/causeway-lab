package org.apache.causeway.wicketstubs.api;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONException;
import com.github.openjson.JSONObject;

import lombok.SneakyThrows;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDefaultAjaxBehavior extends AbstractAjaxBehavior {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(AbstractDefaultAjaxBehavior.class);
    public static final ResourceReference INDICATOR = new PackageResourceReference(AbstractDefaultAjaxBehavior.class, "indicator.gif");
    private static final String DYNAMIC_PARAMETER_FUNCTION_SIGNATURE = "function(attrs)";
    private static final String PRECONDITION_FUNCTION_SIGNATURE = "function(attrs)";
    private static final String COMPLETE_HANDLER_FUNCTION_SIGNATURE = "function(attrs, jqXHR, textStatus)";
    private static final String FAILURE_HANDLER_FUNCTION_SIGNATURE = "function(attrs, jqXHR, errorMessage, textStatus)";
    private static final String SUCCESS_HANDLER_FUNCTION_SIGNATURE = "function(attrs, jqXHR, data, textStatus)";
    private static final String AFTER_HANDLER_FUNCTION_SIGNATURE = "function(attrs)";
    private static final String BEFORE_SEND_HANDLER_FUNCTION_SIGNATURE = "function(attrs, jqXHR, settings)";
    private static final String BEFORE_HANDLER_FUNCTION_SIGNATURE = "function(attrs)";
    private static final String INIT_HANDLER_FUNCTION_SIGNATURE = "function(attrs)";
    private static final String DONE_HANDLER_FUNCTION_SIGNATURE = "function(attrs)";

    public AbstractDefaultAjaxBehavior() {
    }

    protected void onBind() {
        Component component = this.getComponent();
        component.setOutputMarkupId(true);
        if (this.getStatelessHint(component)) {
            component.getBehaviorId(this);
        }

    }

    private boolean getStatelessHint(Component component) {
        return false;
    }

    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);
        CoreLibrariesContributor.contributeAjax(component.getApplication(), response);
        RequestCycle requestCycle = component.getRequestCycle();
        Url baseUrl = requestCycle.getUrlRenderer().getBaseUrl();
        CharSequence ajaxBaseUrl = Strings.escapeMarkup(baseUrl.toString());
        response.render(JavaScriptHeaderItem.forScript("Wicket.Ajax.baseUrl=\"" + ajaxBaseUrl + "\";", "wicket-ajax-base-url"));
        this.renderExtraHeaderContributors(component, response);
    }

    private void renderExtraHeaderContributors(Component component, IHeaderResponse response) {
        AjaxRequestAttributes attributes = this.getAttributes();
        List<IAjaxCallListener> ajaxCallListeners = attributes.getAjaxCallListeners();
        Iterator var5 = ajaxCallListeners.iterator();

        while (var5.hasNext()) {
            IAjaxCallListener ajaxCallListener = (IAjaxCallListener) var5.next();
            if (ajaxCallListener instanceof IComponentAwareHeaderContributor contributor) {
                contributor.renderHead(component, response);
            }
        }

    }

    protected final AjaxRequestAttributes getAttributes() {
        AjaxRequestAttributes attributes = new AjaxRequestAttributes();
        WebApplication application = (WebApplication) this.getComponent().getApplication();
        AjaxRequestTargetListenerCollection ajaxRequestTargetListeners = application.getAjaxRequestTargetListeners();
        Iterator var4 = ajaxRequestTargetListeners.iterator();

        while (var4.hasNext()) {
            AjaxRequestTarget.IListener listener = (AjaxRequestTarget.IListener) var4.next();
            listener.updateAjaxAttributes(this, attributes);
        }

        this.updateAjaxAttributes(attributes);
        return attributes;
    }

    protected Form.MethodMismatchResponse onMethodMismatch() {
        return Form.MethodMismatchResponse.CONTINUE;
    }

    protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
    }

    protected final CharSequence renderAjaxAttributes(Component component) {
        AjaxRequestAttributes attributes = this.getAttributes();
        return this.renderAjaxAttributes(component, attributes);
    }

    @SneakyThrows
    protected final CharSequence renderAjaxAttributes(Component component, AjaxRequestAttributes attributes) {
        JSONObject attributesJson = new JSONObject();

        try {
            attributesJson.put(AjaxAttributeName.URL.jsonName(), this.getCallbackUrl());
            AjaxRequestAttributes.Method method = attributes.getMethod();
            if (AjaxRequestAttributes.Method.POST == method) {
                attributesJson.put(AjaxAttributeName.METHOD.jsonName(), method);
            }

            String formId;
            if (!(component instanceof Page)) {
                formId = component.getMarkupId();
                attributesJson.put(AjaxAttributeName.MARKUP_ID.jsonName(), formId);
            }

            formId = attributes.getFormId();
            if (!Strings.isEmpty(formId)) {
                attributesJson.put(AjaxAttributeName.FORM_ID.jsonName(), formId);
            }

            if (attributes.isMultipart()) {
                attributesJson.put(AjaxAttributeName.IS_MULTIPART.jsonName(), true);
            }

            String submittingComponentId = attributes.getSubmittingComponentName();
            if (!Strings.isEmpty(submittingComponentId)) {
                attributesJson.put(AjaxAttributeName.SUBMITTING_COMPONENT_NAME.jsonName(), submittingComponentId);
            }

            CharSequence childSelector = attributes.getChildSelector();
            if (!Strings.isEmpty(childSelector)) {
                attributesJson.put(AjaxAttributeName.CHILD_SELECTOR.jsonName(), childSelector);
            }

            if (attributes.isSerializeRecursively()) {
                attributesJson.put(AjaxAttributeName.SERIALIZE_RECURSIVELY.jsonName(), true);
            }

            String indicatorId = this.findIndicatorId();
            if (!Strings.isEmpty(indicatorId)) {
                attributesJson.put(AjaxAttributeName.INDICATOR_ID.jsonName(), indicatorId);
            }

            Iterator var9 = attributes.getAjaxCallListeners().iterator();

            CharSequence dynamicExtraParameter;
            while (var9.hasNext()) {
                IAjaxCallListener ajaxCallListener = (IAjaxCallListener) var9.next();
                if (ajaxCallListener != null) {
                    CharSequence initHandler = ajaxCallListener.getInitHandler(component);
                    this.appendListenerHandler(initHandler, attributesJson, AjaxAttributeName.INIT_HANDLER.jsonName(), "function(attrs)");
                    dynamicExtraParameter = ajaxCallListener.getBeforeHandler(component);
                    this.appendListenerHandler(dynamicExtraParameter, attributesJson, AjaxAttributeName.BEFORE_HANDLER.jsonName(), "function(attrs)");
                    CharSequence beforeSendHandler = ajaxCallListener.getBeforeSendHandler(component);
                    this.appendListenerHandler(beforeSendHandler, attributesJson, AjaxAttributeName.BEFORE_SEND_HANDLER.jsonName(), "function(attrs, jqXHR, settings)");
                    CharSequence afterHandler = ajaxCallListener.getAfterHandler(component);
                    this.appendListenerHandler(afterHandler, attributesJson, AjaxAttributeName.AFTER_HANDLER.jsonName(), "function(attrs)");
                    CharSequence successHandler = ajaxCallListener.getSuccessHandler(component);
                    this.appendListenerHandler(successHandler, attributesJson, AjaxAttributeName.SUCCESS_HANDLER.jsonName(), "function(attrs, jqXHR, data, textStatus)");
                    CharSequence failureHandler = ajaxCallListener.getFailureHandler(component);
                    this.appendListenerHandler(failureHandler, attributesJson, AjaxAttributeName.FAILURE_HANDLER.jsonName(), "function(attrs, jqXHR, errorMessage, textStatus)");
                    CharSequence completeHandler = ajaxCallListener.getCompleteHandler(component);
                    this.appendListenerHandler(completeHandler, attributesJson, AjaxAttributeName.COMPLETE_HANDLER.jsonName(), "function(attrs, jqXHR, textStatus)");
                    CharSequence precondition = ajaxCallListener.getPrecondition(component);
                    this.appendListenerHandler(precondition, attributesJson, AjaxAttributeName.PRECONDITION.jsonName(), "function(attrs)");
                    CharSequence doneHandler = ajaxCallListener.getDoneHandler(component);
                    this.appendListenerHandler(doneHandler, attributesJson, AjaxAttributeName.DONE_HANDLER.jsonName(), "function(attrs)");
                }
            }

            JSONArray extraParameters = JsonUtils.asArray(attributes.getExtraParameters());
            if (extraParameters.length() > 0) {
                attributesJson.put(AjaxAttributeName.EXTRA_PARAMETERS.jsonName(), extraParameters);
            }

            List<CharSequence> dynamicExtraParameters = attributes.getDynamicExtraParameters();
            if (dynamicExtraParameters != null) {
                Iterator var25 = dynamicExtraParameters.iterator();

                while (var25.hasNext()) {
                    dynamicExtraParameter = (CharSequence) var25.next();
                    JSONFunction function = this.getJsonFunction("function(attrs)", dynamicExtraParameter);
                    attributesJson.append(AjaxAttributeName.DYNAMIC_PARAMETER_FUNCTION.jsonName(), function);
                }
            }

            if (!attributes.isAsynchronous()) {
                attributesJson.put(AjaxAttributeName.IS_ASYNC.jsonName(), false);
            }

            String[] eventNames = attributes.getEventNames();
            String dataType;
            if (eventNames.length == 1) {
                attributesJson.put(AjaxAttributeName.EVENT_NAME.jsonName(), eventNames[0]);
            } else {
                String[] var27 = eventNames;
                int var30 = eventNames.length;

                for (int var32 = 0; var32 < var30; ++var32) {
                    dataType = var27[var32];
                    attributesJson.append(AjaxAttributeName.EVENT_NAME.jsonName(), dataType);
                }
            }

            AjaxChannel channel = attributes.getChannel();
            if (channel != null && !channel.equals(AjaxChannel.DEFAULT)) {
                attributesJson.put(AjaxAttributeName.CHANNEL.jsonName(), channel);
            }

            if (attributes.isPreventDefault()) {
                attributesJson.put(AjaxAttributeName.IS_PREVENT_DEFAULT.jsonName(), true);
            }

            if (AjaxRequestAttributes.EventPropagation.STOP.equals(attributes.getEventPropagation())) {
                attributesJson.put(AjaxAttributeName.EVENT_PROPAGATION.jsonName(), "stop");
            } else if (AjaxRequestAttributes.EventPropagation.STOP_IMMEDIATE.equals(attributes.getEventPropagation())) {
                attributesJson.put(AjaxAttributeName.EVENT_PROPAGATION.jsonName(), "stopImmediate");
            }

            Duration requestTimeout = attributes.getRequestTimeout();
            if (requestTimeout != null) {
                attributesJson.put(AjaxAttributeName.REQUEST_TIMEOUT.jsonName(), requestTimeout.toMillis());
            }

            boolean wicketAjaxResponse = attributes.isWicketAjaxResponse();
            if (!wicketAjaxResponse) {
                attributesJson.put(AjaxAttributeName.IS_WICKET_AJAX_RESPONSE.jsonName(), false);
            }

            dataType = attributes.getDataType();
            if (!"xml".equals(dataType)) {
                attributesJson.put(AjaxAttributeName.DATATYPE.jsonName(), dataType);
            }

            ThrottlingSettings throttlingSettings = attributes.getThrottlingSettings();
            if (throttlingSettings != null) {
                JSONObject throttlingSettingsJson = new JSONObject();
                String throttleId = throttlingSettings.getId();
                if (throttleId == null) {
                    throttleId = component.getMarkupId();
                }

                throttlingSettingsJson.put(AjaxAttributeName.THROTTLING_ID.jsonName(), throttleId);
                throttlingSettingsJson.put(AjaxAttributeName.THROTTLING_DELAY.jsonName(), throttlingSettings.getDelay().toMillis());
                if (throttlingSettings.getPostponeTimerOnUpdate()) {
                    throttlingSettingsJson.put(AjaxAttributeName.THROTTLING_POSTPONE_ON_UPDATE.jsonName(), true);
                }

                attributesJson.put(AjaxAttributeName.THROTTLING.jsonName(), throttlingSettingsJson);
            }

            this.postprocessConfiguration(attributesJson, component);
        } catch (JSONException var20) {
            JSONException e = var20;
            throw new WicketRuntimeException(e);
        }

        String attributesAsJson = attributesJson.toString();
        return attributesAsJson;
    }

    private void appendListenerHandler(CharSequence handler, JSONObject attributesJson, String propertyName, String signature) throws JSONException {
        if (!Strings.isEmpty(handler)) {
            JSONFunction function;
            if (handler instanceof JSONFunction) {
                function = (JSONFunction) handler;
            } else {
                function = this.getJsonFunction(signature, handler);
            }

            attributesJson.append(propertyName, function);
        }

    }

    private JSONFunction getJsonFunction(String signature, CharSequence body) {
        String func = signature + "{" + body + "}";
        return new JSONFunction(func);
    }

    protected void postprocessConfiguration(JSONObject attributesJson, Component component) throws JSONException {
    }

    public CharSequence getCallbackScript() {
        return this.getCallbackScript(this.getComponent());
    }

    protected CharSequence getCallbackScript(Component component) {
        CharSequence ajaxAttributes = this.renderAjaxAttributes(component);
        return "Wicket.Ajax.ajax(" + ajaxAttributes + ");"; //FIXME
    }

    public CharSequence getCallbackFunction(CallbackParameter... extraParameters) {
        StringBuilder sb = new StringBuilder();
        sb.append("function (");
        boolean first = true;
        CallbackParameter[] var4 = extraParameters;
        int var5 = extraParameters.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            CallbackParameter curExtraParameter = var4[var6];
            if (curExtraParameter.getFunctionParameterName() != null) {
                if (!first) {
                    sb.append(',');
                } else {
                    first = false;
                }

                sb.append(curExtraParameter.getFunctionParameterName());
            }
        }

        sb.append(") {\n");
        sb.append(this.getCallbackFunctionBody(extraParameters));
        sb.append("}\n");
        return sb;
    }

    @SneakyThrows
    public CharSequence getCallbackFunctionBody(CallbackParameter... extraParameters) {
        AjaxRequestAttributes attributes = this.getAttributes();
        attributes.setEventNames(new String[0]);
        CharSequence attrsJson = this.renderAjaxAttributes(this.getComponent(), attributes);
        StringBuilder sb = new StringBuilder();
        sb.append("var attrs = ");
        sb.append(attrsJson);
        sb.append(";\n");
        JSONArray jsonArray = new JSONArray();
        CallbackParameter[] var6 = extraParameters;
        int var7 = extraParameters.length;

        for (int var8 = 0; var8 < var7; ++var8) {
            CallbackParameter curExtraParameter = var6[var8];
            if (curExtraParameter.getAjaxParameterName() != null) {
                try {
                    JSONObject object = new JSONObject();
                    object.put("name", curExtraParameter.getAjaxParameterName());
                    object.put("value", new JSONFunction(curExtraParameter.getAjaxParameterCode()));
                    jsonArray.put(object);
                } catch (JSONException var11) {
                    JSONException e = var11;
                    throw new WicketRuntimeException(e);
                }
            }
        }

        sb.append("var params = ").append(jsonArray).append(";\n");
        sb.append("attrs.").append(AjaxAttributeName.EXTRA_PARAMETERS).append(" = params.concat(attrs.").append(AjaxAttributeName.EXTRA_PARAMETERS).append(" || []);\n");
        sb.append("Wicket.Ajax.ajax(attrs);\n");
        return sb;
    }

    protected String findIndicatorId() {
        if (this.getComponent() instanceof IAjaxIndicatorAware) {
            return ((IAjaxIndicatorAware) this.getComponent()).getAjaxIndicatorMarkupId();
        } else if (this instanceof IAjaxIndicatorAware) {
            return ((IAjaxIndicatorAware) this).getAjaxIndicatorMarkupId();
        } else {
            for (Component parent = this.getComponent().getParent(); parent != null; parent = ((Component) parent).getParent()) {
                if (parent instanceof IAjaxIndicatorAware) {
                    return ((IAjaxIndicatorAware) parent).getAjaxIndicatorMarkupId();
                }
            }

            return null;
        }
    }

    public final void onRequest() {
        Form.MethodMismatchResponse methodMismatch = this.onMethodMismatch();
        if (methodMismatch == Form.MethodMismatchResponse.ABORT) {
            AjaxRequestAttributes attrs = this.getAttributes();
            String desiredMethod = attrs.getMethod().toString();
            String actualMethod = ((HttpServletRequest) RequestCycle.get().getRequest().getContainerRequest()).getMethod();
            if (!desiredMethod.equalsIgnoreCase(actualMethod)) {
                LOG.debug("Ignoring the Ajax request because its method '{}' is different than the expected one '{}", actualMethod, desiredMethod);
                return;
            }
        }

        WebApplication app = (WebApplication) this.getComponent().getApplication();
        AjaxRequestTarget target = app.newAjaxRequestTarget(this.getComponent().getPage());
        RequestCycle requestCycle = RequestCycle.get();
        requestCycle.scheduleRequestHandlerAfterCurrent(target);
        this.respond(target);
    }

    protected abstract void respond(AjaxRequestTarget var1);
}

