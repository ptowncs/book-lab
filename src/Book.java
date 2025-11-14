//A few assumptions.......

//Words will be separated by spaces. 
//There can be punctuation in a word, we will only add/keep punctuation at the end of a string if it is at the end of a string.
//    for examples: Hello.==> Ellohay.    Good-bye! ==> Ood-byegay!    so... ==> osay...
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

      while (s.hasNext()) {
        String text = s.nextLine();

        // System.out.println(text);
        boolean addText = text.contains("*** START OF THE PROJECT GUTENBERG EBOOK")^ !(text.contains("*** END OF THE PROJECT GUTENBERG EBOOK"));
        if (addText) {
          book += text;
        }
      }
      System.out.println(book);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public String pigLatin(String word) {
    String vowelString = "aeiouy";
    String numberString = "123456789";
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
    boolean alpha = false;
    for (int i = 0; i < word.length(); i++) {
      if (Character.isAlphabetic(word.charAt(i))) {
        alpha = true;
      } else if (!Character.isAlphabetic(word.charAt(i))) {
        if (alpha) {
          alpha = false;
          return i;
        }
      }
    }
    if (alpha) {
      return -1;
    } else {
      return 0;
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
    String retSentence = "";
    int lastSpaceIndex = 0;
    for (int i = 0; i < sentence.length(); i++) {
      if (sentence.substring(i, i + 1).equals(" ") || i == sentence.length() - 1) {
        retSentence += translateWord(sentence.substring(lastSpaceIndex, i + 1));
        lastSpaceIndex = i + 1;
      }

    }
    return retSentence;
  }

}
