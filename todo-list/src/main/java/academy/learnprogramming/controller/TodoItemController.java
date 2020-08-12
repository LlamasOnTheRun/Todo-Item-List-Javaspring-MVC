package academy.learnprogramming.controller;

import academy.learnprogramming.model.TodoData;
import academy.learnprogramming.model.TodoItem;
import academy.learnprogramming.service.TodoItemService;
import academy.learnprogramming.util.AttributeNames;
import academy.learnprogramming.util.Mappings;
import academy.learnprogramming.util.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@Slf4j
@Controller
public class TodoItemController {

    private final TodoItemService todoItemService;

    @Autowired
    public TodoItemController(TodoItemService todoItemService) {
        this.todoItemService = todoItemService;
    }

    @ModelAttribute
   public TodoData todoData() {
       return todoItemService.getData();
   }

   @GetMapping(Mappings.ITEMS)
   public String items() {
       return ViewNames.ITEMS_LIST;
   }

   @GetMapping(Mappings.ADD_ITEM)
   public String addEditItem(Model model) {
        TodoItem todoItem = new TodoItem("", "", LocalDate.now()); // makes a brand new item with empty fields
        model.addAttribute(AttributeNames.TODO_ITEM, todoItem); // will add the item as a attribute for the page
        return ViewNames.ADD_ITEM; // points to the jsp file we have
    }

   @PostMapping(Mappings.ADD_ITEM)
   public String processItem(@ModelAttribute(AttributeNames.TODO_ITEM) TodoItem todoItem) {
        log.info("todoItem from form = {}", todoItem);
        todoItemService.addItem(todoItem);
        return "redirect:/" + Mappings.ITEMS; // redirects to the items list view
   }
}
