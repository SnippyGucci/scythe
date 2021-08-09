package scythe.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import scythe.Scythe;

import java.awt.*;

public class ChangePrefix extends Command {
    //private static String prefix;

    public ChangePrefix () {
        super.name = "prefix";
        super.aliases = new String[]{"changeprefix"};
        super.cooldown = 5;
        super.arguments = "[new prefix]";
    }

    @Override
    protected void execute(CommandEvent event) {
        System.out.println("changeprefix running!");

        if (event.getArgs().isEmpty()) {
            EmbedBuilder usage = new EmbedBuilder()
                    .setColor(0x5404fc)
                    .setTitle("Specify what you want to change your prefix to")
                    .setDescription("The prefix is currently: `" + Scythe.prefix + "`\nUsage: `" + Scythe.prefix + "prefix [set/reset] [prefix]`")
                    .setFooter("If resetting, do not enter a prefix!");
            event.getChannel().sendMessage(usage.build()).queue();

        } else {
            String[] args = event.getArgs().split("\\s+");
            EmbedBuilder success = new EmbedBuilder().setColor(Color.GREEN);

            //System.out.println(args[0].equalsIgnoreCase("set") && args[1].isEmpty());

            if (args[0].equalsIgnoreCase("reset")) {
                setPrefix(args);
                success.setTitle(":white_check_mark: Successfully reset prefix")
                        .setDescription("Prefix set to `>`");
                event.getChannel().sendMessage(success.build()).queue();

            } else if (args[0].equalsIgnoreCase("set")) {
                if (args[1].equals("")) { //for some reason this does not work TODO fix args[1] for setPrefix
                    System.out.println("helloooo?");
                    EmbedBuilder error = new EmbedBuilder()
                            .setColor(Color.RED)
                            .setTitle(":red_circle: Specify what you want to change your prefix to")
                            .setDescription("Usage: `" + Scythe.prefix + "prefix set [prefix]`");
                    event.getChannel().sendMessage(error.build()).queue();

                } else {
                    setPrefix(args);
                    success.setTitle(":white_check_mark: Successfully set new prefix")
                            .setDescription("Prefix set to `" + Scythe.prefix + "`");
                    event.getChannel().sendMessage(success.build()).queue();
                    Scythe.prefixIfElse();
                }

            }

        }

    }

    public void setPrefix (String[] args) {
        if (args[0].equalsIgnoreCase("set"))
            Scythe.prefix = args[1];
        else if (args[0].equalsIgnoreCase("reset"))
            Scythe.prefix = ">";

        Scythe.builder.setPrefix(Scythe.prefix);
    }

    public static String getPrefix () {
        return Scythe.prefix;
    }

}
