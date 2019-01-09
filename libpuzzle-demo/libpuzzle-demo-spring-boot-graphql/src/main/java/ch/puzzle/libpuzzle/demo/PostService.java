package ch.puzzle.libpuzzle.demo;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.annotations.GraphQLSubscription;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;
import org.reactivestreams.Publisher;
import org.springframework.core.ReactiveAdapter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketMessage;

import java.util.Arrays;
import java.util.List;
import java.util.stream.StreamSupport;

@Component
@GraphQLApi
public class PostService {


    @GraphQLQuery(name = "posts")
    public List<PostDto> list() {
        return Arrays.asList(new PostDto("one"), new PostDto("two"));
    }

    @GraphQLMutation
    public PostDto createPost(@GraphQLArgument(name = "post") PostDto dto) {
        return dto;
    }

    @GraphQLSubscription
    public Publisher<PostDto> latestPost() {
        return null;
    }

}
