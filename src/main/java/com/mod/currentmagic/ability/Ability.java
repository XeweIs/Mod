package com.mod.currentmagic.ability;

import com.mod.currentmagic.gui.CoolDownText;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
public class Ability{
    enum Status {active, nonacitve}
    Status statusAbility = Status.nonacitve;
    Status statusCoolDown = Status.nonacitve;
    public int cooldown, maxcooldown;
    public String name;
    public EntityPlayer player;
    private short period, maxperiod, repeat;

    protected void setPeriod(short period){
        //Если кулдаун на данную способность наложен
        if(statusCoolDown.equals(Status.active)) return;

        this.period = period;
        this.maxperiod = period;
    }
    protected void setRepeat(short repeat){
        if(statusCoolDown.equals(Status.active)) return;

        this.repeat = repeat;
    }
    protected void setCooldown(int cooldown){
        if(statusCoolDown.equals(Status.active)) return;

        this.cooldown = cooldown * 40;
        this.maxcooldown = cooldown * 40;
    }
    @SubscribeEvent
    public void onTick(TickEvent.WorldTickEvent event) {
        //Если активен статус способности, то есть если её юзнули.
        if (statusAbility.equals(Status.active)) {
            period--; //Каждый тик уменьшаем период. Период отвечает за частоту обновления (запуска onUpdate)активной способности.

            if (period == 0) {
                onUpdate();
                period = maxperiod; //Устанавливаем изначальное значение периода и так по кругу.
                repeat--; //Уменьшаем количество повторений.

                //Вследствие этого мы можем контролировать сколько раз нам обновлять активную способность и когда завершать этот процесс.
                if (repeat == 0) {
                    statusAbility = Status.nonacitve;
                }
            }
        }

        //Если наложили кулдаун...
        if(statusCoolDown.equals(Status.active)){
            cooldown--;

            //Данная проверка предотвращает забитость коллекции одним именем кулдауна
            if(!CoolDownText.text.contains(name +" "+ (cooldown / 40))) {
                CoolDownText.text.remove(name +" "+ ((cooldown / 40) + 1)); //Убираем отображение кулдауна предыдущей секунды
                CoolDownText.text.add(name +" "+ (cooldown / 40)); //Добавляем отображение текущего кулдауна в актбар
            }

            //Ставим всё на место при завершении кулдауна.
            if(cooldown == 0){
                CoolDownText.text.remove(name +" "+ 0);
                statusCoolDown = Status.nonacitve;
            }
        }
    }
    public void start(EntityPlayer player){
        if(statusCoolDown.equals(Status.active)) return;

        statusAbility = Status.active; //Указываем, что способность активирована
        statusCoolDown = Status.active; //Накладываем кулдаун
        this.player = player;
    }
    protected void onUpdate(){
        //Здесь ничего не пишем, но оставляем этот метод, т.к. всё равно будем переопределять его в классе-наследнике
    }
}
