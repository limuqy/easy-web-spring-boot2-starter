package io.github.limuqy.easyweb.config;

import io.github.limuqy.easyweb.cache.util.DictUtil;
import io.github.limuqy.easyweb.core.context.AppContext;
import io.github.limuqy.easyweb.core.util.TraceIdUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EasyWebFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String traceId = request.getHeader(TraceIdUtil.TRACE_ID);
            if (StringUtils.isBlank(traceId)) {
                TraceIdUtil.randomTraceId();
            }
            response.setHeader(TraceIdUtil.TRACE_ID, TraceIdUtil.getTraceId());
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
            DictUtil.clear();
            AppContext.clear();
        }
    }
}
