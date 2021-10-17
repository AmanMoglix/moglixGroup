package com.hackathon.gateway.filter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.GsonBuilder;
import com.hackathon.gateway.model.Error;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class PostFilter extends ZuulFilter {

    private Logger log = LoggerFactory.getLogger(PostFilter.class);

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletResponse response = requestContext.getResponse();	

        log.info("Service Response Code :{}", response.getStatus());

        if (response.getStatus() == 0)
            serviceResponseUnavailable(requestContext);
        return null;
    }

    private void serviceResponseUnavailable(RequestContext requestContext) {
        requestContext.setResponseStatusCode(500);
        requestContext.setSendZuulResponse(true);
        requestContext.setResponseBody(new GsonBuilder().serializeNulls().create().toJson(Error.setErrorResponse(500,
                "Internal Server Error From Gateway. Please report to the system about for this.")));
    }
}
