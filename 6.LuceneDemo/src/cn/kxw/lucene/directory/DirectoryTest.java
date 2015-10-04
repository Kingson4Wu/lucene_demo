package cn.kxw.lucene.directory;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Test;

import cn.kxw.lucene.utils.File2DocumentUtils;

public class DirectoryTest {

    String filePath = "D:\\Workspaces\\MyEclipse 8.5\\LuceneDemo\\luceneDatasource\\IndexWriter addDocument's javadoc.txt";
    String indexPath = "D:\\Workspaces\\MyEclipse 8.5\\LuceneDemo\\luceneIndex";

    Analyzer analyzer = new StandardAnalyzer();

    @Test
    public void test1() throws Exception {

        //	Directory dir=FSDirectory.getDirectory(indexPath);

        Directory dir = new RAMDirectory();//保存在内存的索引，可以在退出程序的时候再保存

        //file-->doc
        Document doc = File2DocumentUtils.File2Document(filePath);

        //System.out.println(filePath);

        IndexWriter indexWriter = new IndexWriter(dir, analyzer, true, MaxFieldLength.LIMITED);
        indexWriter.addDocument(doc);

        indexWriter.close();

    }

    @Test
    public void test2() throws Exception {

        Directory fsDir = FSDirectory.getDirectory(indexPath);

        //1、启动时读取
        Directory ramDir = new RAMDirectory(fsDir);

        //运行程序时操作ramDir
        IndexWriter ramIndexWriter = new IndexWriter(ramDir, analyzer, MaxFieldLength.LIMITED);
        //添加Document
        Document doc = File2DocumentUtils.File2Document(filePath);
        ramIndexWriter.addDocument(doc);
        ramIndexWriter.close();

        //退出时保存

        IndexWriter fsIndexWriter = new IndexWriter(fsDir, analyzer, MaxFieldLength.LIMITED);
        //为true则每次索引重填
        fsIndexWriter.addIndexesNoOptimize(new Directory[]{ramDir});
        //文件合并
        fsIndexWriter.close();

    }

    @Test
    public void test3() throws Exception {
        Directory fsDir = FSDirectory.getDirectory(indexPath);

        IndexWriter fsIndexWriter = new IndexWriter(fsDir, analyzer, MaxFieldLength.LIMITED);
        //fsIndexWriter.flush();
        //fsIndexWriter.commit();//清缓存中的索引

        fsIndexWriter.optimize();//优化，合并小文件成为大文件

        fsIndexWriter.close();
    }
}
