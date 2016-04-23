
/**
 * 此模板为后缀数组的模板, 参考doc目录下《后缀数组——处理字符串的有力工具》论文实现
 * Created by shenyuan on 16/4/18.
 */
public class SuffixTree {
    public static void main(String[] args) {
        String s = "abc#";
        int n = s.length();
        int[] r = new int[n];
        for (int i = 0; i < n; i++)
            r[i] = s.charAt(i) - '#';

        SuffixTree suffixTree = new SuffixTree();
        int m = 200;  //比r中所有的字符串整数值要大
        int maxn = n + 10 > m ? n + 10 : m;
        suffixTree.wa = new int[maxn]; //临时排名数组
        suffixTree.wb = new int[maxn]; //对第二关键字排序时, 使用的临时排名数组
        suffixTree.wv = new int[maxn]; //对第一关键字排序时, 使用的临时sa数组
        suffixTree.ws = new int[maxn]; //基数排序时用到的计数数组
        suffixTree.rank = new int[maxn]; //排名数组
        suffixTree.height = new int[maxn];  //height数组
        int[] sa = new int[maxn];
        suffixTree.da(r, sa, n, m);
        suffixTree.calHeight(r, sa, n-1);
    }

    int[] wa, wb, wv, ws;  //临时数组
    int[] rank, height;

    boolean cmp(int[] r, int a, int b, int l) {
        return r[a] == r[b] && r[a+l] == r[b+l];
    }

    /**
     * 求后缀数组
     * @param r: 字符串数组, 需要保证最大值小于m, r[n-1]为0, 其余大于0
     * @param sa: 后缀数组, 存放结果
     * @param n: 字符串数组长度
     * @param m: 比原字符串字符都大的整数值
     */
    void da(int[] r, int[] sa, int n, int m) {
        int i, j, p;
        int[] x = wa, y = wb;
        int[] tmp;
        /*
         * 进行第一次基数排序
         */
        for (i = 0; i < m; i++)
            ws[i] = 0;
        for (i = 0; i < n; i++)
            ws[x[i]=r[i]]++;
        for (i = 1; i < m; i++)
            ws[i] += ws[i-1];
        for (i = n - 1; i >= 0; i--)
            sa[--ws[x[i]]] = i;

        /*
         * 2倍增算法
         */
        for (j = 1, p = 1; p < n; j *= 2, m = p) {
            //对第二关键字基数排序
            for (p = 0, i = n - j; i < n; i++)
                y[p++] = i;
            for (i = 0; i < n; i++)
                if (sa[i] >= j)
                    y[p++] = sa[i] - j;
            //对第一关键字排序
            for (i = 0; i < n; i++)
                wv[i] = x[y[i]];
            for (i = 0; i < m; i++)
                ws[i] = 0;
            for (i = 0; i < n; i++)
                ws[wv[i]]++;
            for (i = 1; i < m; i++)
                ws[i] += ws[i-1];
            for (i = n - 1; i >= 0; i--)
                sa[--ws[wv[i]]] = y[i];
            for (tmp = x, x = y, y = tmp, p = 1, x[sa[0]] = 0, i = 1; i < n; i++)
                x[sa[i]] = cmp(y, sa[i-1], sa[i], j) ? p - 1 : p++;
        }
    }

    /**
     * 计算排名数组及height
     * @param r: 字符串数组
     * @param sa: 后缀数组
     * @param n: 需要求的height数组最大的下标. 注意这里不是字符串数组的长度.
     */
    void calHeight(int[] r, int[] sa, int n) {
        int i, j, k = 0;
        for (i = 1; i <= n; i++)
            rank[sa[i]] = i;
        for (i = 0; i < n; height[rank[i++]] = k) {
            if (k > 0) k--;
            for (j = sa[rank[i] -1]; r[i+k] == r[j+k]; k++);
        }
    }
}
