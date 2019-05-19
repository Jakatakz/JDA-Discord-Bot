package commands;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Help extends ListenerAdapter
{
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		
		String[] message = event.getMessage().getContentRaw().split(" ");
		if (message[0].equalsIgnoreCase("!help") && message.length == 1)
		{
			event.getChannel().sendMessage("COMMANDS\n!calculate [add/sub] [number] [number]\n"
					+ "other commands coming...").queue();
		}
		
	}
}