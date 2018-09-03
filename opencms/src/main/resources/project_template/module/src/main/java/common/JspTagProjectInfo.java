#set( ${symbol_pound} = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.common;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Writes out the project info to identify the version
 * @author Lars Brandt, brandt@silpion.de
 */
public class JspTagProjectInfo extends TagSupport {

    private static final String LF = "\n";

    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.write(createContent());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }
        return EVAL_PAGE;
    }

    /**
     * adds a span with title and property to stringbuffer
     * @param pSb the target
     * @param pTitle title to identify on webpage
     * @param value type of value
     */
    private void addValue(StringBuilder pSb, String pTitle, BuildVersion.VALUE value) {
        BuildVersion v = BuildVersion.getInstance();
        pSb.append(pTitle).append(": ").append(v.get(value));
    }

    /**
     * generates the content for projectinfo
     * @return the html content. div-tag that contains span-tags
     */
    public String createContent() {
        StringBuilder sb = new StringBuilder();

        sb.append("${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound} COMMON ${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}").append(LF);

        addValue(sb, "BRANCH", BuildVersion.VALUE.BRANCH);
        sb.append(LF);

        addValue(sb, "TAGS", BuildVersion.VALUE.TAGS);
        sb.append(LF);

        sb.append("${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound} BUILD ${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}").append(LF);

        addValue(sb, "BUILD_TIME", BuildVersion.VALUE.BUILD_TIME);
        sb.append(LF);

        addValue(sb, "BUILD_USER_EMAIL", BuildVersion.VALUE.BUILD_USER_EMAIL);
        sb.append(LF);

        sb.append("${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound} COMMIT ${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}").append(LF);

        addValue(sb, "COMMIT_ID_DESCRIBE_SHORT", BuildVersion.VALUE.COMMIT_ID_DESCRIBE_SHORT);
        sb.append(LF);

        addValue(sb, "COMMIT_ID", BuildVersion.VALUE.COMMIT_ID);
        sb.append(LF);

        addValue(sb, "COMMIT_TIME", BuildVersion.VALUE.COMMIT_TIME);
        sb.append(LF);

        sb.append("SCHEME").append(": ").append(pageContext.getRequest().getScheme());
        sb.append(LF);

        sb.append("SERVERNAME").append(": ").append(pageContext.getRequest().getServerName());
        sb.append(LF);

        sb.append("PORT").append(": ").append(pageContext.getRequest().getServerPort());
        sb.append(LF);


        return sb.toString();
    }
}
