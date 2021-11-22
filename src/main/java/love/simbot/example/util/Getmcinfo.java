package love.simbot.example.util;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.InitialDirContext;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;

public class Getmcinfo {
    public static String[] Parsing_dnz(String IP){
        try {
            Hashtable<String, String> hashtable = new Hashtable<>();
            hashtable.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            hashtable.put("java.naming.provider.url", "dns:");

            Attribute qwqre = (new InitialDirContext(hashtable)).getAttributes("_Minecraft._tcp."+IP , new String[]{"SRV"}).get("srv");
            if (qwqre==null){
                return null;
            }
            String[] re = qwqre.get().toString().split(" ", 4);
            String[] Stringts = new String[]{re[3],re[2]};
            return Stringts;
        } catch (NamingException e) {
            return null;
        }
    }

    public static String getmcinfo(String url){
        try {
        String address =url;

        int port = 25565;
        String[] I =    Parsing_dnz(url);
        if (I!=null){
            address = I[0];
            port = Integer.parseInt(I[1]);
        }
        InetSocketAddress host = new InetSocketAddress(address, port);

        Socket socket = new Socket();
        socket.connect(host, 3000);

        DataOutputStream output = new DataOutputStream(socket.getOutputStream());

        DataInputStream input = new DataInputStream(socket.getInputStream());
        byte [] handshakeMessage = createHandshakeMessage(address, port);
        writeVarInt(output, handshakeMessage.length);
        output.write(handshakeMessage);
        output.writeByte(0x01); //size is only 1
        output.writeByte(0x00); //packet id for ping

        int size = readVarInt(input);

        int packetId = readVarInt(input);

        if (packetId == -1) {
            return null;
        }

        if (packetId != 0x00) { //we want a status response
            return null;
        }
        int length = readVarInt(input); //length of json string
        if (length == -1) {
            return null;
        }

        if (length == 0) {
            return null;
        }
        byte[] in = new byte[length];
        input.readFully(in); //read json string
        String json = new String(in);

        long now = System.currentTimeMillis();

        output.writeByte(0x09); //size of packet

        output.writeByte(0x01); //0x01 for ping

        output.writeLong(now); //time!?

// S->C : Pong

        readVarInt(input);

        packetId = readVarInt(input);

        if (packetId == -1) {
            throw new IOException("Premature end of stream.");

        }

        if (packetId != 0x01) {
            throw new IOException("Invalid packetID");

        }

        long pingtime = input.readLong(); //read response

// print out server info

        return json;

        } catch (IOException e) {
            return null;
        }
    }


    public static byte [] createHandshakeMessage(String host, int port) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        DataOutputStream handshake = new DataOutputStream(buffer);

        handshake.writeByte(0x00); //packet id for handshake

        writeVarInt(handshake, 4); //protocol version

        writeString(handshake, host, StandardCharsets.UTF_8);

        handshake.writeShort(port); //port

        writeVarInt(handshake, 1); //state (1 for handshake)

        return buffer.toByteArray();

    }

    public static void writeString(DataOutputStream out, String string, Charset charset) throws IOException {
        byte [] bytes = string.getBytes(charset);

        writeVarInt(out, bytes.length);

        out.write(bytes);

    }

    public static void writeVarInt(DataOutputStream out, int paramInt) throws IOException {
        while (true) {
            if ((paramInt & 0xFFFFFF80) == 0) {
                out.writeByte(paramInt);

                return;

            }

            out.writeByte(paramInt & 0x7F | 0x80);

            paramInt >>>= 7;

        }

    }

    public static int readVarInt(DataInputStream in) throws IOException {
        int i = 0;

        int j = 0;

        while (true) {
            int k = in.readByte();

            i |= (k & 0x7F) << j++ * 7;

            if (j > 5) throw new RuntimeException("VarInt too big");

            if ((k & 0x80) != 128) break;

        }

        return i;

    }
}
