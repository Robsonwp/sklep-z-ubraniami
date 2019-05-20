package com.mikolajewald.sklepzubraniami.Dao;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mikolajewald.sklepzubraniami.entity.Item;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Component
public class ItemDao {

    private List<Item> items;
    private ObjectMapper mapper;
    private ObjectWriter writer;


    public void loadData() {
        mapper = new ObjectMapper();
        try {
            ClassPathResource classPathResource = new ClassPathResource("przedmioty.json");

            InputStream inputStream = classPathResource.getInputStream();
            File tempFile = File.createTempFile("test", ".json");

                FileUtils.copyInputStreamToFile(inputStream, tempFile);

            System.out.println(tempFile);
            System.out.println(ItemDao.class.getProtectionDomain().getCodeSource().getLocation().getPath().toString());
            items = mapper.readValue(tempFile, new TypeReference<List<Item>>() {
            });
        } catch (IOException e) {
            items = null;
            e.printStackTrace();
        }
    }

    public void saveData() {

        mapper = new ObjectMapper();
        writer = mapper.writer(new DefaultPrettyPrinter());
        try {
            writer.writeValue(new ClassPathResource("przedmioty.json").getFile(), items);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<Item> getItems() {
        loadData();
        return items;
    }


    public void saveItem(Item item) {
        item.setId(items.get(items.size() - 1).getId() + 1);
        items.add(item);
    }

    public void updateItem(Item item) {
        loadData();
        boolean czyZnaleziona = false;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == item.getId()) {
                items.set(i, item);
                czyZnaleziona = true;
            }
        }
        if (!czyZnaleziona) {
            saveItem(item);
        }
        saveData();
    }


    public Item getItem(int id) {
        loadData();
        for (Item s :
                items) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }


    public void deleteItem(int id) {
        loadData();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == id) {
                items.remove(i);
                saveData();
            }
        }

    }

    public List<Item> getRodzaj(String rodzaj) {
        List<Item> tempItems = new ArrayList<>();
        for (Item i :
                items) {
            if (i.getRodzaj() == "rodzaj") {
                tempItems.add(i);
            }
        }
        return tempItems;
    }
}
