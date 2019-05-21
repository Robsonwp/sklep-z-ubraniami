package com.mikolajewald.sklepzubraniami.service;


import com.mikolajewald.sklepzubraniami.Dao.ItemDao;
import com.mikolajewald.sklepzubraniami.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;

@Service
public class ItemsService {

    ItemDao itemDao;

    @Autowired
    public ItemsService(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public List<Item> getItems() {
        return itemDao.getItems();
    }

    public Item getItem(int itemId) {
        return itemDao.getItem(itemId);
    }

    public void update(Item item) {
        itemDao.updateItem(item);
    }

    public void deleteItem(int itemId) {
        itemDao.deleteItem(itemId);
    }

    public Resource loadFileAsResource(Path path) {
        try {
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("file not found: " + path.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    public List<Item> findItems(Item item) {
        return itemDao.findItems(item);
    }
}
