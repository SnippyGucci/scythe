package scythe.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import scythe.Scythe;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Spam extends Command {
    public Spam () {
        super.name = "spam";
        super.help = "Spam a message";
        super.category = new Category("Misc");
        super.cooldown = 15;
        super.arguments = "[amount] [args]";
    }

    @Override
    protected void execute(CommandEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        System.out.println("Spam running");

        if (args.length < 2) {
            EmbedBuilder usage = new EmbedBuilder()
                    .setColor(0x5404fc)
                    .setTitle("Specify what you want to spam")
                    .setDescription("Usage: `" + Scythe.prefix + "spam [# of messages] [message]`\n*Limited to 99 messages!*");
            event.getChannel().sendMessageEmbeds(usage.build()).queue();

        } else {
            if (Integer.parseInt(args[1]) <= 0 || Integer.parseInt(args[1]) > 99) {
                EmbedBuilder error = new EmbedBuilder();error.setColor(Color.RED)
                        .setTitle(":red_circle: Invalid Amount")
                        .setDescription("Amount of messages must be from 1-99");
                event.getChannel().sendMessageEmbeds(error.build()).queue();
            } else {
                for (int i = 0; i < Integer.parseInt(args[1]); i++) {
                    String spamOutput = "";
                    for (int x = 0; args.length - 2 > x; x++)
                        spamOutput += args[x + 2] + " ";

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(spamOutput).queueAfter(500, TimeUnit.MILLISECONDS);

                    System.out.println("spamming >:)");
                }

            }

        }

    }

}
