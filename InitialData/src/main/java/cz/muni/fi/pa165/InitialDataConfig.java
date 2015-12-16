package cz.muni.fi.pa165;

import cz.muni.fi.pa165.config.ServiceApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@ComponentScan(basePackageClasses = {InitialDataLoaderImpl.class})
public class InitialDataConfig {

    private static final Logger logger = LoggerFactory.getLogger(InitialDataConfig.class);

    @Autowired
    private InitialDataLoader initialDataLoader;


    @PostConstruct
    public void loadData() {
        logger.debug("PostConstruct loadData()");
        initialDataLoader.LoadData();
    }
}
