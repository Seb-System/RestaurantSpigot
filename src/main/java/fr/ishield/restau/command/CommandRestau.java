package fr.ishield.restau.command;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.model.Sorts;
import com.mongodb.reactivestreams.client.MongoCollection;
import fr.ishield.restau.Main;
import fr.ishield.restau.gui.RestauGUI;
import fr.ishield.restau.gui.RestauReservations;
import fr.ishield.restau.helper.SubscriberHelpers;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.reactivestreams.Publisher;

import java.util.Arrays;

public class CommandRestau implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        final RestauGUI restauGUI = new RestauGUI();
        final RestauReservations restauReservations = new RestauReservations();

        final MongoCollection<Document> collection = Main.getInstance().getMongoManager().getRestauDatabase().getCollection("restau");
        final MongoCollection<Document> reservations = Main.getInstance().getMongoManager().getReservationsDatabase().getCollection("reservations");
        SubscriberHelpers.ObservableSubscriber check = new SubscriberHelpers.ObservableSubscriber<>();
        SubscriberHelpers.ObservableSubscriber check2 = new SubscriberHelpers.ObservableSubscriber<>();
        if(!(sender instanceof Player))
            return false;

        final Player p = ((Player) sender).getPlayer();
        assert p != null;

        collection.find().sort(Sorts.ascending("Nom")).subscribe(check);

        BasicDBObject query = new BasicDBObject("owner", p.getUniqueId().toString());
        reservations.find(query).subscribe(check2);

        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("count")) {
                p.sendMessage("§7[§eRestau§7] §6Il y a actuellement §b" + check.get().size() + " §6restaurants recensés");
                return true;
            } else if(args[0].equalsIgnoreCase("list")) {
                restauGUI.inv(p, "");
                return true;
            } else if(args[0].equalsIgnoreCase("reservations")) {
                restauReservations.inv(p);
                return true;
            }
        } else {
            p.sendMessage("§7[§eRestau§7] §c/restau help");
        }

        return false;
    }
}
