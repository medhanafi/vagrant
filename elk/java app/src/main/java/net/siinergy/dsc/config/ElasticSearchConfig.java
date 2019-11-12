package net.siinergy.dsc.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {

	@Value("${elasticsearch.host}")
	private String elasticHost;
	@Value("${elasticsearch.http.port}")
	private int elasticHttpPort;
	@Value("${elasticsearch.transport.tcp.port}")
	private int elasticTransportTcpPort;
	@Value("${elasticsearch.cluster.name}")
	private String elasticClusterName;
	@Value("${elasticsearch.node.name}")
	private String elasticNodeName;

	@Bean
	public Client client() {
		TransportClient client = null;
		try {
			Settings settings = Settings.builder().put("cluster.name", this.elasticClusterName)
					.put("node.name", this.elasticNodeName).build();
			client = new PreBuiltTransportClient(settings);
			client.addTransportAddress(
					new TransportAddress(InetAddress.getByName(this.elasticHost), this.elasticTransportTcpPort));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return client;
	}
}
