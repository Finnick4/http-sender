package com.gallfeder.httpsender.events;

import com.gallfeder.httpsender.HTTPSender;
import com.gallfeder.httpsender.commands.HTTPSendPostCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = HTTPSender.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new HTTPSendPostCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }
}
