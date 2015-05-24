package com.uenpjdo1;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import com.google.appengine.api.datastore.Key;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PrimaryKey;
import java.util.HashSet;
import java.util.List;

@PersistenceCapable
public class User {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	@Persistent
	private HashSet<Article> children = new HashSet<Article>();
	
	@Persistent
    private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Key getKey() {
		return key;
	}

	public HashSet<Article> getChildren() {
		return children;
	}

	public void addChild(Article child) {
		this.children.add(child);
	}
}