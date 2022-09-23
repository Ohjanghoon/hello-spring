<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<table>
		<c:forEach items="${chatMembers}" var="chatMember">
			<tr>
				<td>
					<button type="button"
					onclick="location.href='${pageContext.request.contextPath}/chat/chat.do?chatTargetId=${chatMember.memberId}';">${chatMember.memberId}</button>
				</td>
			</tr>
		</c:forEach>
	</table>
		
</body>
</html>