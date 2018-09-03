package ${package}.common;

import org.apache.commons.lang3.StringUtils;
import org.opencms.file.CmsObject;
import org.opencms.jsp.util.CmsJspElFunctions;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

/**
 * @author Lars Brandt
 */
public class JspVarTagSupport extends BodyTagSupport implements TryCatchFinally {

    private String var;
    private CmsObject cmsObject;

    public CmsObject getCmsObject() {
        if (cmsObject == null) {
            cmsObject = CmsJspElFunctions.getCmsObject(pageContext);
        }
        return cmsObject;
    }

    @Override
    public final void release() {
        super.release();
        var = null;
        cmsObject = null;
        doRelease();
    }

    protected void doRelease() {
    }

    protected void removeValue() {
        if (StringUtils.isNotBlank(var)) {
            pageContext.removeAttribute(var);
        }
    }

    protected void setValue(Object value) throws JspException {
        if (StringUtils.isNotBlank(var)) {
            pageContext.setAttribute(var, value);
        } else {

            // clear body if needed
            if (getBodyContent() != null) {
                try {
                    getBodyContent().clear();
                } catch (IOException e) {
                    throw new JspException("could not clean body", e);
                }
            }

            // write result to content
            try {
                pageContext.getOut().write(value == null ? "null" : value.toString());
            } catch (IOException e) {
                throw new JspException("could not write to outstream", e);
            }
        }
    }

    protected Locale getLocale() {
        return getCmsObject().getRequestContext().getLocale();
    }

    protected boolean isOnlineProject() {
        return getCmsObject().getRequestContext().getCurrentProject().isOnlineProject();
    }

    @Override
    public void doFinally() {
        release();
    }

    @Override
    public void doCatch(Throwable throwable) throws Throwable {
        throw throwable;
    }

    //<editor-fold desc="Getter/Setter">
    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }
    //</editor-fold>
}
