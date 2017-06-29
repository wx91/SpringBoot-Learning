package com.wangx.log;

import org.apache.log4j.AppenderSkeleton;
import org.slf4j.event.LoggingEvent;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * 日志插入MongoDB
 *
 * @author 程序猿DD
 * @version 1.0.0
 * @date 16/5/23 下午4:53.
 * @blog http://blog.didispace.com
 */
public class MongoAppender extends AppenderSkeleton {

	private MongoClient mongoClient;
	private MongoDatabase mongoDatabase;
	private MongoCollection<BasicDBObject> logsCollection;

	private String connectionUrl;
	private String databaseName;
	private String collectionName;

	protected void append(LoggingEvent loggingEvent) {

		if (mongoDatabase == null) {
			MongoClientURI connectionString = new MongoClientURI(connectionUrl);
			mongoClient = new MongoClient(connectionString);
			mongoDatabase = mongoClient.getDatabase(databaseName);
			logsCollection = mongoDatabase.getCollection(collectionName, BasicDBObject.class);
		}
		logsCollection.insertOne(BasicDBObject.parse(loggingEvent.getMessage()));

	}

	public void close() {
		if (mongoClient != null) {
			mongoClient.close();
		}
	}

	public boolean requiresLayout() {
		return false;
	}

	public String getConnectionUrl() {
		return connectionUrl;
	}

	public void setConnectionUrl(String connectionUrl) {
		this.connectionUrl = connectionUrl;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

}
