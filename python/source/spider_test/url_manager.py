'''
Created on 2019年1月13日

@author: SuikaXhq
'''
import urllib3 as u
from bs4 import BeautifulSoup as bs

class URLManager:
    
    default_header = {}
    
    def __init__(self, timeout=2, user_agent='Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0', cookie='', proxy_url=''):
        
        self.default_header['User-Agent'] = user_agent
        
        if cookie!='':
            self.default_header['Cookie'] = cookie
        
        if proxy_url!='':
            self.manager = u.ProxyManager(proxy_url, headers=self.default_header, timeout=timeout)
        else:
            self.manager = u.PoolManager(headers=self.default_header, timeout=timeout)
            
    def get_html_soup(self, url):
        return bs(self.manager.request('GET', url).data, 'lxml')
    
    def request(self, method, url, headers={}):
        if headers != {}:
            return self.manager.request(method, url, headers=headers)
        else:
            return self.manager.request(method, url)
    
    def download(self, url, file_path):
        request = self.manager.request('GET', url)
        with open(file_path, mode='wb') as f:
            f.write(request.data)
            print('Created', file_path)