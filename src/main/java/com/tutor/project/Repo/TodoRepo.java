package com.tutor.project.Repo;

import com.tutor.project.Entities.Todo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepo  extends MongoRepository<Todo, ObjectId> {
}
