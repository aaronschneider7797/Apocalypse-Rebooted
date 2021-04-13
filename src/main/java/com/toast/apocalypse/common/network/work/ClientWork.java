package com.toast.apocalypse.common.network.work;

import com.toast.apocalypse.client.event.ClientEvents;
import com.toast.apocalypse.common.core.Apocalypse;
import com.toast.apocalypse.common.network.message.S2CUpdateWorldDifficulty;
import com.toast.apocalypse.common.network.message.S2CUpdateWorldDifficultyRate;
import com.toast.apocalypse.common.network.message.S2CUpdateWorldMaxDifficulty;
import com.toast.apocalypse.common.util.References;

/**
 * Referencing client only code here should cause no trouble
 * as long as this class isn't loaded by anything else
 * than the client itself (which should be the case).
 */
public class ClientWork {

    public static void handleDifficultyUpdate(S2CUpdateWorldDifficulty message) {
        Apocalypse.INSTANCE.getDifficultyManager().setDifficulty(message.difficulty);
    }

    public static void handleDifficultyRateUpdate(S2CUpdateWorldDifficultyRate message) {
        Apocalypse.INSTANCE.getDifficultyManager().setDifficultyRate(message.difficultyRate);
    }

    public static void handleMaxDifficultyUpdate(S2CUpdateWorldMaxDifficulty message) {
        long maxDifficulty = message.maxDifficulty;

        Apocalypse.INSTANCE.getDifficultyManager().setMaxDifficulty(maxDifficulty);

        ClientEvents.COLOR_CHANGE = maxDifficulty > -1 ? maxDifficulty : References.DEFAULT_COLOR_CHANGE;
    }
}
