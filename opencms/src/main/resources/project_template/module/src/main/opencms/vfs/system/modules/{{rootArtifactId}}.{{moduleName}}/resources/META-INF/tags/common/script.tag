
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ tag body-content="scriptless" %>

<%@ attribute name="async" required="false" type="java.lang.Boolean" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="com" uri="http://${artifactId}/common" %>

<c:set var="TBetQjwVsOqgFaMOqSc"><com:linkExternal absolute="false"><jsp:doBody/></com:linkExternal></c:set>
<script type="text/javascript" src="${TBetQjwVsOqgFaMOqSc}" ${async ? 'async' : ''} ></script>
<c:remove var="TBetQjwVsOqgFaMOqSc"/>
