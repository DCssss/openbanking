package by.openbanking.openbankingservice.mocks;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class WireMockServerInit implements InitializingBean {
    private final WireMockServer wireMockServer;

    public WireMockServerInit(WireMockServer wireMockServer) {
        this.wireMockServer = wireMockServer;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        wireMockServer.start();
    }

    @PreDestroy
    public void onExit() {
        wireMockServer.stop();
    }
}