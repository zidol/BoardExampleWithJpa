# 콜버스랩 과제 설명

# 1. 실행방법

1. Mysql에 community라는 데이터베이스를 생성합니다.
2. src/resources 에 있는 application.yml의 datasource.url에 사용하실 DB ip를 변경합니다.

```
spring:
  profiles:
    active: local
  datasource:
    url: jdbc:mysql://192.168.55.148:3306/community?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Seoul&tinyInt1isBit=false
    username: zidol
    password: qwer1234!@#$
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: create
    properties:
      hibernate:
        show_sql: true
        format_sql: true
        default_batch_fetch_size: 100
    generate-ddl: true
    database-platform: org.hibernate.dialect.MySQL8Dialect
  mvc:
    pathmatch:
      matching-strategy: ant_path_matcher
#    database-platform: org.hibernate.dialect.MySQL5InnoDBDialect
```

3. 인텔리제이 우측의  Gradle을 클릭 하시고 community → other → compileQuerydsl을 더블클릭하여 실행하여  Q파일 생성해줍니다.

![Untitled](https://zidols.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F56756974-cefc-426f-8846-1cc89c284005%2FUntitled.png?table=block&id=055dd3bc-9261-446f-bbc4-81b11189bcce&spaceId=370dbc5e-872d-4d9e-9f3a-f7113cda9427&width=1210&userId=&cache=v2)

4. `CommunityApplication` 클래스의 메인 함수를 실행 합니다.
5. 브라우저에서 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) 로 접속합니다.
6. Hearder Authorization 값 넣기

    1) Authorize

    2) Authorization 값을 넣습니다. ex)  Realtor 1


![Untitled](https://zidols.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F1b14590d-d547-4b3b-a6a4-7f5b27356144%2FUntitled.png?table=block&id=ce219c2b-a068-4027-b622-350400317f38&spaceId=370dbc5e-872d-4d9e-9f3a-f7113cda9427&width=2000&userId=&cache=v2)

7. 실행할 api르 선택하여 Try it out 을 눌러 활성화 시킵니다.

![Untitled](https://zidols.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fd8cde8a9-dd57-4a03-ba68-a53a66b6b582%2FUntitled.png?table=block&id=9c6ba401-6dda-4ab4-8730-417eac5a3a9a&spaceId=370dbc5e-872d-4d9e-9f3a-f7113cda9427&width=2000&userId=&cache=v2)

8. 필요한 파라미터를 입력하고 excute를 누릅니다.

![Untitled](https://zidols.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F8c1764ed-8760-4250-bb19-271939cf13a1%2FUntitled.png?table=block&id=f90e52d7-a0d2-467a-9eb5-f030ab002040&spaceId=370dbc5e-872d-4d9e-9f3a-f7113cda9427&width=2000&userId=&cache=v2)

9. 예상한 응답이 맞는지 확인합니다.

![Untitled](https://zidols.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F40852428-62b8-403f-a49c-55e056ab7988%2FUntitled.png?table=block&id=49b73100-5152-4d9a-ba04-0f8ff458bee0&spaceId=370dbc5e-872d-4d9e-9f3a-f7113cda9427&width=2000&userId=&cache=v2)

---

# 2.  구현방법

- jpa를 사용 하므로 Entity를 구현하면서 테이블을 설계하였습니다.
- Test 코드를 먼저 구현하고 비즈니스 로직을 구현 하였습니다.
- Authorization을 통해 회원 인증하는 로직은 Spring security를 구현하지 않고 `OncePerRequestFilter`를 상속받아 간단히 구현하였습니다.
- 각 글은 작성시간, 마지막 수정시간, 삭제시간의 대한 히스토리는 실제 테이블의 데이터를 삭제 하지 않고 IS_USE라는 컬럼을 추가하여 true, false(DB 상에선 1, 0)으로 구분하여 구현 하였으며 spring data jpa가 지원하는 `AwareAudit`기능을 사용하여 구현 하였습니다.

---

# 3. 추가한 라이브러리

1. `querydsl` 아래와 같은 장점이 있어 사용하였습니다.
    - jpa 사용시 복잡 동적 쿼리 작성시 편리합니다.
    - 문자가 아닌 코드로 쿼리를 작성함으로써, 컴파일 시점에 문법 오류를 쉽게 확인할 수 있습니다.
    - 쿼리 작성 시 제약 조건등을 메서드로 추출하여 재사용 가능합니다.
2. `swagger` 아래와 같은 장점이 있어 사용하였습니다.
    - API 정보 현행화 가능
    - API를 통해 Parameter, 응답 정보, 예제 등 Spec 정보 전달이 용이합니다.
    - Postman과 같은 툴을 사용하지 않아도 API를 실행 할수 있습니다.