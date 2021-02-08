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

import java.util.ArrayList;
import java.util.List;

public class RestauRdv {


    public void inv(Player p, String title) {

        final MongoCollection<Document> collection = Main.getInstance().getMongoManager().getRestauDatabase().getCollection("restau");
        final SubscriberHelpers.ObservableSubscriber check = new SubscriberHelpers.ObservableSubscriber<>();
        final List<String> lore = new ArrayList<>();

        final ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        final ItemMeta glassMeta = glass.getItemMeta();
        glassMeta.setDisplayName("§c❌");
        glass.setItemMeta(glassMeta);

        final ItemStack close = new ItemStack(Material.BARRIER, 1);
        final ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName("§cRetour");
        closeMeta.setLocalizedName("Page -1");
        close.setItemMeta(closeMeta);

        final ItemStack open = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
        final ItemMeta openMeta = open.getItemMeta();

        final ItemStack ferme = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        final ItemMeta fermeMeta = ferme.getItemMeta();
        fermeMeta.setDisplayName("§cFermé");
        ferme.setItemMeta(fermeMeta);

        BasicDBObject query = new BasicDBObject("Nom", title);
        collection.find(query).first().subscribe(check);
        Document document = null;
        for (Object o : check.get()) {
            document = (Document) o;
        }

        assert document != null;
        final Inventory inv =  Bukkit.createInventory(p, 27, "§9" + document.getString("Nom"));

        String jour0 = document.getString("Jour0");
        String jour1 = document.getString("Jour1");
        String jour2 = document.getString("Jour2");
        String jour3 = document.getString("Jour3");
        String jour4 = document.getString("Jour4");
        String jour5 = document.getString("Jour5");
        String jour6 = document.getString("Jour6");
        openMeta.setLocalizedName(document.getString("Adresse"));

        if(document.get("Ouverture").equals("Ferme"))
            inv.setItem(10, ferme);
        else {
            openMeta.setDisplayName("§6" + jour0);
            lore.add("");
            lore.add("§eOuverture: §b" + document.get("Ouverture"));
            lore.add("§eFermeture: §b" + document.get("Fermeture"));
            openMeta.setLore(lore);
            open.setItemMeta(openMeta);
            inv.setItem(10, open);
            lore.clear();
        }
        if(document.get("Ouverture__1").equals("Ferme"))
            inv.setItem(11, ferme);
        else {
            lore.add("");
            lore.add("§eOuverture: §b" + document.get("Ouverture__1"));
            lore.add("§eFermeture: §b" + document.get("Fermeture__1"));
            openMeta.setLore(lore);
            openMeta.setDisplayName("§6" + jour1);
            open.setItemMeta(openMeta);
            inv.setItem(11, open);
            lore.clear();
        }

        if(document.get("Ouverture__2").equals("Ferme"))
            inv.setItem(12, ferme);
        else {
            lore.add("");
            lore.add("§eOuverture: §b" + document.get("Ouverture__2"));
            lore.add("§eFermeture: §b" + document.get("Fermeture__2"));
            openMeta.setLore(lore);
            openMeta.setDisplayName("§6" + jour2);
            open.setItemMeta(openMeta);
            inv.setItem(12, open);
            lore.clear();
        }

        if(document.get("Ouverture__3").equals("Ferme"))
            inv.setItem(13, ferme);
        else {
            lore.add("");
            lore.add("§eOuverture: §b" + document.get("Ouverture__3"));
            lore.add("§eFermeture: §b" + document.get("Fermeture__3"));
            openMeta.setLore(lore);
            openMeta.setDisplayName("§6" + jour3);
            open.setItemMeta(openMeta);
            inv.setItem(13, open);
            lore.clear();
        }

        if(document.get("Ouverture__4").equals("Ferme"))
            inv.setItem(14, ferme);
        else {
            lore.add("");
            lore.add("§eOuverture: §b" + document.get("Ouverture__4"));
            lore.add("§eFermeture: §b" + document.get("Fermeture__4"));
            openMeta.setLore(lore);
            openMeta.setDisplayName("§6" + jour4);
            open.setItemMeta(openMeta);
            inv.setItem(14, open);
            lore.clear();
        }

        if(document.get("Ouverture__5").equals("Ferme"))
            inv.setItem(15, ferme);
        else {
            lore.add("");
            lore.add("§eOuverture: §b" + document.get("Ouverture__5"));
            lore.add("§eFermeture: §b" + document.get("Fermeture__5"));
            openMeta.setLore(lore);
            openMeta.setDisplayName("§6" + jour5);
            open.setItemMeta(openMeta);
            inv.setItem(15, open);
            lore.clear();
        }

        if(document.get("Ouverture__6").equals("Ferme"))
            inv.setItem(16, ferme);
        else {
            lore.add("");
            lore.add("§eOuverture: §b" + document.get("Ouverture__6"));
            lore.add("§eFermeture: §b" + document.get("Fermeture__6"));
            openMeta.setLore(lore);
            openMeta.setDisplayName("§6" + jour6);
            open.setItemMeta(openMeta);
            inv.setItem(16, open);
            lore.clear();
        }

        for (int i = 0; i < 9; i++)
            inv.setItem(i, glass);
        for (int i = 18; i < 27; i++)
            inv.setItem(i, glass);

        inv.setItem(9, glass);
        inv.setItem(22, close);
        inv.setItem(17, glass);

        p.openInventory(inv);


    }

}
