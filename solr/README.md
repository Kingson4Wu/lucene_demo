<http://www.cnblogs.com/hoojo/archive/2011/10/21/2220431.html>

cd E:\JAR\solr\apache-solr-3.4.0\example

然后利用java命令，启动jetty服务器。Java –jar start.jar

http://127.0.0.1:8983/solr/#/

<http://zhidao.baidu.com/>
先说一点部署之后肯定会有人用solrj，solr 4.0好像添加了不少东西，其中CommonsHttpSolrServer这个类改

名为HttpSolrServer，我是找了半天才发现，大家以后可以注意。

***

<http://m.blog.csdn.net/blog/fz2543122681/44905643>
Elasticsearch 与 Solr 的比较总结
二者安装都很简单；
Solr 利用 Zookeeper 进行分布式管理，而 Elasticsearch 自身带有分布式协调管理功能;
Solr 支持更多格式的数据，而 Elasticsearch 仅支持json文件格式；
Solr 官方提供的功能更多，而 Elasticsearch 本身更注重于核心功能，高级功能多有第三方插件提供；
Solr 在传统的搜索应用中表现好于 Elasticsearch，但在处理实时搜索应用时效率明显低于 Elasticsearch。
Solr 是传统搜索应用的有力解决方案，但 Elasticsearch 更适用于新兴的实时搜索应用。