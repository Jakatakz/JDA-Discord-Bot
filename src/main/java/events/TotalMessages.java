package events;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.Jakatakz.jda_bot_bd.DBConnect;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class TotalMessages extends ListenerAdapter
{
	static Connection conn = DBConnect.dbConnect();
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		String userID = event.getAuthor().getId();
		try
		{
			PreparedStatement  ppst = conn.prepareStatement("UPDATE users "
					+ "SET totalmessages = totalMessages + 1 "
					+ "WHERE userid = " + userID);
			ppst.executeUpdate();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
