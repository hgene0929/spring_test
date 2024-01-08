package com.jyujyu.dayonetest;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import com.redis.testcontainers.RedisContainer;

@Ignore // 현재 클래스가 다른 클래스에 상속시켜줄 부모 클래스이므로, 테스트 수행을 무시해도 되기 때문에 무시하라는 어노테이션을 붙여줌
@Transactional // 테스트 코드에 트랜잭셔널 어노테이션을 붙여주면, 테스트 수행이 끝나면 모두 롤백 수행
@SpringBootTest // 실제로 스프링 애플리케이션 api를 실행하는 것처럼 빈들을 모두 스캔해서 등록해줌(해당 어노테이션이 없으면 테스트에는 스프링빈 객체 스캔하지 X)
@ContextConfiguration(initializers = IntegrationTest.IntegrationTestInitializer.class)
public class IntegrationTest {

	static DockerComposeContainer rdbms;
	static RedisContainer redis;
	static LocalStackContainer aws;

	static {
		rdbms = new DockerComposeContainer(new File("infra/test/docker-compose.yaml"))
			.withExposedService(
				"local-db",
				3306,
				Wait.forLogMessage(".*ready for connections.*", 1)
					.withStartupTimeout(Duration.ofSeconds(300))
			)
			.withExposedService(
				"local-db-migrate",
				0,
				Wait.forLogMessage("(.*Successfully applied.*)|(.*Successfully validated.*)", 1)
					.withStartupTimeout(Duration.ofSeconds(300))
			);
		rdbms.start();

		redis = new RedisContainer(RedisContainer.DEFAULT_IMAGE_NAME.withTag("6"));
		redis.start();

		aws = (new LocalStackContainer())
			.withServices(LocalStackContainer.Service.S3)
			.withStartupTimeout(Duration.ofSeconds(600));
		aws.start();
	}

	static class IntegrationTestInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

		@Override
		public void initialize(ConfigurableApplicationContext applicationContext) {
			Map<String, String> properties = new HashMap<>();

			var rdbmsHost = rdbms.getServiceHost("local-db", 3306);
			var rdbmsPort = rdbms.getServicePort("local-db", 3306);

			properties.put("spring.datasource.url", "jdbc:mysql://" + rdbmsHost + ":" + rdbmsPort + "/score");

			var redisHost = redis.getHost();
			var redisPort = redis.getFirstMappedPort();

			properties.put("spring.data.redis.host", redisHost);
			properties.put("spring.data.redis.port", redisPort.toString());

			try {
				aws.execInContainer(
					"awslocal",
					"s3api",
					"create-bucket",
					"--bucket",
					"test-bucket"
				);
				properties.put("aws.endpoint", aws.getEndpoint().toString());
			} catch (Exception e) {
				// ignore
			}

			TestPropertyValues.of(properties)
				.applyTo(applicationContext);
		}
	}
}
