package ecommerce.Http.Filter;

import ecommerce.Http.IO.CachedBodyHttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Collections;
import java.util.Set;
import java.util.StringJoiner;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class RequestLoggingFilter implements Filter {

  private static final Set<String> SENSITIVE_HEADERS =
      Set.of("authorization", "cookie", "set-cookie", "proxy-authorization");

  private static final Pattern SENSITIVE_BODY_FIELDS =
      Pattern.compile(
          "(\"(?:password|senha|pass|secret|token|credential)\"\\s*:\\s*)\"[^\"]*\"",
          Pattern.CASE_INSENSITIVE);

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;

    String contentType = req.getContentType();
    boolean isMultipart = contentType != null && contentType.startsWith("multipart/");

    if (isMultipart) {
      System.out.println(buildRequestLogHeader(req));
      System.out.println("[multipart/form-data]");
      chain.doFilter(req, response);
    } else {
      CachedBodyHttpServletRequest cachedReq = new CachedBodyHttpServletRequest(req);
      System.out.println(buildRequestLogHeader(cachedReq));
      System.out.println(buildRequestLogBody(cachedReq));
      chain.doFilter(cachedReq, response);
    }
  }

  private String buildRequestLogHeader(HttpServletRequest req) {
    StringJoiner headers = new StringJoiner(", ", "{", "}");
    for (String name : Collections.list(req.getHeaderNames())) {
      String value = isSensitiveHeader(name) ? "****" : req.getHeader(name);
      headers.add(name + ": " + value);
    }
    return "[" + Instant.now() + "] " + req.getMethod() + " " + req.getRequestURI() + " " + headers;
  }

  private String buildRequestLogBody(CachedBodyHttpServletRequest req) {
    String body = new String(req.getCachedBody(), StandardCharsets.UTF_8);
    if (body.isBlank()) return "[empty]";
    return maskSensitiveBodyFields(body);
  }

  private boolean isSensitiveHeader(String name) {
    return SENSITIVE_HEADERS.contains(name.toLowerCase());
  }

  private String maskSensitiveBodyFields(String body) {
    return SENSITIVE_BODY_FIELDS.matcher(body).replaceAll("$1\"****\"");
  }

  @Override
  public void destroy() {}
}
