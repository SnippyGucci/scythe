package scythe.autoResponder;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.concurrent.TimeUnit;

public class Ping extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase("ping") && args.length == 1) {
            System.out.println("Pinging " + event.getAuthor());
            event.getChannel().sendMessage("Pong! " + event.getAuthor().getAsMention()).queue(m -> {
                long messageId = m.getIdLong();
                m.getChannel().editMessageById(messageId, "Pong! Round trip: **" + event.getJDA().getGatewayPing() + " ms** " + event.getAuthor().getAsMention()).queueAfter(1, TimeUnit.SECONDS);
            });
        }

    }

}
