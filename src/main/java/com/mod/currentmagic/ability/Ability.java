package com.mod.currentmagic.ability;

import com.mod.currentmagic.gui.TextGui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Collections;

public class Ability{
    protected enum Status {active, nonacitve}
    private Status statusAbility = Status.nonacitve;
    protected Status statusCoolDown = Status.nonacitve;
    protected int coolDown;
    protected String nameCoolDown;
    protected String colorCoolDown = "";

    protected EntityPlayer player;
    protected Vec3d playerVec, playerPosVec;
    protected float playerEyeHeight;
    private short period, maxPeriod;
    protected short repeat, maxRepeat;

    protected void setPeriod(short period){
        this.period = period;
        this.maxPeriod = period;
    }
    protected void setRepeat(short repeat){
        this.repeat = repeat;
        this.maxRepeat = repeat;
    }
    protected void setCoolDown(int coolDown){
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
                TextGui.coolDownText = new ArrayList<>();
                return;
            }

            coolDown--;
            String pretext = colorCoolDown+"§m"+ nameCoolDown + " " + ((coolDown / 160) + 2) + "§r";
            String initext = colorCoolDown+"§m"+ nameCoolDown + " " + ((coolDown / 160) + 1) + "§r";

            //Данная проверка даёт знать когда нам обновлять текст в актбаре,
            //а когда его стоит и вовсе удалить, а за одно завершить этот процесс.
            if(!TextGui.coolDownText.contains(initext)) {
                Collections.replaceAll(TextGui.coolDownText, pretext, initext);
            }
            if (coolDown == 0) {
                TextGui.coolDownText.remove(initext);
                statusCoolDown = Status.nonacitve;
                MinecraftForge.EVENT_BUS.unregister(this);
            }
        }
    }

    /** Активация способности и установка ей начальных свойств
     * @param name Установка имени, отображаемого при кулдауне
     * @param color Установка цвета для кулдауна
     * @param coolDown Установка длительности кулдауна
     * @param period Установка частоты обновления способности, то есть частота запуска onUpdate
     * @param repeat Количество повторов, то есть количество запусков onUpdate
     */
    public void start(EntityPlayer player, String name, String color, int coolDown, short period, short repeat){
        if(statusCoolDown.equals(Status.active)) return;

        MinecraftForge.EVENT_BUS.register(this);
        statusAbility = Status.active; //Указываем, что способность активирована
        statusCoolDown = Status.active; //Накладываем кулдаун
        this.player = player;
        this.playerVec = player.getLookVec();
        this.playerEyeHeight = player.eyeHeight;
        this.playerPosVec = player.getPositionVector();
        this.nameCoolDown = name;
        this.colorCoolDown = color;
        this.setCoolDown(coolDown);
        this.setPeriod(period);
        this.setRepeat(repeat);

        TextGui.coolDownText.add(colorCoolDown+"§m"+ nameCoolDown + " " + ((coolDown / 160) + 1) + "§r");
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
