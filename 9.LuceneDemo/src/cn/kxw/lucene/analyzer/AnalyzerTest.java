package cn.kxw.lucene.analyzer;

import java.io.StringReader;

import jeasy.analysis.MMAnalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.junit.Test;

public class AnalyzerTest {

    String text = "IndexWriter addDocument's  a javadoc.txt";
    String zhText = "我们都爱托雷斯";


    //英文分词器
    Analyzer a1 = new StandardAnalyzer();
    Analyzer a2 = new SimpleAnalyzer();

    //中文分词器
    Analyzer a3 = new CJKAnalyzer();//二分法分词
    Analyzer a4 = new MMAnalyzer();//词库分词

    @Test
    public void test() throws Exception {

        analyze(a1, text);
        analyze(a2, text);
        analyze(a3, zhText);
        analyze(a4, zhText);

    }

    public void analyze(Analyzer analyzer, String text) throws Exception {

        System.out.println("----------------->分词器：" + analyzer.getClass());

        TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(text));
        for (Token token = new Token(); (token = tokenStream.next(token)) != null; ) {
            System.out.println(token);
        }
    }
}
