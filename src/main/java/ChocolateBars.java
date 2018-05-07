public class ChocolateBars {
    public static void main(String[] args) {
        System.out.println(chocolateBars(22, 2, 5));
        System.out.println(countChoc(22, 2, 5));
    }

    //Anna's implementation
    public static int chocolateBars(int money, int price, int wraps){
        int allWraps = money/price; //how many wraps we have by buying all bars for money
        int bars = money/price; //how many bars we bought for money
        do {
            int newWraps = allWraps / wraps; //how many bars(wraps) we got by exchanging
            allWraps = newWraps + (allWraps % wraps); //wraps by exchanging + wraps that couldn't be changed
            bars += newWraps;
        } while(allWraps >= wraps);

        return bars;
    }

    //Leonid's implementation
    public static int countChoc(int cash, int price, int wraps){
        int choc = cash/price; //count of choc that could be purchase by cash
        int allWraps = cash/price; //count of wraps, that could be purchase by cash
        for (int i = allWraps; i >=wraps+1 ; i-=wraps) {
            choc ++;
            allWraps-=wraps;
        }
        return choc;
    }

}
