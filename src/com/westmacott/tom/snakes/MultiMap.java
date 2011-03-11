package com.westmacott.tom.snakes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiMap<K, V> {
	private final Map<K, List<V>> innerMap = new HashMap<K, List<V>>();
	public void put(K key, V value) {
		if (!innerMap.containsKey(key)) {
			innerMap.put(key, new ArrayList<V>());
		} 
		innerMap.get(key).add(value);
	}
	public List<V> get(K key) {
		return new ArrayList<V>(innerMap.get(key));
	}
	
	public boolean removeElement(K key, V element) {
		List<V> elements = innerMap.get(key);
		return elements != null && elements.remove(element);
	}
	
	public void remove(K mapKey) {
		innerMap.remove(mapKey);
	}
}