<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<fmt:requestEncoding value="utf-8"/>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param name="title" value="데이터 활용" />
</jsp:include>

	<div id="data-container" class="w-75 mx-auto">
		<h2>course - xml</h2>
		<button class="btn btn-block btn-outline-primary" id="btn-course-xml">확인</button>
		<div id="course-xml-wrapper"></div>
		<script>
		document.querySelector("#btn-course-xml").addEventListener('click', (e) => {
			
			$.ajax({
				url : "${pageContext.request.contextPath}/data/xml/course.do",
				method : "GET",
				success(data){
					console.log(data);
					const {id, title, price, created, students} = data;
					
					const wrapper = document.querySelector("#course-xml-wrapper");
					wrapper.innerHTML = `
					<table class="table" id="tbl-xml-course">
						<tbody>
							<tr>
								<th>번호</th>
								<td>\${id}</td>
							</tr>
							<tr>
								<th>수업명</th>
								<td>\${title}</td>
							</tr>
							<tr>
								<th>수강료</th>
								<td>￦\${price.toLocaleString()}</td>
							</tr>
							<tr>
								<th>개설일</th>
								<td>\${created.join('-')}</td>
							</tr>
						</tbody>
					</table>
					<br />
					
					<table class="table" id="tbl-xml-student">
						<thead>
							<th>번호</th>
							<th>이름</th>
							<th>전화번호</th>
						</thead>
						<tbody>
					
					`;
					
					//students 배열 처리
					students.forEach(({id, name, tel}) => {
						wrapper.querySelector("#tbl-xml-student tbody").innerHTML += `
							<tr>
								<td>\${id}</td>
								<td>\${name}</td>
								<td>\${tel}</td>
							</tr>
						`;
					});
					
					wrapper.innerHTML += `
						</tbody>
					</table>
					`;
						
				},
				error : console.log
					
			});
		});
		</script>
		<h2>course - json</h2>
		<button class="btn btn-block btn-outline-warning" id="btn-json-course">확인</button>
	
		<script>
		document.querySelector("#btn-json-course").addEventListener('click', (e) => {
			$.ajax({
				url : '${pageContext.request.contextPath}/data/json/course.do',
				success(data){
					console.log(data);
					
				},
				error : console.log
			});
		});
		</script>
		<br />
		<h2>코로나 확진자 현황</h2>
		<input type="date" name="date" id="date" class="form-control mb-1"/>
		<button class="btn btn-block btn-outline-danger" id="btn-covid19">확인</button>
		<div id="covid19-wrapper"></div>	
		<script>
		document.querySelector("#btn-covid19").addEventListener('click', (e) => {
			$.ajax({
				//url : 'http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson?serviceKey=xG7FbVRiB%2FVRULA5s7YlsBsZOsoP1a%2BS%2BdHFBLAaDX%2FRj3QgOTytvSMvI7EbGXFYK0yIDMpKM97N9%2F9awD9dYg%3D%3D&pageNo=1&numOfRows=10&startCreateDt=20220824&endCreateDt=20220824',
				url : '${pageContext.request.contextPath}/data/covid19.do',
				data : {
					date : document.querySelector("#date").value  //2022-08-24
				},
				success(data){
					console.log(data);
					const wrapper = document.querySelector("#covid19-wrapper");
					// 제주(Jeju)   날짜   확진자수
					
					
					wrapper.innerHTML = `
					<table class="table">
						<thead>
							<tr>
								<th>지역</th>
								<th>확진자수</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
					`;
					
					wrapper.querySelector("tbody").innerHTML = data.reduce((html, covid) => {
						const {gubun, gubunEn, defCnt} = covid;
						
						return `\${html}
							<tr>
								<td>\${gubun}(\${gubunEn})</td>
								<td>\${defCnt}</td>
							</tr>
						`;
					}, "");
				},
				error : console.log
			});
		});
		</script>
		
	</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
		