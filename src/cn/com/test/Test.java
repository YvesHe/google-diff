/**   
 * Filename:    Test.java   
 * Copyright:   Copyright (c)2010  
 * Company:     dreams8 
 * @version:    1.0   
 * @since:  JDK 1.7
 * Create at:   2017-1-16 下午4:00:43   
 * Description:  
 *   
 * Modification History:   
 * Date    Author      Version     Description   
 * ----------------------------------------------------------------- 
 * 2017-1-16 Yves He      1.0     1.0 Version   
 */
package cn.com.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

import name.fraser.neil.plaintext.diff_match_patch;
import name.fraser.neil.plaintext.diff_match_patch.Diff;
import name.fraser.neil.plaintext.diff_match_patch.Operation;

public class Test {

    public static void main(String[] args) {
        diff_match_patch dmp = new diff_match_patch();
        /* diff */
        dmp.Diff_DualThreshold = 32;// ?
        dmp.Diff_EditCost = 4;// 至少要多少个相同的字符(包括空格)
        dmp.Diff_Timeout = 1.0f;// 设置超时时间

        String text1 = readFileContent("files\\file1.txt");
        String text2 = readFileContent("files\\file2.txt");

        System.out.println("******文本内容*******");
        System.out.println("******text1*******");
        System.out.println(text1);
        System.out.println("******text2*******");
        System.out.println(text2);
        System.out.println("******文本内容*******");

        LinkedList<Diff> diffs = dmp.diff_main(text1, text2);
        System.out.println(diffs);

        /* patch */
        // System.out.println("patch *********");
        // LinkedList<Patch> patch = dmp.patch_make(text1, text2);
        // System.out.println(patch);
    }

    public static String readFileContent(String filePath) {

        // 问题: 当一个文件特别大的时候,String不能完全放下的时候,如何比较?
        // 问题: 当一个文件换行时,可以能连接着下一个字母,如何处理.
        // 问题: StringBuffered 转成String后会不会丢失内容.

        FileInputStream fis = null;
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();

        try {
            fis = new FileInputStream(filePath);
            br = new BufferedReader(new InputStreamReader(fis));

            while (br.ready()) {
                String result = br.readLine();
                sb.append(result);
                sb.append("\r\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

    public static void Test1(String[] args) {
        diff_match_patch dmp = new diff_match_patch();

        /* diff */
        dmp.Diff_DualThreshold = 32;
        dmp.Diff_EditCost = 4;// 至少要多少个相同的字符(包括空格)
        dmp.Diff_Timeout = 1.0f;// 设置超时时间

        String text1 = "hello";
        String text2 = "ahallo2";
        LinkedList<Diff> list = dmp.diff_main(text1, text2);
        System.out.println(text1);
        LinkedList<Diff> list2 = dmp.diff_main(text1, text2, false);
        System.out.println(list);
        System.out.println(list2);

        System.out.println("********");

        LinkedList<Diff> diffs = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            Diff d = new Diff(Operation.DELETE, "yves" + i);
            diffs.add(d);
        }

        dmp.diff_cleanupEfficiency(diffs);
        dmp.diff_cleanupMerge(diffs);
        dmp.diff_cleanupSemantic(diffs);
        dmp.diff_cleanupSemantic(diffs);
        dmp.diff_cleanupSemanticLossless(diffs);
        dmp.diff_levenshtein(diffs);
        dmp.diff_prettyHtml(diffs);
        dmp.diff_text1(diffs);
        dmp.diff_text2(diffs);
        dmp.diff_toDelta(diffs);
        dmp.diff_toDelta(diffs);

        dmp.diff_xIndex(diffs, 1);

        System.out.println(diffs);

    }
}
