package com.monyetmabuk.rajawali.tutorials.wallpaper;

import org.rajawali3d.surface.IRajawaliSurfaceRenderer;
import org.rajawali3d.wallpaper.Wallpaper;

/**
 * @author Jared Woolston (jwoolston@idealcorp.com)
 */
public class RajawaliExampleWallpaper extends Wallpaper {

    private IRajawaliSurfaceRenderer mRenderer;

    @Override
    public Engine onCreateEngine() {
        mRenderer = new WallpaperRenderer(this);
        return new WallpaperEngine(getBaseContext(), mRenderer, true, true);
    }
}
