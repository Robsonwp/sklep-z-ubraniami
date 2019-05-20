package com.mikolajewald.sklepzubraniami.controller;


import com.mikolajewald.sklepzubraniami.entity.Item;
import com.mikolajewald.sklepzubraniami.raport.PdfGenaratorUtil;
import com.mikolajewald.sklepzubraniami.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    //    @RequestMapping(value = "/downloadRaportString", method = RequestMethod.GET,
//    produces = MediaType.APPLICATION_PDF_VALUE)
    @GetMapping("/download")
    public String download() {

        List<Item> items = itemsService.getItems();
//
//        GeneratePdfReport pdfReport = new GeneratePdfReport();
//        pdfReport.itemsReport(items);

        try {
            pdfGenaratorUtil.createPdf("pdf-template", items);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=itemsraport.pdf");

        return "redirect:/ubrania/list";

    }
}
