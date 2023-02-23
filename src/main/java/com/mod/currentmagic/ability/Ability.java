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
    protected Status statusAbility = Status.nonacitve;
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
        this.coolDown = (coolDown * 40)+1;
    }

    /** Активация способности и установка ей начальных свойств
     * @param name Установка имени, отображаемого при кулдауне
     * @param color Установка цвета для кулдауна
     * @param coolDown Установка длительности кулдауна
     * @param period Установка частоты обновления способности, то есть частота запуска onUpdate
     * @param repeat Количество повторов, то есть количество запусков onUpdate
     */
    protected void start(EntityPlayer player, String name, String color, int coolDown, short period, short repeat){
        if(statusCoolDown.equals(Status.active) || statusAbility.equals(Status.active)) return;

        MinecraftForge.EVENT_BUS.register(this); //Регистрируем данную способность чтобы далее использовать ивенты внутри нее
        statusAbility = Status.active; //Указываем, что способность активирована
        this.player = player;
        this.playerVec = player.getLookVec();
        this.playerEyeHeight = player.eyeHeight;
        this.playerPosVec = player.getPositionVector();
        this.nameCoolDown = name;
        this.colorCoolDown = color;
        this.setCoolDown(coolDown);
        this.setPeriod(period);
        this.setRepeat(repeat);
    }

    @SubscribeEvent
    protected void onTickAbility(TickEvent.PlayerTickEvent event) {
        if(event.side.isServer()) return;

        //Если активен статус способности, то есть если её юзнули.
        if (statusAbility.equals(Status.active)) {

            //Если вдруг игрок умрёт во время использования способности, она отменится.
            if(player.isDead){
                onExit();
                return;
            }

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
                    onExit();
                }
            }
        }

        //Если наложили кулдаун...
        if(statusCoolDown.equals(Status.active)){

            coolDown--;
            String pretext = colorCoolDown+"§m"+ nameCoolDown + " " + ((coolDown / 40) + 2) + "§r";
            String initext = colorCoolDown+"§m"+ nameCoolDown + " " + ((coolDown / 40) + 1) + "§r";

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

    //Обновление способности
    protected boolean onUpdate(){
        return false;
    }

    //Завершение способности
    protected void onExit(){
        statusCoolDown = Status.active; //Накладываем кулдаун
        TextGui.coolDownText.add(colorCoolDown+"§m"+ nameCoolDown + " " + ((this.coolDown / 40)+1) + "§r");
        statusAbility = Status.nonacitve;
    }
}
