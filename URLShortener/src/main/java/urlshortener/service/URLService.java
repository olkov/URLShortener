package urlshortener.service;

import java.util.List;

import urlshortener.entity.URL;

public interface URLService {
	public URL save(URL url);

	public void deleteById(Long urlId);

	public List<URL> getAllURLs();

	public URL getById(Long id);

	public URL getURLByShortURL(String shortURL);
	
	public List<URL> getAllByUserId(Long userId);
}
