package com.example;

import com.google.common.collect.ImmutableMap;
import com.yammer.metrics.annotation.Timed;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * @author berinle
 */
@Path("/foo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AppResource {

    private BarService barService;

    public AppResource(BarService barService) {
        this.barService = barService;
    }

    @GET
    @Timed
    public Map<String, String> foo() {
        barService.doSomeWork();
        return ImmutableMap.of("hello", "world");
    }
}
