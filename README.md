# spring-gift-enhancement

## Step 1 - JPA로 리펙터링

- [x] build.gradle 의존성, application.properties 설정:
    - `build.gradle` : `spring-boot-starter-data-jpa` 의존성 추가
    - `application.properties` : JPA 및 Hibernate 관련 설정 추가
- [x] 엔티티 리팩터링
  - [x] `@Entity` 어노테이션을 사용하여 모든 도메인 객체를 JPA 엔티티로 변환
  - [x] 객체 간의 연관 관계(`@ManyToOne`, `@OneToMany`) 설정
- [x] Repository 리팩터링: 기존 Repository 구현체를 삭제하고, `JpaRepository`를 상속받는 인터페이스로 변경
- [x] `@DataJpaTest`를 사용하여 JPA Repository가 올바르게 동작하는지 테스트


## Step 2 - Pagenation 적용

- [x] 모든 상품 조회 API에 적용
- [x] 모든 위시리스트 조회 API에 적용
- [x] (추가 - 관리자 기능) 모든 멤버 조회 API에 적용