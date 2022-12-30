package org.example.blog;

import java.time.LocalDate;
import java.util.List;

public class BlogTest {

    public static void main(String... args) {
        Blog blog1 = new Blog("blog1",
                List.of(
                        new Post("post1", LocalDate.now(), "lorem ipsum", "author1"),
                        new Post("post2", LocalDate.now(), "lorem ipsum", "author2")
                )
        );

        Blog blog2 = new Blog("blog2",
                List.of(
                        new Post("post1", LocalDate.now(), "lorem ipsum", "author1"),
                        new Post("post2", LocalDate.now(), "lorem ipsum", "author1"),
                        new Post("post3", LocalDate.now(), "lorem ipsum", "author2")
                )
        );

        BlogRepository blogRepo = new BlogRepository();
        blogRepo.saveBlog(blog1);
        blogRepo.saveBlog(blog2);

        blogRepo.savePost("blog2",
                new Post("post4", LocalDate.now(), "lorem ipsum", "author1")
        );

        System.out.println(blogRepo.getPosts("blog2"));
        System.out.println(blogRepo.getFirstPost("blog2"));
    }

}
