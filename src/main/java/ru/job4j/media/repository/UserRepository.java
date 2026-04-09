package ru.job4j.media.repository;

import ru.job4j.media.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("""
            SELECT u FROM User AS u
            WHERE u.email = :email
            and u.password =:password
            """)
    Optional<User> findByEmailAndPassword(@Param("email") String email, @Param("password") String password);


    @Query(value = """
            SELECT users.* FROM users JOIN followers
            on users.id = followers.follower_id
            where followers.followed_id = :id
            """, nativeQuery = true)
    List<User> findFollowersById(@Param("id") int id);

    @Query(value = """
    SELECT u.* FROM users u
    JOIN followers f1 ON u.id = f1.follower_id
    JOIN followers f2 ON u.id = f2.followed_id
    WHERE f1.followed_id = :id AND f2.follower_id = :id
    """, nativeQuery = true)
    List<User> findFriendsById(@Param("id") int id);

}
