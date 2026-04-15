package ecommerce.Http.IO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class CapturedBodyHttpServletResponse extends HttpServletResponseWrapper {

  private final ByteArrayOutputStream capture = new ByteArrayOutputStream();
  private ServletOutputStream outputStream;
  private PrintWriter writer;

  public CapturedBodyHttpServletResponse(HttpServletResponse response) {
    super(response);
  }

  @Override
  public ServletOutputStream getOutputStream() throws IOException {
    if (writer != null) throw new IllegalStateException("getWriter() already called");
    if (outputStream == null) {
      outputStream = new CapturedServletOutputStream(capture);
    }
    return outputStream;
  }

  @Override
  public PrintWriter getWriter() throws IOException {
    if (outputStream != null) throw new IllegalStateException("getOutputStream() already called");
    if (writer == null) {
      writer = new PrintWriter(new OutputStreamWriter(capture, getCharacterEncoding()));
    }
    return writer;
  }

  @Override
  public void flushBuffer() {
    if (writer != null) writer.flush();
  }

  public byte[] getCapturedBody() {
    if (writer != null) writer.flush();
    return capture.toByteArray();
  }

  private static class CapturedServletOutputStream extends ServletOutputStream {

    private final ByteArrayOutputStream target;

    CapturedServletOutputStream(ByteArrayOutputStream target) {
      this.target = target;
    }

    @Override
    public boolean isReady() {
      return true;
    }

    @Override
    public void setWriteListener(WriteListener listener) {}

    @Override
    public void write(int b) throws IOException {
      target.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
      target.write(b, off, len);
    }
  }
}
