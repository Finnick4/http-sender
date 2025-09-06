package com.gallfeder.httpsender.commands;

import com.gallfeder.httpsender.HTTPSender;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HTTPSendPostCommand {
    public HTTPSendPostCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("sendhttp").requires((p_137103_) -> {
            return p_137103_.hasPermission(2);
        }).then(Commands.literal("get").then(Commands.argument("message", StringArgumentType.string())
                .executes(context -> {
                    String message = StringArgumentType.getString(context, "message");
                    CommandSourceStack source = context.getSource();
                    // send request
                    try {
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(new URI(message))
                                .GET()
                                .build();
                        HttpClient client = HttpClient.newBuilder().build();
                        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                        source.sendSuccess(() -> Component.literal("Sent the Get Request"), false);
                    } catch (IllegalArgumentException e) {
                        source.sendFailure(Component.literal("Something went wrong! Did you forget the http(s):// at the start?"));
                    } catch (URISyntaxException | IOException | InterruptedException e) {
                        source.sendFailure(Component.literal("Something went wrong!"));
                    }

                    return 1;
                }))));
    }
    private int sendPost(CommandSource source) throws CommandSyntaxException {
        source.sendSystemMessage(Component.translatableWithFallback(HTTPSender.MOD_ID + ".post", "Test"));
        return 1;
    }
}
