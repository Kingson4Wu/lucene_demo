package com.kxw.solr.entity;

import org.apache.solr.client.solrj.beans.Field;

/**
 * <b>function:</b> JavaEntity Bean；Index需要添加相关的Annotation注解，便于告诉solr哪些属性参与到index中
 * @author hoojo
 * @createDate 2011-10-19 下午05:33:27
 * @file Index.java
 * @package com.hoo.entity
 * @project SolrExample
 * @blog http://blog.csdn.net/IBM_hoojo
 * @email hoojo_@126.com
 * @version 1.0
 */
public class Index {
    //@Field setter方法上添加Annotation也是可以的
    private String id;
    @Field
    private String name;
    @Field
    private String manu;
    @Field
    private String[] cat;

    @Field
    private String[] features;
    @Field
    private float price;
    @Field
    private int popularity;
    @Field
    private boolean inStock;

    public String getId() {
        return id;
    }

    @Field
    public void setId(String id) {
        this.id = id;
    }
    //getter、setter方法

    public String toString() {
        return this.id + "#" + this.name + "#" + this.manu + "#" + this.cat;
    }
}
