package cz.muni.fi.pa165;

import javax.sql.DataSource;

import cz.muni.fi.pa165.dao.AreaDao;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;


@Configuration
public class InMemoryDatabaseApplicationContext {

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
//		em.setLoadTimeWeaver(instrumentationLoadTimeWeaver());
		em.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
		em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		return em;
	}

	@Bean
	public JpaTransactionManager transactionManager(){
		return  new JpaTransactionManager(entityManagerFactory().getObject());
	}

//	@Bean
//	public LoadTimeWeaver instrumentationLoadTimeWeaver() {
//		return new InstrumentationLoadTimeWeaver();
//	}

	@Bean
	public DataSource dataSource(){
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.DERBY).build();
		return db;
	}
}
