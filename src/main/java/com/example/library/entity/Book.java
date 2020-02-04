package com.example.library.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class Book {
	
	@Id
	@Column(name = "book_isn")
	private String isn;

	@Column(name="book_author")
	private String author;

	@Column(name="book_name")
	private String name;

	@Column(name="book_owner")
	private String owner;
	
}
