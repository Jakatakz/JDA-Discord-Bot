package com.Jakatakz.jda_bot_bd;

import events.CategoryCreate;
import events.HelloEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class App 
{
    public static void main( String[] args ) throws Exception
    {
        JDA jda1 = new JDABuilder("NTc2NDY4MjA1MDIzNzg5MDc4.XNXCxA.bK8AgWwaG4jNov1ErhvIg1BHncY").build();
        
        jda1.addEventListener(new HelloEvent());
        jda1.addEventListener(new CategoryCreate());
    }
}
