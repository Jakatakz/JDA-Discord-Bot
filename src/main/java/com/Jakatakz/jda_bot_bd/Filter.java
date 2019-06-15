package com.Jakatakz.jda_bot_bd;

import java.util.ArrayList;


import commands.BanWord;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

//contains list of banned words, and get/set methods for the banned words list. changes banned words to *
public class Filter extends ListenerAdapter
{		
	private ArrayList<String> bannedWordsCopy = BanWord.getBannedWords();
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		String[] message = event.getMessage().getContentRaw().split(" ");
		
		
		try 
		{
			if (!event.getAuthor().isBot())	//if message is from bot, dont filter it
			{
				isBannedWord(message, bannedWordsCopy, event);
			}
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
					//event.getMessage().editMessage(message[i].replaceAll("[a-zA-z]", "*")).queue();
					//message[i].replaceAll("[a-zA-z]", "*");
				
					
			
		
	}
	
	//get user who said ban word and add a warning up to 3 until timeout'd from channel
	//dont add warning counter for first time, just warning for first offense, time limit until refresh 1 week
	
	public void isBannedWord(String[] message_param, ArrayList<String> bannedWordsCopy_p, GuildMessageReceivedEvent event_p) throws InterruptedException
	{
		for (int i = 0; i < message_param.length; i++)
		{
			for (int k = 0; k < bannedWordsCopy.size(); k++)
			{
				if (message_param[i].contains(bannedWordsCopy.get(k)))
				{ 
					event_p.getChannel().sendMessage("banned word").queue(); 
					//Thread.sleep(4000);
					event_p.getMessage().delete().queue();
				}
			}
		}
	}
	
	//warning counter
	public void warningBan()
	{
		
	}
	
}
