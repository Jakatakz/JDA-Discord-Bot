package com.Jakatakz.jda_bot_bd;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class trackUserInfo extends ListenerAdapter {
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		Guild guild = event.getGuild();
		List<Member> membersInDiscord = guild.getMembers();
		
		
	}
	
	//store warnings  >3 is ban for day, refresh after week
	public void addWarning()
	{
		
	}
	
	
	//other information?
	
	
	
}
