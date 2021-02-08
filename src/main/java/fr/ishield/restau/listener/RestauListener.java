package fr.ishield.restau.listener;

import com.mongodb.reactivestreams.client.MongoCollection;
import fr.ishield.restau.Main;
import fr.ishield.restau.gui.RestauGUI;
import fr.ishield.restau.gui.RestauPerso;
import fr.ishield.restau.gui.RestauRdv;
import fr.ishield.restau.helper.SubscriberHelpers;
import fr.ishield.restau.reservation.Reservation;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.print.Doc;

public class RestauListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        final Player p = (Player) e.getWhoClicked();
        final ItemStack is = e.getCurrentItem();
        final RestauGUI restauGUI = new RestauGUI();
        final RestauPerso restauPerso = new RestauPerso();
        final RestauRdv restauRdv = new RestauRdv();
        final InventoryView inventoryView = e.getView();
        final MongoCollection<Document> collection = Main.getInstance().getMongoManager().getReservationsDatabase().getCollection("reservations");


        if(is == null || is.getType().equals(Material.AIR))
            return;

        final ItemMeta im = is.getItemMeta();

        if(im.getDisplayName().equals("§aSuivant")) {
            restauGUI.inv(p, im.getLocalizedName());
            e.setCancelled(true);
        }
        else if(im.getDisplayName().equals("§cRetour")) {
            restauGUI.inv(p, im.getLocalizedName());
            e.setCancelled(true);
        }
        else if(im.getDisplayName().equals("§4Quitter")) {
            p.closeInventory();
            e.setCancelled(true);
        }  else if(im.getDisplayName().equals("§c❌"))
            e.setCancelled(true);
        else if(is.getType().equals(Material.PAPER)) {
            restauPerso.inv(p, im.getDisplayName().substring(2));
            e.setCancelled(true);
        } else if(im.getDisplayName().equals("§aPrendre un rendez-vous")) {
            restauRdv.inv(p, inventoryView.getTitle().substring(2));
            e.setCancelled(true);
        } else if(is.getType().equals(Material.WRITTEN_BOOK))
            e.setCancelled(true);
        else if(im.getDisplayName().equals("§cFermé")) {
            p.sendMessage("§7[§eRestau§7] §cImpossible de prendre le rendez-vous sur un jour fermé.");
            e.setCancelled(true);
        } else if(is.getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
            p.sendTitle("§6" + inventoryView.getTitle().substring(2), "§bAdresse: §e" + im.getLocalizedName(), 1, 100, 1);
            Location loc = p.getLocation();
            World world = p.getWorld();
            world.strikeLightning(loc);
            p.closeInventory();
            e.setCancelled(true);

            Reservation reservation = new Reservation(inventoryView.getTitle().substring(2),
                    p.getUniqueId(),
                    im.getDisplayName().substring(2),
                    im.getLore().get(1).substring(15),
                    im.getLore().get(2).substring(15),
                    im.getLocalizedName());

            Document document = new Document();
            document.put("owner", reservation.getUuid().toString());
            document.put("jour", reservation.getJour());
            document.put("ouverture", reservation.getOuverture());
            document.put("fermeture", reservation.getFermeture());
            document.put("name", reservation.getName());
            document.put("adresse", reservation.getAdresse());
            collection.insertOne(document).subscribe(new SubscriberHelpers.OperationSubscriber<>());

        }

    }

}
