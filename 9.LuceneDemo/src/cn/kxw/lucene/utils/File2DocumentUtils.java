package cn.kxw.lucene.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.NumberTools;

public class File2DocumentUtils {

    public static Document File2Document(String path) {

        System.out.println(path);

        File file = new File(path);
        Document doc = new Document();
        doc.add(new Field("name", file.getName(), Store.YES, Index.ANALYZED));
        doc.add(new Field("content", readFileContent(file), Store.YES, Index.ANALYZED));
        //doc.add(new Field("size",String.valueOf(file.length()),Store.YES,Index.NOT_ANALYZED));
        doc.add(new Field("size", NumberTools.longToString(file.length()), Store.YES, Index.NOT_ANALYZED));
        doc.add(new Field("path", file.getAbsolutePath(), Store.YES, Index.NO));

        return doc;
    }

    public static String readFileContent(File file) {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            StringBuffer content = new StringBuffer();

            for (String line = null; (line = reader.readLine()) != null; ) {
                content.append(line).append("\n");
            }

            return content.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    //获取name属性的值 的两种方法

    public static void printDocumentInfo(Document doc) {

        //1.
        //Field f=doc.getField("name");
        //f.stringValue();
        //2.
        //doc.get("name");

        System.out.println("------------------------------------------");
        System.out.println("name =" + doc.get("name"));
        System.out.println("content =" + doc.get("content"));
        System.out.println("size =" + NumberTools.stringToLong(doc.get("size")));
        System.out.println("path =" + doc.get("path"));
    }
}
