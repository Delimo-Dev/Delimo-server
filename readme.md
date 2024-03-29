## [See Swagger](http://delimo-server.ap-northeast-2.elasticbeanstalk.com/swagger-ui/index.html)
<details>
  <summary>로그인 API</summary>

# 1. 사용자가 email과 password를 입력하여 로그인합니다.

- 입력 데이터: email, password
- email 또는 password가 하나라도 일치하지 않으면 로그인 실패입니다.
- 사용자가 입력한 email을 바탕으로 회원DB에서 일치하는 회원을 찾고, `BcryptPasswordEncoder().matches()` 를 이용해 인코딩된 pw와 동일한지 여부 확인

### URL / Method

```jsx
POST /users/login
```

### Request Headers

- Authorization : Bearer Token
- Content-Type : application/json; charset=utf-8

### Request Body

| 항목 | 타입 | 설명 | 값(예시) | 필수 |
| --- | --- | --- | --- | --- |
| email | string (varchar) | 사용자 email | lyb2325@gmail.com | O |
| password | string (varchar) | 사용자 비밀번호 | 12345678*** | O |

```json
{
    "email":"lyb2325@gmail.com",
    "password":"12345678***"
}
```

### Response

- `200 OK`  : 로그인 성공

```json
{
  "code": 200,
  "message": "인증 성공",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dEBnbWFpbC5jb20iLCJwYXNzd29yZCI6InBhc3N3b3JkIiwiZXhwIjoxNjg1OTA2ODM0fQ.IWnrlUFJY2BM45a1tEWDfBRM5SoFShWypH6wOjBuFok"
  }
}
```
- `401 Unauthorized`  : 로그인 실패
```json
{
  "code": 401,
  "message": "인증 실패",
  "data": null
}
```

</details>

<details>
    <summary> 회원 가입 API </summary>

# 1. 사용자가  회원 가입합니다. ✅

- 입력 데이터: email, password, nickname
- 프론트에서는 다음의 과정을 진행합니다.
    - confirm password
    - 영문 8자리 이상, 특수 문자 입력 등 프론트에서 처리
- 이후 완료되면, 사용자에게 한 줄 소개 추가적으로 입력 받음

### URL / Method

```jsx
POST /users/new
```

### Request Body

```json
{
  "email":"lyb2325@gmail.com",
  "nickname":"예빈",
  "password":"12345678***"
}
```

### Response

- `200 OK` / `201 Created`
    - POST 전송 성공적으로 완료될 시 JWT 토큰 발행
        - payload : email, nickname, password
    - JWT 토큰 앞자리 7자리로 사용자 고유 토큰 ID 값을 발행합니다.

    ```json
    {
        "code": 400,
        "message": "이미 사용 중인 이메일입니다.",
        "data": null
    }
    ```

    ```json
    {
        "code": 200,
        "message": "회원 가입 성공",
        "data": {
            "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjMzZGQiLCJleHAiOjE2ODI5NTIzNjB9.gpxmzejXcChpbqq02BACqbEd_99qOFSXkFxV6qQfOZE"
        }
    }
    ```

# 2. 회원 가입 후 한 줄 소개 수정 ✅

### URL / Method

```jsx
PATCH /users/updateResolution
```

### Request Headers

- **Authorization : Bearer Token**
- Content-Type : application/json; charset=utf-8

### RequestBody

```json
{
    "resolution":"화이팅팅"
}
```

### ResponseBody

```json
{
    "code": 401,
    "message": "회원 인증 실패",
    "data": null
}
```

```json
{
    "code": 200,
    "message": "다짐이 수정되었습니다.",
    "data": {
        "resolution": "화이팅팅"
    }
}
```

</details>


<details>
    <summary> 마이 페이지, 친구 API </summary>

# 1. 마이 페이지에서 나의 정보를 확인합니다. ✅

### URL / Method

```jsx
GET /users/myPage
```

### Request Headers

- **Authorization : Bearer Token**
- Content-Type : application/json; charset=utf-8

### Response Body

- 오늘의 구절(phrase)은 매일 바뀌므로, 오늘의 date에 해당하는 phrase를 응답으로 받습니다.
- 내 정보들: email, code, resolution, friendList, requestedList 
- 내 고유 code는 친구 추가 시 필요한 정보입니다.


```json
{
  "code": 200,
  "message": "회원의 정보를 성공적으로 불러왔습니다.",
  "data": {
    "id": 1,
    "email": "1234@gmail.com",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0IiwiZXhwIjoxNjgyODY0ODQ0fQ.gjhdUxtf81pvp8EZfR9YO94_ZXkgQswQCPdJcVcXEIk",
    "code": "eeea16ab",
    "resolution": null,
    "friendList": [],
    "requestedList": [
      2
    ],
    "requesterList": []
  }
}
```

```json
{
  "code": 200,
  "message": "회원의 정보를 성공적으로 불러왔습니다.",
  "data": {
    "id": 1,
    "email": "1234@gmail.com",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0IiwiZXhwIjoxNjgyODY0ODQ0fQ.gjhdUxtf81pvp8EZfR9YO94_ZXkgQswQCPdJcVcXEIk",
    "code": "eeea16ab",
    "resolution": null,
    "friendList": [],
    "requestedList": [],
    "requesterList": []
  }
}
```

```json
{
    "code": 401,
    "message": "회원 인증 실패",
    "data": null
}
```

# 2. code로 친구를 검색합니다.

- 친구 코드로 친구를 검색합니다. (path variable)

### URL / Method
- React Native에서 Get 메서드에 대해 Request Body 확인하지 않으므로 Post로 변경
- 친구 코드는 노출되어도 큰 문제 없는 정보라 판단되어 PathVariable로 변경

```jsx
POST /friend/findByCode/{code}
```

### Path Variable
```jsx
POST /friend/findByCode/1234567
```

### Response Body

```json
{
    "code": 200,
    "message": "친구 검색 성공",
    "data": {
        "friendId": 1
    }
}
```

```json
{
    "code": 404,
    "message": "회원을 찾을 수 없습니다.",
    "data": null
}
```

# 3. 친구 신청을 보냅니다. ✅

- 친구 신청을 보냅니다.

### URL / Method

```jsx
POST /friend/request
```

### Request Headers

- **Authorization : Bearer Token**
- Content-Type : application/json; charset=utf-8

### Request Body

```json
{
    "friendId":1
}
```

### Response Body

- 친구 신청이 완료되면 myPage에서 requesterIdList 에 친구 id가 추가됩니다. 상대방 친구 mypage에는 requestedId에 id가 추가됩니다.

```json
{
    "code": 201,
    "message": "친구 신청을 성공적으로 보냈습니다.",
    "data": null
}
```

- 자기 자신을 검색하거나, 친구 신청이 이미 완료됐거나, 친구 검색에 실패한 경우 400 code를 반환합니다.

```json
{
    "code": 400,
    "message": "자기 자신에게 친구 신청을 보낼 수 없습니다.",
    "data": null
}
```

```json
{
    "code": 400,
    "message": "친구 신청이 이미 완료됐습니다.",
    "data": null
}
```

```json
{
    "code": 400,
    "message": "친구 검색에 실패했습니다.",
    "data": null
}
```

# 4. 친구 신청을 승인합니다. ✅

### URL / Method

```jsx
POST /friend/acceptRequest
```

### Request Headers

- **Authorization : Bearer Token**
- Content-Type : application/json; charset=utf-8

### Request Body
```json
{
    "friendId":1
}
```

### Response Body

- 친구 신청을 승인하면 requesterIdList, requestedIdList에서 삭제가 되고, friendList에 추가됩니다.
- 자기 자신을 검색하거나, 친구 신청이 이미 완료됐거나, 친구 검색에 실패한 경우 400 code를 반환합니다.

```json
{
    "code": 201,
    "message": "친구 신청 승인 완료",
    "data": null
}
```

# 5. 친구 신청을 거절합니다. ✅

### URL / Method

```jsx
POST /friend/rejectRequest
```

### Request Headers

- **Authorization : Bearer Token**
- Content-Type : application/json; charset=utf-8

### Request Body
```json
{
    "friendId":1
}
```

### Response Body

- 친구 신청 거절을 완료하면, friendRequest 객체가 삭제됩니다.


```json
{
    "code": 201,
    "message": "친구 신청 거절 완료",
    "data": null
}
```

# 6. 친구 목록을 조회합니다. ✅

### URL / Method

```jsx
GET /friend/list
```

### Request Headers

- **Authorization : Bearer Token**
- Content-Type : application/json; charset=utf-8

### 

### Response Body

- 친구의 id, 닉네임, 한줄 소개를 표시합니다.

```json
{
    "code": 200,
    "message": "친구 목록 가져오기 성공",
    "data": [
        {
            "friendId": 1,
            "nickname": "ybrin",
            "resolution": null
        },
        {
            "friendId": 1,
            "nickname": "ybrin",
            "resolution": null
        }
    ]
}
```

```json
{
    "code": 401,
    "message": "회원 인증 실패",
    "data": null
}
```

# 7. 받은 친구 신청 목록을 조회합니다. ✅

### URL / Method

```jsx
GET /friend/requested
```

### Request Headers

- **Authorization : Bearer Token**
- Content-Type : application/json; charset=utf-8

### Response Body

- 받은 친구 신청 각각에 대해, 친구 id, 닉네임, resolution이 표시됩니다.

```json
{
    "code": 201,
    "message": "친구 신청 목록을 성공적으로 가져왔습니다.",
    "data": [
        {
            "friendId": 1,
            "nickname": "ybrin",
            "resolution": null
        }
    ]
}
```

```json
{
    "code": 401,
    "message": "회원 인증 실패",
    "data": null
}
```
</details>

<details>
  <summary>일기장 API</summary>

# 1. 사용자는 오늘 작성한 일기를 조회합니다. ✅

- 오늘 작성한 일기를 조회합니다.
- 오늘의 날짜와 작성한 일기의 날짜를 비교하여 작성한 일기가 있는지 확인합니다.

### URL / Method

```jsx
GET /diary/today
```

### Request Headers

- Authorization : Bearer Token
- Content-Type : application/json; charset=utf-8

### Response

- `200 OK` / `201 Created`
  - POST 전송 성공
- `401 Unauthorized`
  - 로그인이 필요한 경우 (JWT 토큰 만료 시)

### Response Body

- `200 OK`
  - 일기를 아직 작성하지 않은 경우

    ```json
    {
        "code": 200,
        "message": "오늘 작성된 일기가 없습니다.",
        "data": null
    }
    ```

- 일기 작성한 경우
  - `diaryId` : 해당 일기 pk값
  - `sentimentId` : 해당 sentiment 테이블 pk값
  - `privacy` : 공개 설정 코드
  - `sentiment` : 감정 코드
  - `visitied` : 방문 조회수 (1일 경우 감정 변경 모달창 구현)

    ```json
    {
        "code": 200,
        "message": "오늘의 일기를 성공적으로 가져왔습니다",
        "data": {
            "diaryId": 2,
            "sentimentId": 2,
            "content": "im happy",
            "privacy": 2,
            "sentiment": 1,
            "visited": 1
        }
    }
    ```

- `401 Unauthorized`  : 회원 인증 실패한 경우

    ```json
    {
        "code": 401,
        "message": "회원 인증 실패",
        "data": null
    }
    ```


# 2. 사용자는 오늘의 일기를 작성 또는 수정합니다. ✅

- 사용자가 오늘의 일기를 새로 작성하거나, 이미 작성된 일기를 수정합니다.

### URL / Method

```jsx
POST /diary/today
```

### Request Headers

- Authorization : Bearer Token
- Content-Type : application/json; charset=utf-8

### Request Body

- privacy 설정하지 않는 경우 자동적으로 0
- setting: 0(비공개, default), 1(친구공개), 2(전체공개)

    ```json
    {
        "content":"즐거웠던 하루였다"
    }
    ```

    ```json
    {
        "content":"즐거워던 하루였다",
        "privacy":1
    }
    ```


### Response 

- `201 Created`
  - POST 전송 성공

    ```json
    {
        "code": 201,
        "message": "새로운 일기가 등록되었습니다.",
        "data": {
            "diaryId": null,
            "sentimentId": null,
            "content": "오늘 스쿼트를 100개 한 하루...힘들지만 매우 뿌듯하다.",
            "privacy": 0,
            "sentiment": 7,
            "visited": 0
        }
    }
    ```

  - 일기 수정

    ```json
    {
        "code": 201,
        "message": "새로운 일기가 등록되었습니다.",
        "data": {
            "diaryId": null,
            "sentimentId": null,
            "content": "오늘 스쿼트를 100개 한 하루...힘들지만 매우 뿌듯하다.",
            "privacy": 0,
            "sentiment": 7,
            "visited": 2
        }
    }
    ```

- `400 Bad Request`
  - content 내용이 없는 경우

    ```json
    {
        "code": 400,
        "message": "일기 내용이 없습니다.",
        "data": null
    }
    ```


# 3. 사용자는 대표 감정을 변경할 수 있습니다. ✅

- 7가지 감정 중, 자연어 처리 모델을 통해 분석된 일기에 대한 대표 감정을 바꿀 수 있습니다.
- 감정 변경 모달 창은 처음 감정을 도출될 때에만 뜨게 됩니다. (`visitied=1` 인 경우)

### URL / Method

```jsx
PATCH /diary/updateSentiment
```

### Request Headers

- Authorization : Bearer Token
- Content-Type : application/json; charset=utf-8

### Request Body

- 바꿀 일기 테이블의 pk id, one-to-one 관계를 맺고 있는 sentiment 테이블의 pk id와 대표 감정의 code를 전달합니다.

```json
{
    "diaryId":1,
    "sentimentId":1,
    "newSentiment":7
}
```

### Response

- `200 OK`
  - PATCH 전송 성공

    ```json
    {
        "code": 200,
        "message": "감정이 성공적으로 변경되었습니다.",
        "data": {
            "diaryId": 1,
            "sentimentId": 1,
            "updatedSentiment": 7
        }
    }
    ```

# 4. 사용자는 자신이 작성한 전체 일기 목록 조회합니다.

- 사용자는 그동안 작성한 전체 일기들을 조회합니다.

### URL / Method

```jsx
GET /diary/list
```

### Request Headers

- Authorization : Bearer Token
- Content-Type : application/json; charset=utf-8

### R**esponse**

- `200 OK` / `201 Created`
  - POST 전송 성공
- `401 Unauthorized`
  - 로그인이 필요한 경우 (JWT 토큰 만료 시)

### Response Body

- `200 OK`

    ```json
    {
        "code": 200,
        "message": "내 일기 목록을 성공적으로 가져왔습니다.",
        "data": [
            {
                "diaryId": 2,
                "sentimentId": 2,
                "content": "드디 주말이다 휴",
                "privacy": 0,
                "sentiment": 0,
                "visited": 2,
                "createdDate": "2023-11-11T01:53:05.970923"
            }
        ]
    }
    ```
  
</details>

<details>
  <summary>커뮤니티 게시판 API</summary>

# 1. 커뮤니티 게시판을 봅니다. (오늘의 일기)

- 사용자는 다른 사용자들의 전체 공개된 일기 게시물들을 볼 수 있습니다.

### URL / Method

```jsx
GET /community/diaries
```

### Request Headers

- Authorization : Bearer Token
- Content-Type : application/json; charset=utf-8

### Response Body

- privacy 설정 값이 전체 공개 (setting값:2) 인 게시물들의 리스트를 보여줍니다.
- comment 리스트도 함께 나옴

### Response Body

- privacy 설정 값이 전체 공개 (setting값:2) 인 게시물들의 리스트를 보여줍니다.
- comment 리스트도 함께 나옴

```json
{
    "code": 200,
    "message": "일기 목록을 커뮤니티에 성공적으로 가져왔습니다.",
    "data": [
        {
            "diaryId": 1,
            "memberId": 1,
            "code": "d3601200",
            "nickname": "yebin",
            "content": "졸업까지 얼마 안남아서 너무 슬퍼퍼",
            "createdDate": "2023-11-09T12:29:45.599339",
            "comments": [
                {
                    "memberId": null,
                    "nickname": "익명",
                    "content": "나는 얼른 졸업하고 싶어~",
                    "createdDate": "2023-11-09T12:47:39.244858"
                },
                {
                    "memberId": 1,
                    "nickname": "yebin",
                    "content": "나도 이제 대학 생활이 끝난 다니 슬퍼~",
                    "createdDate": "2023-11-09T13:00:32.353228"
                },
                {
                    "memberId": 1,
                    "nickname": "yebin",
                    "content": "나도 슬퍼",
                    "createdDate": "2023-11-10T21:37:38.18776"
                }
            ]
        }
    ]
}
```

### R**esponse**

- `200 OK` / `201 Created`
  - GET성공
- `401 Unauthorized`
  - 로그인이 필요한 경우 (JWT 토큰 만료 시)


# 2. 사용자는 게시글에 댓글을 달 수 있습니다.

- 사용자는 관심이 가는 게시글에 댓글을 달아 반응을 할 수 있습니다.

### URL / Method

```jsx
POST /community/diaries/{diary_id}/comment
```

### Request Headers

- Authorization : Bearer Token
- Content-Type : application/json; charset=utf-8\

### Request Body

- content : 댓글 내용

```json
{
    "content":"나도 이제 대학 생활이 끝난 다니 슬퍼~"
}
```

### **Response**

- `200 OK` / `201 Created`
  - POST 전송 성공
- `401 Unauthorized`
  - 로그인이 필요한 경우 (JWT 토큰 만료 시)
- `404 Not Found`
  - 원본 게시글이 비공개로 전환되었거나, 삭제되는 등 어떠한 이유로 원본 게시글을 더 이상 볼 수 없는 경우
- `400 Bad Request`
  - 서버 오류로 댓글을 다는데 실패한 경우

</details>
