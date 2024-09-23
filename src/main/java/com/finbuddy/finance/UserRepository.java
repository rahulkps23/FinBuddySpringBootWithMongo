package com.finbuddy.finance;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserCredentials, ObjectId> {
    Optional<UserCredentials> findByEmail(String email);
}
