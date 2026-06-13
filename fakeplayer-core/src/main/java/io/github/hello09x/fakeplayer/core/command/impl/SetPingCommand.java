package io.github.hello09x.fakeplayer.core.command.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.jorel.commandapi.exceptions.WrapperCommandSyntaxException;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.hello09x.fakeplayer.core.entity.Fakeplayer;
import io.github.hello09x.fakeplayer.core.manager.FakeplayerList;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Singleton
public class SetPingCommand extends AbstractCommand {

    @Inject
    FakeplayerList fakeplayers;

    public void setPing(@NotNull Player sender, @NotNull CommandArguments args) throws WrapperCommandSyntaxException {
        Fakeplayer fake = fakeplayers.getByUUID(getFakeplayer(sender, args).getUniqueId());
        if (fake != null) {
            int ping = Optional.ofNullable((Integer) args.get("ping")).orElse(fake.getFirstPing());
            fake.setFirstPing(ping);
            fake.getNetwork().getServerGamePacketListener().setPing(ping,true);
        }
    }
}
