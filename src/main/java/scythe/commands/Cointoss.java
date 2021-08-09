package scythe.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.io.File;
import java.lang.Math;
import java.util.concurrent.TimeUnit;

public class Cointoss extends Command {
    public Cointoss () {
        super.name = "cointoss";
        super.help = "Toss a coin!";
        super.aliases = new String[]{"coinflip", "ct"};
        super.category = new Category("Misc");
        super.cooldown = 2;
        super.arguments = "[name]";
    }

    @Override
    protected void execute(CommandEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        System.out.println("Cointoss running");

        double flipResultDouble = Math.random();
        String flipResultString;
        File flipResultSource;

        EmbedBuilder result = new EmbedBuilder().setColor(Color.YELLOW);

        if (flipResultDouble < 0.4999165) {
            flipResultString = "Heads!";
            flipResultSource = new File("coin-pngs/heads.png");
            result.setTitle(flipResultString).setDescription("<@" + event.getAuthor().getIdLong() + ">").setImage("attachment://result.png");
        } else if (flipResultDouble >= 0.5000835) {
            flipResultString = "Tails!";
            flipResultSource = new File("coin-pngs/tails.png");
            result.setTitle(flipResultString).setDescription("<@" + event.getAuthor().getIdLong() + ">").setImage("attachment://result.png");
        } else {
            flipResultString = "Side :interrobang: (thats a 0.0167% chance btw :eyes:)";
            flipResultSource = new File("coin-pngs/side.png");
            result.setTitle(flipResultString).setDescription("<@" + event.getAuthor().getIdLong() + ">").setImage("attachment://result.png");
        }

        System.out.println("flipResult exists? " + flipResultSource.exists());

        EmbedBuilder tossing = new EmbedBuilder()
                .setColor(Color.YELLOW)
                .setTitle("Tossing Coin... :coin:")
                .setImage("https://media.discordapp.net/attachments/855514528531349504/857699160555913226/cointoss.gif");

        event.getChannel().sendMessageEmbeds(tossing.build()).queue(m -> {
            long messageId = m.getIdLong();
            m.getChannel().editMessageEmbedsById(messageId, result.build()).addFile(flipResultSource, "result.png").queueAfter(2, TimeUnit.SECONDS);
        });

    }
}
