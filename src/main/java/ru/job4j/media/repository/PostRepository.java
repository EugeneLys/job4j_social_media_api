package ru.job4j.media.repository;

import jakarta.transaction.Transactional;
import ru.job4j.media.model.File;
import ru.job4j.media.model.Post;
import ru.job4j.media.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends CrudRepository<Post, Integer> {

    List<Post> findByAuthor(User author);

    List<Post> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    Page<Post> findByOrderByCreatedAt(Pageable pageable);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = """
            UPDATE posts SET title = :title,
            text = :text
            WHERE id = :id
            """, nativeQuery = true)
    int updatePost(@Param("title") String title, @Param("text") String text, @Param("file") File file, @Param("id") int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = """
            UPDATE posts SET file_id = NULL 
            WHERE id = :id
            """, nativeQuery = true)
    int deleteFileFromPost(@Param("id") int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("""
            DELETE FROM Post post
            WHERE post.id = :id
            """)
    int deletePostById(@Param("id") int id);

    @Query(value = """
            SELECT * FROM posts
            WHERE user_id IN
            (SELECT follower_id FROM followers
            WHERE followed_id = :id)
            """, nativeQuery = true)
    Page<Post> findOrderDescByFollowers(Pageable pageable, @Param("id") int id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = """
    UPDATE posts
    SET title = COALESCE(CAST(:#{#post.title} AS VARCHAR), title),
        text = COALESCE(CAST(:#{#post.text} AS VARCHAR), text),
        user_id = COALESCE(CAST(:#{#post.author != null ? #post.author.id : null} AS INTEGER), user_id),
        file_id = COALESCE(CAST(:#{#post.file != null ? #post.file.id : null} AS INTEGER), file_id)
    WHERE id = :#{#post.id}
    """, nativeQuery = true)
    int patch(@Param("post") Post post);
}
