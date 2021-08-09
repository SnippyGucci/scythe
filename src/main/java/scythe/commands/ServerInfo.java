package scythe.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.Arrays;

public class ServerInfo extends Command {

    public ServerInfo(){
        this.name = "serverinfo";
        this.aliases = new String[]{"server", "guildinfo"};
        this.help = "Gives information about the server.";
    }

    @Override
    protected void execute(CommandEvent event) {
        //Makes an array that will hold all of the members
        String[] test = new String[event.getGuild().getMembers().size()];
        String description = "";
        for(int i = 0; i < event.getGuild().getMembers().size(); i++)
            test[i] = event.getGuild().getMembers().get(i).getEffectiveName();

        if (event.getGuild().getMembers().size() <= 100)
            description += "**Members:** \n" + Arrays.toString(test) + "\n\n **Invite link:** \n" + Invite.link;
        else
            description += "**Invite link:** \n" + Invite.link;

        EmbedBuilder eb = new EmbedBuilder()
                .setColor(0x5404fc)
                .setAuthor(event.getGuild().getName())
                .setThumbnail(event.getGuild().getIconUrl())
                .addField("Server Owner:", event.getGuild().getOwner().getEffectiveName(), true)
                .addField("Member Count:", Integer.toString(event.getGuild().getMembers().toArray().length), true)
                .setDescription(description);

        event.getChannel().sendMessage(eb.build()).queue();
        event.getChannel().sendMessage(event.getAuthor().getAsMention()).queue();
    }
}