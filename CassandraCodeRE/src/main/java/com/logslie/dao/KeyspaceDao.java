package com.logslie.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;



import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSlicesQuery;

public class KeyspaceDao {
	private Logger logger = Logger.getLogger(KeyspaceDao.class);
	private Cluster cluster;

	/**
	 * @param cluster
	 */
	public KeyspaceDao(Cluster cluster) {
		super();
		this.cluster = cluster;
	}

	public List<String> getColumnsFamily(String keyspace) {
		List<String> listClass = new ArrayList<String>();
		List<ColumnFamilyDefinition> listCF = this.cluster.describeKeyspace(
				keyspace).getCfDefs();

		for (int i = 0; i < listCF.size(); i++) {
			listClass.add(listCF.get(i).getName());

		}

		return listClass;
	}

	public ArrayList<ArrayList<String>> getConstructorAttributes(String keyspace,String columnFamily) {
		
		ArrayList<ArrayList<String>> columnsName = new ArrayList<ArrayList<String>>();

		StringSerializer sser = new StringSerializer();
		Keyspace keyspaceObject = HFactory.createKeyspace(keyspace,
				this.cluster);
		RangeSlicesQuery<String, String, String> rangeSlicesQuery = HFactory
				.createRangeSlicesQuery(keyspaceObject, sser, sser, sser);
		rangeSlicesQuery.setColumnFamily(columnFamily);
		rangeSlicesQuery.setRange("", "", false, 10000000);
		QueryResult<OrderedRows<String, String, String>> result = rangeSlicesQuery
				.execute();

		logger.info("list size:" + result.get().getList().size());
		List<Row<String, String, String>> resultList = result.get().getList();

		for (int i = 0; i < resultList.size(); i++) {
			ArrayList<String> attByKey = new ArrayList<String>();
			if (!resultList.get(i).getColumnSlice().getColumns().isEmpty()) {

				List<HColumn<String, String>> list = resultList.get(i)
						.getColumnSlice().getColumns();

				for (int j = 0; j < list.size(); j++) {

					attByKey.add(list.get(j).getName());
				}

			}
			if(!this.contains(attByKey,columnsName)){
				columnsName.add(attByKey);

			}
			
		}
		return columnsName;

	}
	
	private boolean contains(ArrayList<String> list, ArrayList<ArrayList<String>>listOfList){
		boolean found = false;
		int i = 0;
		while(!found && i < listOfList.size()){
			ArrayList<String> objectList = listOfList.get(i);
			if(objectList.size() == list.size()){
				boolean exist = true;
				int j = 0;
				while (exist && j<list.size()){
					if(!objectList.contains(list.get(j))){
						exist = false;
					}
					j++;
				}
				
				if(exist){
					found = true;
				}
				
			}
			i++;
		}
		return found;
	}
}
