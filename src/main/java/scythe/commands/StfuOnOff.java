package scythe.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import scythe.Scythe;

import java.awt.*;
import java.util.ArrayList;

public class StfuOnOff extends ListenerAdapter {
    public static Member name;
    public static User shutAuthor;
    public static ArrayList<Member> memberArrayList = new ArrayList<Member>();

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        boolean onlySnipsUwU = event.getAuthor().getName().equals("Snippy");

        if (args[0].equalsIgnoreCase(Scythe.prefix + "stfu") || args[0].equalsIgnoreCase(Scythe.prefix + "unstfu") ) {
            System.out.println("STFU running!");

            if (args.length != 2) {
                EmbedBuilder usage = new EmbedBuilder()
                        .setColor(0x5404fc)
                        .setTitle("Specify who you want to STFU")
                        .setDescription("Usage: `" + Scythe.prefix + "stfu [user]`\nUsage: `" + Scythe.prefix + "unstfu [user]`\nUsage: `" + Scythe.prefix + "stfu list`")
                        .setFooter("No, this does not mute them. >:)");
                event.getChannel().sendMessage(usage.build()).queue();

            } else if (args[0].equalsIgnoreCase(Scythe.prefix + "stfu"))
                shutUser(event, args);
            else if (memberArrayList.size() > 0 && (event.getAuthor() == shutAuthor || onlySnipsUwU))
                unShutUser(event);

        }

    }

    public void shutUser (GuildMessageReceivedEvent event, String[] args) {
        try {
            if (args[1].equalsIgnoreCase("list")) {
                String setMarked = "**Marked Members:** ";
                for (int i = 0; i < memberArrayList.size(); i++)
                    setMarked += "<@" + memberArrayList.get(i).getUser().getIdLong() + "> ";

                event.getChannel().sendMessage(setMarked).queue();
            } else {
                name = event.getMessage().getMentionedMembers().get(0);
                if (!name.getUser().getName().equalsIgnoreCase("Scythe")) {
                    memberArrayList.add(name);

                    System.out.println(name);
                    EmbedBuilder marked = new EmbedBuilder()
                            .setColor(Color.GREEN)
                            .setTitle(":white_check_mark: Marked someone to STFU :eyes:")
                            .setDescription("<@" + name.getId() + "> will be marked until `" + Scythe.prefix + "unstfu [user]` is used.");
                    event.getChannel().sendMessage(marked.build()).queue();

                    shutAuthor = event.getAuthor();
                } else
                    event.getChannel().sendMessage("**Cannot STFU this user!**").queue();
            }

        } catch (IndexOutOfBoundsException ex) {
            EmbedBuilder error = new EmbedBuilder()
                    .setColor(Color.RED)
                    .setTitle(":red_circle: You need to provide the name as a mention.")
                    .setDescription("Usage: `" + Scythe.prefix + "stfu [user]`");
            event.getChannel().sendMessage(error.build()).queue();

        }
    }

    public void unShutUser (GuildMessageReceivedEvent event) {
        try {
            name = event.getMessage().getMentionedMembers().get(0);
            memberArrayList.remove(name);

            System.out.println(name);
            EmbedBuilder marked = new EmbedBuilder()
                    .setColor(Color.GREEN)
                    .setTitle(":white_check_mark: Unmarked someone from STFU :upside_down:")
                    .setDescription("<@" + name.getId() + "> has been unmarked.");
            event.getChannel().sendMessage(marked.build()).queue();

        } catch (IndexOutOfBoundsException ex) {
            EmbedBuilder error = new EmbedBuilder()
                    .setColor(Color.RED)
                    .setTitle(":red_circle: You need to provide the name as a mention.")
                    .setDescription("Usage: `" + Scythe.prefix + "ustfu [user]`");
            event.getChannel().sendMessage(error.build()).queue();

        }
    }
}