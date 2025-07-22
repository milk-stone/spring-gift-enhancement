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

## Step 3 - 상품에 Option 매핑

### 요구 사항

1. 상품에는 항상 하나 이상의 옵션이 있어야 한다.
2. 옵션 이름은 공백을 포함하여 최대 50자까지 입력할 수 있다.
   - 특수 문자
       - 가능: ( ), [ ], +, -, &, /, _
       - 그 외 특수 문자 사용 불가 
3. 옵션 수량은 최소 1개 이상 1억 개 미만이다. 
4. 동일한 상품 내의 옵션 이름은 중복될 수 없다.
5. 상품 옵션의 수량을 지정된 숫자만큼 빼는 기능을 구현한다.
    - 별도의 HTTP API를 만들 필요는 없다.
    - 서비스 클래스 또는 엔티티 클래스에서 기능을 구현하고 나중에 사용할 수 있도록 한다.

### 체크리스트

- [ ] Option 엔티티 작성, Product : Option = 1 : N 매핑
- [ ] 옵션 조회 API 작성
- [ ] 옵션 생성 시에 검증 로직 추가 (특수 문자 사용, 옵션 수량 제한, 중복 확인)
- [ ] 상품 옵션의 수량을 지정된 숫자만큼 빼는 기능 구현. (메서드만 작성)

| URL                     | 메서드 | 기능       | 설명               |
|-------------------------|-----|----------|------------------|
| /api/products/1/options | GET | 상품 옵션 조회 | 상품의 옵션 정보를 받아온다. |