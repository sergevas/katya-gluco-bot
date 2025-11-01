package dev.sergevas.tool.katya.gluco.bot.support;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.SecureRandom;

public class TestRestClientBuilder {

    public static RestClient insecureRestClient(String storePath, String password, String baseUrl) throws Exception {

        var keyStorePath = Path.of(storePath);
        var keyStorePassword = password.toCharArray();

        var trustStore = KeyStore.getInstance("PKCS12");
        try (var fis = new FileInputStream(keyStorePath.toFile())) {
            trustStore.load(fis, keyStorePassword);
        }
        var tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(trustStore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), new SecureRandom());

        var factory = new SimpleClientHttpRequestFactory() {
            @Override
            protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
                if (connection instanceof HttpsURLConnection httpsConn) {
                    httpsConn.setSSLSocketFactory(sslContext.getSocketFactory());
                    httpsConn.setHostnameVerifier((hostname, _) -> "localhost".equals(hostname));
                }
                super.prepareConnection(connection, httpMethod);
            }
        };

        return RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(factory)
                .build();
    }
}
