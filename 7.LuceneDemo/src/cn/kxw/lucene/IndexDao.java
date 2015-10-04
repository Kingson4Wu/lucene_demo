package cn.kxw.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jeasy.analysis.MMAnalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.NumberTools;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.RangeFilter;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;

public class IndexDao {

    String indexPath = "D:\\Workspaces\\MyEclipse 10\\7.LuceneDemo\\luceneIndex";

    // Analyzer analyzer = new StandardAnalyzer();
    Analyzer analyzer = new MMAnalyzer();// 词库分词

    /**
     * 添加/创建索引
     *
     * @param doc
     */
    public void save(Document doc) {
        IndexWriter indexWriter = null;
        try {
            indexWriter = new IndexWriter(indexPath, analyzer, MaxFieldLength.LIMITED);
            indexWriter.addDocument(doc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                indexWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Term是搜索的最小单位，代表某个 Field 中的一个关键词，如：<title, lucene>
     * <p/>
     * new Term( "title", "lucene" );
     * <p/>
     * new Term( "id", "5" );
     * <p/>
     * new Term( "id", UUID );
     *
     * @param term
     */
    public void delete(Term term) {
        IndexWriter indexWriter = null;
        try {
            indexWriter = new IndexWriter(indexPath, analyzer, MaxFieldLength.LIMITED);
            indexWriter.deleteDocuments(term);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                indexWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 更新索引
     * <p/>
     * <pre>
     * indexWriter.deleteDocuments(term);
     * indexWriter.addDocument(doc);
     * </pre>
     *
     * @param term
     * @param doc
     */
    public void update(Term term, Document doc) {
        IndexWriter indexWriter = null;
        try {
            indexWriter = new IndexWriter(indexPath, analyzer, MaxFieldLength.LIMITED);
            indexWriter.updateDocument(term, doc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                indexWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * <pre>
     * totalPage = recordCount / pageSize;
     * if (recordCount % pageSize &gt; 0)
     * 	totalPage++;
     * </pre>
     *
     * @param queryString
     * @param firstResult
     * @param maxResults
     * @return
     */
    public QueryResult search(String queryString, int firstResult, int maxResults) {
    /*	IndexSearcher indexSearcher = null;
		try {
			// 1，把要搜索的文本解析为 Query
			String[] fields = { "name", "content" };
			QueryParser queryParser = new MultiFieldQueryParser(fields, analyzer);
			Query query = queryParser.parse(queryString);
			
			// 2，进行查询
						indexSearcher = new IndexSearcher(indexPath);
						Filter filter = null;
						TopDocs topDocs = indexSearcher.search(query, filter, 10000);
						int recordCount = topDocs.totalHits;
						List<Document> recordList = new ArrayList<Document>();

						// ============== 准备高亮器
						Formatter formatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
						Scorer scorer = new QueryScorer(query);
						Highlighter highlighter = new Highlighter(formatter, scorer);
						//关键字最频繁的附近50个字符
						Fragmenter fragmenter = new SimpleFragmenter(50);//取50个字符
						highlighter.setTextFragmenter(fragmenter);
						// ==============
						
						// 3，取出当前页的数据
						int end = Math.min(firstResult + maxResults, topDocs.totalHits);
						for (int i = firstResult; i < end; i++) {
							ScoreDoc scoreDoc = topDocs.scoreDocs[i];
							int docSn = scoreDoc.doc; // 文档内部编号
							Document doc = indexSearcher.doc(docSn); // 根据编号取出相应的文档
						
							// =========== 高亮
							// 返回高亮后的结果，如果当前属性值中没有出现关键字，会返回 null
							//analyzer分词器
							//从文档的内容content里面根据关键字（分词操作）搜索，并在频繁出现的50个字符中对关键字高亮
							String hc = highlighter.getBestFragment(analyzer, "content", doc.get("content"));
							if (hc == null) {//如果搜索内容没有所找的关键字，返回null
								String content = doc.get("content");
								int endIndex = Math.min(50, content.length());//得到两者较小值
								hc = content.substring(0, endIndex);// 最多前50个字符
							}
							doc.getField("content").setValue(hc);
							// ===========
							recordList.add(doc);
						}
			return  new QueryResult(recordCount, recordList);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}*/
        try {
            // 1，把要搜索的文本解析为 Query
            String[] fields = {"name", "content"};
            QueryParser queryParser = new MultiFieldQueryParser(fields, analyzer);
            Query query = queryParser.parse(queryString);

            return search(query, firstResult, maxResults);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public QueryResult search(Query query, int firstResult, int maxResults) {
        IndexSearcher indexSearcher = null;

        try {
            // 2，进行查询
            indexSearcher = new IndexSearcher(indexPath);
            Filter filter = null;
            TopDocs topDocs = indexSearcher.search(query, filter, 10000);
            int recordCount = topDocs.totalHits;
            List<Document> recordList = new ArrayList<Document>();

            // ============== 准备高亮器
            Formatter formatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
            Scorer scorer = new QueryScorer(query);
            Highlighter highlighter = new Highlighter(formatter, scorer);

            Fragmenter fragmenter = new SimpleFragmenter(50);
            highlighter.setTextFragmenter(fragmenter);
            // ==============

            // 3，取出当前页的数据
            int end = Math.min(firstResult + maxResults, topDocs.totalHits);
            for (int i = firstResult; i < end; i++) {
                ScoreDoc scoreDoc = topDocs.scoreDocs[i];

                int docSn = scoreDoc.doc; // 文档内部编号
                Document doc = indexSearcher.doc(docSn); // 根据编号取出相应的文档

                // =========== 高亮
                // 返回高亮后的结果，如果当前属性值中没有出现关键字，会返回 null
                String hc = highlighter.getBestFragment(analyzer, "content", doc.get("content"));
                if (hc == null) {
                    String content = doc.get("content");
                    int endIndex = Math.min(50, content.length());
                    hc = content.substring(0, endIndex);// 最多前50个字符
                }
                doc.getField("content").setValue(hc);
                // ===========
                recordList.add(doc);
            }

            // 返回结果
            return new QueryResult(recordCount, recordList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                indexSearcher.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
