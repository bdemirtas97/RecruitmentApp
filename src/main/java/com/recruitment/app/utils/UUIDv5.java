package com.recruitment.app.utils;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;

public class UUIDv5 {
    private static final UUID NAMESPACE = UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479");

    public static UUID fromEmailOrPhone(String email, String phone) {
        String input;

        if (!email.equalsIgnoreCase("unknown") && !email.isBlank()) {
            input = email.trim().toLowerCase();
        } else if (!phone.equalsIgnoreCase("unknown") && !phone.isBlank()) {
            input = phone.trim();
        } else {
            throw new IllegalArgumentException("Email and phone cannot be unknown");
        }

        return fromNamespaceAndName(NAMESPACE, input);
    }

    private static UUID fromNamespaceAndName(UUID namespace, String name) {
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            sha1.update(toBytes(namespace));
            sha1.update(name.getBytes(StandardCharsets.UTF_8));
            byte[] hash = sha1.digest();

            hash[6] &= 0x0f;
            hash[6] |= 0x50;
            hash[8] &= 0x3f;
            hash[8] |= (byte) 0x80;

            long msb = 0, lsb = 0;
            for (int i = 0; i < 8; i++) msb = (msb << 8) | (hash[i] & 0xff);
            for (int i = 8; i < 16; i++) lsb = (lsb << 8) | (hash[i] & 0xff);

            return new UUID(msb, lsb);
        } catch (Exception e) {
            throw new RuntimeException("UUID v5 generation failed", e);
        }
    }

    private static byte[] toBytes(UUID uuid) {
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        byte[] buffer = new byte[16];
        for (int i = 0; i < 8; i++) buffer[i] = (byte)(msb >>> (8 * (7 - i)));
        for (int i = 8; i < 16; i++) buffer[i] = (byte)(lsb >>> (8 * (7 - i)));
        return buffer;
    }
}

