# movieRecommend

영화 검색 및 추천 with Spring

##### `POST`  /api/buildData

###### - 영화 정보 DB에 넣어주기

##### `GET`  /api/search/genre?name=

##### - 장르로 영화 검색하기

##### `GET`  /api/search/movie?name= &content=

##### - 영화 내용 또는 이름으로 검색하기

---

- [x]  2022/02/17
    - 테이블 생성 오류
        - application 다시 보기
        - build.gradle 다시 보기
    - 매핑이 이상한가?
        - X
    - jdk 버전이 이상한가?
        - X
- [x]  2022/02/18
    - 새로운 프로젝트 생성
        - SPRING DATA JPA, LOMBOK, HIBERNATE, SPRING WEB SERVICE, H2 DRIVER, VALIDATION, THYMELEAF
    - domain, repository, service, controller 패키지 생성
    - domain -엔티티 클래스 생성
        - 테이블 자동 매핑 `spirng.jpa.gibernate.ddl-auto: create`
        - 영화, 배우, 감독,
    - repository - Moive JPA로 persist해보기
    - controller - Post Method 호출
- [x]  2022/02/19
    - 영화
        - repository
            - 영화 (제목, 내용) 검색 기능
- [x]  2022/02/20
    - Tag 엔티티 추가
    - 영화 정보 API 검색
        - API로 가져와서 DB에 넣어주기
        - DataFactory
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
- [x]  2022/02/23
    - [양방향 연관관계의 주의점](https://www.notion.so/79556ce77a9e43898e088c13dc3b0ea3)
    - DataFactoryController
        - `genreService.save(genre)`가 중복 방지 메서드로 인해 실패할 경우 `catch`로 가기 때문에 `moiveService.save(movie)`가 실행되지 않는다...
          try-catch 부분을 다시 생각해 봐야 함.
- [x]  2022/02/24
    - GenreService에 중복 방지 메서드 부분 수정
        - 중복 있으면 오류? ❌
        - 중복이면 무시하고 save ❌
    - `@OneToMany`에 `CascadeType.ALL`을 하지 않아서 Moive에 MovieGenre를 넣어서 생성 메서드를 호출하여도 아무도 일도 일어나지 않음
    - 1~30 번호의 Movie를 검색은 해도 저장되지 않음
        - genre부분이 이미 테이블에 등록 돼 있는 Moive를 호출할 시에 검색은 되나 저장이 안됨
            - MovieGenre를 생성할 때 들어가는 Genre가 name값만 있는 Genre여서 Genre를 찾을 수 없었고 때문에 오류가 나서 `movieService.save(moive)`가
              이루어지지 않고 `catch`를 처리해버림
    - `movieService.save(movie)`문제는 해결했는데 MovieGenre에 `genre_id`가 `null`로 들어감
    - `genreService.save(genre)`에서 중복될 경우 `0L`을 반환했고 밑 코드에서 반환된 값으로 `genre`를 찾아서 `null`값이 들어가게됨.
    - `genre_id`를 `save`하지 않아도 찾을 수 있게 위에서 `name`으로 생성된 객체를 `genre.getName()`
      하여 `genreRepository.findByName(genre.getName())`으로 DB 내에 `Genre`를 받아 MovieGenre에 넣어줌.
        - DB에 이제 값은 매우 잘 들어간다 !!!
            - 🙄 근데 MovieGenre에 중복되는 부분 왜 이렇게 거슬릴까.. 뭔가 더 좋은 방법이 없을까 고민중
- [x]  2022/02/25
    - 사용하지 않을 테이블 삭제
    - 장르명으로 영화 검색 API
        - JSON 형태로 출력은 했으나 `movieGenre`이 데이터를 받아오지 못했고 또한 `movie`가 중복되어서 나온다.
        - 1️⃣ 🤢
            - 중복 제거
            - `movieGenre` 에서 `Genre`가 한 개만 나오고 내부 데이터를 받아오지 않음
        - 2️⃣ 😁
            - `패치 조인`으로 한 줄 쿼리
            - `movieGenre`에서 `Genre`가 나오는데 한 개만 나옴.
            - 🤢 `N + 1` 문제가 터짐
            - Genre를 가져올 때 하나하나 검색해서 name을 가져오기 때문이다.
            - 코드 보기

                ```java
                    public List<Movie> findByGenre(String name) {
                        List<Movie> result = em.createQuery(
                                        "select m " +
                                                " from Movie m" +
                                                " join fetch m.movieGenres mg" +
                                                " where mg.genre.name like :name", Movie.class
                                ).setParameter("name", "%" + name + "%")
                                .getResultList();
                    
                        return result;
                     }
                ```

- [x]  2022/02/26
    - 3️⃣ 😄
      - Genre까지 `패치 조인`으로 한 쿼리로 가져옴 → `N + 1` 문제 해결

          ```java
          public List<Movie> findByGenre(String name) {
                  List<Movie> result = em.createQuery(
                                  "select m " +
                                          " from Movie m" +
                                          " join fetch m.movieGenres mg" +
                                          " join fetch mg.genre g" +
                                          " where g.name like :name", Movie.class
                          ).setParameter("name", "%" + name + "%")
                          .getResultList();
            
                  return result;
              }
          ```

    - 4️⃣ 🤢
        - 그러나 Movie를 `조인`하면서 `일:다 조인`을 하므로 데이터가 뻥튀기 되었다.
        - 😊
            - `distinct`를 사용해서 중복 쿼리를 제거해줬다.

            ```java
            public List<Movie> findByGenre(String name) {
                    List<Movie> result = em.createQuery(
                                    "select distinct m" +
                                            " from Movie m" +
                                            " join fetch m.movieGenres mg" +
                                            " join fetch mg.genre g" +
                                            " where g.name like :name", Movie.class
                            ).setParameter("name", "%" + name + "%")
                            .getResultList();
            
                    return result;
                }
            ```