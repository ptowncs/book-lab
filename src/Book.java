//A few assumptions.......

//Words will be separated by spaces. 
//There can be punctuation in a word, we will only add/keep punctuation at the end of a string if it is at the end of a string.
//    for examples: Hello.==> Ellohay.    Good-bye! ==> Ood-byegay!    so... ==> osay...
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.net.URL;

public class Book {
  private String book;

  public Book(String url) {
    readBook(url);
  }

  private void readBook(String link) {
    try {
      URL url = new URL(link);
      Scanner s = new Scanner(url.openStream());
      boolean startReading = false;

      book = "";

      while (s.hasNextLine()) {
        String text = s.nextLine();

        if (text.contains("*** START OF THE PROJECT GUTENBERG EBOOK")) {
          startReading = true;
          continue;
        }
        if (text.contains("*** END OF THE PROJECT GUTENBERG EBOOK")) {
          startReading = false;
          break;
        }
        if (startReading) {
          book += text + "\n";
        }
      }
      // After you finish reading the book text into the 'book' string:
      int numWords = book.trim().split("\\s+").length;
      System.out.println("Word count: " + numWords);

      try (FileWriter writer = new FileWriter("TranslatedBook.txt")) {
        Scanner lineScanner = new Scanner(book);
        while (lineScanner.hasNextLine()) {
          String line = lineScanner.nextLine();
          writer.write(translateSentence(line) + "\n");
        }
        lineScanner.close();
      }

    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public String pigLatin(String word) {
    String vowelString = "aeiouy";
    String numberString = "0123456789";
    word = word.toLowerCase();
    if (word.length() == 0 || numberString.contains(word.substring(0, 1))) {
      return word;
    }
    if (vowelString.contains((word.substring(0, 1)))) {
      return word + "yay";
    } else {
      int index = 0;
      for (int i = 0; i < word.length(); i++) {
        if (vowelString.contains((word.substring(i, i + 1)).toLowerCase())) {
          index = i;
          break;
        }
      }
      return word.substring(index) + word.substring(0, index) + "ay";
    }
  }

  public int endPunctuation(String word) // return the index of where the punctuation is at the end of a String. If it
                                         // is all punctuation return 0, if there is no punctuation return -1
  {
   boolean hasAlpha = false;
    for (int i = 0; i < word.length(); i++) {
      if(Character.isAlphabetic(word.charAt(i))) {
        hasAlpha = true;
      }
    }

    int punctIndex = -1;
    for(int i = word.length()-1; i >= 0;i--){
      if(Character.isAlphabetic(word.charAt(i))){
        break;
      } else {
        punctIndex = i;
      }
    }
    if (!hasAlpha){
      return 0;
    } else{
      return punctIndex;
    }
  }

  public String translateWord(String word) // to share with class
  {
    String result;
    int punct = endPunctuation(word);
    if (punct != -1) {
      result = pigLatin(word.substring(0, punct)) + word.substring(punct);
    } else {
      result = pigLatin(word);

    }
    if (Character.isUpperCase(word.charAt(0))) {
      result = Character.toUpperCase(result.charAt(0)) + result.substring(1);
    }

    return result;
  }

  public String translateSentence(String sentence) {
    String[] words = sentence.split("\\s+"); // Split on all whitespace
    StringBuffer retSentence = new StringBuffer();

    for (int i = 0; i < words.length; i++) {
      if (!words[i].isEmpty()) {
        retSentence.append(translateWord(words[i]));
      }
      if (i < words.length - 1) {
        retSentence.append(" ");
      }
    }
    return retSentence.toString();
  }

}
