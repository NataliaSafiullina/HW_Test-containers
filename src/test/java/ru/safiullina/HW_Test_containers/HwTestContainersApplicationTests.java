package ru.safiullina.HW_Test_containers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HwTestContainersApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	// Объявим контейнеры для каждого образа и пробросим порт
	private static final GenericContainer<?> appDev = new GenericContainer<>("devapp")
			.withExposedPorts(8080);
	private static final GenericContainer<?> appProd = new GenericContainer<>("prodapp")
			.withExposedPorts(8081);

	// Запускаем контейнеры
	@BeforeAll
	public static void setUp() {
		appDev.start();
		appProd.start();
	}

	// Проверим ответ от Dev
	@Test
	void testDevBody() {
		ResponseEntity<String> forEntityDev = restTemplate.getForEntity("http://localhost:" +
				appDev.getMappedPort(8080) + "/profile", String.class);
		System.out.println(forEntityDev.getBody());
		Assertions.assertEquals("Current profile is dev", forEntityDev.getBody());
	}

	// Проверим ответ от Prod
	@Test
	void testProdBody() {
		ResponseEntity<String> forEntityProd = restTemplate.getForEntity("http://localhost:" +
				appProd.getMappedPort(8081) + "/profile", String.class);
		System.out.println(forEntityProd.getBody());
		Assertions.assertEquals("Current profile is production", forEntityProd.getBody());
	}

}
