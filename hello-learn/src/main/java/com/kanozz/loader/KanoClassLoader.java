package com.kanozz.loader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

public class KanoClassLoader extends ClassLoader {


    private static final String END = ".class";


    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        ClassLoader parent = getParent();
        if (parent!=null && !name.startsWith("com.kanozz")){
            Class<?> c = parent.loadClass(name);
            if (c !=null){
                return c;
            }
        }
        return findClass(name);
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException
    {

        try{

            File file = new File(KanoClassLoader.getSystemResource(name.replace('.','/')+END).toURI());
            byte[] bytes = getClassBytes(file);

            Class<?> c = this.defineClass(name, bytes, 0, bytes.length);
            return c;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return super.findClass(name);
    }

    private byte[] getClassBytes(File file) throws Exception
    {
        // 这里要读入.class的字节，因此要使用字节流
        FileInputStream fis = new FileInputStream(file);
        FileChannel fc = fis.getChannel();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        WritableByteChannel wbc = Channels.newChannel(baos);
        ByteBuffer by = ByteBuffer.allocate(1024);

        while (true){
            int i = fc.read(by);
            if (i == 0 || i == -1)
                break;
            by.flip();
            wbc.write(by);
            by.clear();
        }
        fis.close();
        return baos.toByteArray();
    }
}
