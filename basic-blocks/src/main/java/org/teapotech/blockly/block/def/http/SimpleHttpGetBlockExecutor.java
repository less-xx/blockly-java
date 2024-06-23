package org.teapotech.blockly.block.def.http;

import org.apache.commons.io.IOUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.slf4j.Logger;
import org.teapotech.blockly.block.def.annotation.ApplyToBlock;
import org.teapotech.blockly.block.execute.AbstractBlockExecutor;
import org.teapotech.blockly.block.execute.BlockExecutionContext;
import org.teapotech.blockly.block.execute.BlockExecutionHelper;
import org.teapotech.blockly.exception.BlockExecutionException;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Shadow;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@ApplyToBlock(value = SimpleHttpGetBlock.class)
public class SimpleHttpGetBlockExecutor extends AbstractBlockExecutor {

    final static int REQUEST_TIMEOUT = 5;

    ThreadLocal<CloseableHttpClient> localHttpClient = ThreadLocal.withInitial(()->{
        RequestConfig config = RequestConfig.custom()
                .setResponseTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .setConnectionRequestTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .setRedirectsEnabled(true).build();
        return HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    });

    public SimpleHttpGetBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {

        Logger LOG = context.getLogger();

        Block urlBlock = this.block.getInputs().get("URL").getBlock();
        Shadow urlShadow = this.block.getInputs().get("URL").getShadow();
        if (urlBlock == null && urlShadow == null) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(), "Missing URL block");
        }
        String url = (String) BlockExecutionHelper.execute(urlBlock, urlShadow, context);
        if (url == null || url.isEmpty()) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(), "URL cannot be empty");
        }

        url = replaceMacro(url, context);

        URI uri = new URI(url);
        String query = uri.getQuery();
        if (query != null) {
            String[] pairs = query.split("&");
            StringBuilder sb = new StringBuilder();
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                String key = pair.substring(0, idx);
                String value = pair.substring(idx + 1);
                sb.append(key).append("=").append(java.net.URLEncoder.encode(value, StandardCharsets.UTF_8)).append("&");
            }
            url = uri.getScheme() + "://" + uri.getAuthority() + uri.getPath() + "?" + sb;
        } else {
            url = uri.getScheme() + "://" + uri.getAuthority() + uri.getPath();
        }
        HttpGet request = new HttpGet(url);
        CloseableHttpClient client = localHttpClient.get();
        CloseableHttpResponse response = client.execute(request);
        LOG.debug("HTTP Status {}", response.getReasonPhrase());
        final HttpEntity entity = response.getEntity();
        String contentType = entity.getContentType();
        if(contentType.startsWith("text")){
            return IOUtils.toString(entity.getContent(),entity.getContentEncoding());
        }
        throw new BlockExecutionException("Only text content type is supported.");
    }
}
