package com.isa.berkeley_starter.dao;

public interface EntityDao<T, V> {
	
	public V get(T key);

	public void set(T key, V value);

	public void delete(T key);
}
