package org.serratec.backend.projeto04.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.serratec.backend.projeto04.dto.BookDTO;
import org.serratec.backend.projeto04.entity.BookEntity;
import org.serratec.backend.projeto04.exception.BookNotFoundException;
import org.serratec.backend.projeto04.mapper.BookMapper;
import org.serratec.backend.projeto04.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BookService {

	@Autowired
	BookRepository repository;
	@Autowired
	BookMapper mapper;

	public BookDTO create(BookDTO book) {
		return mapper.toDTO(repository.save(mapper.toEntity(book)));

	}

	public void update(Integer id, BookEntity bookTemp) throws BookNotFoundException {
		BookEntity book = getById(id);
		if (bookTemp.getTitle() != null) {
			book.setTitle(bookTemp.getTitle());
		}
		if (bookTemp.getAuthor() != null) {
			book.setAuthor(bookTemp.getAuthor());
		}
		if (bookTemp.getType() != null) {
			book.setType(bookTemp.getType());
		}
		if (bookTemp.getPublicationDate() != null) {
			book.setPublicationDate(bookTemp.getPublicationDate());
		}
		repository.save(book);
	}

	public List<BookDTO> getAll(String ordem) {
		return repository.findAll(Sort.by(ordem)).stream().map(mapper::toDTO).collect(Collectors.toList());
	}

	public BookDTO getById(Integer id) throws BookNotFoundException {
		Optional<BookEntity> book = repository.findById(id);
		if (book.isEmpty()) {
			throw new BookNotFoundException("Não existe livro com esse id!");
		}
		return mapper.toDTO(book.get());

	}

	public void delete(Integer id) throws BookNotFoundException {
		Optional<BookEntity> book = repository.findById(id);
		if (book.isEmpty()) {
			throw new BookNotFoundException("Não existe livro com esse id!");
		}
		repository.deleteById(id);
	}

	public List<BookEntity> orderBy(String orderType) {
		return repository.findAll(Sort.by(orderType));
	}

}
