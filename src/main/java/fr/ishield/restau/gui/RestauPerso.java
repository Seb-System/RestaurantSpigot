package fr.ishield.restau.gui;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Sorts;
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

public class RestauPerso {


    public void inv(Player p, String title) {

        final MongoCollection<Document> collection = Main.getInstance().getMongoManager().getRestauDatabase().getCollection("restau");
        final SubscriberHelpers.ObservableSubscriber check = new SubscriberHelpers.ObservableSubscriber<>();

        final ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        final ItemMeta glassMeta = glass.getItemMeta();
        glassMeta.setDisplayName("§c❌");
        glass.setItemMeta(glassMeta);

        final ItemStack book = new ItemStack(Material.WRITABLE_BOOK, 1);
        final ItemMeta bookMeta = glass.getItemMeta();
        bookMeta.setDisplayName("§aPrendre un rendez-vous");
        book.setItemMeta(bookMeta);

        final ItemStack close = new ItemStack(Material.BARRIER, 1);
        final ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName("§cRetour");
        closeMeta.setLocalizedName("Page -1");
        close.setItemMeta(closeMeta);

        BasicDBObject query = new BasicDBObject("Nom", title);
        collection.find(query).first().subscribe(check);
        Document document = null;
        for (Object o : check.get()) {
            document = (Document) o;
        }

        assert document != null;
        final Inventory inv =  Bukkit.createInventory(p, 27, "§9" + document.getString("Nom"));

        for (int i = 0; i < 9; i++)
            inv.setItem(i, glass);
        for (int i = 18; i < 27; i++)
            inv.setItem(i, glass);


        inv.setItem(9, glass);
        inv.setItem(11, book);
        inv.setItem(15, close);
        inv.setItem(17, glass);

        p.openInventory(inv);


    }

}
