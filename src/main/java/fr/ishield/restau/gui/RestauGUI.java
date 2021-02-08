package fr.ishield.restau.gui;

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

import java.util.ArrayList;
import java.util.List;

public class RestauGUI {

    public void inv(Player p, String nb) {

        final MongoCollection<Document> collection = Main.getInstance().getMongoManager().getRestauDatabase().getCollection("restau");
        final SubscriberHelpers.ObservableSubscriber check = new SubscriberHelpers.ObservableSubscriber<>();
        final List<String> lore = new ArrayList<>();

        collection.find().sort(Sorts.ascending("Nom")).subscribe(check);

        final Inventory inv =  Bukkit.createInventory(p, 54, "§8[P°1] §9Liste des restaurants");
        final Inventory inv2 =  Bukkit.createInventory(p, 54, "§8[P°2] §9Liste des restaurants");
        final Inventory inv3 =  Bukkit.createInventory(p, 54, "§8[P°3] §9Liste des restaurants");

        final ItemStack next = new ItemStack(Material.LIME_CARPET, 1);
        final ItemMeta nexMeta = next.getItemMeta();
        nexMeta.setDisplayName("§aSuivant");

        final ItemStack back = new ItemStack(Material.RED_CARPET, 1);
        final ItemMeta backMeta = next.getItemMeta();
        backMeta.setDisplayName("§cRetour");

        final ItemStack close = new ItemStack(Material.BARRIER, 1);
        final ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName("§4Quitter");
        close.setItemMeta(closeMeta);

        final ItemStack glass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
        final ItemMeta glassMeta = glass.getItemMeta();
        glassMeta.setDisplayName("§c❌");
        glass.setItemMeta(glassMeta);

        int[] list = {9*2-1, 9*3-1, 9*4-1, 9*5-1, 9, 18, 27, 36};

        nexMeta.setLocalizedName("Page 0");
        next.setItemMeta(nexMeta);
        inv.setItem(8, next);
        nexMeta.setLocalizedName("Page 1");
        next.setItemMeta(nexMeta);
        inv2.setItem(8, next);
        inv3.setItem(8, glass);

        for (int i = 0; i < 8; i++) {
            inv.setItem(i, glass);
            inv2.setItem(i, glass);
            inv3.setItem(i, glass);
        }

        backMeta.setLocalizedName("Page -1");
        back.setItemMeta(backMeta);
        inv2.setItem(0, back);
        backMeta.setLocalizedName("Page 0");
        back.setItemMeta(backMeta);
        inv3.setItem(0, back);

        inv.setItem(4, close);
        inv2.setItem(4, close);
        inv3.setItem(4, close);

        for (int i : list) {
            inv.setItem(i, glass);
            inv2.setItem(i, glass);
            inv3.setItem(i, glass);
        }
        for (int i = 45; i < 54; i++) {
            inv.setItem(i, glass);
            inv2.setItem(i, glass);
            inv3.setItem(i, glass);
        }

        final ItemStack is = new ItemStack(Material.PAPER, 1);
        final ItemMeta im = is.getItemMeta();

        int i = 0;
        for (Object o : check.get()) {
            Document doc = (Document) o;
            im.setDisplayName("§6" + doc.getString("Nom"));
            lore.add("");
            lore.add("§eTéléphone: §b" + doc.getString("Telephone"));
            lore.add("§eAdresse: §b" + doc.getString("Adresse"));
            if(doc.getBoolean("Halal"))
                lore.add("§eHalal: §aOui");
            else
                lore.add("§eHalal: §cNon");
            im.setLore(lore);
            is.setItemMeta(im);
            lore.clear();
            if(i < 28) inv.addItem(is);
            else if(i < 28*2) inv2.addItem(is);
            else  inv3.addItem(is);
            i++;
        }

        if(nb.equals("Page 0"))
            p.openInventory(inv2);
        else if(nb.equals("Page 1"))
            p.openInventory(inv3);
        else
            p.openInventory(inv);

    }

}
