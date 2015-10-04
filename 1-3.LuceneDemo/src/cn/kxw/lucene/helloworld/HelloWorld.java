package cn.kxw.lucene.helloworld;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import org.junit.Test;

import cn.kxw.lucene.utils.File2DocumentUtils;

import java.io.File;

/**
 * @author sony
 */

public class HelloWorld {

    String projectPath = System.getProperty("user.dir");

    String filePath = projectPath + File.separator + "luceneDatasource\\IndexWriter addDocument's javadoc.txt";
    String indexPath = projectPath + File.separator + "luceneIndex";

    Analyzer analyzer = new StandardAnalyzer();

    /**
     * 创建索引
     * <p/>
     * IndexWriter用来操作索引库（增、删、改）
     */

    @Test
    public void createIndex() throws Exception {
//		File file =new File(path);
//		Document doc=null;

        //file-->doc
        Document doc = File2DocumentUtils.File2Document(filePath);

        //System.out.println(filePath);

        IndexWriter indexWriter = new IndexWriter(indexPath, analyzer, true, MaxFieldLength.LIMITED);
        indexWriter.addDocument(doc);

        indexWriter.close();

    }

    /**
     * 搜索
     *
     * @throws ParseException
     */

    @Test
    public void search() throws Exception {

        String queryString = "document";
        //1.把搜索的文本解析为Query

        String[] fields = {"name", "content"};
        QueryParser queryParser = new MultiFieldQueryParser(fields, analyzer);
        Query query = queryParser.parse(queryString);

        //2.进行查询
        IndexSearcher indexSearcher = new IndexSearcher(indexPath);
        Filter filter = null;
        TopDocs topDocs = indexSearcher.search(query, filter, 10000);

        System.out.println("总共有" + topDocs.totalHits + "条匹配结果！");

        //打印结果

        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            int docSn = scoreDoc.doc;//文档内部编号
            Document doc = indexSearcher.doc(docSn);//根据编号取出相应文档
            File2DocumentUtils.printDocumentInfo(doc);//打印出文档信息

        }

    }

}
