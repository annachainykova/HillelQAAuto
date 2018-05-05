public class Tower {
    public static void main(String[] args) {
        tower(12, '*', "  ");
    }

    public static void tower(int height, char symbol, String space) {
        for (int i = 1; i < height + 1; i++) {
            for (int y = height - 1; y > i - 1; y--) {
                System.out.print(' ');
            }
            for (int y = 0; y < i + 1; y++) {
                System.out.print(symbol);
            }
            System.out.print(space);
            for (int y = 0; y < i + 1; y++) {
                System.out.print(symbol);

            }
            System.out.println();
        }
    }
}