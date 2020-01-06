# Java--Login_DB_a-housekeeping-book
 
# DB 구조
- cashdiary 테이블
CREATE TABLE cashdiary (
        year varchar(50) not null,  --년
        month varchar(50) not null,  --월
        time varchar(50) not null,   --시간
        day varchar(50) not null,  --일
        item varchar(50) not null,   --아이템
        cost varchar(50) not null,   --가격
        memo varchar(50) not null,   --메모
        CONSTRAINT PLAYER_PK PRIMARY KEY (year)   --기본기
);

- TB_USERSIST 테이블
CREATE TABLE TB_USERLIST(
    id varchar(20) NOT NULL, -- 아이디
    name varchar(20) NOT NULL, -- 이름
    age NUMBER NOT NULL, -- 나이
    addr varchar(50) NOT NULL, -- 주소
    CONSTRAINT PLAYER_PK PRIMARY KEY (id) --기본키
);

#
