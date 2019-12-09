package net.edrop.edrop_user.utils;

import com.bumptech.glide.module.AppGlideModule;

/**
 * Created by mysterious
 * User: mysterious
 * Date: 2019/12/9
 * Time: 16:59
 */
public class MyAppGlideModule extends AppGlideModule{
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
