package com.tutor.project.Entities;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "todos")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Todo {
    @org.springframework.data.annotation.Id
    private ObjectId id;
    @NonNull
    private String title;
    @NonNull
    private String description;
    @NonNull
   
    private String status;
    @NonNull
    private LocalDate createdAt;

}

