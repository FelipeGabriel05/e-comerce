package ecommerce.Http.IO;

import ecommerce.Exceptions.ValidationException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

public class PathVariableExtractor {

  public static Map<String, String> extractPathVariables(
      HttpServletRequest request, String pathPattern) throws ValidationException {
    String pathInfo = request.getPathInfo();

    if (pathInfo == null || pathInfo.isEmpty() || pathInfo.equals("/")) {
      throw new ValidationException("Invalid path");
    }

    String regex = convertPatternToRegex(pathPattern);
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(pathInfo);

    if (!matcher.matches()) {
      throw new ValidationException("Path does not match expected pattern");
    }

    Map<String, String> variables = new HashMap<>();
    Pattern varPattern = Pattern.compile(":([a-zA-Z_][a-zA-Z0-9_]*)");
    Matcher varMatcher = varPattern.matcher(pathPattern);

    int groupIndex = 1;
    while (varMatcher.find()) {
      String varName = varMatcher.group(1);
      String value = matcher.group(groupIndex);
      if (value == null || value.isEmpty()) {
        throw new ValidationException("Path variable '" + varName + "' is empty");
      }
      variables.put(varName, value);
      groupIndex++;
    }

    return variables;
  }

  public static String extractPathVariable(
      HttpServletRequest request, String pathPattern, String variableName)
      throws ValidationException {
    Map<String, String> variables = extractPathVariables(request, pathPattern);

    if (!variables.containsKey(variableName)) {
      throw new ValidationException("Path variable '" + variableName + "' not found");
    }

    return variables.get(variableName);
  }

  public static int extractIntPathVariable(
      HttpServletRequest request, String pathPattern, String variableName)
      throws ValidationException {
    String variable = extractPathVariable(request, pathPattern, variableName);

    try {
      int value = Integer.parseInt(variable);
      if (value <= 0) {
        throw new ValidationException(
            "Path variable '" + variableName + "' must be greater than zero");
      }
      return value;
    } catch (NumberFormatException e) {
      throw new ValidationException("Path variable '" + variableName + "' must be a valid number");
    }
  }

  public static long extractLongPathVariable(
      HttpServletRequest request, String pathPattern, String variableName)
      throws ValidationException {
    String variable = extractPathVariable(request, pathPattern, variableName);

    try {
      long value = Long.parseLong(variable);
      if (value <= 0) {
        throw new ValidationException(
            "Path variable '" + variableName + "' must be greater than zero");
      }
      return value;
    } catch (NumberFormatException e) {
      throw new ValidationException("Path variable '" + variableName + "' must be a valid number");
    }
  }

  private static String convertPatternToRegex(String pathPattern) {
    // SECURITY: Pattern variables use ([^/]+) which matches only characters that are NOT slashes
    // This prevents path traversal attacks like /image/../../etc/passwd
    // The colon notation ensures single-segment matching: /image/:filename will match
    // /image/file.jpg
    // but NOT /image/folder/file.jpg or /image/../../../etc/passwd
    String regex = pathPattern.replaceAll(":[a-zA-Z_][a-zA-Z0-9_]*", "([^/]+)");
    regex = "^" + regex + "$";
    return regex;
  }
}
