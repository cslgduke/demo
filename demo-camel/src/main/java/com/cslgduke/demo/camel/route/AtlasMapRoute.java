package com.cslgduke.demo.camel.route;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * @author i565244
 */
//@Component
@Slf4j
public class AtlasMapRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:foo&period=1000")
                .setBody(simple(
                        "{\n" +
                                "    \"uuid\": \"002cfb81822f44e3a9f3b5eef05b4b07\",\n" +
                                "    \"accountPlanName\": \"Costco\",\n" +
                                "    \"yearCodeUuid\": 2023,\n" +
                                "    \"yearCodeStartDate\": \"2023-01-01T00:00:00Z\",\n" +
                                "\t\"totalRevenue\": 1000000.85,\n" +
                                "    \"products\": [\n" +
                                "        {\n" +
                                "            \"productTreeNodeName\": \"productName\",\n" +
                                "            \"productTreeNodeUuid\": \"productUid\"\n" +
                                "        }\n" +
                                "    ]\n" +
                                "}"
                ))
                .to("atlasmap:promotion_1001.adm")
                .to("log:bar");
    }
}
