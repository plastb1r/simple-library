package com.example.library.repos;

import java.util.List;
import java.util.Optional;

import com.example.library.entity.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {

        @Autowired
        private JdbcTemplate jdbcTemplate;

        public int count() {
                return jdbcTemplate.queryForObject("select count(*) from BOOKS", Integer.class);
        }

        public List<Book> findAll() {
                return jdbcTemplate.query("select * from  BOOKS", (rs, rowNum) -> new Book(rs.getString("book_isn"),
                                rs.getString("book_author"), rs.getString("book_name"), rs.getString("book_owner")));
        }

        public Optional<Book> findByIsn(String isn) {
                Book book = null;
                try {
                        book = jdbcTemplate.queryForObject("select * from  BOOKS where book_isn = ?",
                                        new Object[] { isn },
                                        (rs, rowNum) -> new Book(rs.getString("book_isn"), rs.getString("book_author"),
                                                        rs.getString("book_name"), rs.getString("book_owner")));
                } catch (EmptyResultDataAccessException e) {
                        return Optional.ofNullable(book);
                }
                return Optional.ofNullable(book);
        }

        public int save(Book book) {
                return jdbcTemplate.update(
                                "insert into BOOKS (book_isn, book_author, book_name, book_owner) values (?,?,?,?)",
                                book.getIsn(), book.getAuthor(), book.getName(), book.getOwner());
        }

        public int update(Book book) {
                return jdbcTemplate.update(
                                "update BOOKS set book_author = ?, book_name = ?, book_owner = ? where book_isn = ?",
                                book.getAuthor(), book.getName(), book.getOwner(), book.getIsn());
        }

        public int updateWhenOwnerDelete(String name) {
                return jdbcTemplate.update("update BOOKS set book_owner = ? where book_owner = ?", null, name);
        }

        public int deleteByIsn(String isn) {
                return jdbcTemplate.update("delete BOOKS where book_isn = ?", isn);
        }

}
