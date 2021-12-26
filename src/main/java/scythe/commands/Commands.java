package scythe.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import scythe.Scythe;

import java.util.concurrent.TimeUnit;

public class Commands extends ListenerAdapter {
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		if (args[0].equalsIgnoreCase(Scythe.prefix + "commands") || args[0].equalsIgnoreCase(Scythe.prefix + "help")) {
			System.out.println("Commands running!");

			EmbedBuilder embInfo = new EmbedBuilder()
				.setTitle(Scythe.prefix + "Scythe Command List")
				.setDescription(descriptions())
				.setColor(0x5404fc)
				.setFooter("Created by Snippy#0757", "https://cdn.discordapp.com/avatars/287381178288177152/5f7bc1e678a47274156b7008189b664f.png?size=4096");
			
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessageEmbeds(embInfo.build()).queueAfter(1, TimeUnit.SECONDS);
			embInfo.clear();
		}
	}

	public String descriptions () {
		String description = "";
		description += "**To find out how to use a command, type the command to find proper usage syntax.**\n\n";
		description += ":point_right: `" + Scythe.prefix + "avatar` - sends an enlarged version of the mentioned member's avatar\n";
		description += ":point_right: `" + Scythe.prefix + "botinfo` - provides information on Scythe bot\n";
		description += ":point_right: `" + Scythe.prefix + "calc` - does addition, subtraction, multiplication, and division\n";
		description += ":point_right: `" + Scythe.prefix + "cointoss` - tosses a coin. Heads or Tails\n";
		description += ":point_right: `" + Scythe.prefix + "invite` - sends the invite link of the discord to the user\n";
		//description += ":point_right: `" + Scythe.prefix + "mute` - mutes user. *Role must have send messages off on desired channels!*\n";
		description += ":point_right: `" + Scythe.prefix + "purge` - deletes up to 100 messages up to 2 weeks old\n";
		description += ":point_right: `" + Scythe.prefix + "rockpaperscissors` - rock paper scissors! :rock: :newspaper: :scissors:\n";
		description += ":point_right: `" + Scythe.prefix + "serverinfo` - provides information on the server\n";
		description += ":point_right: `" + Scythe.prefix + "spam` - spams a message a certain amount of times\n";
		description += ":point_right: `" + Scythe.prefix + "stfu` - marks a user to be stfu :smiling_imp:\n";
		description += ":point_right: `" + Scythe.prefix + "uptime` - time Scythe has been online\n";
		description += ":point_right: `" + Scythe.prefix + "userinfo` - provides information on a user\n";
		description += "*and more to come!*";

		return description;
	}

}
