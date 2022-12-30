package org.example.blog;

import java.time.LocalDate;

public record Post(String title, LocalDate date, String text, String author) {
}
