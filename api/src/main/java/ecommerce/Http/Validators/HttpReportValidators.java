package ecommerce.Http.Validators;

import ecommerce.Exceptions.ValidationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class HttpReportValidators {

  private static final DateTimeFormatter DATE_FORMATTER =
      DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT);

  private static final List<String> ALLOWED_FORMATS = List.of("pdf", "csv", "html");

  public void validateReportParams(HttpServletRequest request) throws ValidationException {

    List<String> errors = new ArrayList<>();

    String startDate = request.getParameter("startDate");
    String endDate = request.getParameter("endDate");

    if (startDate == null || endDate == null || startDate.isEmpty() || endDate.isEmpty()) {
      throw new ValidationException("Missing required parameters: startDate and endDate");
    }

    LocalDate parsedStartDate = null;
    LocalDate parsedEndDate = null;

    try {
      parsedStartDate = LocalDate.parse(startDate, DATE_FORMATTER);
    } catch (DateTimeParseException e) {
      errors.add("Parameter 'startDate' must be a valid calendar date in yyyy-MM-dd format");
    }

    try {
      parsedEndDate = LocalDate.parse(endDate, DATE_FORMATTER);
    } catch (DateTimeParseException e) {
      errors.add("Parameter 'endDate' must be a valid calendar date in yyyy-MM-dd format");
    }

    if (parsedStartDate != null
        && parsedEndDate != null
        && parsedStartDate.isAfter(parsedEndDate)) {
      errors.add("Parameter 'startDate' must not be after 'endDate'");
    }

    String errorMessage = String.join(", ", errors);
    if (!errorMessage.isEmpty()) {
      throw new ValidationException(errorMessage);
    }
  }

  public void validateReportExportParams(HttpServletRequest request) throws ValidationException {

    validateReportParams(request);

    String format = request.getParameter("format");

    if (format != null && !format.isBlank()) {
      if (!ALLOWED_FORMATS.contains(format.toLowerCase())) {
        throw new ValidationException("Invalid format. Allowed values are: " + ALLOWED_FORMATS);
      }
    }
  }
}
