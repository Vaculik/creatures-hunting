package cz.muni.fi.pa165;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Karel Vaculik
 */

@Component
@Transactional
public class InitialDataLoaderImpl implements InitialDataLoader {

    @Override
    public void LoadData() {

    }
}
