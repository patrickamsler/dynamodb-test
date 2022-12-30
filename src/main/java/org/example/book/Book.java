package org.example.book;

import java.util.Set;

public record Book(Number id, String title, String isbn, Set<String> authors, String vendorInfo) {

}