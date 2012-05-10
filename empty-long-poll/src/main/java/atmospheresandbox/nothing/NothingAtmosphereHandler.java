package atmospheresandbox.nothing;

import java.io.IOException;

import org.atmosphere.cpr.AtmosphereHandler;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;

public class NothingAtmosphereHandler implements AtmosphereHandler {

    @Override
    public void onRequest(AtmosphereResource resource) throws IOException {
        resource.suspend();
    }

    @Override
    public void onStateChange(AtmosphereResourceEvent event) throws IOException {
    }

    @Override
    public void destroy() {
    }

}
