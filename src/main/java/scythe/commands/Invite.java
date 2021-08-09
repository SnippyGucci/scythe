package scythe.commands;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import scythe.Scythe;

public class Invite extends ListenerAdapter {
	public static String link;

	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		if (args[0].equalsIgnoreCase(Scythe.prefix + "invite")) {
			link = event.getChannel().createInvite().complete().getUrl();

			System.out.println("Invite running!");
			//String serverName = event.getGuild()
			
			if (args.length < 2) {
				EmbedBuilder usage = new EmbedBuilder();
				usage.setColor(0x5404fc);
				usage.setTitle("Specify what you want invite to do");
				usage.setDescription("Usage: `" + Scythe.prefix + "invite [get/scythe/set]`");
				usage.setFooter("**Some commands are reserved for administrators only!**");
				
				event.getChannel().sendMessage(usage.build()).queue();
			} else if (args[1].equalsIgnoreCase("get")) {
				event.getChannel().sendTyping().queue();
				event.getChannel().sendMessage("Here is the link: " + link + ". Send it to your friends!").queue();
				
			} else if (args[1].equalsIgnoreCase("scythe")) {
				event.getChannel().sendTyping().queue();
				event.getChannel().sendMessage("Here is the link: https://discord.gg/UH4C9MyZ9E. Send it to your friends!").queue();
				
			} /*else if (args[1].equalsIgnoreCase("set") && event.getAuthor().getAsMention()) {
				EmbedBuilder propSet = new EmbedBuilder();
				System.out.println(link);
				
				if (!args[2].isBlank()) {
					link = args[2];
					propSet.setColor(Color.GREEN);
					propSet.setTitle("Invite Link Set!");
					propSet.setDescription(link);
					event.getChannel().sendMessage(propSet.build()).queue();
					System.out.println(link);
					
				} else {

					System.out.println("\n\nhi");
					event.getChannel().sendTyping().queue();
					propSet.setColor(Color.RED);
					propSet.setTitle(":red_circle: Please set a link!");
					propSet.setDescription("Please type `>invite set [link]`");
					event.getChannel().sendMessage(propSet.build()).queue();
				}
				
			}*/ else {
				EmbedBuilder error = new EmbedBuilder();error.setColor(Color.RED);
				error.setTitle(":red_circle: Unrecognized command!");
				error.setDescription("Usage: `" + Scythe.prefix + "invite [get/scythe/set]`");
				event.getChannel().sendMessage(error.build()).queue();
				
			}
		}
	
	}

}
