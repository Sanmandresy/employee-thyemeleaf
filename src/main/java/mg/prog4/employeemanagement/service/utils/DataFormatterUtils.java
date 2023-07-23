package mg.prog4.employeemanagement.service.utils;


import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Base64;
import java.util.Locale;
import org.springframework.web.multipart.MultipartFile;

public class DataFormatterUtils {
  private DataFormatterUtils() {

  }

  public static Instant stringToInstant(String str) {
    LocalDate localDate = LocalDate.parse(str);
    return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
  }

  public static String instantToCommonDate(Instant instant) {
    DateTimeFormatter formatter = DateTimeFormatter
        .ofLocalizedDate(FormatStyle.LONG)
        .withLocale(Locale.FRENCH)
        // Madagascar's timezone
        .withZone(ZoneId.of("UTC+3"));
    return formatter.format(instant);
  }

  public static byte[] multipartFileToByte(MultipartFile file) {
    if (file != null) {
      try {
        return file.getBytes();
      } catch (IOException e) {
        throw new RuntimeException("Error on mapping image");
      }
    }
    return null;
  }

  public static String byteAToBase64(byte[] file) {
    if (file != null) {
      return Base64.getEncoder().encodeToString(file);
    }
    return null;
  }

}

