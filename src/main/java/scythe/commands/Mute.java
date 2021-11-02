package scythe.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import scythe.Scythe;

import java.awt.*;
import java.util.List;
import java.util.Objects;

public class Mute extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String reason = "";
        Member mutee;
        //Role muteeRole = findRole(event.getMessage().getMentionedMembers().get(0), "Muted"); //TODO make role if muted role does not already exist
        Role muteeRole = event.getGuild().getRoleById("846780954386759701"); //admin-er backdoor

        if (muteeRole == null) {
            event.getGuild().createRole().setName("Muted").setPermissions(Permission.MESSAGE_READ).queue();
            event.getChannel().createPermissionOverride(Objects.requireNonNull(event.getMember())).setDeny(Permission.MESSAGE_WRITE).queue();
            muteeRole = findRole(event.getMessage().getMentionedMembers().get(0), "Muted");
        }

        if (args[0].equalsIgnoreCase(Scythe.prefix + "mute")) {
            System.out.println("Mute running!");

            if (args.length == 1) {
                EmbedBuilder usage = new EmbedBuilder()
                        .setColor(0x5404fc)
                        .setTitle("Specify who you want to mute")
                        .setDescription("Usage: `" + Scythe.prefix + "mute [user] [reason]`");
                event.getChannel().sendMessageEmbeds(usage.build()).queue();

            } else {
                mutee = event.getMessage().getMentionedMembers().get(0);

                if (args.length == 2) {
                    reason += "No reason given.";
                    muter(event, reason, mutee, muteeRole);
                } else {
                    for (int i = 2; i < args.length; i++)
                        reason += args[i] + " ";
                    muter(event, reason, mutee, muteeRole);

                }

            }

        } else if (args[0].equalsIgnoreCase(Scythe.prefix + "unmute")) {
            System.out.println("Unmute running!");

            if (args.length == 1) {
                EmbedBuilder usage = new EmbedBuilder()
                        .setColor(0x5404fc)
                        .setTitle("Specify who you want to unmute")
                        .setDescription("Usage: `" + Scythe.prefix + "unmute [user]`*");
                event.getChannel().sendMessage(usage.build()).queue();

            } else if (args.length == 2) {
                mutee = event.getMessage().getMentionedMembers().get(0);
                unmuter(event, mutee, muteeRole);
            }

        }
    }

    public static Role findRole(Member member, String name) {
        List<Role> roles = member.getGuild().getRoles();
        return roles.stream()
                .filter(role -> role.getName().equals(name)) // filter by role name
                .findFirst() // take first result
                .orElse(null); // else return null
    }

    public void muter(GuildMessageReceivedEvent event, String reason, Member mutee, Role muteeRole) {
        if (!mutee.getRoles().contains(muteeRole)) {
            event.getMessage().delete().queue();

            assert muteeRole != null;
            event.getGuild().addRoleToMember(mutee, muteeRole).queue();

            EmbedBuilder success = new EmbedBuilder()
                    .setColor(Color.GREEN)
                    .setDescription(":white_check_mark: ***" + mutee.getEffectiveName() + " was muted ***| " + reason);
            event.getChannel().sendMessage(success.build()).queue();
        } else {
            EmbedBuilder error = new EmbedBuilder()
                    .setColor(Color.RED)
                    .setTitle(":red_circle: Cannot mute an already muted member!");
            event.getChannel().sendMessage(error.build()).queue();
        }
    }

    public void unmuter(GuildMessageReceivedEvent event, Member mutee, Role muteeRole) {
        if (mutee.getRoles().contains(muteeRole)) {
            event.getMessage().delete().queue();

            assert muteeRole != null;
            event.getGuild().removeRoleFromMember(mutee, muteeRole).queue();

            EmbedBuilder success = new EmbedBuilder()
                    .setColor(Color.GREEN)
                    .setDescription(":white_check_mark: ***" + mutee.getEffectiveName() + " was unmuted***");
            event.getChannel().sendMessage(success.build()).queue();
        } else {
            EmbedBuilder error = new EmbedBuilder()
                    .setColor(Color.RED)
                    .setTitle(":red_circle: Cannot unmute an already unmuted member!");
            event.getChannel().sendMessage(error.build()).queue();
        }
    }

}
