public class ChocolateBars {
    public static void main(String[] args) {
        System.out.println(chocolateBars(15, 1, 3));
    }
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
}
