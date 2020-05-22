package me.crupette.surrealbiomes;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class SBModMenuIntegration implements ModMenuApi {
    @Override
    public String getModId() {
        return "surrealbiomes";
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return SBClient::createConfigScreen;
    }
}
