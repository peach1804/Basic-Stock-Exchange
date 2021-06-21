package basepackage;

import DSA.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.MessageDigest;
import java.util.*;

public class UserData {

    private static BinarySearch<User> bs = new BinarySearch<>();
    private static MergeSort<User> ms = new MergeSort<>();
    private static ArrayList<User> users = new ArrayList<>();

    public class User implements Comparable<User> {

        private String username;
        private String password;
        private String salt;

        public User() {
        }

        public User(String username, String password, String salt) {
            this.username = username;
            this.password = password;
            this.salt = salt;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getSalt() {
            return salt;
        }

        public int compareTo(User user) {
            return this.username.compareTo(user.username);
        }
    }

    public void addUser(String username, String password, String salt) {

        User user = new User(username, password, salt);
        users.add(user);

        MergeSort<User> ms = new MergeSort<>();
        ms.mergeSort(users);
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    // create a salt and store as a byte array
    // generate hash for salted password and store as byte array
    // create new user and add hexadecimal salt and salted password hash to user properties
    public void createUser(String username, String password) throws NoSuchAlgorithmException {

        byte[] newSalt = getSalt();
        byte[] newHash = getSaltedHash(password, newSalt);

        User user = new User();
        user.username = username;
        user.password = toHex(newHash);
        user.salt = toHex(newSalt);

        users.add(user);
        ms.mergeSort(users);
    }

    public boolean checkUser(String username) {

        User temp = new User();
        temp.username = username;

        User user = bs.binarySearch(users, temp);

        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

    // return null is user does not exist
    // convert the hexadecimal salt from string to byte array
    // generate salted password hash from password attempt and convert from byte array to hexadecimal string
    // if stored salted password hash is equal to the hash of password attempt + stored salt, return that user object
    public User checkLogin(String username, String password) throws NoSuchAlgorithmException {

        if (!checkUser(username)) {
            return null;
        }

        User temp = new User();
        temp.username = username;

        User user = bs.binarySearch(users, temp);

        byte[] byteSalt = fromHex(user.salt);

        String saltedPasswordHash = toHex(getSaltedHash(password, byteSalt));

        if (saltedPasswordHash.equals(user.password)) {
            return user;
        } else {
            return null;
        }
    }

    // return byte array of randomly generated salt number
    public byte[] getSalt() {

        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);

        return salt;
    }

    // use SHA-256 to create a hash of password string and salt byte array
    // return salted password hash as byte array
    public byte[] getSaltedHash(String password, byte[] salt) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);

        byte[] hashBytes = md.digest(password.getBytes());
        md.reset();

        return hashBytes;
    }

    // convert a byte array to a hexadecimal number
    public String toHex(byte[] bytes) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {

            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    // convert a hexadecimal number to a byte array
    public byte[] fromHex(String hex) {

        byte[] hexBytes = new byte[hex.length() / 2];

        for (int i = 0; i < hexBytes.length; i++) {

            hexBytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }

        return hexBytes;
    }
}
