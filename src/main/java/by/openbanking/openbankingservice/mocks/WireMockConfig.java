package by.openbanking.openbankingservice.mocks;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class WireMockConfig {

    @Bean
    WireMockServer wireMockServer(@Value("${server.port.wiremock}") int port) {
        WireMockConfiguration configuration = WireMockConfiguration
                .wireMockConfig()
                .port(port)
                .usingFilesUnderDirectory("src/main/resources/wiremock");
        return new WireMockServer(configuration);
    }

}

