package cz.muni.fi.pa165;

import cz.muni.fi.pa165.facadeImpl.CreatureFacadeImpl;
import cz.muni.fi.pa165.service.CreatureServiceImpl;
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
@ComponentScan(basePackageClasses = {CreatureServiceImpl.class, CreatureFacadeImpl.class})
public class ServiceApplicationContext {

    @Bean
    public Mapper dozer(){
        DozerBeanMapper dozer = new DozerBeanMapper();
        return dozer;
    }
}
