package com.readmoree.utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.readmoree.dto.BookResponseDto;

public class RequestUtils {

	public static  BookResponseDto requestBookService(Long bookId) {
		final String URl = "http://localhost:8080/book/public/details/"+bookId;
		BookResponseDto book=new BookResponseDto();
		try { 
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(URl))
					.GET()
					.build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			// Convert JSON to Java Object
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());

			if (response.statusCode() != 200) {
				System.err.println("Error: Received HTTP " + response.statusCode());
				System.err.println("Error: "+response.body());
				//return "Error: " + response.body(); // Return error response instead of parsing
			}
			book = (BookResponseDto) (objectMapper.readValue(response.body(), BookResponseDto.class));

		}catch (Exception e) {
			e.printStackTrace();
		}
		return book;
	}

}
