package scythe.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import scythe.Scythe;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserInfo extends Command {

    public UserInfo() {
        super.name = "user-info";
        super.help = "Get some information about a user";
        super.aliases = new String[]{"userinfo"};
        super.category = new Category("Members");
        super.cooldown = 5;
        super.arguments = "[name]";
    }

    @Override
    protected void execute(CommandEvent event) {
        System.out.println("UserInfo running!");

        if (event.getArgs().isEmpty()) {
            EmbedBuilder usage = new EmbedBuilder()
                    .setColor(0x5404fc)
                    .setTitle("Specify which user you want info on")
                    .setDescription("Usage: `" + Scythe.prefix + "userinfo [user]`");
            event.getChannel().sendMessageEmbeds(usage.build()).queue();

        } else {
            //Gets the member object of the user so we can use easil
            Member name;
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/dd/yyyy"); //Time formatter

            try{
                name = event.getMessage().getMentionedMembers().get(0);
                EmbedBuilder eb = new EmbedBuilder()
                        .setColor(0x5404fc)
                        .setThumbnail(name.getUser().getAvatarUrl())
                        .setAuthor("Information on " + name.getUser().getName(), name.getUser().getAvatarUrl(), name.getUser().getAvatarUrl())
                        .setDescription(name.getUser().getName() + " joined on *" + name.getTimeJoined().format(fmt) + "* :clock: \n created on *" + name.getTimeCreated().format(fmt) + "*")
                        .addField("Game:", displayGameInfo(name), true)
                        .addField("Status:", name.getOnlineStatus().toString(), true)
                        .addField("Roles:", getRolesAsString(name.getRoles()), true)
                        .addField("Nickname: ", name.getNickname() == null ? "No Nickname" : name.getNickname(), true);
                event.reply(eb.build());
                event.reply(event.getAuthor().getAsMention() + " there you go");

            } catch (IndexOutOfBoundsException ex) {
                System.out.println("Exception Occured");
                EmbedBuilder error = new EmbedBuilder().setColor(Color.RED)
                        .setTitle(":red_circle: You need to provide the name as a mention.")
                        .setDescription("`" + Scythe.prefix + "userinfo [user]` where [user] must be tagged \nie. `" + Scythe.prefix + "userinfo @Scythe`");
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessageEmbeds(error.build()).queue();

            }
        }
    }
    //Display game status
    private String displayGameInfo(Member name) {
        try {
            String game = "";
            for (int n = 0; n < name.getActivities().size(); n++)
                game += "\n - " + name.getActivities().get(n).getName();

            return "Playing: " + game;
        } catch (NullPointerException exx) {
            return "No Game Being Played";
        }
    }

    //Get roles for the user
    private String getRolesAsString(List rolesList) {
        String roles;
        if (!rolesList.isEmpty()) {
            Role tempRole = (Role) rolesList.get(0);
            roles = tempRole.getName();

            for (int i = 1; i < rolesList.size(); i++) {
                tempRole = (Role) rolesList.get(i);
                roles = roles + ", " + tempRole.getName();
            }

        } else
            roles = "No Roles";

        return roles;
    }

}
