package com.tutor.project.Controller;


import java.time.LocalDate;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.tutor.project.Entities.Todo;
import com.tutor.project.Services.TodoServices;

import jakarta.validation.Valid;

import org.springframework.ui.*;
import org.springframework.validation.BindingResult;


@Controller
public class PagesController {
    @Autowired
    private TodoServices todoServices;

    @GetMapping("/")
    public String Home() {
        return "Home";
    }
    @GetMapping("/create")
    public String CreateTodo(Model model) {
        model.addAttribute("todo", new Todo()) ;
        model.addAttribute("statuses",new String[]{"Pending", "Completed"});
    
        return "create";
    }
   
    @PostMapping("/submitTodo")
    public String SubmitTodo(@Valid @ModelAttribute("todo") Todo todo, BindingResult result, Model model) {
        try {
            System.out.println(todo);
            todo.setCreatedAt(LocalDate.now());
            if(!todo.getTitle().isEmpty() && !todo.getDescription().isEmpty()){
                todoServices.saveTodo(todo);
                model.addAttribute("todo", new Todo()) ;
    
                return "redirect:/all";
            }
            return "create";
           
            
        } catch (Exception e) {
            e.printStackTrace();
            return "create";
           
        }
       
    }

    @GetMapping("/all")
    public String getTodos(Model model) {
        List<Todo> list = todoServices.getTodos();
        model.addAttribute("todos",list);
       
        return "getTodos";
    }
    @GetMapping("/edit/{id}")
    public String editTodo(@PathVariable ObjectId id , Model model) {
        Todo todo = todoServices.getTodoById(id).orElseThrow(()->new IllegalStateException("Todo not found"));
        model.addAttribute("todo", todo) ;
        model.addAttribute("statuses",new String[]{"Pending", "Completed"});
        return "create";
    }
    @PostMapping("/edit/{id}")
    public String UpdateTodo ( @PathVariable ObjectId id , @ModelAttribute Todo updatedTodo) {
      Todo todo = todoServices.getTodoById(id).orElse(updatedTodo);
        todo.setTitle(updatedTodo.getTitle());
        todo.setDescription(updatedTodo.getDescription());
        todoServices.saveTodo(todo);
        return "redirect:/all";
    }
    @GetMapping("/delete/{id}")
    public String DeleteTodo ( @PathVariable ObjectId id ){
    todoServices.deleteById(id);
    return "redirect:/all";
    }
    

}
