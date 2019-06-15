package commands;

import java.util.ArrayList;

import com.Jakatakz.jda_bot_bd.Filter;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class BanWord extends ListenerAdapter
{
	
	private static ArrayList<String> bannedWords = new ArrayList<>();
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		
		String list = "";
		String[] message = event.getMessage().getContentRaw().split(" ");
	
		if (!event.getAuthor().isBot())	//ignore bot
		{
			if (message.length == 1 && message[0].equalsIgnoreCase("!banw"))
			{
				event.getChannel().sendMessage("to ban a word, type !banw [word]").queue();
			}
			else if (message.length == 2 && message[0].equalsIgnoreCase("!banw") && message[1].equalsIgnoreCase("list"))
			{
				if (bannedWords.isEmpty())
					event.getChannel().sendMessage("no banned words").queue();
				else
				{
					for (String a: bannedWords)
						list += a + " ";
					event.getChannel().sendMessage(list).queue();
				}
			}
			else if (message.length == 2 && message[0].equalsIgnoreCase("!banw") && alreadyBanned(message[1]) == true)
			{
				event.getChannel().sendMessage("already banned: " + message[1]).queue();
			}
			else if (message.length == 2 && message[0].equalsIgnoreCase("!banw") && alreadyBanned(message[1]) == false)
			{
				bannedWords.add(message[1]);
				event.getChannel().sendMessage(message[1] + " added to banned words").queue();
			}
		}
	}
	
	public boolean alreadyBanned(String word)
	{
		boolean status = false;
		for (String a: bannedWords)
		{
			if (a.equalsIgnoreCase(word))
				status = true;
			else
				status = false;
		}
		return status;
	}
	
	public static ArrayList<String> getBannedWords()
	{
		return bannedWords;
	}
	
	
}
