package zhipeng.top.smartshare.solr.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;

import zhipeng.top.smartshare.solr.common.PropertiesUtil;

/**
 * SolrClient
 * 
 * @author shaozhipeng
 * 
 */
public class SolrClient {

	protected static final Log logger = LogFactory.getLog(PropertiesUtil.class);

	public static void main(String[] args) {
		/*
		 * HttpSolrServer
		 */
		HttpSolrServer server = new HttpSolrServer(
				PropertiesUtil.getValue("solr.server"));
		autoCompete("你好", 0, 5, 5, server);
	}

	/**
	 * 简单分页查询
	 * 
	 * @param str
	 * @param start
	 * @param limit
	 * @param server
	 */
	public static void query(String str, int start, int limit,
			HttpSolrServer server) {
		/*
		 * SolrQuery
		 */
		SolrQuery query = new SolrQuery();

		query.setQuery(str);
		query.setStart(start);
		query.setRows(limit);

		QueryResponse resp = null;
		try {
			resp = server.query(query);
			System.out.println("resp.getResults().getNumFound()->"
					+ resp.getResults().getNumFound());
			for (SolrDocument document : resp.getResults()) {
				System.out.println(document.getFieldValue("id") + "     "
						+ document.getFieldValue("post_name") + "     "
						+ document.getFieldValue("post_title") + "     "
						+ document.getFieldValue("post_date"));
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 简单自动补全
	 * 
	 * @param str
	 * @param start
	 * @param limit
	 * @param facetLimit
	 * @param server
	 */
	public static void autoCompete(String str, int start, int limit,
			int facetLimit, HttpSolrServer server) {
		/*
		 * SolrQuery
		 */
		SolrQuery query = new SolrQuery();

		query.setQuery(str);
		query.setStart(start);
		query.setTermsLimit(limit);

		QueryResponse resp = null;
		try {
			query.setQuery("世界");
			query.setStart(start);
			query.setFacet(true);
			query.setRows(limit);
			query.setFacetLimit(facetLimit);
			query.setFacetPrefix("世界");
			// schema.xml中配置的field name_autocomplete
			query.addFacetField("name_autocomplete");

			System.out.println("query str->" + query.toString());

			resp = server.query(query);

			// 自动补全结果
			System.out.println(resp.getFacetFields());

			// 查询结果
			System.out.println("resp.getResults().getNumFound()->"
					+ resp.getResults().getNumFound());
			for (SolrDocument document : resp.getResults()) {
				System.out.println(document.getFieldValue("id") + "     "
						+ document.getFieldValue("post_name") + "     "
						+ document.getFieldValue("post_title") + "     "
						+ document.getFieldValue("post_date"));
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
	}

}
