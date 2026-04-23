package ru.job4j.media.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.job4j.media.model.Post;
import ru.job4j.media.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostsMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "posts", source = "posts")
    PostsDto getModelFromEntity(User user, List<Post> posts);
}
