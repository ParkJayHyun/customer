# 회원가입 및 회원정보 조회 Application

REST API를 통해 회원가입 및 회원정보를 조회할 수 있습니다.

## 프로젝트 구성 안내

기술 스택

- java 11
- gradle
- Spring boot 2.7.3
- Spring Data JPA
- Thymeleaf
- H2 DB

### 프로젝트 환경 설정 정보 및 구동
- DB는 별도의 설치 없이 Spring boot 제공하는 Embedded DB를 사용하여 Application 빌드 후 실행시키면 됩니다.

### 프로젝트 구동 후 사용법
- 서버 올린 후 브라우저에 localhost:8080 입력하면 "index.html" rending 되어 회원가입 및 회원정보 조회 기능을 사용할 수 있습니다.

## 프로그램 기능 목록 및 설명

- 회원가입
  :아이디,비밀번호,이메일,휴대번호 입력정보를 받아 회원가입을 하여 회원가입 한 회원의 고유한 번호를 전달 합니다.
- 회원정보 조회
  :회원의 고유한 번호를 가지고 조회하여 아이디, 이메일, 휴대폰 번호를 전달 받습니다.

## REST API 명세서
- API list
<table>
  <tr>
    <th>Section</th>
    <th>API명</th>
    <th>HTTP Method</th>
    <th>URL</th>
    <th>Input Parameter(QueryString)</th>
    <th>Input Parameter(Body)</th>
    <th>Response</th>
  </tr>
  <tr>
    <td rowspan="2">Customer</td>
    <td>회원가입</td>
    <td>POST</td>
    <td>/customers/signUp</td>
    <td>X</td>
    <td>
        {
          "password": "customer1",
          "phoneNumber": "010-1111-1111",
          "userId": "customer",
          "email": "customer@naver.com"
        }
    </td>
    <td>
        {
          "result": "SUCCESS",
          "code": 201,
          "id": 1
        }
    </td>
  </tr>
  <tr>
    <td>회원정보 조회</td>
    <td>GET</td>
    <td>/customers/{id}</td>
    <td>X</td>
    <td>X</td>
    <td>
        {
          "code": 200,
          "result": "SUCCESS",
          "customer": {
            "userId": "customer",
            "email": "customer@naver.com",
            "phoneNumber": "010-1111-1111"
          }
        }
  </td>
  </tr>
</table>

- 회원가입
<table>
  <tr>
    <th>Depth1</th>
    <th>설명</th>
    <th>Type</th>
    <th>비고</th>
  </tr>
  <tr>
    <td>code</td>
    <td>HttpStatus</td>
    <td>int</td>
    <td>201,200,400,500...</td>
  </tr>
  <tr>
    <td>result</td>
    <td>처리 결과</td>
    <td>String</td>
    <td>SUCCESS , FAIL</td>
  </tr>
  <tr>
    <td>id</td>
    <td>회원id</td>
    <td>Integer</td>
    <td></td>
  </tr>
  </tr>
</table>

- 회원정보 조회
<table>
  <tr>
    <th>Depth1</th>
    <th>Depth2</th>
    <th>Depth3</th>
    <th>설명</th>
    <th>Type</th>
    <th>비고</th>
  </tr>
  <tr>
    <td>code</td>
    <td></td>
    <td></td>
    <td>HttpStatus</td>
    <td>int</td>
    <td>201,200,400,500...</td>
  </tr>
  <tr>
    <td>result</td>
    <td></td>
    <td></td>    
    <td>처리 결과</td>
    <td>String</td>
    <td>SUCCESS , FAIL</td>
  </tr>
  <tr>
    <td rowspan="3">customer</td>
    <td>userId</td>
    <td></td>
    <td>회원 정보</td>
    <td>String</td>
    <td></td>
  </tr>
  <tr>
    <td>email</td>
    <td></td>
    <td>이메일</td>
    <td>String</td>
    <td></td>
  </tr>
  <tr>
    <td>phoneNumber</td>
    <td></td>
    <td>휴대폰번호</td>
    <td>String</td>
    <td></td>
  </tr>
  </tr>
  <tr>
    <td rowspan="4">errors</td>
    <td>code</td>
    <td></td>
    <td>에러 코드</td>
    <td>String</td>
    <td></td>
  </tr>
  <tr>
    <td>message</td>
    <td></td>
    <td>에러 메시지</td>
    <td>String</td>
    <td></td>
  </tr>
  <tr>
    <td rowspan="2">List&lt;FieldErrors&gt;</td>
    <td>name</td>
    <td>필드 id</td>
    <td>String</td>
    <td></td>
  </tr>
   <tr>
    <td>reason</td>
    <td>필드 오류 메시지</td>
    <td>String</td>
    <td></td>
  </tr>
  </tr>
</table>






