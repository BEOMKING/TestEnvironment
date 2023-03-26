### TestContainers
DB와 상호작용하는 통합 테스트를 수행하기 위해 인메모리 DB를 사용하고는 한다. 

하지만 DB마다 스프링에서 지원하는 트랜잭션 고립 수준이 다르고 SQL 문법도 조금씩 다르기 때문에 테스트에서 잡아내지 못하는 것들이 프로덕션 레벨에서 발생할 수 있다.

따라서, 테스트 환경에서도 프로덕션 환경과 동일한 DB를 사용하는 것이 좋다.

로컬에서 DB에 테스트용 스크립트를 실행해서 모든 테스트를 진행하는 것은 테스트들이 서로 영향을 줄 수 있기 때문에 하지 않아야한다.

물론 도커가 설치되어 있어야 하고 테스트가 느려진다는 단점이 있으나 프로덕션 레벨에 가깝게 테스트를 진행할 수 있고 설정을 통해 간편하게 테스트를 수행할 수 있기 때문에 사용한다.

항상 모든 테스트를 수행하지 않고 태깅을 통해 필요한 환경에서만 통합 테스트를 진행하는 방식으로 테스트 속도 문제를 해결할 수 있다.

#### 사용 예제
```java
@SpringBootTest
@Testcontainers // @Container 애노테이션을 찾아 자동 시작과 중지를 수행한다.
class MemberTest {
    @Autowired
    private MemberRepository memberRepository;

    @Container // 관리되어야 하는 컨테이너임을 명시한다.
    // static으로 생성하면 한 번의 컨테이너를 생성해서 모든 테스트를 수행하고 인스턴스로 생성하면 각 테스트마다 컨테이너를 생성한다.
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer()
        .withDatabaseName("test");

    @Test
    void test() {
        Member member = new Member("BJP");
        memberRepository.save(member);

        Assertions.assertThat(memberRepository.findAll()).hasSize(1);
    }
}
```
