package {{package}}.common;

import javax.servlet.jsp.JspException;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class JspTagOnlineProject extends JspVarTagSupport {

  @Override
  public int doStartTag() throws JspException {
    return isOnlineProject() ? EVAL_BODY_INCLUDE : SKIP_BODY;
  }
}
