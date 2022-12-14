<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="채팅관리" name="title"/>
</jsp:include>

<table class="table text-center" id="tbl-chat-list">
  <thead>
    <tr>
      <th scope="col">회원아이디</th>
      <th scope="col">메세지</th>
    </tr>
  </thead>
  <tbody>
  	<c:forEach items="${list}" var="chat">
  		<tr data-chatroom-id="${chat.chatroomId}">
  			<td>${chat.memberId}</td>
  			<td class="msg">${chat.msg}</td>
  		</tr>
  	</c:forEach>
  </tbody>
</table>
<script>
const trClickHandler = (e) => {
	const parent = e.target.parentElement;
	const chatroomId = parent.dataset.chatroomId;
	console.log(chatroomId);
	
	const url = `${pageContext.request.contextPath}/admin/chat.do?chatroomId=\${chatroomId}`;
	const name = chatroomId; // window의 이름으로 사용.
	const spec = "width=500px, height=500px";
	open(url, name, spec);
};

document.querySelectorAll("tr[data-chatroom-id]").forEach((tr) => {
	tr.addEventListener('click', trClickHandler);
});

setTimeout(() => {
	stompClient.subscribe("/app/admin/chatList", (message) => {
		console.log("/app/admin/chatList : ", message);
		
		const {chatroomId, member, msg} = JSON.parse(message.body);
		
		let tr = document.querySelector(`tr[data-chatroom-id="\${chatroomId}"]`);
		
		if(tr){
			tr.querySelector(".msg").innerHTML = msg;
		}
		else {
			//신규채팅방인 경우
			tr = document.createElement("tr");
			tr.dataset.chatroomId = chatroomId;
			
			const td1 = document.createElement("td");
			td1.innerHTML = memberId;

			const td2 = document.createElement("td");
			td2.classList.add('msg');
			td2.innerHTML = msg;
			
			tr.append(td1, td2);
			tr.addEventListener('click', trClickHandler);
		}
		
		// 끌어올리기
		const tbody = document.querySelector("#tbl-chat-list tbody");
		tbody.insertAdjacentElement('afterbegin', tr);
	});
}, 500);

</script>
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>