package events;

import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.guild.member.GuildMemberNickChangeEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class NickChangeEvent extends ListenerAdapter {

	public void onGuildMemberNickChange(GuildMemberNickChangeEvent event)
	{
		//disregard normal users, only include members
		String prevNick = event.getPrevNick();
		String newNick = event.getNewNick();
		
		TextChannel textChannel = event.getGuild().getTextChannelsByName("main",true).get(0);
		System.out.println(textChannel);
		textChannel.sendMessage(prevNick + " changed their name to " + newNick).queue();
		
	}
	
}
