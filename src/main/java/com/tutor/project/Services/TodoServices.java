package com.tutor.project.Services;

import com.tutor.project.Entities.Todo;
import com.tutor.project.Repo.TodoRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoServices {
    @Autowired
    private TodoRepo todoRepo;

    public void saveTodo(Todo todo) {
        todoRepo.save(todo);
    }

    public List<Todo> getTodos() {
        List<Todo> todos = todoRepo.findAll();
       
        return todos;
    }

    public Optional<Todo> getTodoById(ObjectId id) {
        
        return todoRepo.findById(id);
    }

    public void deleteById(ObjectId id) {
        todoRepo.deleteById(id);
    }

}
