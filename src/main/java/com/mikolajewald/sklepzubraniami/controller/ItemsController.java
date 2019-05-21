package com.mikolajewald.sklepzubraniami.controller;


import com.mikolajewald.sklepzubraniami.entity.Item;
import com.mikolajewald.sklepzubraniami.raport.PdfGenaratorUtil;
import com.mikolajewald.sklepzubraniami.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@Controller
@RequestMapping("/ubrania")
public class ItemsController {


    private ItemsService itemsService;

    @Autowired
    private PdfGenaratorUtil pdfGenaratorUtil;

    @Autowired
    public ItemsController(ItemsService itemsService) {
        this.itemsService = itemsService;
    }


    @GetMapping("/list")
    public String listItems(Model model) {

        List<Item> items = itemsService.getItems();

        model.addAttribute("items", items);

        return "list-items";
    }

    @GetMapping("/updateForm")
    public String updateForm(@RequestParam("itemId") int itemId,
                             Model model) {
        Item item = itemsService.getItem(itemId);

        model.addAttribute("item", item);

        return "update-form";
    }

    @PostMapping("/save")
    public String updateItem(@ModelAttribute("item") Item item) {
        itemsService.update(item);

        return "redirect:/ubrania/list";
    }

    @GetMapping("/addForm")
    public String addItem(Model model) {
        Item item = new Item();

        model.addAttribute("item", item);
        return "update-form";
    }

    @GetMapping("/delete")
    public String deleteItem(@RequestParam("itemId") int itemId) {
        itemsService.deleteItem(itemId);

        return "redirect:/ubrania/list";
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download() {

        List<Item> items = itemsService.getItems();

        Resource resource = null;

        try {
            Path path = pdfGenaratorUtil.createPdf("pdf-template", items);
            resource = itemsService.loadFileAsResource(path);

        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=itemsraport.pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "Attachment; filename=\"" + resource.getFilename()
                        + "\"")
                .body(resource);
    }
}
