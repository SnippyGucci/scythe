package scythe.autoResponder;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import scythe.commands.StfuOnOff;

public class Stfu extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        for (int nameChecker = 0; nameChecker < StfuOnOff.memberArrayList.size(); nameChecker++) {
            Member markedName = StfuOnOff.memberArrayList.get(nameChecker);

            System.out.println("getAuthor: " + event.getAuthor().getIdLong() + " markedName: " + markedName.getIdLong() + " server: " + markedName.getGuild());

            if (event.getAuthor().getIdLong() == markedName.getIdLong() && event.getGuild() == markedName.getGuild()) {
                String markedMessage = event.getMessage().toString();
                String returnMessage = "*";
                int authorNameLength = event.getAuthor().getName().length() + 6;
                int messageIdLength = (int) (Math.log10(event.getMessageIdLong()) + 3);

                for (int i = 2 + authorNameLength, len = markedMessage.length() - messageIdLength; i < len; i++) {
                    char ch = markedMessage.charAt(i);

                    if (i % 2 == 0)
                        returnMessage += Character.toLowerCase(ch);
                    else
                        returnMessage += Character.toUpperCase(ch);
                }
                returnMessage += "* **shut up** <@" + event.getAuthor().getIdLong() + "> :)";

                event.getChannel().sendMessage(returnMessage).complete();
            }
        }
    }
}
