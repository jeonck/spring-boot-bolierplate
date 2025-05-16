package com.example.boilerplate.controller;

import com.example.boilerplate.model.Item;
import com.example.boilerplate.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/items")
public class ItemWebController {

    private final ItemService itemService;

    @Autowired
    public ItemWebController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String listItems(Model model) {
        model.addAttribute("items", itemService.getAllItems());
        return "items/list";
    }

    @GetMapping("/new")
    public String newItemForm(Model model) {
        model.addAttribute("item", new Item());
        return "items/form";
    }

    @PostMapping
    public String saveItem(@Valid @ModelAttribute Item item, 
                          BindingResult result, 
                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "items/form";
        }
        
        itemService.saveItem(item);
        redirectAttributes.addFlashAttribute("message", "Item saved successfully!");
        return "redirect:/items";
    }

    @GetMapping("/edit/{id}")
    public String editItemForm(@PathVariable Long id, Model model) {
        return itemService.getItemById(id)
                .map(item -> {
                    model.addAttribute("item", item);
                    return "items/form";
                })
                .orElse("redirect:/items");
    }

    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        itemService.deleteItem(id);
        redirectAttributes.addFlashAttribute("message", "Item deleted successfully!");
        return "redirect:/items";
    }
}
