package bss.student.registration.config.datasource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Configuration
@EntityScan("bss.student.registration.model")
@EnableJpaRepositories("bss.student.registration.repository")
public class DataSourceConfiguration {

	@Value("${defaultTenant}")
	private String defaultTenant;

	@Bean
	@ConfigurationProperties(prefix = "tenants")
	public DataSource dataSource() {
		File[] files = Paths.get("allTenants").toFile().listFiles();
		Map<Object, Object> resolvedDataSources = new HashMap<>();

		for (File propertyFile : files) {
			Properties tenantProperties = new Properties();
			@SuppressWarnings("rawtypes")
			DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();

			try {
				tenantProperties.load(new FileInputStream(propertyFile));
				String tenantId = tenantProperties.getProperty("name");

				dataSourceBuilder.driverClassName(tenantProperties.getProperty("spring.datasource.driver-class-name"));
				dataSourceBuilder.username(tenantProperties.getProperty("spring.datasource.username"));
				dataSourceBuilder.password(tenantProperties.getProperty("spring.datasource.password"));
				dataSourceBuilder.url(tenantProperties.getProperty("spring.datasource.url"));
				resolvedDataSources.put(tenantId, dataSourceBuilder.build());
			} catch (IOException exp) {
				throw new RuntimeException("Problem in tenant datasource:" + exp);
			}
		}

		AbstractRoutingDataSource dataSource = new MultitenantDataSource();
		dataSource.setDefaultTargetDataSource(resolvedDataSources.get(defaultTenant));
		dataSource.setTargetDataSources(resolvedDataSources);

		dataSource.afterPropertiesSet();
		return dataSource;
	}

}
