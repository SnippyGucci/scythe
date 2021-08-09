package scythe.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import scythe.Scythe;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class RockPaperScissors extends Command {
    public RockPaperScissors () {
        super.name = "RockPaperScissors";
        super.help = "Rock Paper Scissors!";
        super.aliases = new String[]{"rps"};
        super.category = new com.jagrosh.jdautilities.command.Command.Category("Misc");
        super.cooldown = 2;
        super.arguments = "[name]";
    }

    @Override
    protected void execute(CommandEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        System.out.println("RockPaperScissors running");

        if (args.length == 1) {
            EmbedBuilder usage = new EmbedBuilder()
                    .setColor(0x5404fc)
                    .setTitle("Specify what you want to choose")
                    .setDescription("Usage: `" + Scythe.prefix + "rockpaperscissors [rock/paper/scissors]`");
            event.getChannel().sendMessageEmbeds(usage.build()).queue();

        } else if (args.length == 2) {
            double rpsRand = Math.random();
            int rpsBotVal, rpsUserVal;
            String rpsTitle, rps, result;
            String presetUserWin = "<@" + event.getAuthor().getId() + "> won! :confetti_ball:";
            String presetUserLoss = "<@" + event.getAuthor().getId() + "> lost. :frowning:";


            //paper beats rock, rock beats scissors, scissors beat paper

            if (rpsRand < (1.0 / 3.0)) {
                rpsBotVal = 1;
                rpsTitle = "I chose Rock! :rock:";
                rps = "https://www.nicepng.com/png/full/6-61708_rock-rock-paper-scissors-clipart.png";
            } else if (rpsRand < (2.0 / 3.0)) {
                rpsBotVal = 2;
                rpsTitle = "I chose Paper! :newspaper:";
                rps = "https://www.pinclipart.com/picdir/big/51-511523_rock-paper-rock-paper-scissors-clipart-png-download.png";
            } else {
                rpsBotVal = 3;
                rpsTitle = "I chose Scissors! :scissors:";
                rps = "https://www.seekpng.com/png/full/111-1114370_rock-paper-scissors-rock-paper-scissors-clipart.png";
            }

            if (args[1].equalsIgnoreCase("rock") || args[1].equalsIgnoreCase("r"))
                rpsUserVal = 1;
            else if (args[1].equalsIgnoreCase("paper") || args[1].equalsIgnoreCase("p"))
                rpsUserVal = 2;
            else if (args[1].equalsIgnoreCase("scissors") || args[1].equalsIgnoreCase("s"))
                rpsUserVal = 3;
            else
                rpsUserVal = -10;


            if (rpsBotVal == rpsUserVal)
                result = "Tie";
            else if (rpsBotVal + rpsUserVal == 3) {
                if (rpsUserVal > rpsBotVal)
                    result = presetUserWin;
                else
                    result = presetUserLoss;

            } else if (rpsBotVal + rpsUserVal == 4) {
                if (rpsUserVal < rpsBotVal)
                    result = presetUserWin;
                else
                    result = presetUserLoss;

            } else if (rpsBotVal + rpsUserVal == 5) {
                if (rpsUserVal > rpsBotVal)
                    result = presetUserWin;
                else
                    result = presetUserLoss;

            } else
                result = "<@" + event.getAuthor().getId() + "> lost by default (invalid argument).";


            EmbedBuilder tossing = new EmbedBuilder()
                    .setColor(Color.YELLOW)
                    .setTitle(rpsTitle)
                    .setImage(rps);

            event.getChannel().sendMessageEmbeds(tossing.build()).queueAfter(1, TimeUnit.SECONDS);
            event.getChannel().sendMessage(result).queueAfter(1050, TimeUnit.MILLISECONDS);

        }

    }

}
