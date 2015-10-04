package cn.kxw.lucene;

import org.apache.lucene.document.Document;
import org.junit.Test;

import cn.kxw.lucene.utils.File2DocumentUtils;

public class IndexDaoTest {

    String filePath = "D:\\Workspaces\\MyEclipse 10\\6.LuceneDemo\\luceneDatasource\\小笑话_总统的房间 Room .txt";

    IndexDao indexDao = new IndexDao();

    @Test
    public void testSave() {
        Document doc = File2DocumentUtils.File2Document(filePath);
        indexDao.save(doc);

    }

    @Test
    public void testSearch() {
        // String queryString = "IndexWriter";
        String queryString = "房间";
        //String queryString = "笑话";
        //String queryString = "room";
        // String queryString = "content:绅士";
        QueryResult qr = indexDao.search(queryString, 0, 10);

        System.out.println("总共有【" + qr.getRecordCount() + "】条匹配结果");
        for (Document doc : qr.getRecordList()) {
            File2DocumentUtils.printDocumentInfo(doc);
        }
    }

}
