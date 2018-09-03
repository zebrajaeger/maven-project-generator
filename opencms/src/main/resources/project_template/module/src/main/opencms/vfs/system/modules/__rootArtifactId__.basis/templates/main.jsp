#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@page buffer="none" session="false" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>

<%@ taglib prefix="cms" uri="http://www.opencms.org/taglib/cms" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="com" uri="http://${artifactId}/common" %>

<%--@elvariable id="cms" type="org.opencms.jsp.util.CmsJspStandardContextBean"--%>

<c:set var="settings" value="${symbol_dollar}{cms.element.settings}"/>
<fmt:setLocale value="${symbol_dollar}{cms.locale}"/>

<%-- TITLE --%>
<c:set var="title" value="${symbol_dollar}{cms.title}"/>

<!DOCTYPE html>
<html class="no-js">
<head>
    <cms:enable-ade/>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <title>${symbol_dollar}{title}</title>
    <meta name="title" content="${symbol_dollar}{title}">

    <com:offlineProject>
        <com:stylesheet>/system/modules/${artifactId}/resources/css/app.css</com:stylesheet>
        <com:stylesheet>/system/modules/${artifactId}/resources/css/vendor.css</com:stylesheet>
    </com:offlineProject>

    <com:onlineProject>
        <com:stylesheet>/system/modules/${artifactId}/resources/css/app.min.css</com:stylesheet>
        <com:stylesheet>/system/modules/${artifactId}/resources/css/vendor.min.css</com:stylesheet>
    </com:onlineProject>

    <com:offlineProject>
        <com:script async="true">/system/modules/${artifactId}/resources/js/vendor.js</com:script>
        <com:script async="true">/system/modules/${artifactId}/resources/js/app.js</com:script>
    </com:offlineProject>

    <com:onlineProject>
        <com:script async="true">/system/modules/${artifactId}/resources/js/vendor.min.js</com:script>
        <com:script async="true">/system/modules/${artifactId}/resources/js/app.min.js</com:script>
    </com:onlineProject>
    <script>
        var vendorReady = false;
        var appReady = false;
        function checkReady() {
            if (vendorReady && appReady && initApp) {
                initApp();
            }
        }
        function onVendorReady() {
            vendorReady = true;
            checkReady();
        }
        function onAppReady() {
            appReady = true;
            checkReady();
        }
    </script>
</head>
<body class="<com:offlineProject>is--offline</com:offlineProject><com:onlineProject>is--online</com:onlineProject>">
<input type="hidden" name="contextPath" id="contextPath" value="${symbol_dollar}{pageContext.request.contextPath}${symbol_dollar}{pageContext.request.servletPath}"/>
<input type="hidden" name="basePath" id="basePath" value="<cms:link>/</cms:link>"/>

<cms:container name="content">
    <div class="ade-placeholder">
        Insert new Element here
    </div>
</cms:container>
    <!--
    Host: ${symbol_dollar}{com:hostId(true)}
    <com:buildInfo/>
    -->
</body>
</html>
