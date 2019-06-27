package com.Jakatakz.jda_bot_bd;

import events.CategoryCreate;
import events.NickChangeEvent;
import events.StatusEvent;
import events.TextChannelCreate;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalTime;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;

import commands.BanWord;
import commands.Calculate;
import commands.DisplayMembers;
import commands.Help;
import commands.Image;
import commands.Invite;
import commands.ServerInfo;
import commands.UserInfo;
import commands.UserInfoCommand;

public class App 
{
    public static void main( String[] args ) throws Exception
    {
        JDA jda1 = new JDABuilder("NTc2NDY4MjA1MDIzNzg5MDc4.XNXCxA.bK8AgWwaG4jNov1ErhvIg1BHncY").build();
        
        EventWaiter waiter = new EventWaiter();
        
        
        //jda utilities commands
        CommandClientBuilder builder = new CommandClientBuilder();
        builder.setOwnerId("576468205023789078");
        builder.setPrefix("!");
        builder.setHelpWord("halp");
        builder.addCommand(new ServerInfo());
        builder.addCommand(new Image());
        builder.addCommand(new UserInfoCommand(waiter));
        
        //jda utilities commands
        CommandClient client = builder.build();
        jda1.addEventListener(client);
        jda1.addEventListener(waiter);
        
       // getConnection();
     
        
        //time
        LocalTime lt1 = LocalTime.now();
		String currentTime = lt1.toString();
		LocalTime time = LocalTime.parse(currentTime);
		int startTime = time.toSecondOfDay();
		
		//events
        jda1.addEventListener(new StatusEvent(startTime));
        jda1.addEventListener(new CategoryCreate());
        jda1.addEventListener(new NickChangeEvent());
        jda1.addEventListener(new TextChannelCreate());
        
        //commands
        jda1.addEventListener(new Calculate());
        jda1.addEventListener(new Help());
        jda1.addEventListener(new Invite());
        jda1.addEventListener(new UserInfo());
        jda1.addEventListener(new BanWord());
        jda1.addEventListener(new DisplayMembers());
        
        jda1.addEventListener(new Filter());
    }
    
    
}
