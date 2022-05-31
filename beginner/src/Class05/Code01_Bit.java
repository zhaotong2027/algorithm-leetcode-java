package Class05;

/**
 * 基础：位运算Class01_Code01
 * 位图，像素组成的坐标，每个四字母，可以标识是否在某区域内
 * long类型在java里是64位有符号的原始数据类型
 * HashSet 底层是 HashMap，具备去重功能，但无索引，不可被重复遍历，且无序
 * 目的：？把表示位图位置的四个点转换成拼接的 bit 存入HashSet，利用HashSet特性做位图操作
 * ？拼接和转换过程需要用到位运算，比如整个位图就是一个HashSet集合，
 * ？在位图中添加点，就是HashSet添加一个值
 */
public class Code01_Bit {

    public static void main(String[] args) {
        int a = 123456;
        System.out.println(a / 64);
        System.out.println(a >> 6);
    }

    public static class BitMap {
        private long[] bits;

        public BitMap(int max) {
            // 右移六位是除以64的意思，即:（max + 64) / 64）
            // 需要准备整数的个数，表示的边界范围
            // (max + 64) >> 6 表示能装下0～64个整数
            bits = new long[(max + 64) >> 6];
        }

        public void add(int num) {
            // bit[num >> 6] 表示 num / 64 可以定位是哪个整数

            // num & 63 表示 num % 64 取余数 可以保存 二进制数 的后七位
            // %表示取余数，%64的余数表示，64占到了7位（64，32，16，8，4：00100，2：00010，1：00001），即保留后七位数，前面全换成0
            // 63是0001111111，&表示只有都是1的才保留，相当于后7位的1均保留，与%64保留的相同
            // + — * / 运算速度远低于位运算 >> << & | ~  10倍起步

            // 1L << （num & 63） 表示 左移num的7位

            // |= 或等于 表示根据右侧或运算重新赋值   ｜ 表示只要有1就要
            // 比如数字 170， /64=2，arr[2]   %64=42，在第42位
            // 把170移进来可以写成 arr[2] = arr[2] | (1<<42)  也可以写成 arr[2] ｜= (1<<42)
            bits[num >> 6] |= (1L << (num & 63));
        }

        public void delete(int num) {
            // num >> 6 定位到整数
            // bits[num >> 6] 定位到的整数用bit表示
            // num & 63 定位位数
            // 1L << (num & 63) 根据定位的位数左移，找到表示数字的位置
            // ~(1L << (num & 63)) 根据找到数字的位置取反（准备删除操作）

            // &= 且等于 表示两者都有的才保存，可分为如下两部，先取&交集，再赋值=
            // bits[num >> 6] & ~(1L << (num & 63)) 表示原bit和取反bit的交集
            // 然后再把结果赋值给 bits[num >> 6]，就删掉了

            // 10111011 要改第4位 1011【1】011
            // 就让该数字和 11110111（只给第4位取反）进行 & 运行，保留所有的除第4位的1
            //   10111011
            // & 11110111   （&运算取交集，都有1才算有）
            // = 10110011
            // 这样 10111011 表示的数字就删掉了 第4位是1的数字

            // 如何做 11110111 这个数呢？采取的方法是做出 00001000 然后取反
            // 00001000就最好做了，直接第四位的数字获取在bit上的位置，让1左移到这个位置即可
            // 程序中 1 会默认成整数，8位只能表示到32，无法左移42位，所以写成1L，表示long类型的1，有64位
            bits[num >> 6] &= ~(1L << (num & 63));
        }

        public boolean contains(int num) {
            return (bits[num >> 6] & (1L << (num & 63))) != 0;
        }
    }
}
