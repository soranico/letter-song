package com.kanozz.loader;

import com.kanozz.loader.data.Music;

public interface KanoParent {


    Music kano(ClassLoader classLoader) throws Exception;

}
