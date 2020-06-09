package com.etysoft.townywars;

import com.palmergames.bukkit.towny.TownyMessaging;
import com.palmergames.bukkit.towny.event.PreDeleteTownEvent;
import com.palmergames.bukkit.towny.event.TownAddResidentEvent;
import com.palmergames.bukkit.towny.event.TownPreRenameEvent;
import com.palmergames.bukkit.towny.event.TownRemoveResidentEvent;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class TWListener implements Listener {

    @EventHandler
    public void onjoin(PlayerJoinEvent e)
    {
        //e.getPlayer().sendMessage("пошёл нахуй!");
    }

    @EventHandler
    public void onResidentLeave(TownRemoveResidentEvent event) {
      if(WarManager.getInstance().isInWar(event.getTown())) {
          War w = WarManager.getInstance().getTownWar(event.getTown());
          if (event.getTown() == w.getJertva())
          {
              w.minusJ();

          }
          else
          {
              w.minusA();
          }

      }


    }


    @EventHandler
    public void rename(TownPreRenameEvent n)
    {



    }

    @EventHandler
    public void Delete(PreDeleteTownEvent n)
    {

        if (!WarManager.getInstance().isNeutral(n.getTown())) {
        WarManager.getInstance().setNeutrality(false, n.getTown());
        }
        if(TownyWars.instance.getConfig().getBoolean("trfeatures"))
        {
            TownyMessaging.sendGlobalMessage(fun.cstring(TownyWars.instance.getConfig().getString("bye-bye").replace("%s", n.getTown().getName())));
        }

    }

    @EventHandler
    public void onResidentAdd(TownAddResidentEvent event) {
        if(WarManager.getInstance().isInWar(event.getTown())) {
            War w = WarManager.getInstance().getTownWar(event.getTown());
            if (event.getTown() == w.getJertva())
            {
                w.minusA();

            }
            else
            {
                w.minusJ();
            }

        }


    }

    @EventHandler
    public void kill(PlayerDeathEvent e)
    {
        Player plr = e.getEntity();

        EntityDamageEvent edc = e.getEntity().getLastDamageCause();
        if (!(edc instanceof EntityDamageByEntityEvent)) {
            return;
        }
        EntityDamageByEntityEvent edbee = (EntityDamageByEntityEvent) edc;
        if (!(edbee.getDamager() instanceof Player)) {
            return;
        }
        Player attacker = (Player) edbee.getDamager();
        Player victim = (Player) edbee.getEntity();
        try {

            Resident r1 =   com.palmergames.bukkit.towny.TownyUniverse.getInstance().getDataSource().getResident(attacker.getName());
            Resident r2 =   com.palmergames.bukkit.towny.TownyUniverse.getInstance().getDataSource().getResident(victim.getName());

            boolean isr1 = WarManager.instance.isInWar(r1.getTown());

           War war = WarManager.instance.getTownWar(r1.getTown());
           if(war != null)
           {
               if(war.hasTown(r2.getTown()))
               {
                   Town tv = war.minus(r2.getTown());
                   if(tv != null)
                   {
                       int nmessage = TownyWars.instance.getConfig().getInt("public-announce-warend");
                       if(nmessage == 2)
                       {
                           Bukkit.broadcastMessage(fun.cstring(TownyWars.instance.getConfig().getString("msg-end").replace("%s", tv.getName()).replace("%j", r2.getTown().getName())));
                       }
                       else
                       {
                           TownyMessaging.sendTownMessagePrefixed(r1.getTown(), fun.cstring(TownyWars.instance.getConfig().getString("msg-end").replace("%s", tv.getName()).replace("%j", r2.getTown().getName())));
                           TownyMessaging.sendTownMessagePrefixed(r2.getTown(), fun.cstring(TownyWars.instance.getConfig().getString("msg-end").replace("%s", tv.getName()).replace("%j", r2.getTown().getName())));
                       }

                       WarManager.instance.end(war, true);

                   }
                   else
                   {
                       victim.sendMessage(fun.cstring(TownyWars.instance.getConfig().getString("msg-points").replace("%s", war.getAPoints() +"").replace("%k", war.getJPoints()+"")));
                       attacker.sendMessage(fun.cstring(TownyWars.instance.getConfig().getString("msg-points").replace("%s", war.getAPoints() +"").replace("%k", war.getJPoints()+"")));
                   }

               }
           }
           else
           {
               //no war
           }

        } catch (NotRegisteredException ex) {
            ex.printStackTrace();
        }

    }
}
