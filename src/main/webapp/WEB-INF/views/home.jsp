<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>VegetLife</title>
<link rel="stylesheet" type="text/css" href="${rootPath}/resources/css/main.css?ver=2019080701">
<link rel="stylesheet" type="text/css" href="${rootPath}/resources/css/list.css?ver=2019080703">
<link rel="stylesheet" type="text/css" href="${rootPath}/resources/css/button.css?ver=2019072501">
<link rel="stylesheet" type="text/css" href="${rootPath}/resources/css/input.css?ver=20190809">
<link rel="stylesheet" type="text/css" href="${rootPath}/resources/css/view.css?ver=2019072603">
<link rel="stylesheet" type="text/css" href="${rootPath}/resources/css/login.css?ver=20190719">
<link rel="stylesheet" type="text/css" href="${rootPath}/resources/css/album.css?ver=2019080706">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include-header.jspf" %>
	<p></p>
	<section>
		<c:choose>
			<c:when test="${BODY == 'BBS_LIST2'}"> <!-- 리뷰게시판 리스트 -->
				<%@ include file="/WEB-INF/views/body/bbs/list.jspf" %>
			</c:when>
			<c:when test="${BODY == 'BBS_ALBUM'}"> <!-- 앨범형게시판 리스트 -->
				<%@ include file="/WEB-INF/views/body/bbs/album.jspf" %>
			</c:when>
			<c:when test="${BODY == 'BBS_WRITE'}"> <!-- 앨범형게시판 글쓰기 -->
				<%@ include file="/WEB-INF/views/body/bbs/write.jspf" %>
			</c:when>
			<c:when test="${BODY == 'BBS_WRITE2'}"> <!-- 리뷰게시판 글쓰기 -->
				<%@ include file="/WEB-INF/views/body/bbs/write2.jspf" %>
			</c:when>
			<c:when test="${BODY == 'BBS_VIEW'}"> <!-- 앨범형게시판 뷰 -->
				<%@ include file="/WEB-INF/views/body/bbs/view.jspf" %>
			</c:when>
			<c:when test="${BODY == 'BBS_VIEW2'}"> <!-- 리뷰게시판 뷰 -->
				<%@ include file="/WEB-INF/views/body/bbs/view2.jspf" %>
			</c:when>
		
			
			<c:otherwise>
				<%@ include file="/WEB-INF/views/body/bbs/album2.jspf" %>
				<!-- 메인화면 앨범형게시판 include -->
				<%@ include file="/WEB-INF/views/body/bbs/list2.jspf" %>
				<!-- 메인화면 리뷰게시판 include -->
			</c:otherwise>	
		</c:choose>
	</section>
	
	
</body>
<%@ include file="/WEB-INF/views/include/footer.jspf" %>
</html>