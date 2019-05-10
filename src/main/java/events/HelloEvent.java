package events;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class HelloEvent extends ListenerAdapter 
{
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		String[] messageSent = event.getMessage().getContentRaw().split(" ");
		if(messageSent[0].equalsIgnoreCase("bb"))
		{
			if (!event.getMember().getUser().isBot())
				event.getChannel().sendMessage("Responded").queue();
		}
			
	}
}
