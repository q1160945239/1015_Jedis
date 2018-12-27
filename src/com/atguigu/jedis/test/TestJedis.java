package com.atguigu.jedis.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.jupiter.api.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;

class TestJedis {

	@Test
	void test() {
		Jedis jedis = new Jedis("192.168.6.10", 6379);
		
		String pong = jedis.ping();
		
		System.out.println(pong);

		System.out.println(pong);

		System.out.println("hello");
	}
	

	@Test
	public void testSentinel() throws Exception {
		
		Set<String> set = new HashSet<>();
		// set中放的是哨兵的Ip和端口
		set.add("192.168.6.10:26379");
		
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		
		JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("mymaster", set, poolConfig, 60000);
		
		Jedis jedis = jedisSentinelPool.getResource();
		
		String value = jedis.get("k7");
		
		jedis.set("Jedis", "Jedis");
		
		System.out.println(value);
		
	}
	
	@Test
	public void testCluster(){
		
		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
		
		//Jedis Cluster will attempt to discover cluster nodes automatically
		
		jedisClusterNodes.add(new HostAndPort("192.168.6.10", 6379));
		
		JedisCluster jc = new JedisCluster(jedisClusterNodes);
		
		jc.set("foo", "bar");
		
		String value = jc.get("foo");
		
		System.out.println(value);
	}

}
