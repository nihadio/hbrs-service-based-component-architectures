package org.hbrs.seka.uebung1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger implements Logging {
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

  @Override
  // 01.04.26 21:22: Zugriff auf ProductManagement über Methode getProductByName.
  // Suchwort: Motor
  public void log(String method, String message) {
    String timestamp = LocalDateTime.now().format(FORMATTER);

    System.out.println("%s: Zugriff auf ProductManagement über Methode %s. %s".formatted(timestamp, method, message));
  }
}
