package scythe;

import javax.security.auth.login.LoginException;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.command.GuildSettingsManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.dv8tion.jda.client.entities.Application;
import org.jetbrains.annotations.Nullable;
import scythe.autoResponder.Ping;
import scythe.autoResponder.Stfu;
import scythe.commands.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Scythe {
	public final JDA jda;
	public static String prefix;
	public static CommandClientBuilder builder = new CommandClientBuilder();
    public static double enableUptime;

    public Scythe() throws LoginException, FileNotFoundException {
        Scanner Token = new Scanner(new File("scythe-token.txt"));

        jda = JDABuilder.createDefault(Token.next())
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_PRESENCES)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .enableCache(CacheFlag.ONLINE_STATUS)
                .enableCache(CacheFlag.ACTIVITY)
                .enableCache(CacheFlag.CLIENT_STATUS)
                .build();

        prefixIfElse(); //meant for the Prefix command, but it doesn't work

        builder.setOwnerId("287381178288177152");
        builder.setPrefix(prefix);
        builder.setHelpWord("de74ujt5n55t516h5er14tdf61b64j651k"); // i don't want this, but jda utils insists :/
        builder.setActivity(Activity.competing("⠗⣐⠫⡷⠮⠺⢓⣤⢳⡒⠽⣬⣣⢮⡄⠙⣠⢃⣒⡑⣿⠀⡢⢕⠡⢊⡞⢼⠑⡰⣀⡣⡉⢘⡘⣒⡻⡀⠾⡺⢉⢏⣔⢓⡍⡝⠂⠜⢎⠬⣦⡬⢂⠟⡤⡷⡚⣖⠓⣷⡮⢅⣢⢵"));

        commandListeners(builder);
        DiscordRPC();

    }
    
    //commands
	private void commandListeners (CommandClientBuilder builder) {
        //made using normal jda
        jda.addEventListener(new Commands());
        jda.addEventListener(new Purge());
        jda.addEventListener(new Calculator());
        jda.addEventListener(new Invite());
        jda.addEventListener(new StfuOnOff());
        jda.addEventListener(new ConsoleText());
        jda.addEventListener(new Mute());

        //auto responders
        jda.addEventListener(new Ping());
        jda.addEventListener(new Stfu());


        //made using jda-utilities
        CommandClient client = builder.build();
        client.addCommand(new UserInfo());
        client.addCommand(new ServerInfo());
        client.addCommand(new Uptime());
        client.addCommand(new BotInfo());
        client.addCommand(new Cointoss());
        client.addCommand(new RockPaperScissors());
        client.addCommand(new Avatar());
        client.addCommand(new Spam());

        //client.addCommand(new ChangePrefix()); //TODO solve changePrefix for jda utilities. very bugged at the moment

        jda.addEventListener(client);

        /*TODO setup invite setter (needs to store set in offline file/database)
         * make a role reqs (must be xxx role to do yyy task)
         * make a change prefix class <----- WIP
         * unrecognized command*/
    }

    //prefix getter and setter
    public static void prefixIfElse() {
        if (ChangePrefix.getPrefix() == null)
            prefix = ">";
        else
            prefix = ChangePrefix.getPrefix();

        /*builder.setPrefixBuilder(event -> {
            if (event.isFromGuild()) {
                // Get server prefix, as long as it's cached.
                return ServerSettings.getServerIfCached(event.getGuild().getId()).getPrefix();
            }
            return null;
        });*/

    }

    //discord rich presence
    private void DiscordRPC () {
        DiscordRPC lib = DiscordRPC.INSTANCE;
        String applicationId = "852718208916914178";
        String steamId = "";
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user) -> System.out.println("RPC Ready!");
        lib.Discord_Initialize(applicationId, handlers, true, steamId);
        DiscordRichPresence discordPresence = new DiscordRichPresence();
        discordPresence.state = "Harvesting Computer Souls";
        discordPresence.details = "Competitive";
        discordPresence.startTimestamp = 999;
        //discordPresence.endTimestamp = 1507665886;
        discordPresence.largeImageKey = "scythe-pfp";
        discordPresence.largeImageText = "Harvester - Level 999 Global Rank #2";
        discordPresence.smallImageKey = "scythe-pfp-smol";
        discordPresence.smallImageText = "JavaSE-13";
        discordPresence.partyId = "ae488379-351d-4a4f-ad32-2b9b01c91657";
        discordPresence.partySize = 999;
        discordPresence.partyMax = 999;
        discordPresence.joinSecret = "MTI4NzM0OjFpMmhuZToxMjMxMjM= ";
        lib.Discord_UpdatePresence(discordPresence);
        // in a worker thread
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                lib.Discord_RunCallbacks();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {}
            }
        }, "RPC-Callback-Handler").start();
    }

    public static void main(String[] args) throws LoginException, FileNotFoundException {
        enableUptime = System.currentTimeMillis();
        new Scythe();
        System.out.println("Scythe Bot took " + (System.currentTimeMillis() - enableUptime) + " ms to start up.");

	}

}
