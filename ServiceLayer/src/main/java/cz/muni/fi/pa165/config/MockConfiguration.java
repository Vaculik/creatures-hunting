package cz.muni.fi.pa165.config;

import cz.muni.fi.pa165.facade.*;
import cz.muni.fi.pa165.service.*;
import cz.muni.fi.pa165.service.WeaponService;
import cz.muni.fi.pa165.service.WeaponServiceImpl;
import cz.muni.fi.pa165.util.EntityMapper;
import cz.muni.fi.pa165.util.EntityMapperImpl;

import static org.mockito.Mockito.mock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;


/**
 * The additional config of mock objects. This config is primarily
 * for tests on facade layer.
 *
 * @author Karel Vaculik
 */

// THIS LINE OF CODE WAS REALLY BIG EVIL
//@Configuration
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
    public WeaponEfficiencyService mockWeaponEfficiencyService() {
        return mock(WeaponEfficiencyService.class);
    }

    @Bean
    @Primary
    public EntityMapper mockEntityMapper() {
        return mock(EntityMapper.class);
    }
}
