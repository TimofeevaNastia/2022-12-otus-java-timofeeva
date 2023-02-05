package otus.ru.tests;

import lombok.extern.slf4j.Slf4j;
import otus.ru.annotatiton.After;
import otus.ru.annotatiton.Before;
import otus.ru.annotatiton.Test;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
public class FailTest {

    public FailTest() {
    }

    @Test
    public void test1() {
        log.info("Run Тест 1");
        assertThat("Test").isEqualTo("Tests");
    }

    @Test
    public void test2() {
        log.info("Run Тест 2");
    }

    @Before
    public void before1() {
        log.info("Run method - Before 1");
    }

    @Before
    public void before2() {
        log.info("Run method - Before 2");
    }

    @After
    public void after1() {
        log.info("Run method - After 1");
    }

    @After
    public void after2() {
        log.info("Run method - After 2");
    }


}