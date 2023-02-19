package com.mod.currentmagic.ability;

import com.mod.currentmagic.gui.CoolDownText;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;

public class Ability{
    enum Status {active, nonacitve}
    Status statusAbility = Status.nonacitve;
    Status statusCoolDown = Status.nonacitve;
    public int coolDown;
    public String name;
    public EntityPlayer player;
    public Vec3d playerVec, playerPosVec;
    public float playerEyeHeight;
    private short period, maxPeriod;
    public short repeat, maxRepeat;

    protected void setPeriod(short period){
        //Если кулдаун на данную способность наложен
        if(statusCoolDown.equals(Status.active)) return;

        this.period = period;
        this.maxPeriod = period;
    }
    protected void setRepeat(short repeat){
        if(statusCoolDown.equals(Status.active)) return;

        this.repeat = repeat;
        this.maxRepeat = repeat;
    }
    protected void setCoolDown(int coolDown){
        if(statusCoolDown.equals(Status.active)) return;

        this.coolDown = (coolDown * 160)-1;
    }

    @SubscribeEvent
    public void onTickAbility(TickEvent event) {
        if(event.side.isServer()) return;

        //Если активен статус способности, то есть если её юзнули.
        if (statusAbility.equals(Status.active)) {
            period--; //Каждый тик уменьшаем период. Период отвечает за частоту обновления (запуска onUpdate)активной способности.

            if (period == 0) {
                //Если апдейт прошёл успешно
                if(onUpdate()){
                    period = maxPeriod; //Устанавливаем изначальное значение периода и так по кругу.
                    repeat--; //Уменьшаем количество повторений.

                    //Вследствие этого мы можем контролировать сколько раз нам обновлять активную способность и когда завершать этот процесс.
                    if (repeat == 0) {
                        onExit();
                    }
                }else{
                    //Данным методом отменяем всю способность.
                    onExit();
                }
            }
        }

        //Если наложили кулдаун...
        if(statusCoolDown.equals(Status.active)){

            //Если вдруг игрок умрёт во время кулдауна, он у него сбросится.
            if(player.isDead){
                statusCoolDown = Status.nonacitve;
                CoolDownText.text = new ArrayList<>();
                return;
            }

            coolDown--;
            String pretext = "§m" + name + " " + ((coolDown / 160) + 2) + "§r";
            String initext = "§m" + name + " " + ((coolDown / 160) + 1) + "§r";

            //Данная проверка даёт знать когда нам обновлять текст в актбаре, а когда его стоит и вовсе удалить, а за одно завершить этот процесс.
            if(!CoolDownText.text.contains(initext)) {
                CoolDownText.text.remove(pretext);
                CoolDownText.text.add(initext);
            }
            if (coolDown == 0) {
                CoolDownText.text.remove(initext);
                statusCoolDown = Status.nonacitve;
                MinecraftForge.EVENT_BUS.unregister(this);
            }
        }
    }

    //Начало способности
    public void start(EntityPlayer player){
        if(statusCoolDown.equals(Status.active)) return;

        MinecraftForge.EVENT_BUS.register(this);
        statusAbility = Status.active; //Указываем, что способность активирована
        statusCoolDown = Status.active; //Накладываем кулдаун
        this.player = player;
        this.playerVec = player.getLookVec();
        this.playerEyeHeight = player.eyeHeight;
        this.playerPosVec = player.getPositionVector();
    }

    //Обновление способности
    protected boolean onUpdate(){
        return false;
    }

    //Завершение способности
    protected void onExit(){
        statusAbility = Status.nonacitve;
    }
}
