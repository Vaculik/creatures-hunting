package cz.muni.fi.pa165.config;

import cz.muni.fi.pa165.InMemoryDatabaseApplicationContext;
import cz.muni.fi.pa165.facadeImpl.CreatureFacadeImpl;
import cz.muni.fi.pa165.facadeImpl.UserSystemFacadeImpl;
import cz.muni.fi.pa165.service.CreatureServiceImpl;
import cz.muni.fi.pa165.service.UserSystemServiceImpl;
import cz.muni.fi.pa165.util.EntityMapperImpl;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by vaculik on 21.11.15.
 */
@Configuration
@Import(InMemoryDatabaseApplicationContext.class)
@ComponentScan(basePackageClasses = {CreatureServiceImpl.class, CreatureFacadeImpl.class, 
		UserSystemServiceImpl.class, UserSystemFacadeImpl.class, EntityMapperImpl.class})
public class ServiceApplicationContext {

    @Bean
    public Mapper dozer(){
        DozerBeanMapper dozer = new DozerBeanMapper();
        return dozer;
    }


}
