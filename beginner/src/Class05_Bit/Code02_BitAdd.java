package Class05_Bit;

/**
 * 计算机执行 + - * / 操作的底层是位运算
 * 手写位运算 + - * / 不如java原生的 + - * / 跑的快，因为到底层执行时需要虚拟机翻译，
 * 自带的跟底层汇编语言有交互
 * 但直接用位运算肯定比 + - * / 快
 * 因此算法大多直接用 位运算 而不是 用位运算封装过的加减乘除
 */
public class Code02_BitAdd {
    public static void main(String[] args) {
        int a = 46;
        // 46 = 32+8+4+2 => 00101110
        System.out.println(a);
        printBit(a);

        int b = 20;
        // 20 = 16+4 => 00010100
        System.out.println(b);
        printBit(b);


        // a ^ b 异或运算，只有不同时为1才保留（相加，进位忽略）
        // 例子：
        //   0110110
        // ^ 1110111
        // = 1000001
        // 实际 46^20
        //   00101110
        // ^ 00010100
        // = 00111010
        // 也叫无进位相加


        // 原始的 a+b 相当于 无进位相加的结果，再加进位信息
        // 00111010 => 2+8+16+32 = 58
        // 第3位有进位，进到了第4位是 8，58+8=66
        // 46 + 20 = 66

        // 计算哪个位置有进位可以用 & 运算，即只有都是1的才保留，然后像左移1位
        // 所以加法是 (a^b + (a&b)<<1)
        // 66 = 64+2 = 10000010
        int c = 66;
        System.out.println(c);
        printBit(c);
        // a^b    => 00111010
        // a&b    => 00000100
        // a&b<<1 => 00001000

        // 循环 ^ 和 & << 直到 进位信息结束，算出来的数就是结果
        //   00111010
        // ^ 00001000
        // = 00110010  无进位加

        // & 00001000  计算进位
        //<< 00010000


        //   00110010
        //   00010000

        // ^ 00100010  无进位加

        // & 00010000  计算进位
        //<< 00100000


        //   00100010
        //   00100000

        // ^ 00000010

        // & 00100000
        //<< 01000000


        //   00000010
        //   01000000

        // ^ 01000010

        // & 00000000  没有进位信息
        // 则 01000010 为最终结果，2+64= 66

        printBit(-1);
    }

    public static void printBit(int a) {
        for (int i = 7; i >= 0; i--) {
            System.out.print((a & (1 << i)) == 0 ? 0 : 1);
        }
        System.out.println();
    }
}
