package scythe.commands;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import scythe.Scythe;

import org.slf4j.*;

public class Purge extends ListenerAdapter {
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		if (args[0].equalsIgnoreCase(Scythe.prefix + "purge")) {
			System.out.println("Purge running!");
			
			if (args.length < 2) {
				EmbedBuilder usage = new EmbedBuilder()
					.setColor(0x5404fc)
					.setTitle("Specify amount to delete")
					.setDescription("Usage: `" + Scythe.prefix + "purge [# of messages]`\n*Limited to 99 messages!*");
				event.getChannel().sendMessage(usage.build()).queue();
			}
			else
				messageDeleter(event, args);
		}
		
	}
	
	public void messageDeleter(GuildMessageReceivedEvent event, String[] args) {
		List<Message> messages = null;
		
		try {
			String channel = new String();
			channel = channel + event.getChannel();
			String idOnly = channel.replaceAll("[^0-9]", "");

			event.getMessage().delete().queue();

			EmbedBuilder success = new EmbedBuilder()
					.setColor(Color.GREEN)
					.setTitle(":white_check_mark: Successfully deleted")
					.setDescription("Cleared " + Integer.parseInt(args[1]) + " messages from <#" + idOnly + ">.");

			if (Integer.parseInt(args[1]) <= 100) {
				event.getChannel().deleteMessages(messageCompiler(event, args, messages)).queue();
				event.getChannel().sendMessage(success.build()).queue(m -> {
					long messageId = m.getIdLong();
					m.getChannel().deleteMessageById(messageId).queueAfter(10, TimeUnit.SECONDS); //deletes success message after 10s delay
				});

			} else if (Integer.parseInt(args[1]) < 1000) {
				for (int i = 0; i < (Integer.parseInt(args[1]) + 1)/100; i++)
					event.getChannel().deleteMessages(messageCompiler(event, args, messages)).queue();
				System.out.println("does run");
				event.getChannel().sendMessage(success.build()).queue(m -> {
					long messageId = m.getIdLong();
					m.getChannel().deleteMessageById(messageId).queueAfter(10, TimeUnit.SECONDS); //deletes success message after 10s delay
				});

			}
			
		} catch (IllegalArgumentException e) {
			EmbedBuilder error = new EmbedBuilder();
			if (Integer.parseInt(args[1]) >= 1000 || Integer.parseInt(args[1]) <= 1) {
				error.setColor(Color.RED)
					.setTitle(":red_circle: Too many messages selected!")
					.setDescription("`[# of messages]` must be between 2-999.");
				event.getChannel().sendMessage(error.build()).queue();

			} else {
				error.setColor(Color.RED)
					.setTitle(":red_circle: Too many messages selected!")
					.setDescription("Cannot delete messages older than 2 weeks.");
				event.getChannel().sendMessage(error.build()).queue();

			}
		}
	}
	
	public List<Message> messageCompiler(GuildMessageReceivedEvent event, String[] args, List<Message> msgs) {
		if (args.length == 2) {
			if (Integer.parseInt(args[1]) <= 100)
				msgs = event.getChannel().getHistory().retrievePast(Integer.parseInt(args[1])).complete();
			else if (Integer.parseInt(args[1]) < 1000)
				msgs = event.getChannel().getHistory().retrievePast((Integer.parseInt(args[1]) + 1)/((Integer.parseInt(args[1]) + 1)/100)).complete();
		}
		/*else if (args.length == 3) {
			Member member = event.getMessage().getMentionedMembers().get(0);
			if (((Message) event.getAuthor()).getChannel().getHistory().retrievePast(Integer.parseInt(args[1]) + 1).complete() == member) {
				msgs = event.getChannel().getHistory().retrievePast(Integer.parseInt(args[1]) + 1).complete();
			}
		}*/
		
		return msgs;
	}
	
}
