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
import org.springframework.web.bind.annotation.RequestParam;

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
   public String addEditItem(@RequestParam(required = false, defaultValue = "-1") int id,
                             Model model) {

        log.info("editing id = {}", id);
        TodoItem todoItem = todoItemService.getItem(id);

        if(todoItem == null) { // cant find id?
            todoItem = new TodoItem("", "", LocalDate.now()); // makes a brand new item with empty fields
        }

        model.addAttribute(AttributeNames.TODO_ITEM, todoItem); // will add the item as a attribute for the page
        return ViewNames.ADD_ITEM; // points to the jsp file we have
    }

   @PostMapping(Mappings.ADD_ITEM)
   public String processItem(@ModelAttribute(AttributeNames.TODO_ITEM) TodoItem todoItem) {
        log.info("todoItem from form = {}", todoItem);
        if(todoItem.getId() == 0)
        {
            todoItemService.addItem(todoItem);
        }
        else {
            todoItemService.updateItem(todoItem);
        }
        return "redirect:/" + Mappings.ITEMS; // redirects to the items list view
   }

   @GetMapping(Mappings.DELETE_ITEM)
   public String deleteItem(@RequestParam int id) {
        log.info("Deleting item with id-{}", id);
        todoItemService.removeItem(id);
        return "redirect:/" + Mappings.ITEMS;
   }
}
