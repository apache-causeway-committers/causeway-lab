package org.apache.causeway.wicketstubs.api;

public enum AjaxAttributeName {
    THROTTLING("tr"),
    THROTTLING_POSTPONE_ON_UPDATE("p"),
    THROTTLING_DELAY("d"),
    THROTTLING_ID("id"),
    DATATYPE("dt"),
    IS_WICKET_AJAX_RESPONSE("wr"),
    REQUEST_TIMEOUT("rt"),
    IS_PREVENT_DEFAULT("pd"),
    EVENT_PROPAGATION("sp"),
    CHANNEL("ch"),
    EVENT_NAME("e"),
    IS_ASYNC("async"),
    DYNAMIC_PARAMETER_FUNCTION("dep"),
    EXTRA_PARAMETERS("ep"),
    PRECONDITION("pre"),
    COMPLETE_HANDLER("coh"),
    FAILURE_HANDLER("fh"),
    SUCCESS_HANDLER("sh"),
    AFTER_HANDLER("ah"),
    BEFORE_SEND_HANDLER("bsh"),
    BEFORE_HANDLER("bh"),
    INIT_HANDLER("ih"),
    DONE_HANDLER("dh"),
    INDICATOR_ID("i"),
    SUBMITTING_COMPONENT_NAME("sc"),
    IS_MULTIPART("mp"),
    FORM_ID("f"),
    MARKUP_ID("c"),
    METHOD("m"),
    URL("u"),
    CHILD_SELECTOR("sel"),
    SERIALIZE_RECURSIVELY("sr");

    private final String jsonName;

    private AjaxAttributeName(String jsonName) {
        this.jsonName = (String) Args.notNull(jsonName, "jsonName");
    }

    public String jsonName() {
        return this.jsonName;
    }

    public String toString() {
        return this.jsonName;
    }
}
