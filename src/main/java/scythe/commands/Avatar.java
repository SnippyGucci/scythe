package scythe.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import scythe.Scythe;

import java.awt.*;

public class Avatar extends Command {

    public Avatar () {
        super.name = "avatar";
        super.help = "Get [user]'s avatar";
        super.aliases = new String[]{"av"};
        super.cooldown = 10;
        super.arguments = "[user]";
    }

    protected void execute(CommandEvent event) {
        System.out.println("Avatar running!");

        if (event.getArgs().isEmpty()) {
            EmbedBuilder usage = new EmbedBuilder()
                    .setColor(0x5404fc)
                    .setTitle("Specify which user you want the avatar for")
                    .setDescription("Usage: `" + Scythe.prefix + "avatar [user]`");
            event.getChannel().sendMessageEmbeds(usage.build()).queue();

        } else {
            Member name;

            try {
                name = event.getMessage().getMentionedMembers().get(0);
                EmbedBuilder eb = new EmbedBuilder()
                        .setColor(0x5404fc)
                        .setTitle(name.getUser().getName() + "'s Avatar:")
                        .setImage(name.getUser().getAvatarUrl());
                event.reply(eb.build());
                event.reply(event.getAuthor().getAsMention() + " there you go");

            } catch (IndexOutOfBoundsException ex) {
                System.out.println("Exception Occured");
                EmbedBuilder error = new EmbedBuilder().setColor(Color.RED)
                        .setTitle(":red_circle: You need to provide the name as a mention.")
                        .setDescription("`" + Scythe.prefix + "avatar [user]` where [user] must be tagged \nie. `" + Scythe.prefix + "avatar @Scythe`");
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessageEmbeds(error.build()).queue();

            }
        }
    }
}
