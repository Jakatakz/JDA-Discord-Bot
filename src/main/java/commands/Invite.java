package commands;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Invite extends ListenerAdapter 
{
	//queue is for threads, so queue makes it so itll execute as soon as possible
	//.complete() will block any thread until this one is run
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		int time = 300;
		String[] message = event.getMessage().getContentRaw().split(" ");
		if (message[0].equalsIgnoreCase("!invite") && message.length == 1)
		{
			event.getChannel().sendMessage("To invite, do: !invite create").queue();
		}
		else if ((message.length >= 2) && message[0].equalsIgnoreCase("!invite") && message[1].equalsIgnoreCase("create"))
		{
			event.getChannel().sendMessage("getting invite...").queue();
			event.getChannel().sendMessage("Invite link: " + event.getChannel().createInvite().setMaxUses(1).setMaxAge(time).complete().getURL()).queue();
			event.getChannel().sendMessage("invite expires in: " + time / 60 + " minutes (" + time + " seconds)").queue();
		}
	}
}
