package urlshortener.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import urlshortener.dao.URLDao;
import urlshortener.entity.URL;
import urlshortener.service.URLService;

@Service
@Transactional
public class URLServiceImpl implements URLService {
	@Autowired
	private URLDao urlDao;
	
	@Override
	public URL save(URL url) {
		return urlDao.save(url);
	}

	@Override
	public void deleteById(Long urlId) {
		urlDao.deleteById(urlId);
	}

	@Override
	public List<URL> getAllURLs() {
		return urlDao.findAll();
	}

	@Override
	public URL getById(Long urlId) {
		if(urlDao.existsById(urlId)) {
			return urlDao.getOne(urlId);
		}
		return null;
	}

	@Override
	public URL getURLByShortURL(String shortURL) {
		return urlDao.findURLByShortURL(shortURL);
	}

	@Override
	public List<URL> getAllByUserId(Long userId) {
		return urlDao.findAllByUserId(userId);
	}
}
