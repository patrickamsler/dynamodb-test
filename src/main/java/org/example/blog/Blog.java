package org.example.blog;

import java.util.List;

public record Blog(String title, List<Post> posts) {
}
