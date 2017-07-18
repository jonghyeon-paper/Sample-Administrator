<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include/taglib.jsp" %>

<script type="text/javascript" src="${contextPath }/resources/js/common/PageUtil.js"></script>

<div class="container">
<div style="margin: 20px 20px 20px 20px;">
    <h5>IT요청>IT요청현황상세</h5>
			<button type="button" class="btn btn-default">완료요청</button>
			
			<h4>■  요청 정보</h4>
			
			 <table class="table table-bordered">
			    <tbody>
			      <tr>
			        <td style="background-color:rgb(240, 240, 240)" width="10%">신청자</td>
			        <td>홍길동</td>
			        <td style="background-color:rgb(240, 240, 240)" width="10%">팀</td>
			        <td>Infra기획팀</td>
			      </tr>
			      <tr>
			        <td style="background-color:rgb(240, 240, 240)" width="10%">신청일</td>
			        <td>2017-07-11</td>
			        <td style="background-color:rgb(240, 240, 240)" width="10%">연락처</td>
			        <td>010-0000-0000</td>
			      </tr>
			    </tbody>
			  </table>
			
			<h4>■  승인 정보</h4>
			
				<table class="table table-bordered">
			    <tbody>
			          <tr>
			        <th>승인</th>
			        <th>이름</th>
			        <th>소속</th>
			        <th>처리일</th>
			        <th>e-mail</th>
			        <th>연락처</th>
			      </tr>
			      <tr>
			        <td style="background-color:rgb(240, 240, 240)" width="10%">승인자</td>
			        <td>홍길동</td>
			        <td>Infra기획팀</td>
			        <td>2017-07-12 12:33</td>
			        <td>email@example.com</td>
			        <td>010-0000-0000</td>
			      </tr>
			      <tr>
			       <td style="background-color:rgb(240, 240, 240)" width="10%">접수자</td>
			        <td>홍길동</td>
			        <td>Infra기획팀</td>
			        <td>2017-07-12 12:33</td>
			        <td>email@example.com</td>
			        <td>010-0000-0000</td>
			      </tr>
			      <tr>
			       <td style="background-color:rgb(240, 240, 240)" width="10%">작업자</td>
			        <td>홍길동</td>
			        <td>Infra기획팀</td>
			        <td>2017-07-12 12:33</td>
			        <td>email@example.com</td>
			        <td>010-0000-0000</td>
			      </tr>			      
			    </tbody>
			  </table>
			  
			  <table class="table table-bordered">
			    <tbody>
			      <tr>
			        <td style="background-color:rgb(240, 240, 240)" width="10%">시스템</td>
			        <td>인트라넷</td>
			        <td style="background-color:rgb(240, 240, 240)" width="10%">요청유형</td>
			        <td>인트라넷 권한설정</td>
			        <td style="background-color:rgb(240, 240, 240)" width="10%">신청구분</td>
			        <td>기타 서비스</td>
			      </tr>
			      <tr>
			        <td style="background-color:rgb(240, 240, 240)" width="10%">완료요청일</td>
			        <td>2017-07-13</td>
			        <td style="background-color:rgb(240, 240, 240)" width="10%">진행현황</td>
			        <td>작업중</td>
			        <td style="background-color:rgb(240, 240, 240)" width="10%">완료일</td>
			        <td></td>
			      </tr>
			    </tbody>
			  </table>

			<h4>■ 신청 내용</h4>
			
			 <table class="table table-bordered">
			    <tbody>
			      <tr>
			        <td style="background-color:rgb(240, 240, 240)" width="10%">제목</td>
			        <td>소모품 운영 담당자 Pnet접근 권한 요청</td>
			      </tr>
			      <tr>
			        <td style="background-color:rgb(240, 240, 240)" width="10%">내용</td>
			        <td>다음과 같이 11번가 과적립으로 인해 변경되었던 회원 상태의 원복처리를 요청합니다.<br><br>

					- 초기 변경일: 7월 3일 <br>
					- 적용 대상: 444명 <br>
					- 해당 모든 고객에 대한 회원상태 원복을 요청함. 
					</td>
			      </tr>
			    </tbody>
			  </table>

			<h4>■ 작업 내용</h4>
			
			 <table class="table table-bordered">
			    <tbody>
			      <tr>
			        <td style="background-color:rgb(240, 240, 240);" width="10%">작업본수</td>
			        <td>1</td>
			        <td style="background-color:rgb(240, 240, 240)" width="10%">소요시간</td>
			        <td>1시간</td>			        
			      </tr>
			      <tr>
			        <td style="background-color:rgb(240, 240, 240)">첨부파일</td>
			        <td colspan="3">intro.gif<br>
					 작업관련문서.pptx 
					</td>
			      </tr>
			    </tbody>
			  </table>
</div>
</div>