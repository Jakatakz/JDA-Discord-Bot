package commands;

import java.awt.Color;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;

public class UserInfoCommand extends Command
{
	private EventWaiter waiter;
	
	public UserInfoCommand(EventWaiter waiter)
	{
		super.name = "ui";
		super.help = "Get some information about the user";
		super.aliases = new String[] {"userinfo"};
		super.category = new Category("Members");
		super.cooldown = 10;
		super.arguments = "[name]";
		this.waiter = waiter;
	}
	
	@Override
	protected void execute(CommandEvent event)
	{
		if (event.getArgs().isEmpty())
		{
			event.reply("Provide a name. like this: !uicommand [name]");
		} else 
		{
			Member name;
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			try 
			{
				name = event.getMessage().getMentionedMembers().get(0);
				EmbedBuilder eb = new EmbedBuilder()
						.setColor(Color.magenta)
						.setThumbnail("https://images-i.jpimedia.uk/imagefetch/c_fill,f_auto,h_1133,q_auto:eco,w_1700/https://inews.co.uk/wp-content/uploads/2016/09/pepehead.jpg")
						.setAuthor("Information on " + name.getUser().getName(),
								"http://www.google.com", 
								name.getUser().getAvatarUrl())
						.setDescription(name.getUser().getName() 
								+ " joined on " + name.getJoinDate().format(fmt))
						.addField("Game: " , displayGameInfo(name), true)
						.addField("Status: ", name.getOnlineStatus().toString(), true)
						.addField("NickName: ", 
								name.getNickname() == null ? "no nickname" : name.getNickname(),
										true)
						.addField("Roles: ", getRolesString(name.getRoles()), true);
				event.reply(eb.build());
			} catch (IndexOutOfBoundsException ex)
			{
				System.out.println("Exception Occurred");
				ex.printStackTrace();
				event.reply("You need to provide name as a mention.");
			}
			
		}
		
	}
	
	private String displayGameInfo(Member name)
	{
		try
		{
			String game = name.getGame().getName();
			return "playing game: " + game;
		} catch (NullPointerException ex)
		{
			return "no game is being played";
		}
		
	}
	
	private String getRolesString(List rolesList)
	{
		String roles;
		if (!rolesList.isEmpty())
		{
			Role tempRole = (Role) rolesList.get(0);
			roles = tempRole.getName();
			for (int i = 1; i < rolesList.size(); i++)
			{
				tempRole = (Role) rolesList.get(i);
				roles = roles + ", " + tempRole.getName();
			}
		}
		else
		{
			roles = "none";
		}
		return roles;
	}
}
