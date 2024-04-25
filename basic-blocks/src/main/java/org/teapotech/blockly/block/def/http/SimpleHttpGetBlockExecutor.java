package org.teapotech.blockly.block.def.http;

import org.slf4j.Logger;
import org.teapotech.blockly.block.def.annotation.ApplyToBlock;
import org.teapotech.blockly.block.execute.AbstractBlockExecutor;
import org.teapotech.blockly.block.execute.BlockExecutionContext;
import org.teapotech.blockly.block.execute.BlockExecutionHelper;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Shadow;
import reactor.netty.http.client.HttpClient;

import java.net.URI;
import java.time.Duration;

@ApplyToBlock(value = SimpleHttpGetBlock.class)
public class SimpleHttpGetBlockExecutor extends AbstractBlockExecutor {

    ThreadLocal<HttpClient> localHttpClient = ThreadLocal.withInitial(HttpClient::create);

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
        URI uri = new URI(url);
        String query = uri.getQuery();
        if (query != null) {
            String[] pairs = query.split("&");
            StringBuilder sb = new StringBuilder();
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                String key = pair.substring(0, idx);
                String value = pair.substring(idx + 1);
                sb.append(key).append("=").append(java.net.URLEncoder.encode(value, "UTF-8")).append("&");
            }
            url = uri.getScheme() + "://" + uri.getAuthority() + uri.getPath() + "?" + sb.toString();
        } else {
            url = uri.getScheme() + "://" + uri.getAuthority() + uri.getPath();
        }

        HttpClient client = localHttpClient.get();
        String result = client.get()
                .uri(url)
                .responseContent()
                .aggregate()
                .asString()
                .block(Duration.ofSeconds(10));
        return result;

    }
}
