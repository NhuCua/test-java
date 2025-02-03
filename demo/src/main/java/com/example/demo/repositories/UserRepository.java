package com.example.demo.repositories;

import com.example.demo.models.User;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class UserRepository {
    private final DynamoDbTable<User> userTable;

    public UserRepository(DynamoDbTable<User> userTable) {
        this.userTable = userTable;
    }

    public void save(User user) {
        userTable.putItem(user);
    }

    public User getById(String userId) {
        return userTable.getItem(r -> r.key(k -> k.partitionValue(userId)));
    }

    public List<User> getAll() {
        return StreamSupport.stream(userTable.scan().items().spliterator(), false)
                .collect(Collectors.toList());
    }

    public void delete(String userId) {
        User user = new User();
        user.setUserId(userId);
        userTable.deleteItem(user);
    }
}
