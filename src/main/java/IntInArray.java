import java.util.HashSet;

public class IntInArray {
    public static void main(String[] args) {
        int[] l = new int[]{5,3,5};
        System.out.println(isIntInArray(l, 11));
        System.out.println(isIntBetter(l, 11));
    }

    public static boolean isIntInArray(int[] l, int x) {
        for (int a = 0; a < l.length; a++){
            for(int b = a+1; b < l.length; b++){
               if ((l[a] + l [b]) == x) {
                 return true;
                }
            }
        }
        return false;
    }

    public static boolean isIntBetter (int[] l, int x) {
        HashSet <Integer> complimentaries = new HashSet<Integer>();
        for (int i : l){
             if(complimentaries.contains(i)) {
                 return true;
             }
                 complimentaries.add(x-i);
        }
        return false;
    }
}

