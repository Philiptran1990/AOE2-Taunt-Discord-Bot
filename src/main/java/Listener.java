import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;
import com.sedmelluq.*;

import java.util.ArrayList;

public class Listener extends ListenerAdapter {
    ArrayList commandQueue = new ArrayList();

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        // Good practise to ignore bots.
        if (event.getAuthor().isBot()) {
            return;
        }

        String message = event.getMessage().getContentRaw().toLowerCase();
        TextChannel channel = event.getChannel();

        String possibleCommands = "[1-9]?|(([1-3][0-9])|(4[0-2]))";

        if (message.matches(possibleCommands)) {
            event.getChannel().sendMessage("lol").queue();
            //commandQueue.add();
            this.joinChannel(channel, event);
        }

    }

    private void joinChannel(TextChannel channel, GuildMessageReceivedEvent event) {

        if (!event.getGuild().getSelfMember().hasPermission(channel, Permission.VOICE_CONNECT)) {
            // The bot does not have permission to join any voice channel. Don't forget the .queue()!
            channel.sendMessage("I do not have permissions to join a voice channel!").queue();
            return;
        }
        // Creates a variable equal to the channel that the user is in.
        VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
        // Checks if they are in a channel -- not being in a channel means that the variable = null.
        if (connectedChannel == null) {
            // Don't forget to .queue()!
            channel.sendMessage("You are not connected to a voice channel!").queue();
            return;
        }
        // Gets the audio manager.
        AudioManager audioManager = event.getGuild().getAudioManager();
        // When somebody really needs to chill.
        if (audioManager.isAttemptingToConnect()) {
            channel.sendMessage("The bot is already trying to connect! Enter the chill zone!").queue();
            return;
        }
        // Connects to the channel.
        audioManager.openAudioConnection(connectedChannel);



        audioManager.setSendingHandler(myAudioSendHandler)

        // Obviously people do not notice someone/something connecting.
        channel.sendMessage("Connected to the voice channel!").queue();
    }

    private void leaveChannel(TextChannel channel, GuildMessageReceivedEvent event) {

        { // Checks if the command is !leave.
            // Gets the channel in which the bot is currently connected.
            VoiceChannel connectedChannel = event.getGuild().getSelfMember().getVoiceState().getChannel();
            // Checks if the bot is connected to a voice channel.
            if (connectedChannel == null) {
                // Get slightly fed up at the user.
                channel.sendMessage("I am not connected to a voice channel!").queue();
                return;
            }
            // Disconnect from the channel.
            event.getGuild().getAudioManager().closeAudioConnection();
            // Notify the user.
            channel.sendMessage("Disconnected from the voice channel!").queue();
        }
    }


    /*
    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        if (event.getAuthor().isBot())
            return;

        System.out.println("Message from " +
                event.getAuthor().getName() + ": " +
                event.getMessage().getContentDisplay());

        if (event.getMessage().getContentRaw().equals("1")){
            event.getChannel().sendMessage("lol").queue();
        }
    } */
}
