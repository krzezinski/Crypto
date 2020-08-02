import java.util.Scanner;

public class Crypto {
    public static void main(String[] args){
        //enter string to be encrypted, shiftKey, groupKey
        Scanner input = new Scanner(System.in);
        System.out.print("Please enter text to be encrypted: ");
        String originalText = input.nextLine();
        if (originalText.isEmpty()) {
            originalText = "This is some \"really\" great. (Text)!?"; // testing
        }
        System.out.print("Please enter the shift down number: ");
        int shiftKey = input.nextInt();
        System.out.print("Please enter the number of characters before space is added: ");
        int groupKey = input.nextInt();
        print("Original text", originalText);
        String printText = encryptedString(originalText, shiftKey, groupKey);
        print("Encrypted text", printText);
        String ungroupedText = ungroupify(printText);
        print("Ungrouped text", ungroupedText);
        String unobifiedText = decryptString(ungroupedText, shiftKey);
        print("Decrypted text", unobifiedText);
    } // main

    public static String normalizeText(String originalText){
        // uppercase, remove punctuation and white space
        return originalText.toUpperCase().replaceAll("[.,:;'\"!?() ]", "");
    } // normalizeText

    public static String obify(String normalizedText){
        // insert O and B before every vowel including Y (A, E, I, O, U, Y)
        // mentioned in video and by name in text but requirements not stated
        String obifiedText = normalizedText;
        return obifiedText;
    } // obify

    public static String ceasarify(String obifiedText, int shiftKey){
        // shift each letter up or down in the alphabet by shiftKey
        shiftKey = shiftKey % 26;
        String ceasarifiedText = "", nextChar = "";
        int charValue = 0, upOrDown = 0, loopCount = 0;
        char currentChar = ' ';
        if (shiftKey > 0) {
            upOrDown = 1;
        }else if(shiftKey < 0) {
            upOrDown = -1;
        }else {
            upOrDown = 0;
        }
        for (int i = 0; i < obifiedText.length(); i++){
            loopCount = Math.abs(shiftKey);
            currentChar = obifiedText.charAt(i);
            charValue = currentChar;
            while (loopCount > 0) {
                if (charValue == 65 && shiftKey < 0) { // at A going backwards, need to flip to Z
                    currentChar = 'Z';
                    charValue = currentChar;
                    loopCount--;
                }else if (charValue == 90 && shiftKey > 0) { // at Z going forward, need to flip to A
                    currentChar = 'A';
                    charValue = currentChar;
                    loopCount--;
                }
                loopCount--;
                charValue += upOrDown;
                nextChar = String.valueOf((char)(charValue));
            }
            ceasarifiedText += nextChar;
        }
        return ceasarifiedText;
    } // ceasarify

    public static String groupify(String ceasarifiedText, int groupKey){
        // divide text into pieces, insert white space
        String tempText = "";
        while (ceasarifiedText.length() % groupKey != 0) {
            ceasarifiedText += "x";
        }
        for (int i = 0, k=1; i < ceasarifiedText.length(); i++,++k){
            tempText += ceasarifiedText.charAt(i);
            if (k == groupKey){
                tempText += ' ';
                k = 0;
            }
        }
        return tempText;
    } // groupify

    public static String ungroupify(String groupedText){
        return groupedText.replaceAll("[ x]", "");
    } // ungroupify

    public static String decryptString(String ungroupedText, int shiftKey){
        shiftKey *= -1;
        String unobifiedText = ceasarify(ungroupedText, shiftKey);
        return unobifiedText;
    } // decryptString

    public static String encryptedString(String originalText, int shiftKey, int groupKey){
        String normalizedText = normalizeText(originalText);
        String obifiedText = obify(normalizedText);
        String ceasarifiedText = ceasarify(obifiedText, shiftKey);
        String groupifiedText = groupify(ceasarifiedText, groupKey);
        return groupifiedText;
    } // encryptedString

    private static void print(String textType, String groupifiedText) {
        System.out.println();
        System.out.println(textType + ": " + groupifiedText);
    } // print
} // Crypto