package com.mikolajewald.sklepzubraniami.service;


import com.mikolajewald.sklepzubraniami.Dao.ItemDao;
import com.mikolajewald.sklepzubraniami.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
