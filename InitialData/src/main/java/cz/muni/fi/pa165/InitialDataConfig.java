package cz.muni.fi.pa165;

import cz.muni.fi.pa165.config.ServiceApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

/**
 * @author Karel Vaculik
 */

@Configuration
@Import(ServiceApplicationContext.class)
@ComponentScan(basePackages = {"cz.muni.fi.pa165"})
public class InitialDataConfig {

    @Autowired
    private InitialDataLoader initialDataLoader;

    @PostConstruct
    public void loadData() {
        initialDataLoader.LoadData();
    }
}
