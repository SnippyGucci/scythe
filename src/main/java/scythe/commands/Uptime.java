package scythe.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import scythe.Scythe;

public class Uptime extends Command {

    public Uptime () {
        super.name = "uptime";
        super.help = "Scythe Uptime";
    }

    protected void execute(CommandEvent event) {
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(0x5404fc)
                .setTitle(upstring());
        event.reply(eb.build());
    }

    public static String upstring() {
        int seconds = (int) ((System.currentTimeMillis() - Scythe.enableUptime) / 1000) % 60 ;
        int minutes = (int) (((System.currentTimeMillis() - Scythe.enableUptime) / (1000*60)) % 60);
        int hours = (int) (((System.currentTimeMillis() - Scythe.enableUptime) / (1000*60*60)) % 24);

        return hours + " hours, " + minutes + " minutes, " + seconds + " seconds";
    }

}
