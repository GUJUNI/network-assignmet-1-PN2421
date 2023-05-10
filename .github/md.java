import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class md {

public class FileMessageDigest {
    public static void main(String[] args) {
        String fileName = "example.txt";
        
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            FileInputStream fileInputStream = new FileInputStream(fileName);
            byte[] buffer = new byte[1024];
            int bytesRead;
            
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                messageDigest.update(buffer, 0, bytesRead);
            }
            
            fileInputStream.close();
            byte[] digest = messageDigest.digest();
            
            System.out.println("Message Digest (SHA-256) for " + fileName + ":");
            System.out.println(bytesToHex(digest));
            
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
    }
    
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            
            if (hex.length() == 1) {
                hexString.append('0');
            }
            
            hexString.append(hex);
        }
        
        return hexString.toString();
    }
}

}
