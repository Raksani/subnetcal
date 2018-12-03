public class Calculate {

    public static void main(String [] args){
        int host = 5;
        int l =0;
        for(int i=(host-1);i>=0;i--){
            l += Math.pow(2,i);
        }
        System.out.println(l);
    }

}