package com.finbuddy.finance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "UserCredentials")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentials {
    @Id
    private ObjectId id; // MongoDB ObjectId
    private String firstName;
    private String lastName;
    private String email;   // Unique email
    private String password; // Hashed password
    private String phoneNumber;
}
