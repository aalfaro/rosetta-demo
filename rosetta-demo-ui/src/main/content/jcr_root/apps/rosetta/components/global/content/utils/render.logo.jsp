<%@include file="/apps/rosetta/components/global/global.jsp" %>
<%
    Image img = null;
    Resource res = currentStyle.getDefiningResource((String)request.getAttribute("logoAltParam"));
    String location = PageUtils.getResolvedPath((String) request.getAttribute("logoURL"), "", slingRequest, pageContext);
    // Verify if the resource was loaded
    if (res != null) {
        img = new Image(res, (String)request.getAttribute("logoName"));
        if (img.hasContent()) {
            img.setItemName(Image.NN_FILE, "image");
            img.setItemName(Image.PN_REFERENCE, "imageReference");
            img.setSelector(".img");
            img.setDoctype(Doctype.fromRequest(request));
            img.setAlt((String)request.getAttribute("logoAlt"));
            img.setTitle((String)request.getAttribute("logoTitle"));
        }
    }
%>
<c:if test="${ ! empty logoURL }"><a href="<%=location %>" title="${ logoTitle }"></c:if><c:choose>
  <c:when test="<%= (img != null) %>"><% img.draw(out); %></c:when>
  <c:otherwise>${ logoTitle }</c:otherwise>
</c:choose><c:if test="${ ! empty logoURL }"></a></c:if>
