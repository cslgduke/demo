package com.cslgduke.demo.camel;

import io.atlasmap.api.AtlasContext;
import io.atlasmap.api.AtlasContextFactory;
import io.atlasmap.api.AtlasSession;
import io.atlasmap.core.DefaultAtlasContextFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author i565244
 */
@Slf4j
public class CamelTest {

    @Test
    public void test_atlasMap_sdk() throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource("promotion_1001.adm");
        AtlasContextFactory factory = DefaultAtlasContextFactory.getInstance();
        AtlasContext context = factory.createContext(url.toURI());
        AtlasSession session = context.createSession();

        url = Thread.currentThread().getContextClassLoader().getResource("promotion.json");
        String source = new String(Files.readAllBytes(Paths.get(url.toURI())));
        log.info("Source document:{}",source);

        session.setSourceDocument("JSONSchemaSource", source);

        context.process(session);
        var targetDoc = session.getDefaultTargetDocument();
        log.info("Target document:{}",targetDoc);
    }
}
