package com.Jakatakz.jda_bot_bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import commands.BanWord;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

//contains list of banned words, and get/set methods for the banned words list. changes banned words to *
public class Filter extends ListenerAdapter
{		
	private ArrayList<String> bannedWordsCopy = BanWord.getBannedWordsList();
	static Connection conn = DBConnect.dbConnect();
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		String[] message = event.getMessage().getContentRaw().split(" ");
		
		
		try 
		{
			if (!event.getAuthor().isBot() && !event.getAuthor().getDiscriminator().contains("0598"))	//if message is from bot, dont filter it
			{
				populateFirstTimeUsersTable(event);
				isBannedWord(message, bannedWordsCopy, event);
			}
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
			System.out.println("heref1");
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
					String offender = event_p.getAuthor().getName();
					String offenderNick = event_p.getMember().getNickname();
					
					long warningsOffender = checkWarningBan(offender, offenderNick);
					//get warning integer and add it to method addWarnings so u can add it to database
					
					//update users warnings
					addWarnings(offender, offenderNick, warningsOffender);
					
					String starBannedWord = (bannedWordsCopy.get(k)).replaceAll(".", "#");
					
					event_p.getChannel().sendMessage(starBannedWord + " is banned").queue(); 
					//Thread.sleep(4000);
					event_p.getMessage().delete().queue();
				}
			}
		}
	}
	
		//check if perma banned or warnings >3, if >3 ban user
		public long checkWarningBan(String offender, String offenderNick)
		{
			long offenderWarnings = 1;
			try
			{
			Statement stmt = conn.createStatement();
			String sql = "SELECT warnings FROM users WHERE name = '" + offender + "'";
			ResultSet rst = stmt.executeQuery(sql);
			while (rst.next())
			{
				System.out.println(offenderWarnings);
				offenderWarnings = rst.getInt("warnings");
				
				//WARNINGS
				//for refreshing warnings, have longer refresh time based on how high warning is.
				
				//if warnings is already 3, temp ban user, 
				//increase length of ban based on how many bans theyve had in past.
				//but refresh warnings to 0 each time their ban expires but keep ban count to calculate length
				
				
				//if warnings is ALREADY 2, warn user they now have 3/3 warnings (refreshes in (time left for refresh))
				//warn user theyre about to be banned for (ban time length) 
				
				//if warnings is already 1, warn user they have 2/3 warnings (refreshes in (time left for refresh))
				
				//if warnings is already 0, warn user they have 1/3 warnings (refreshes in (time left for refresh))
			}
			System.out.println("offender Warnings: " + offenderWarnings);
			//return offenderWarnings;
			
			
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				System.out.println("heref2");
			}
			System.out.println("offender Warnings: " + offenderWarnings);
			return offenderWarnings;
		}
	
		//add warning
//		public void addWarnings(String offender, String offenderNick)
//		{
//			try
//			{
//			//PreparedStatement prepStat = conn.prepareStatement("insert into users (name, nickname, warnings, banned) " 
//			//+ "values (?, ?, ?, ?)");
//			PreparedStatement prepStat = conn.prepareStatement("UPDATE users "
//					+ "SET name varchar(45), CHANGE nickname varchar(45), warnings int(7), banned varchar(3) " 
//				+ "values (?, ?, ?, ?)");
//			prepStat.setString(1, offender);
//			prepStat.setString(2, offenderNick);
//			prepStat.setLong(3, 1);
//			prepStat.setString(4, "no?");
//			prepStat.executeUpdate();
//			System.out.println("added warnings");
//			}
//			catch (Exception ex)
//			{
//				ex.printStackTrace();
//				System.out.println("heref3");
//			}
//		}
	
		
		
	//remove warning
		
		
		
	//add warning
	public void addWarnings(String offender, String offenderNick, long warningsOfOffender)
	{
		try
		{
		//PreparedStatement prepStat = conn.prepareStatement("insert into users (name, nickname, warnings, banned) " 
		//+ "values (?, ?, ?, ?)");
		PreparedStatement prepStat = conn.prepareStatement("UPDATE users "
				+ "SET warnings = ? "
				+ "WHERE name = ?");
			//+ "values (?, ?)");
		//prepStat.setString(1, offender);
		//prepStat.setString(2, offenderNick);
		
		prepStat.setLong(1, warningsOfOffender+1);		
		//need to make checkwarningban return an integer for warning and give it to this method
		
		prepStat.setString(2, offender);
		//prepStat.setString(4, "no?");
		prepStat.executeUpdate();
		System.out.println("added warnings");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			System.out.println("heref3");
		}
	}
	
	//if the table is dropped or its an empty set , this repopulates the user table, everythings new, bans removed
	public void populateFirstTimeUsersTable(GuildMessageReceivedEvent event)
	{
		try
		{
			List<Member> memberList = event.getGuild().getMembers();
			int memberListSize = memberList.size();
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM users";
			ResultSet rst = stmt.executeQuery(sql);
			
			//if first time (empty USER table), populate the user table
			if (!rst.next())
			{
				rst.beforeFirst(); //moves cursor to front 
				System.out.println("empty set");
				String sql2 = "insert into users(name, nickname,warnings,banned,"
						+ "permabanned,discriminator) " + "values (?,?,?,?,?,?)";
				PreparedStatement prepStat = conn.prepareStatement(sql2);
				//conn.setAutoCommit(false);
				for (int i = 0; i < memberListSize; i++)
				{
					prepStat.setString(1, memberList.get(i).getEffectiveName());
					prepStat.setString(2, memberList.get(i).getNickname());
					prepStat.setInt(3, 0);
					prepStat.setString(4, "no");
					prepStat.setString(5,"no");
					prepStat.setString(6, memberList.get(i).getUser().getDiscriminator());
					prepStat.addBatch();
				}
				prepStat.executeBatch();
				//prepStat.executeUpdate();
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			System.out.println("heref4");
		}
	}
	
}
