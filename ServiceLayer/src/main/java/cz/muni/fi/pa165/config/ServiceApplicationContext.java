package cz.muni.fi.pa165.config;

import cz.muni.fi.pa165.InMemoryDatabaseApplicationContext;
import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.entity.Creature;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Karel Vaculik
 */
@Configuration
@Import(InMemoryDatabaseApplicationContext.class)
@ComponentScan(basePackages = {"cz.muni.fi.pa165.service", "cz.muni.fi.pa165.util", "cz.muni.fi.pa165, facade"})
public class ServiceApplicationContext {

    @Bean
    public Mapper mapper() {
        DozerBeanMapper dozer = new DozerBeanMapper();
        return dozer;
    }
}

