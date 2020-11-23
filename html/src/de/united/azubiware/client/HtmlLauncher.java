package de.united.azubiware.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.freetype.gwt.FreetypeInjector;
import com.badlogic.gdx.graphics.g2d.freetype.gwt.inject.OnCompletion;
import de.golfgl.gdxgamesvcs.IGameServiceClient;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.client.login.ActionResolverHtml;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(true);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new AzubiWareGame(new ActionResolverHtml());
        }

        @Override
        public void onModuleLoad () {
                FreetypeInjector.inject(new OnCompletion() {
                        public void run () {
                                HtmlLauncher.super.onModuleLoad();
                        }
                });
        }
}