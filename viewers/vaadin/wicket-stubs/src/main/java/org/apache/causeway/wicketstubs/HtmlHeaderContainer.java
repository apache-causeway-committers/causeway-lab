package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.ComponentTag;
import org.apache.causeway.wicketstubs.api.IHeaderResponse;
import org.apache.causeway.wicketstubs.api.WicketRuntimeException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlHeaderContainer
        extends TransparentWebMarkupContainer {
    private static final long serialVersionUID = 1L;
    private transient Map<String, List<String>> renderedComponentsPerScope;
    private transient IHeaderResponse headerResponse = null;

    public HtmlHeaderContainer(String id) {
        super(id);
        this.setRenderBodyOnly(true);
        this.setAuto(true);
    }

    public final void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
        Response webResponse = this.getResponse();

        try {
            StringResponse response = new StringResponse();
            this.getRequestCycle().setResponse(response);
            IHeaderResponse headerResponse = this.getHeaderResponse();

            try {
                if (!response.equals(headerResponse.getResponse())) {
                    this.getRequestCycle().setResponse(headerResponse.getResponse());
                }

                AbstractHeaderRenderStrategy.get().renderHeader(this, new HeaderStreamState(markupStream, openTag), this.getPage());
            } catch (Throwable var13) {
                if (headerResponse != null) {
                    try {
                        headerResponse.close();
                    } catch (Throwable var12) {
                        var13.addSuppressed(var12);
                    }
                }

                throw var13;
            }

            if (headerResponse != null) {
                headerResponse.close();
            }

            CharSequence output = getCleanResponse(response);
            if (output.length() > 0) {
                if (this.renderOpenAndCloseTags()) {
                    webResponse.write("<head>");
                }

                webResponse.write(output);
                if (this.renderOpenAndCloseTags()) {
                    webResponse.write("</head>");
                }
            }
        } finally {
            this.getRequestCycle().setResponse(webResponse);
        }

    }

    public void renderHeaderTagBody(HeaderStreamState headerStreamState) {
        if (headerStreamState != null) {
            Response oldResponse = this.getRequestCycle().getResponse();

            try {
                StringResponse bodyResponse = new StringResponse();
                this.getRequestCycle().setResponse(bodyResponse);
                super.onComponentTagBody(headerStreamState.getMarkupStream(), headerStreamState.getOpenTag());
                CharSequence bodyOutput = getCleanResponse(bodyResponse);
                if (bodyOutput.length() > 0) {
                    this.getHeaderResponse().render(new PageHeaderItem(bodyOutput));
                }
            } finally {
                this.getRequestCycle().setResponse(oldResponse);
            }

        }
    }

    private static CharSequence getCleanResponse(StringResponse response) {
        CharSequence output = response.getBuffer();
        if (output.length() > 0) {
            int i;
            char ch;
            if (output.charAt(0) == '\r') {
                for (i = 2; i < output.length(); i += 2) {
                    ch = output.charAt(i);
                    if (ch != '\r') {
                        output = output.subSequence(i - 2, output.length());
                        break;
                    }
                }
            } else if (output.charAt(0) == '\n') {
                for (i = 1; i < output.length(); ++i) {
                    ch = output.charAt(i);
                    if (ch != '\n') {
                        output = output.subSequence(i - 1, output.length());
                        break;
                    }
                }
            }
        }

        return output;
    }

    protected boolean renderOpenAndCloseTags() {
        return true;
    }

    public boolean okToRenderComponent(String scope, String id) {
        if (this.renderedComponentsPerScope == null) {
            this.renderedComponentsPerScope = new HashMap();
        }

        List<String> componentScope = (List) this.renderedComponentsPerScope.get(scope);
        if (componentScope == null) {
            componentScope = new ArrayList();
            this.renderedComponentsPerScope.put(scope, componentScope);
        }

        if (((List) componentScope).contains(id)) {
            return false;
        } else {
            ((List) componentScope).add(id);
            return true;
        }
    }

    protected void onAfterRender() {
        super.onAfterRender();
        this.renderedComponentsPerScope = null;
        this.headerResponse = null;
    }

    protected IHeaderResponse newHeaderResponse() {
        return new HeaderResponse() {
            protected Response getRealResponse() {
                return HtmlHeaderContainer.this.getResponse();
            }
        };
    }

    public IHeaderResponse getHeaderResponse() {
        if (this.headerResponse == null) {
            this.headerResponse = this.getApplication().decorateHeaderResponse(this.newHeaderResponse());
        }

        return this.headerResponse;
    }

    public IMarkupFragment getMarkup() {
        if (this.getParent() == null) {
            throw new WicketRuntimeException("Bug: The Wicket internal instance of HtmlHeaderContainer is not connected to a parent");
        } else {
            IMarkupFragment markup = this.getPage().getMarkup();
            if (markup == null) {
                throw new MarkupException("Unable to get page markup: " + this.getPage().toString());
            } else {
                MarkupStream stream = new MarkupStream(markup);

                IMarkupFragment headerMarkup;
                for (headerMarkup = null; stream.skipUntil(ComponentTag.class); stream.next()) {
                    ComponentTag tag = stream.getTag();
                    if (tag.isOpen() || tag.isOpenClose()) {
                        if (tag instanceof WicketTag) {
                            WicketTag wtag = (WicketTag) tag;
                            if (wtag.isHeadTag() || wtag.isHeaderItemsTag()) {
                                headerMarkup = stream.getMarkupFragment();
                                break;
                            }
                        } else if (tag.getName().equalsIgnoreCase("head") && tag.isAutoComponentTag()) {
                            headerMarkup = stream.getMarkupFragment();
                            break;
                        }
                    }
                }

                this.setMarkup(headerMarkup);
                return headerMarkup;
            }
        }
    }

    public static class HeaderStreamState {
        private final MarkupStream markupStream;
        private final ComponentTag openTag;

        private HeaderStreamState(MarkupStream markupStream, ComponentTag openTag) {
            this.markupStream = markupStream;
            this.openTag = openTag;
        }

        public MarkupStream getMarkupStream() {
            return this.markupStream;
        }

        public ComponentTag getOpenTag() {
            return this.openTag;
        }
    }
}
