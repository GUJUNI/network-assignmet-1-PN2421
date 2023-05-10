public class encryption {
    public class CaesarCipher {
        public static void main(String[] args) {
            int key = 3;
            String message = "Hello, World!";
            
            System.out.println("Original Message: " + message);
    
            String encryptedMessage = encrypt(message, key);
            System.out.println("Encrypted Message: " + encryptedMessage);
    
            String decryptedMessage = decrypt(encryptedMessage, key);
            System.out.println("Decrypted Message: " + decryptedMessage);
        }
    
        public static String encrypt(String message, int key) {
            StringBuilder encrypted = new StringBuilder();
            
            for (char c : message.toCharArray()) {
                if (Character.isLetter(c)) {
                    char shifted = (char) (c + key);
                    
                    if ((Character.isLowerCase(c) && shifted > 'z') || (Character.isUpperCase(c) && shifted > 'Z')) {
                        shifted = (char) (shifted - 26);
                    }
                    
                    encrypted.append(shifted);
                } else {
                    encrypted.append(c);
                }
            }
            
            return encrypted.toString();
        }
    
        public static String decrypt(String encryptedMessage, int key) {
            StringBuilder decrypted = new StringBuilder();
            
            for (char c : encryptedMessage.toCharArray()) {
                if (Character.isLetter(c)) {
                    char shifted = (char) (c - key);
                    
                    if ((Character.isLowerCase(c) && shifted < 'a') || (Character.isUpperCase(c) && shifted < 'A')) {
                        shifted = (char) (shifted + 26);
                    }
                    
                    decrypted.append(shifted);
                } else {
                    decrypted.append(c);
                }
            }
            
            return decrypted.toString();
        }
    }
    
}
