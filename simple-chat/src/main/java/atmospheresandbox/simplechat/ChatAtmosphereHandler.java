package atmospheresandbox.simplechat;

import java.io.IOException;
import java.io.PrintWriter;

import org.atmosphere.cpr.*;

public class ChatAtmosphereHandler implements AtmosphereHandler {

    @Override
    public void onRequest(AtmosphereResource r) throws IOException {

        AtmosphereRequest req = r.getRequest();
        String method = req.getMethod();

        if (method.equalsIgnoreCase("GET")) {
            r.suspend();
        } else if (method.equalsIgnoreCase("POST")) {
            Broadcaster broadcaster = r.getBroadcaster();
            String body = req.getReader().readLine().trim();
            broadcaster.broadcast(body);
        }
    }

    @Override
    public void onStateChange(AtmosphereResourceEvent event) throws IOException {

        AtmosphereResource r = event.getResource();
        AtmosphereResponse res = r.getResponse();

        if (event.isSuspended()) {
            String body = event.getMessage().toString();
            PrintWriter w = res.getWriter();
            w.write(body);
            event.getResource().resume();
        }
    }

    @Override
    public void destroy() {
    }

}
