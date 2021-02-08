package fr.ishield.restau.gui;

import com.mongodb.BasicDBObject;
import com.mongodb.reactivestreams.client.MongoCollection;
import fr.ishield.restau.Main;
import fr.ishield.restau.helper.SubscriberHelpers;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class RestauReservations {

    public void inv(Player p) {

        final MongoCollection<Document> collection = Main.getInstance().getMongoManager().getReservationsDatabase().getCollection("reservations");
        final SubscriberHelpers.ObservableSubscriber check = new SubscriberHelpers.ObservableSubscriber<>();
        final List<String> lore = new ArrayList<>();

        BasicDBObject query = new BasicDBObject("owner", p.getUniqueId().toString());
        collection.find(query).subscribe(check);

        final Inventory inv =  Bukkit.createInventory(p, 54, "§9Mes reservations §7[§e" + check.get().size() + "§7]");

        final ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        final ItemMeta glassMeta = glass.getItemMeta();
        glassMeta.setDisplayName("§c❌");
        glass.setItemMeta(glassMeta);

        final ItemStack close = new ItemStack(Material.BARRIER, 1);
        final ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName("§4Quitter");
        close.setItemMeta(closeMeta);

        final ItemStack is = new ItemStack(Material.WRITTEN_BOOK, 1);
        final ItemMeta im = is.getItemMeta();


        int[] list = {9*2-1, 9*3-1, 9*4-1, 9*5-1, 9, 18, 27, 36};

        for (int i = 0; i < 9; i++)
            inv.setItem(i, glass);
        for (int i : list)
            inv.setItem(i, glass);
        for (int i = 45; i < 54; i++)
            inv.setItem(i, glass);

        inv.setItem(4, close);

        for (Object o : check.get()) {
            Document doc = (Document) o;
            im.setDisplayName("§6" + doc.getString("name"));
            lore.add("");
            lore.add("§eJour: §b" + doc.getString("jour"));
            lore.add("");
            lore.add("§eOuverture: §b" + doc.getString("ouverture"));
            lore.add("§eFermeture: §b" + doc.getString("fermeture"));
            lore.add("");
            lore.add("§eAdresse: §b" + doc.getString("adresse"));
            im.setLore(lore);
            is.setItemMeta(im);
            lore.clear();
            inv.addItem(is);
        }


        p.openInventory(inv);
    }
}
