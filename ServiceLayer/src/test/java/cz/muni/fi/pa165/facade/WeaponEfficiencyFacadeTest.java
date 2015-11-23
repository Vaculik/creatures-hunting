package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.config.MockConfiguration;
import cz.muni.fi.pa165.config.ServiceApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * Tests for all methods of the WeaponEfficiencyFacade interface.
 *
 * @author Karel Vaculik
 */
@ContextConfiguration(classes = {ServiceApplicationContext.class, MockConfiguration.class})
public class WeaponEfficiencyFacadeTest extends AbstractTestNGSpringContextTests {
    // TODO
}
