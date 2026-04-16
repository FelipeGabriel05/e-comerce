package ecommerce.Http.Filter;

import ecommerce.Http.IO.CapturedBodyHttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResponseLoggingFilter implements Filter {

  private static final int MAX_LOG_LINES = 10;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;

    long startTime = System.currentTimeMillis();
    CapturedBodyHttpServletResponse capturedRes = new CapturedBodyHttpServletResponse(res);

    chain.doFilter(request, capturedRes);

    long elapsed = System.currentTimeMillis() - startTime;
    byte[] body = capturedRes.getCapturedBody();

    System.out.println(buildResponseLogHeader(req, capturedRes, elapsed));
    System.out.println(buildResponseLogBody(capturedRes, body));

    res.getOutputStream().write(body);
  }

  private String buildResponseLogHeader(
      HttpServletRequest req, CapturedBodyHttpServletResponse res, long elapsed) {
    return "["
        + Instant.now()
        + "] "
        + req.getMethod()
        + " "
        + req.getRequestURI()
        + " "
        + res.getStatus()
        + " ("
        + elapsed
        + "ms)";
  }

  private String buildResponseLogBody(CapturedBodyHttpServletResponse res, byte[] body) {
    if (body.length == 0) return "[empty]";

    String contentType = res.getContentType();
    if (contentType == null) return "[no content-type]";

    String mimeType = contentType.split(";")[0].trim().toLowerCase();

    if (mimeType.startsWith("image/")) return "[image]";
    if (mimeType.startsWith("video/")) return "[video]";
    if (mimeType.startsWith("audio/")) return "[audio]";
    if (mimeType.equals("application/octet-stream")) return "[binary]";
    if (mimeType.equals("application/pdf")) return "[pdf]";
    if (mimeType.equals("text/html")) return "[html]";
    if (mimeType.equals("application/xml") || mimeType.equals("text/xml")) return "[xml]";

    String text = new String(body, StandardCharsets.UTF_8);
    return truncateToLines(text, MAX_LOG_LINES);
  }

  private String truncateToLines(String text, int maxLines) {
    String[] lines = text.split("\n", maxLines + 1);
    if (lines.length <= maxLines) return text.stripTrailing();

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < maxLines; i++) {
      sb.append(lines[i]).append("\n");
    }
    sb.append("...");
    return sb.toString();
  }

  @Override
  public void destroy() {}
}
