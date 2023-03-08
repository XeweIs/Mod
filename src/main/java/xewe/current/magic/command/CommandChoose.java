package xewe.current.magic.command;

import xewe.current.magic.data.Element;
import xewe.current.magic.enums.ElementEnum;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class CommandChoose extends CommandBase {

    String[] elements = {ElementEnum.Air.toString(), ElementEnum.Fire.toString()};
    String[] options = {"choose"};

    @Nonnull
    @Override
    public String getName() {
        return "magic";
    }

    @Nonnull
    @Override
    public String getUsage(@Nonnull ICommandSender sender) {
        return "magic <option>:\nmagic choose <element> </player>";
    }


    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender.canUseCommand(1, this.getName());
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            throw new WrongUsageException("magic <option>" + Arrays.toString(options) + " ...");
        } else if (args.length == 1 && args[0].equals("choose")) {
            throw new WrongUsageException("magic choose <element>" + Arrays.toString(elements));
        }

        if (args[0].equals("choose")) {
            EntityPlayerMP player = args.length == 3 ? getPlayer(server, sender, args[2]) : getCommandSenderAsPlayer(sender);

            Element.setElement(player, ElementEnum.valueOf(args[1]));
            player.sendMessage(new TextComponentString("Элемент " + player.getName() + " соответствует " + Element.getElement(player)));
        }

    }

    @Nonnull
    @Override
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] arguments,
                                          BlockPos pos) {
        switch (arguments.length) {

            case 1:
                return getListOfStringsMatchingLastWord(arguments, options);
            case 2:
                if (arguments[0].equals("choose"))
                    return getListOfStringsMatchingLastWord(arguments, elements);
            case 3:
                if (arguments[0].equals("choose"))
                    return getListOfStringsMatchingLastWord(arguments, server.getOnlinePlayerNames());
                else break;
        }
        return super.getTabCompletions(server, sender, arguments, pos);
    }
}
