package net.siinergy.dsc.service;

import java.io.IOException;

import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.siinergy.dsc.domain.Cve;



@Service
public class ElasticService {

	@Autowired
	Client client;
	
	public String index(Cve cve) throws IOException {
		IndexRequestBuilder response = client.prepareIndex("dsc_index", "_doc");
		System.out.println(cve.toString());
		response.setSource(cve.toString(), XContentType.JSON);
		System.out.println("response id:" + response.get().getId());
		return response.get().getResult().toString();

	}
	

}
