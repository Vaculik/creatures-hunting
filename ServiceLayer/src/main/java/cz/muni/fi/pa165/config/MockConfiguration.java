package cz.muni.fi.pa165.config;

import cz.muni.fi.pa165.facade.CreatureFacadeImpl;
import cz.muni.fi.pa165.facade.UserSystemFacadeImpl;
import cz.muni.fi.pa165.facade.WeaponFacadeImpl;
import cz.muni.fi.pa165.service.AreaService;
import cz.muni.fi.pa165.service.CreatureService;
import cz.muni.fi.pa165.service.CreatureServiceImpl;
import cz.muni.fi.pa165.service.UserSystemService;
import cz.muni.fi.pa165.service.UserSystemServiceImpl;
import cz.muni.fi.pa165.service.WeaponService;
import cz.muni.fi.pa165.service.WeaponServiceImpl;
import cz.muni.fi.pa165.util.EntityMapper;
import cz.muni.fi.pa165.util.EntityMapperImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

/**
 * The additional configuration of mock objects. This configuration is primarily for tests on facade layer.
 *
 * @author Karel Vaculik
 */
@Configuration
@ComponentScan(basePackageClasses = {CreatureServiceImpl.class, CreatureFacadeImpl.class, UserSystemServiceImpl.class, 
		UserSystemFacadeImpl.class, EntityMapperImpl.class, WeaponServiceImpl.class, WeaponFacadeImpl.class})
public class MockConfiguration {

    @Bean
    @Primary
    public CreatureService mockCreatureService() {
        return mock(CreatureService.class);
    }

    @Bean
    @Primary
    public AreaService mockAreaService() {
        return mock(AreaService.class);
    }
    
    @Bean
    @Primary
    public UserSystemService mockUserSystemService() {
        return mock(UserSystemService.class);
    }


    @Bean
    @Primary
    public WeaponService mockWeaponService() {
        return mock(WeaponService.class);
    }

    @Bean
    @Primary
    public EntityMapper mockEntityMapper() {
        return mock(EntityMapper.class);
    }
}
