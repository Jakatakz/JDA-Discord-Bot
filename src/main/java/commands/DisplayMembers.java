package commands;

import java.util.List;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class DisplayMembers extends ListenerAdapter {

	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		Guild guild = event.getGuild();
		List<Member> membersInDiscord = guild.getMembers();
		
		String[] message = event.getMessage().getContentRaw().split(" ");
		
		//get users by nickname
		if (message[0].equalsIgnoreCase("!members") && message.length == 1)
		{
			for (Member m: membersInDiscord)
			{
				if (m.getNickname() == null)
					event.getChannel().sendMessage(m.getUser().getName() + "\n").queue();
				else
					event.getChannel().sendMessage(m.getNickname() + "\n").queue();
			}
		}
			//get actual users, no nick
		if (message[0].equalsIgnoreCase("!realMembers") && message.length == 1)
		{
			for (Member m: membersInDiscord)
			{
				event.getChannel().sendMessage(m.getUser().getName() + "\n").queue();
			}
		}
	}
}
