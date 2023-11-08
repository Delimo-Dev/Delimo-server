package com.cos.delimo.controller.status;

public class ResponseMessage {
    public static final String LOGIN_SUCCESS = "인증 성공";
    public static final String LOGIN_FAILED = "인증 실패";
    public static final String SIGNIN_SUCCESS = "회원 가입 성공";
    public static final String EMAIL_OK = "사용 가능한 이메일입니다.";
    public static final String RESOLUTION_UPDATED = "다짐이 수정되었습니다.";
    public static final String EMAIL_EXISTED = "이미 사용 중인 이메일입니다.";
    public static final String NOT_FOUND_USER = "회원을 찾을 수 없습니다.";
    public static final String UNAUTHORIZED = "회원 인증 실패";

    public static final String TOKEN_EXPIRED = "토큰이 만료되었습니다.";
    public static final String MEMBER_INFO_SUCCESS = "회원의 정보를 성공적으로 불러왔습니다.";
    public static final String FRIEND_REQUESTED_SUCCESS = "친구 신청을 성공적으로 보냈습니다.";
    public static final String FRIEND_NOT_FOUND = "친구 검색에 실패했습니다.";
    public static final String REQUEST_EXISTED = "친구 신청이 이미 완료됐습니다.";
    public static final String REQUEST_FAILED = "자기 자신에게 친구 신청을 보낼 수 없습니다.";
    public static final String FRIEND_FOUND = "친구 검색 성공";
    public static final String FRIEND_REQUEST_ACCEPTED = "친구 신청 승인 완료";
    public static final String FRIEND_REQUEST_REJECTED = "친구 신청 거절 완료";
    public static final String FRIEND_REQUEST_ACCEPTED_FAILED = "친구 신청 승인 실패";
    public static final String FRIEND_REQUEST_REJECTED_FAILED = "친구 신청 거절 실패";
    public static final String FRIEND_LIST_SUCCESSFUL = "친구 목록 가져오기 성공";
    public static final String FRIEND_LIST_FAILED = "친구 목록 가져오기 실패";
    public static final String FRIEND_REQUEST_LIST_SUCCESSFUL = "친구 신청 목록을 성공적으로 가져왔습니다.";
    public static final String FRIEND_INCLUDED = "이미 친구입니다.";

    public static final String DIARY_CREATED = "새로운 일기가 등록되었습니다.";
    public static final String DIARY_UPDATED = "일기가 수정되었습니다.";
    public static final String DIARY_NO_CONTENT = "일기 내용이 없습니다.";
    public static final String DIARY_NONE_TODAY = "오늘 작성된 일기가 없습니다.";
    public static final String DIARY_CONTENT_SUCCESSFUL = "오늘의 일기를 성공적으로 가져왔습니다";
    public static final String DIARY_UPDATE_SUCCESSFULLY = "일기 내용을 성공적으로 수정했습니다.";
    public static final String SENTIMENT_RESULT_SUCCEED = "감정이 성공적으로 도출되었습니다.";
    public static final String SENTIMENT_RESULT_FAILED = "감정이 정상적으로 도출되지 못했습니다.";
    public static final String SENTIMENT_UPDATED_SUCCEED = "감정이 성공적으로 변경되었습니다.";

    public static final String COMMUNITY_SUCCESSFUL = "일기 목록을 커뮤니티에 성공적으로 가져왔습니다.";
    public static final String DIARY_CONTENT_SUCCESFUL = "일기를 성공적으로 가져왔습니다.";
    public static final String COMMENT_FAILED = "댓글 작성에 실패했습니다.";

    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";
    public static final String DB_ERROR = "데이터 베이스 에러";
    public static final String API_CALL_ERROR = "API 호출 실패";
    public static final String API_CALL_SUCCESS = "API 호출 성공";
}
