package com.tutor.project.Controller;

import com.tutor.project.Entities.Todo;
import com.tutor.project.Services.TodoServices;


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todo")
public class TodoController {
    @Autowired
    private TodoServices todoServices;


    @PostMapping("/create")
    public ResponseEntity<Todo> CreateTodo(  @RequestBody Todo todo) {
        System.out.println(todo);
        todo.setCreatedAt(LocalDate.now());
        todoServices.saveTodo(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(todo);

    }

    @GetMapping("/all")
    public ResponseEntity<List<Todo>> getAllTodos() {
        System.out.println(LocalDate.now());
        List<Todo> list = todoServices.getTodos();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }


    @GetMapping("/id/{Id}")
    public ResponseEntity<Todo> findTodoById(@PathVariable String Id) {
        ObjectId id = new ObjectId(Id);
        Optional<Todo> todo = todoServices.getTodoById(id);
        System.out.println(todo);
        if (todo.isPresent()) {
            return new ResponseEntity<>(todo.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{todoId}")
    public ResponseEntity<Todo> updateTodo(@RequestBody Todo todo, @PathVariable ObjectId todoId) {
        try {
            Todo oldTodo = todoServices.getTodoById(todoId).orElse(null);
            if (oldTodo != null) {
                oldTodo.setTitle(todo.getTitle() != null && !todo.getTitle().equals("") ? todo.getTitle() : oldTodo.getTitle());
                oldTodo.setDescription(todo.getDescription() != null && !todo.getDescription().equals("") ? todo.getDescription() : oldTodo.getDescription());
                oldTodo.setStatus(todo.getStatus() != null && !todo.getStatus().equals("") ? todo.getStatus() : oldTodo.getStatus());
            }
            todoServices.saveTodo(oldTodo);
            return new ResponseEntity<>(oldTodo, HttpStatus.OK);


        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable ObjectId id) {
        todoServices.deleteById(id);
        return new ResponseEntity<>("Todo deleted successfully", HttpStatus.NO_CONTENT);
    }
}
