package org.apache.isis.lab.experiments.wktbs.widgets.markup;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.parser.XmlTag.TagType;
import org.apache.wicket.model.IModel;

public class PlainHtml extends WebComponent {

    private static final long serialVersionUID = 1L;

    public PlainHtml(final String id, final IModel<?> model){
        super(id, model);
    }

    @Override
    protected void onComponentTag(final ComponentTag tag)   {
        super.onComponentTag(tag);
        tag.setType(TagType.OPEN);
    }

    @Override
    public void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag){
        replaceComponentTagBody(markupStream, openTag, extractHtmlOrElse(getDefaultModelObject(), ""));
    }

    protected CharSequence extractHtmlOrElse(final Object modelObject, final String fallback) {
        if(modelObject==null) {
            return fallback;
        }
        return modelObject.toString();
    }

}
