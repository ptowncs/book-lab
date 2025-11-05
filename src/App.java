class App {
  public static void main(String[] args) {
    Book aBook = new Book();
    System.out.println(aBook.pigLatin("hello"));
    System.out.println(aBook.translateWord("What?!?"));
    System.out.println(aBook.translateWord("Allons-y "));
    System.out.println(aBook.translateSentence("hello, my name is pranav."));
  }
}
