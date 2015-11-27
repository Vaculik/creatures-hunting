package cz.muni.fi.pa165.config;

import cz.muni.fi.pa165.InMemoryDatabaseApplicationContext;
import cz.muni.fi.pa165.facade.CreatureFacadeImpl;
import cz.muni.fi.pa165.facade.UserSystemFacadeImpl;
import cz.muni.fi.pa165.facade.WeaponFacadeImpl;
import cz.muni.fi.pa165.service.CreatureServiceImpl;
import cz.muni.fi.pa165.service.UserSystemServiceImpl;
import cz.muni.fi.pa165.service.WeaponServiceImpl;
import cz.muni.fi.pa165.util.EntityMapperImpl;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Karel Vaculik
 */
@Configuration
@Import(InMemoryDatabaseApplicationContext.class)
@ComponentScan(basePackageClasses = {CreatureServiceImpl.class, CreatureFacadeImpl.class,
    UserSystemServiceImpl.class, UserSystemFacadeImpl.class,
    EntityMapperImpl.class, WeaponServiceImpl.class, WeaponFacadeImpl.class,})
public class ServiceApplicationContext {

    @Bean
    public Mapper mapper() {
        DozerBeanMapper dozer = new DozerBeanMapper();
        return dozer;
    }
}
