public class DeleteLastCharInString {
    public static void main(String[] args) {
        deleteLastCharInString("Some text which contains few 's' letters.", 's');
    }

    public static void deleteLastCharInString(String text, char charToBeDeleted) {
            System.out.println(text.replaceAll(("(?i)(.*)" + charToBeDeleted), "$1"));

    }
}
