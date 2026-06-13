package io.github.hello09x.fakeplayer.core.manager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.hello09x.fakeplayer.core.Main;
import io.github.hello09x.fakeplayer.core.config.FakeplayerConfig;
import io.github.hello09x.fakeplayer.core.entity.Fakeplayer;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

@Singleton
public class FakeplayerPingSetter {

    private final FakeplayerConfig config;
    private final FakeplayerList fakeplayers;
    private BukkitTask task;

    @Inject
    public FakeplayerPingSetter(FakeplayerConfig config, FakeplayerList fakeplayers) {
        this.config = config;
        this.fakeplayers = fakeplayers;
    }

    public void start() {
        if (config.getCustomPingDynamic()) {
            task = Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), this::run, 0, config.getCustomPingDynamicTaskDelay());
        }
    }

    public void stop() {
        if (task!=null) task.cancel();
    }

    public void run () {
        List.copyOf(fakeplayers.getAll()).forEach(Fakeplayer::updateDynamicPing);
    }

}
