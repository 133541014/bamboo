package pers.fish.bamboo.common.util;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * byte工具类
 *
 * @author fish
 * @date 2019/12/6 20:29
 */
public class ByteUtils {

    public static byte[] getBytes(Serializable obj) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bout);
        out.writeObject(obj);
        out.flush();
        byte[] bytes = bout.toByteArray();
        out.close();
        return bytes;
    }

    public static int sizeof(Serializable obj) throws IOException {
        return getBytes(obj).length;
    }

    public static Object getObject(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
        ObjectInputStream oi = new ObjectInputStream(bi);
        Object obj = oi.readObject();
        oi.close();
        return obj;
    }

    public static Object getObject(ByteBuffer byteBuffer) throws ClassNotFoundException, IOException {
        InputStream input = new ByteArrayInputStream(byteBuffer.array());
        ObjectInputStream oi = new ObjectInputStream(input);
        Object obj = oi.readObject();
        input.close();
        oi.close();
        byteBuffer.clear();
        return obj;
    }

    public static ByteBuffer getByteBuffer(Serializable obj) throws IOException {
        byte[] bytes = getBytes(obj);
        ByteBuffer buff = ByteBuffer.wrap(bytes);
        return buff;
    }

}

