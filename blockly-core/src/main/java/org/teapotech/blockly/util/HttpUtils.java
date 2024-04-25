package org.teapotech.blockly.util;

import io.netty.handler.ssl.SslContextBuilder;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import javax.net.ssl.SSLException;
import javax.net.ssl.X509TrustManager;

import java.security.cert.X509Certificate;

public class HttpUtils {
    private static HttpClient createHttpClient(){
        return HttpClient.create(ConnectionProvider.builder("auto")
                        .maxConnections(100)
                        .build())
                .secure(sslContextSpec -> {
                    try {
                        X509TrustManager trustManager = new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
                            }

                            @Override
                            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
                            }

                            @Override
                            public X509Certificate[] getAcceptedIssuers() {
                                return null;
                            }
                        };
                        sslContextSpec.sslContext(SslContextBuilder.forClient()
                                .trustManager(trustManager)
                                .startTls(true)
                                .protocols("TLSv1.2")
                                .build());
                    } catch (SSLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
