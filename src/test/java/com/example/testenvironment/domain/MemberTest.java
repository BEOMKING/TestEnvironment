package com.example.testenvironment.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class MemberTest {
    @Autowired
    private MemberRepository memberRepository;

    @Container
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer()
        .withDatabaseName("test");

    @Test
    void test() {
        Member member = new Member("BJP");
        memberRepository.save(member);

        Assertions.assertThat(memberRepository.findAll()).hasSize(1);
    }
}
