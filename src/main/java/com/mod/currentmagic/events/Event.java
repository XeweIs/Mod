package com.mod.currentmagic.events;

import com.mod.currentmagic.magic.AbilityMech;
import com.mod.currentmagic.magic.Element;
import com.mod.currentmagic.magic.elements.EARTH;
import com.mod.currentmagic.playercap.AbilityProvider;
import com.mod.currentmagic.playercap.IAbility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;


public class Event {

    public static int Z, X, C, V, time, cdlinefire, cdspurt;
    public static String extra = "";
    public static World world = null;

    @SubscribeEvent
    public void TickWorld(TickEvent.WorldTickEvent event){
        if(world != event.world) {
            world = event.world;
        }
    }

    @SubscribeEvent
    public void TickClient(TickEvent.ClientTickEvent event) {
            if (time >= 120) {
                Z = 0;
                X = 0;
                C = 0;
                V = 0;
                time = 0;
            } else if (Z + X + C + V != 0) {
                time++;
            }

            if (!AbilityMech.cdlf) {
                cdlinefire++;
                if (cdlinefire > 200) {
                    cdlinefire = 0;
                    AbilityMech.cdlf = true;
                }
            }

            if (!AbilityMech.cdsp) {
                cdspurt++;
                if (cdspurt >= 120) {
                    cdspurt = 0;
                    AbilityMech.cdsp = true;
                }
            }
    }

    @SubscribeEvent
    public void RightClickE(PlayerInteractEvent.RightClickEmpty event) {
        if(event.getEntity() instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer) event.getEntity();
            Combo(player);
        }
    }

    @SubscribeEvent
    public void RightClickB(PlayerInteractEvent.RightClickBlock event) {
        if(event.getEntity() instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer) event.getEntity();
            Combo(player);
        }
    }

    @SubscribeEvent
    public void RightClickEnt(PlayerInteractEvent.EntityInteractSpecific event) {
        if(event.getEntity() instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer) event.getEntity();
            Combo(player);
        }
    }

    @SubscribeEvent
    public void FailFly(LivingFallEvent event) {
        /*if(event.getEntity() instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer) event.getEntity();
            if(event.getDistance() >= 7){
                player.sendMessage(new TextComponentString("способность*"));
            }
        } */
    }

    public static void executeProcedure(EntityPlayer player) {
        new Object() {
            private int ticks = 0;
            private float waitTicks;

            public void start(int waitTicks) {
                this.waitTicks = waitTicks;
                MinecraftForge.EVENT_BUS.register(this);
            }

            @SubscribeEvent
            public void tick(TickEvent.ServerTickEvent event) {
                if (event.phase == TickEvent.Phase.END) {
                    this.ticks += 1;
                    if (this.ticks >= this.waitTicks)
                        run();
                }
            }
            private void run() {
                player.sendMessage(new TextComponentString("Лул"));
                MinecraftForge.EVENT_BUS.unregister(this);
            }
        }.start((int) 20);
    }

    public static void Combo(EntityPlayer player){
        if(Z == 1 && X+C+V == 0) {
            AbilityMech.LineFire(player);
            Z = 0;
        }else if(X == 1 && Z+C+V == 0) {
            AbilityMech.Spurt(player);
            X = 0;
        }else if(Z == 2 && X == 1 && C+V==0) {
            AbilityMech.RingsFire(player);
        }else if(X == 3 && V == 2 && Z+C == 0){
            AbilityMech.Und(player);
        }else{
            Z = 0; X = 0; C = 0; V = 0;
        }
    }

    /*
            IAbility ability = player.getCapability(AbilityProvider.ABILITY_CAP, null);
            assert ability != null;
            ability.setElement(Element.Fire());
            player.sendMessage(new TextComponentString("Теперь ваш элемент соответствует " + ability.getElement()));
     */

}
