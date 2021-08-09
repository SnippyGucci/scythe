package scythe.commands;

import java.awt.Color;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import scythe.Scythe;

public class Calculator extends ListenerAdapter {
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		if (args[0].equalsIgnoreCase(Scythe.prefix + "calc")) {
			System.out.println("Calculator running!");
			
			double result = 0;
			
			if (args.length == 1) { 
				EmbedBuilder usage = new EmbedBuilder()
					.setColor(0x5404fc)
					.setTitle("Add, Subtract, Multiply, Divide, or Modulate")
					.setDescription("Usage: `" + Scythe.prefix + "calc [add/subt/mult/divd/mod] [#1] [#2] ... [#n]`");
				event.getChannel().sendMessage(usage.build()).queue();
				
			}

			EmbedBuilder error = new EmbedBuilder()
				.setColor(Color.RED)
				.setTitle(":red_circle: Parameters not set!")
				.setDescription("Usage: `" + Scythe.prefix + "calc " + args[1] + " [#1] [#2] ... [#n]`");

			if (args.length < 4 && (args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("subt") || args[1].equalsIgnoreCase("mult") || args[1].equalsIgnoreCase("divd") || args[1].equalsIgnoreCase("mod")))
				event.getChannel().sendMessage(error.build()).queue();
			else {
				if (args[1].equalsIgnoreCase("add")) {
					String inArgsOutput = "";
				
					for (int numOfArgs = 2; numOfArgs < args.length; numOfArgs++) {
						if (numOfArgs == 2)
							result = Double.parseDouble(args[2]);
						else
							result += Double.parseDouble(args[numOfArgs]);
						
						event.getChannel().sendTyping().queue();
						
						inArgsOutput += args[numOfArgs];
						if (numOfArgs + 1 < args.length)
							inArgsOutput += " + ";
						
					}
					event.getChannel().sendMessage(inArgsOutput + " = " + "**" + result + "**").queueAfter(500, TimeUnit.MILLISECONDS);

				} else if (args[1].equalsIgnoreCase("subt")) {
					String inArgsOutput = "";
				
					for (int numOfArgs = 2; numOfArgs < args.length; numOfArgs++) {
						if (numOfArgs == 2)
							result = Double.parseDouble(args[2]);
						else
							result -= Double.parseDouble(args[numOfArgs]);
						
						event.getChannel().sendTyping().queue();
						
						inArgsOutput += args[numOfArgs];
						if (numOfArgs + 1 < args.length)
							inArgsOutput += " - ";
						
					}
					event.getChannel().sendMessage(inArgsOutput + " = " + "**" + result + "**").queueAfter(500, TimeUnit.MILLISECONDS);
					
				} else if (args[1].equalsIgnoreCase("mult")) {
					String inArgsOutput = "";
				
					for (int numOfArgs = 2; numOfArgs < args.length; numOfArgs++) {
						if (numOfArgs == 2)
							result = Double.parseDouble(args[2]);
						else
							result *= Double.parseDouble(args[numOfArgs]);
						
						event.getChannel().sendTyping().queue();
						
						inArgsOutput += args[numOfArgs];
						if (numOfArgs + 1 < args.length)
							inArgsOutput += " × ";
						
					}
					event.getChannel().sendMessage(inArgsOutput + " = " + "**" + result + "**").queueAfter(500, TimeUnit.MILLISECONDS);
					
				} else if (args[1].equalsIgnoreCase("divd")) {
					String inArgsOutput = "";

					for (int numOfArgs = 2; numOfArgs < args.length; numOfArgs++) {
						if (numOfArgs == 2)
							result = Double.parseDouble(args[2]);
						else
							result /= Double.parseDouble(args[numOfArgs]);

						event.getChannel().sendTyping().queue();

						inArgsOutput += args[numOfArgs];
						if (numOfArgs + 1 < args.length)
							inArgsOutput += " ÷ ";

					}
					event.getChannel().sendMessage(inArgsOutput + " = " + "**" + result + "**").queueAfter(500, TimeUnit.MILLISECONDS);
					
				} else if (args[1].equalsIgnoreCase("mod")) {
					String inArgsOutput = "";

					for (int numOfArgs = 2; numOfArgs < args.length; numOfArgs++) {
						if (numOfArgs == 2)
							result = Double.parseDouble(args[2]);
						else
							result %= Double.parseDouble(args[numOfArgs]);

						event.getChannel().sendTyping().queue();

						inArgsOutput += args[numOfArgs];
						if (numOfArgs + 1 < args.length)
							inArgsOutput += " % ";

					}
					event.getChannel().sendMessage(inArgsOutput + " = " + "**" + result + "**").queueAfter(500, TimeUnit.MILLISECONDS);

				} else {
					error.setDescription("Usage: `" + Scythe.prefix + "calc [add/subt/mult/divd/mod] [#1] [#2] ... [#n]`");
					event.getChannel().sendMessage(error.build()).queue();
					
				}
			}
			
			if ((int) result == 69)
				event.getChannel().sendMessage(":smirk:").queue();			
			else if ((int) result == 420)
				event.getChannel().sendMessage(":herb:").queue();
				
			
		} 
	}
}
