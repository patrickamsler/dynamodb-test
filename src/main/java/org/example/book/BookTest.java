package org.example.book;

import java.util.List;
import java.util.Set;

public class BookTest {

    public static void main(String... args) {
        BookRepository repo = new BookRepository();

        String vendorDocument = "{"
                + "    \"V01\": {"
                + "        \"Name\": \"Acme Books\","
                + "        \"Offices\": [ \"Seattle\" ]"
                + "    },"
                + "    \"V02\": {"
                + "        \"Name\": \"New Publishers, Inc.\","
                + "        \"Offices\": [ \"London\", \"New York\"" + "]" + "},"
                + "    \"V03\": {"
                + "        \"Name\": \"Better Buy Books\","
                +          "\"Offices\": [ \"Tokyo\", \"Los Angeles\", \"Sydney\""
                + "            ]"
                + "        }"
                + "    }";

        List<Book> books = List.of(
                new Book(1, "book1", "978-3-86680-192-9", Set.of("author1", "author2"), vendorDocument),
                new Book(2, "book2", "978-3-86680-192-9", Set.of("author1"), vendorDocument),
                new Book(3, "book3", "978-3-86680-192-9", Set.of("author3"), vendorDocument)
        );

        books.forEach(repo::persist);

        System.out.println(repo.findById(1));
        System.out.println(repo.getFirstVendor(1));

        String result = repo.persist(List.of(
                new Book(4, "book4", "978-3-86680-192-9", Set.of("author4"), vendorDocument),
                new Book(5, "book5", "978-3-86680-192-9", Set.of("author5"), vendorDocument)
        ));
        System.out.println(result);
    }

}
