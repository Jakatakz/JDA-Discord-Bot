package com.Jakatakz.jda_bot_bd;

import events.CategoryCreate;
import events.NickChangeEvent;
import events.StatusEvent;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import java.time.LocalTime;

import commands.Calculate;
import commands.Help;
import commands.Invite;

public class App 
{
    public static void main( String[] args ) throws Exception
    {
        JDA jda1 = new JDABuilder("NTc2NDY4MjA1MDIzNzg5MDc4.XNXCxA.bK8AgWwaG4jNov1ErhvIg1BHncY").build();
       
        //time
        LocalTime lt1 = LocalTime.now();
		String currentTime = lt1.toString();
		LocalTime time = LocalTime.parse(currentTime);
		int startTime = time.toSecondOfDay();
        
		//events
        jda1.addEventListener(new StatusEvent(startTime));
        jda1.addEventListener(new CategoryCreate());
        jda1.addEventListener(new NickChangeEvent());
        
        //commands
        jda1.addEventListener(new Calculate());
        jda1.addEventListener(new Help());
        jda1.addEventListener(new Invite());
    }
}
