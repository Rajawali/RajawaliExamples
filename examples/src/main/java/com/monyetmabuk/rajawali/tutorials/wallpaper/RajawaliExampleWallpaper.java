package com.monyetmabuk.rajawali.tutorials.wallpaper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.rajawali3d.surface.IRajawaliSurfaceRenderer;
import org.rajawali3d.util.RajLog;
import org.rajawali3d.wallpaper.Wallpaper;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Jared Woolston (jwoolston@idealcorp.com)
 */
public class RajawaliExampleWallpaper extends Wallpaper {

    private IRajawaliSurfaceRenderer mRenderer;

    @Override
    public Engine onCreateEngine() {
        final SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean useFallback = false;
        try {
            final Class rendererClass = Class.forName(mSharedPreferences.getString("renderer_class", WallpaperRenderer.class.getCanonicalName()));
            RajLog.d(this, "Creating wallpaper engine: " + rendererClass.getCanonicalName());
            mRenderer = (IRajawaliSurfaceRenderer) rendererClass.getConstructor(Context.class).newInstance(this);
        } catch (NoSuchMethodException e) {
            useFallback = true;
        } catch (InvocationTargetException e) {
            useFallback = true;
        } catch (InstantiationException e) {
            useFallback = true;
        } catch (IllegalAccessException e) {
            useFallback = true;
        } catch (ClassNotFoundException e) {
            useFallback = true;
        }
        if (useFallback) mRenderer = new WallpaperRenderer(this);
        return new WallpaperEngine(getBaseContext(), mRenderer, true, true);
    }
}
