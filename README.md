# movieRecommend
영화 검색 및 추천 with Spring


- [x]  2022/02/17
    - 테이블 생성 오류
    
- [x]  2022/02/18
    - 새로 DATA JPA, LOMBOK, HIBERNATE, SPRING WEB SERVICE, H2 DRIVER, VALIDATION, THYMELEAF
    - domain, repository, service, controller 패키지 생성
    - domain -엔티티 클래스 생성
        - 테이블 운 프로젝트 생성
        - SPRING자동 매핑 `spirng.jpa.gibernate.ddl-auto: create`
    - repository - Moive JPA로 persist해보기
    - controller - Post Method 호출
    
- [x]  2022/02/19
    - 영화 정보
    
- [x]  2022/02/20
    - Tag 엔티티 추가
    - 영화 정보 API 검색
        - API로 가져와서 DB에 넣어주기    
        
- [x]  2022/02/21
    - Tag 엔티티 → Genre 엔티티 변경
    - 다:다 관계로 인해 MovieGenre 엔티티 생성
    - DataFactoryController
        - POST Method로 부를 시 Moive 테이블 잘 생성
        - 오류 → View를 안만듬
    - 배우 데이터 없어서 계획 변경 예정

- [x]  2022/02/22
    - 연관 관계 메서드
        - 사용한 이유?
            - JPA에서 양방향 연관관계 매핑할 때 객체 관점에서 처리하기 위해서
                - 연관 관계의 주인쪽에만 저장하지 않고 객체 관점에서 양쪽 방향에 모두 값을 입력해주는 것
                    - 실수 방지 !!!
    - Movie, Genre 중복 방지
        - validateDuplicate 메서드
    - Movie, Genre 생성, 검색
